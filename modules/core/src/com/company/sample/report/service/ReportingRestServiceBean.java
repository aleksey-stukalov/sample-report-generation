package com.company.sample.report.service;

import com.company.sample.report.entity.Order;
import com.haulmont.bali.util.ParamsMap;
import com.haulmont.cuba.core.app.DataService;
import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.reports.app.service.ReportService;
import com.haulmont.reports.entity.Report;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Map;
import java.util.UUID;

@Service(ReportingRestService.NAME)
public class ReportingRestServiceBean implements ReportingRestService {

    private static final String INVOICE_REPORT_CODE = "INVOICE_PDF";

    @Inject
    private ReportService reportService;

    @Inject
    private DataService dataService;

    @Override
    public UUID generateInvoice(String orderId, String fileName) {
        Report report = dataService.load(
            LoadContext.create(Report.class).setQuery(
                    LoadContext.createQuery("select e from report$Report e where e.code = :reportCode")
                        .setParameter("reportCode", INVOICE_REPORT_CODE)
            )
        );

        Order order = dataService.load(LoadContext.create(Order.class).setId(UUID.fromString(orderId)));
        Map<String, Object> params = ParamsMap.of("parameter_order", order);

        FileDescriptor reportFileDescriptor = reportService.createAndSaveReport(report, params, fileName);

        return reportFileDescriptor.getId();
    }
}