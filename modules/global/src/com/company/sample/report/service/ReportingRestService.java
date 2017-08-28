package com.company.sample.report.service;


import java.util.UUID;

public interface ReportingRestService {
    String NAME = "reportgeneration_ReportingRestService";

    UUID generateInvoice(String orderId, String fileName);
}