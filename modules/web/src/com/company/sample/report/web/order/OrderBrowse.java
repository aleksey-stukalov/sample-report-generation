package com.company.sample.report.web.order;

import com.company.sample.report.entity.Order;
import com.company.sample.report.entity.OrderItem;
import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.core.global.UserSessionSource;
import com.haulmont.cuba.gui.ComponentsHelper;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.components.actions.CreateAction;
import com.haulmont.cuba.gui.components.actions.EditAction;
import com.haulmont.cuba.gui.components.actions.RemoveAction;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.data.DataSupplier;
import com.haulmont.cuba.gui.data.Datasource;
import com.haulmont.reports.gui.actions.TablePrintFormAction;

import javax.inject.Inject;
import javax.inject.Named;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

public class OrderBrowse extends AbstractLookup {

    /**
     * The {@link CollectionDatasource} instance that loads a list of {@link Order} records
     * to be displayed in {@link OrderBrowse#ordersTable} on the left
     */
    @Inject
    private CollectionDatasource<Order, UUID> ordersDs;

    /**
     * The {@link Datasource} instance that contains an instance of the selected entity
     * in {@link OrderBrowse#ordersDs}
     * <p/> Containing instance is loaded in {@link CollectionDatasource#addItemChangeListener}
     * with the view, specified in the XML screen descriptor.
     * The listener is set in the {@link OrderBrowse#init(Map)} method
     */
    @Inject
    private Datasource<Order> orderDs;

    /**
     * The {@link Table} instance, containing a list of {@link Order} records,
     * loaded via {@link OrderBrowse#ordersDs}
     */
    @Inject
    private Table<Order> ordersTable;

    /**
     * The {@link BoxLayout} instance that contains components on the left side
     * of {@link SplitPanel}
     */
    @Inject
    private BoxLayout lookupBox;

    /**
     * The {@link BoxLayout} instance that contains buttons to invoke Save or Cancel actions in edit mode
     */
    @Inject
    private BoxLayout actionsPane;

    /**
     * The {@link FieldGroup} instance that is linked to {@link OrderBrowse#orderDs}
     * and shows fields of the selected {@link Order} record
     */
    @Inject
    private FieldGroup fieldGroup;

    /**
     * The {@link TabSheet} instance, located on the right side of {@link SplitPanel}
     * <p/> First tab contains {@link OrderBrowse#fieldGroup}. Others tabs are designed to manipulate with
     * one-two-many related collections
     */
    @Inject
    private TabSheet tabSheet;

    /**
     * The {@link RemoveAction} instance, related to {@link OrderBrowse#ordersTable}
     */
    @Named("ordersTable.remove")
    private RemoveAction ordersTableRemove;

    @Inject
    private DataSupplier dataSupplier;

    /**
     * {@link Boolean} value, indicating if a new instance of {@link Order} is being created
     */
    private boolean creating;

    @Inject
    private CollectionDatasource<OrderItem, UUID> itemsDs;

    @Inject
    private Button reportButton;

    @Inject
    private UserSessionSource userSessionSource;

    @Override
    public void init(Map<String, Object> params) {

        userSessionSource.getUserSession().getUser().getLogin();

        /**
         * Adding {@link Datasource.ItemChangeListener} to {@link ordersDs}
         * The listener reloads the selected record with the specified view and sets it to {@link orderDs}
         */
        ordersDs.addItemChangeListener(e -> {
            if (e.getItem() != null) {
                Order reloadedItem = dataSupplier.reload(e.getDs().getItem(), orderDs.getView());
                orderDs.setItem(reloadedItem);
            }
        });

        /**
         * Adding {@link CreateAction} to {@link ordersTable}
         * The listener removes selection in {@link ordersTable}, sets a newly created item to {@link orderDs}
         * and enables controls for record editing
         */
        ordersTable.addAction(new CreateAction(ordersTable) {
            @Override
            protected void internalOpenEditor(CollectionDatasource datasource, Entity newItem, Datasource parentDs, Map<String, Object> params) {
                ordersTable.setSelected(Collections.emptyList());
                orderDs.setItem((Order) newItem);
                enableEditControls(true);
            }
        });

        /**
         * Adding {@link EditAction} to {@link ordersTable}
         * The listener enables controls for record editing
         */
        ordersTable.addAction(new EditAction(ordersTable) {
            @Override
            protected void internalOpenEditor(CollectionDatasource datasource, Entity existingItem, Datasource parentDs, Map<String, Object> params) {
                if (ordersTable.getSelected().size() == 1) {
                    enableEditControls(false);
                }
            }
        });

        /**
         * Setting {@link RemoveAction#afterRemoveHandler} for {@link ordersTableRemove}
         * to reset record, contained in {@link orderDs}
         */
        ordersTableRemove.setAfterRemoveHandler(removedItems -> orderDs.setItem(null));

        disableEditControls();

        //----------------------STANDARD SCREEN SCRIPT ENDS--------------------------//


        /**
         * Calculating total price for the order
         */
        itemsDs.addCollectionChangeListener(e -> {
            if (orderDs.getItem() == null)
                return;

            BigDecimal totalPrice = itemsDs.getItems().stream()
                    .map(OrderItem::getSubTotal)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            orderDs.getItem().setTotalPrice(totalPrice);
        });

        LookupPickerField field = (LookupPickerField) fieldGroup.getFieldComponent("customer");
        field.setRefreshOptionsOnLookupClose(true);

        /**
         * Adding action to be called when the report button is clicked
         */
        TablePrintFormAction action = new TablePrintFormAction("printInvoiceAction", ordersTable);
        reportButton.setAction(action);
        ordersTable.addAction(action);
    }

    /**
     * Method that is invoked by clicking Save button after editing an existing or creating a new record
     */
    public void save() {
        getDsContext().commit();

        Order editedItem = orderDs.getItem();
        if (creating) {
            ordersDs.includeItem(editedItem);
        } else {
            ordersDs.updateItem(editedItem);
        }
        ordersTable.setSelected(editedItem);

        disableEditControls();
    }

    /**
     * Method that is invoked by clicking Save button after editing an existing or creating a new record
     */
    public void cancel() {
        Order selectedItem = ordersDs.getItem();
        if (selectedItem != null) {
            Order reloadedItem = dataSupplier.reload(selectedItem, orderDs.getView());
            ordersDs.setItem(reloadedItem);
        } else {
            orderDs.setItem(null);
        }

        disableEditControls();
    }

    /**
     * Enabling controls for record editing
     * @param creating indicates if a new instance of {@link Order} is being created
     */
    private void enableEditControls(boolean creating) {
        this.creating = creating;
        initEditComponents(true);
        fieldGroup.requestFocus();
    }

    /**
     * Disabling editing controls
     */
    private void disableEditControls() {
        initEditComponents(false);
        ordersTable.requestFocus();
    }

    /**
     * Initiating edit controls, depending on if they should be enabled/disabled
     * @param enabled if true - enables editing controls and disables controls on the left side of the splitter
     *                if false - visa versa
     */
    private void initEditComponents(boolean enabled) {
        ComponentsHelper.walkComponents(tabSheet, (component, name) -> {
            if (component instanceof FieldGroup) {
                ((FieldGroup) component).setEditable(enabled);
            } else if (component instanceof Table) {
                ((Table) component).getActions().forEach(action -> action.setEnabled(enabled));
            }
        });
        actionsPane.setEnabled(enabled);
        lookupBox.setEnabled(!enabled);
    }
}