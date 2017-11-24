# sample-report-generation
The sample shows how to use reporting add-on in the CUBA Platform (requires premium package). 

#### Programmatic Report generation and REST API
The application provides [ReportingRestService](https://github.com/aleksey-stukalov/sample-report-generation/blob/402aed80d3783f47c524b067414d0ee429d98392/modules/global/src/com/company/sample/report/service/ReportingRestService.java),
which introduces the ```generateInvoice``` method, implemented in [ReportingRestServiceBean](https://github.com/aleksey-stukalov/sample-report-generation/blob/402aed80d3783f47c524b067414d0ee429d98392/modules/core/src/com/company/sample/report/service/ReportingRestServiceBean.java).
The method is exposed as a REST method via [rest-services.xml](https://github.com/aleksey-stukalov/sample-report-generation/blob/402aed80d3783f47c524b067414d0ee429d98392/modules/web/src/com/company/sample/report/rest-services.xml).

The method programmatically generates the report with the code field equals to _INVOICE_PDF_. The report gets an instance of the Order entity as 
a parameter. The resulting report is stored in the File Storage and can be accessed later by the returned Id of its File Descriptor.

Useful links:
- [Services Configuration](https://doc.cuba-platform.com/manual-6.6/rest_api_v2_services_config.html)
- [Getting an OAuth Token](https://doc.cuba-platform.com/manual-6.6/rest_api_v2_ex_get_token.html)
- [Service Method Invocation (GET)](https://doc.cuba-platform.com/manual-6.6/rest_api_v2_ex_service_get.html)
- [Files Downloading](https://doc.cuba-platform.com/manual-6.6/rest_api_v2_ex_file_download.html)

#### Using from Polymer client
The Polymer client module contains a showcase of how to invoke ReportingRestService to generate a report and how to provide a link for downloading the result from the File Storage. 
See [reportgeneration-generate-invoice-form.html](https://github.com/aleksey-stukalov/sample-report-generation/blob/master/modules/polymer-client/src/report/reportgeneration-generate-invoice-form.html) component.

#### QR Code support
The application uses the [zxing](https://github.com/zxing/zxing) library to generate barcodes and QR codes. The application provides the [service](https://github.com/aleksey-stukalov/sample-report-generation/blob/fd442bb73dd6f29552c7da1713444fc2d59cd23e/modules/core/src/com/company/sample/report/service/QRCodeServiceBean.java) to generate QR codes. The _[DOCX2PDF] Invoice report_ is using this service to include a QR code into the report.
