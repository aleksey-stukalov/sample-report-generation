package com.company.sample.report.web.util_screens;

import com.company.sample.report.service.QRCodeService;
import com.haulmont.cuba.gui.WindowParam;
import com.haulmont.cuba.gui.components.AbstractWindow;
import com.haulmont.cuba.gui.components.Image;
import com.haulmont.cuba.gui.components.StreamResource;

import javax.inject.Inject;
import java.io.ByteArrayInputStream;
import java.util.Map;
import java.util.UUID;

public class QrCodeScreen extends AbstractWindow {
    @Inject
    private QRCodeService qrCodeService;

    @WindowParam
    private UUID orderId;

    @Inject
    private Image image;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);

        try {
            byte[] qr = qrCodeService.getQRCodeByteArray(orderId.toString());
            image.setScaleMode(Image.ScaleMode.CONTAIN);
            image.setSource(StreamResource.class)
                    .setStreamSupplier(() -> new ByteArrayInputStream(qr))
                    .setBufferSize(1024);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}