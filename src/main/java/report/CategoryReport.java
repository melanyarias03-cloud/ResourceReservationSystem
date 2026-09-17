package report;

import model.entity.Category;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class CategoryReport {

    private final PdfReportGenerator pdfGenerator;


    public CategoryReport() {

        this.pdfGenerator =
                new PdfReportGenerator();
    }


    public void generate(
            List<Category> categories)
            throws IOException {

        String[] headers = {
                "Id",
                "Description"
        };

        List<String[]> rows =
                createRows(categories);

        String fileName =
                pdfGenerator.generateFileName(
                        "Category_Report"
                );

        pdfGenerator.generateTableReport(
                fileName,
                "Category List",
                headers,
                rows
        );
    }


    private List<String[]> createRows(
            List<Category> categories) {

        List<String[]> rows =
                new ArrayList<>();

        for (Category category : categories) {

            rows.add(
                    new String[]{
                            category.getId(),
                            category.getDescription()
                    }
            );
        }

        return rows;
    }




}
