package report;

import model.entity.Resource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ResourceReport {

    private final PdfReportGenerator pdfGenerator;

    public ResourceReport() {
        this.pdfGenerator = new PdfReportGenerator();
    }

    public void generate(List<Resource> resources)
            throws IOException {

        String[] headers = {
                "Id",
                "Category",
                "Description"
        };

        List<String[]> rows =
                createRows(resources);

        String fileName =
                pdfGenerator.generateFileName(
                        "Resource_Report"
                );

        pdfGenerator.generateTableReport(
                fileName,
                "Resource List",
                headers,
                rows
        );
    }

    private List<String[]> createRows(
            List<Resource> resources) {

        List<String[]> rows =
                new ArrayList<>();

        for (Resource resource : resources) {

            rows.add(
                    new String[]{
                            resource.getId(),
                            resource.getCategory().getDescription(),
                            resource.getDescription()
                    }
            );
        }

        return rows;
    }

}
