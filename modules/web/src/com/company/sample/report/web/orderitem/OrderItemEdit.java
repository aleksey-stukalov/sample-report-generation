package com.company.sample.report.web.orderitem;

import com.haulmont.cuba.gui.components.AbstractEditor;
import com.company.sample.report.entity.OrderItem;

import java.math.BigDecimal;

public class OrderItemEdit extends AbstractEditor<OrderItem> {

    @Override
    protected boolean preCommit() {
        BigDecimal subTotal = getItem().getProduct().getPrice().multiply(BigDecimal.valueOf(getItem().getQuantity()));
        getItem().setSubTotal(subTotal);
        return super.preCommit();
    }
}