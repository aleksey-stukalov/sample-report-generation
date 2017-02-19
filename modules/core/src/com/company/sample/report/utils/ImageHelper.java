package com.company.sample.report.utils;

import com.haulmont.cuba.core.app.FileStorageAPI;
import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.FileStorageException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by aleksey on 18/02/2017.
 */

public class ImageHelper {

    public static byte[] fitImageInRect(FileDescriptor imageFileDescriptor, int width, int height) throws FileStorageException, IOException {
        FileStorageAPI api = AppBeans.get(FileStorageAPI.class);
        byte[] imageBytes = api.loadFile(imageFileDescriptor);
        BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageBytes));

        int originalWidth = image.getWidth();
        int originalHeight = image.getHeight();

        double widthScaleFactor = (double) width / (double) originalWidth;
        double heightScaleFactor = (double) height / (double) originalHeight;
        double scaleFactor = Math.min(widthScaleFactor, heightScaleFactor);

        int scaledWidth = (int) Math.round(originalWidth * scaleFactor);
        int scaledHeight = (int) Math.round(originalHeight * scaleFactor);

        BufferedImage scaledImage = new BufferedImage(scaledWidth, scaledHeight, image.getType());
        Graphics2D g = scaledImage.createGraphics();
        g.drawImage(image, 0, 0, scaledWidth, scaledHeight, null);
        g.dispose();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(scaledImage, imageFileDescriptor.getExtension(), outputStream);
        byte[] result = outputStream.toByteArray();
        outputStream.close();

        return result;
    }
}
