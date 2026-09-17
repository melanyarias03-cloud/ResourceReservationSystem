package report;


import model.entity.ResourceUsageEntry;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StatisticsReport {


    private final PdfReportGenerator pdfGenerator;

    public StatisticsReport() {
        this.pdfGenerator =
                new PdfReportGenerator();
    }

    public void generate(
            String fromDate,
            String toDate,
            int totalReservations,
            int totalResources,
            String mostUsedResource,
            String mostRequestedCategory,
            List<ResourceUsageEntry> entries)
            throws IOException {

        String[] headers = {
                "Resource",
                "Category",
                "Reservations"
        };

        List<String[]> rows =
                createRows(entries);

        // Add statistical summary
        rows.add(
                0,
                new String[]{
                        "Period",
                        fromDate + " to " + toDate,
                        ""
                }
        );

        rows.add(
                1,
                new String[]{
                        "Reservations",
                        String.valueOf(totalReservations),
                        ""
                }
        );

        rows.add(
                2,
                new String[]{
                        "Registered Resources",
                        String.valueOf(totalResources),
                        ""
                }
        );

        rows.add(
                3,
                new String[]{
                        "Most Used Resource",
                        mostUsedResource,
                        ""
                }
        );

        rows.add(
                4,
                new String[]{
                        "Most Requested Category",
                        mostRequestedCategory,
                        ""
                }
        );

        String fileName =
                pdfGenerator.generateFileName(
                        "Statistics_Report"
                );

        pdfGenerator.generateTableReport(
                fileName,
                "System Statistics",
                headers,
                rows
        );
    }


    private List<String[]> createRows(
            List<ResourceUsageEntry> entries) {

        List<String[]> rows =
                new ArrayList<>();

        for (ResourceUsageEntry entry :
                entries) {

            rows.add(
                    new String[]{
                            entry.getResource(),
                            entry.getCategory(),
                            String.valueOf(
                                    entry.getReservations()
                            )
                    }
            );
        }

        return rows;
    }
}
