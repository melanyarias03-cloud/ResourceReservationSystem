package report;

import model.entity.Category;
import model.entity.Resource;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ResourceCalendarReport {

    private final PdfReportGenerator pdfGenerator;

    private final DateTimeFormatter timeFormatter =
            DateTimeFormatter.ofPattern("HH:mm");


    public ResourceCalendarReport() {

        this.pdfGenerator =
                new PdfReportGenerator();
    }


    public void generate(
            Category category,
            String date,
            List<Resource> resources,
            List<LocalTime> hours,
            Map<String, Map<LocalTime, String>> matrix)
            throws IOException {

        String[] headers =
                createHeaders(resources);

        List<String[]> rows =
                createRows(
                        resources,
                        hours,
                        matrix
                );

        String fileName =
                pdfGenerator.generateFileName(
                        "Resource_Calendar_Report"
                );

        String title =
                "Resource Calendar - "
                        + category.getDescription()
                        + " - "
                        + date;

        pdfGenerator.generateTableReport(
                fileName,
                title,
                headers,
                rows
        );
    }


    private String[] createHeaders(
            List<Resource> resources) {

        String[] headers =
                new String[
                        resources.size() + 1
                        ];

        headers[0] = "Hour";

        for (int i = 0;
             i < resources.size();
             i++) {

            headers[i + 1] =
                    resources
                            .get(i)
                            .getDescription();
        }

        return headers;
    }


    private List<String[]> createRows(
            List<Resource> resources,
            List<LocalTime> hours,
            Map<String, Map<LocalTime, String>> matrix) {

        List<String[]> rows =
                new ArrayList<>();

        for (LocalTime hour : hours) {

            String[] row =
                    new String[
                            resources.size() + 1
                            ];

            row[0] =
                    hour.format(
                            timeFormatter
                    );

            for (int i = 0;
                 i < resources.size();
                 i++) {

                Resource resource =
                        resources.get(i);

                row[i + 1] =
                        matrix
                                .get(
                                        resource.getId()
                                )
                                .get(hour);
            }

            rows.add(row);
        }

        return rows;
    }
}