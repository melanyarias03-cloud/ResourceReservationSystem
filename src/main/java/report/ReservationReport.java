package report;

import model.entity.Reservation;
import model.entity.Resource;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class ReservationReport {

    private final PdfReportGenerator pdfGenerator;

    private final DateTimeFormatter dateFormatter =
            DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private final DateTimeFormatter timeFormatter =
            DateTimeFormatter.ofPattern("HH:mm");

    public ReservationReport() {
        this.pdfGenerator =
                new PdfReportGenerator();
    }

    public void generate(
            List<Reservation> reservations)
            throws IOException {

        String[] headers = {
                "Id",
                "Employee",
                "Activity",
                "Date",
                "Start",
                "End",
                "Resources"
        };

        List<String[]> rows =
                createRows(reservations);

        String fileName =
                pdfGenerator.generateFileName(
                        "Reservation_Report"
                );

        pdfGenerator.generateTableReport(
                fileName,
                "Reservation List",
                headers,
                rows
        );
    }

    private List<String[]> createRows(
            List<Reservation> reservations) {

        List<String[]> rows =
                new ArrayList<>();

        for (Reservation reservation : reservations) {

            rows.add(
                    new String[]{
                            reservation.getId(),
                            reservation.getEmployee().getName(),
                            reservation.getActivity(),
                            reservation.getDate().format(dateFormatter),
                            reservation.getStartTime().format(timeFormatter),
                            reservation.getEndTime().format(timeFormatter),
                            getResourcesText(reservation)
                    }
            );
        }

        return rows;
    }

    private String getResourcesText(
            Reservation reservation) {

        StringBuilder text =
                new StringBuilder();

        for (Resource resource :
                reservation.getResources()) {

            if (text.length() > 0) {
                text.append(", ");
            }

            text.append(
                    resource.getDescription()
            );
        }

        return text.toString();
    }

}
