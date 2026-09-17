package report;

import model.entity.Employee;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeReport {

    private final PdfReportGenerator pdfGenerator;

    public EmployeeReport() {

        this.pdfGenerator =
                new PdfReportGenerator();
    }


    public void generate(
            List<Employee> employees)
            throws IOException {

        String[] headers = {
                "Id",
                "Name",
                "Phone"
        };

        List<String[]> rows =
                createRows(employees);

        String fileName =
                pdfGenerator.generateFileName(
                        "Employee_Report"
                );

        pdfGenerator.generateTableReport(
                fileName,
                "Employee List",
                headers,
                rows
        );
    }


    private List<String[]> createRows(
            List<Employee> employees) {

        List<String[]> rows =
                new ArrayList<>();

        for (Employee employee : employees) {

            rows.add(
                    new String[]{
                            employee.getUser().getId(),
                            employee.getName(),
                            employee.getPhone()
                    }
            );
        }

        return rows;
    }

}
