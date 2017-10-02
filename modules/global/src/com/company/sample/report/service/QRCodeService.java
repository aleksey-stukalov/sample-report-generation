package com.company.sample.report.service;


import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.global.FileStorageException;

import java.io.IOException;

public interface QRCodeService {
    String NAME = "reportgeneration_QRCodeService";

    byte[] getQRCodeByteArray(String content) throws IOException;
    FileDescriptor getQRCode(String content) throws IOException, FileStorageException;
}