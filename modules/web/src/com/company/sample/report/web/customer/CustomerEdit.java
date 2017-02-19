package com.company.sample.report.web.customer;

import com.company.sample.report.entity.Customer;
import com.haulmont.cuba.core.app.FileStorageService;
import com.haulmont.cuba.core.global.FileStorageException;
import com.haulmont.cuba.core.global.UuidProvider;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.haulmont.cuba.gui.components.Embedded;
import com.haulmont.cuba.gui.components.FileUploadField;
import org.slf4j.Logger;

import javax.inject.Inject;
import java.io.ByteArrayInputStream;
import java.util.Map;

public class CustomerEdit extends AbstractEditor<Customer> {

    @Inject
    private Embedded imgLogo;

    @Inject
    private FileUploadField customerUpload;

    @Inject
    private FileStorageService fileStorageService;

    @Inject
    private Logger log;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);

        customerUpload.addFileUploadSucceedListener(e ->
                imgLogo.setSource("img-" + UuidProvider.createUuid(), customerUpload.getFileContent()));
        customerUpload.addAfterValueClearListener(e -> imgLogo.resetSource());
    }

    @Override
    public void ready() {
        super.ready();

        Customer loadedItem = getItem();
        if (loadedItem != null && loadedItem.getLogo() != null) {
            try {
                imgLogo.setSource("img-" + UuidProvider.createUuid(),
                        new ByteArrayInputStream(fileStorageService.loadFile(loadedItem.getLogo())));
            } catch (FileStorageException e) {
                log.debug("Error showing the logo", e);
            }
        } else
            imgLogo.resetSource();
    }
}