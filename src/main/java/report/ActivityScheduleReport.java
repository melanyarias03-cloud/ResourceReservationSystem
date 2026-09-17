package report;

import model.entity.ActivityScheduleEntry;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ActivityScheduleReport {

    private final PdfReportGenerator pdfGenerator;

    public ActivityScheduleReport() {
        this.pdfGenerator =
                new PdfReportGenerator();
    }

    public void generate(
            List<ActivityScheduleEntry> entries)
            throws IOException {

        String[] headers = {
                "Date",
                "Day",
                "Start",
                "End",
                "Activity",
                "Employee",
                "Resources"
        };

        List<String[]> rows =
                createRows(entries);

        String fileName =
                pdfGenerator.generateFileName(
                        "Activity_Schedule_Report"
                );

        pdfGenerator.generateTableReport(
                fileName,
                "Weekly Activity Schedule",
                headers,
                rows
        );
    }

    private List<String[]> createRows(
            List<ActivityScheduleEntry> entries) {

        List<String[]> rows =
                new ArrayList<>();

        for (ActivityScheduleEntry entry :
                entries) {

            rows.add(
                    new String[]{
                            entry.getDate(),
                            entry.getDay(),
                            entry.getStartTime(),
                            entry.getEndTime(),
                            entry.getActivity(),
                            entry.getEmployee(),
                            entry.getResources()
                    }
            );
        }

        return rows;
    }
}
