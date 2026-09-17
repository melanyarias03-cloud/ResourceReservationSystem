package report;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PdfReportGenerator {

    private static final float MARGIN = 50;
    private static final float ROW_HEIGHT = 22;
    private static final float TITLE_FONT_SIZE = 18;
    private static final float HEADER_FONT_SIZE = 11;
    private static final float DATA_FONT_SIZE = 10;


    public void generateTableReport(
            String filePath,
            String title,
            String[] headers,
            List<String[]> rows)
            throws IOException {

        File pdfFile =
                new File(filePath)
                        .getAbsoluteFile();

        try (PDDocument document =
                     new PDDocument()) {

            PDPage page =
                    new PDPage(
                            PDRectangle.A4
                    );

            document.addPage(page);

            try (PDPageContentStream contentStream =
                         new PDPageContentStream(
                                 document,
                                 page
                         )) {

                drawTitle(
                        contentStream,
                        title
                );

                drawTable(
                        contentStream,
                        page,
                        headers,
                        rows
                );
            }

            document.save(pdfFile);
        }

        openPdf(pdfFile);
    }


    private void drawTitle(
            PDPageContentStream contentStream,
            String title)
            throws IOException {

        PDType1Font titleFont =
                new PDType1Font(
                        Standard14Fonts.FontName.HELVETICA_BOLD
                );

        contentStream.beginText();

        contentStream.setFont(
                titleFont,
                TITLE_FONT_SIZE
        );

        contentStream.newLineAtOffset(
                MARGIN,
                790
        );

        contentStream.showText(
                title
        );

        contentStream.endText();
    }


    private void drawTable(
            PDPageContentStream contentStream,
            PDPage page,
            String[] headers,
            List<String[]> rows)
            throws IOException {

        float tableWidth =
                page.getMediaBox().getWidth()
                        - (2 * MARGIN);

        float columnWidth =
                tableWidth / headers.length;

        float y =
                750;

        PDType1Font headerFont =
                new PDType1Font(
                        Standard14Fonts.FontName.HELVETICA_BOLD
                );

        PDType1Font dataFont =
                new PDType1Font(
                        Standard14Fonts.FontName.HELVETICA
                );


        // Draw table header
        drawRow(
                contentStream,
                headers,
                MARGIN,
                y,
                columnWidth,
                headerFont,
                HEADER_FONT_SIZE
        );

        y -= ROW_HEIGHT;


        // Draw table data
        for (String[] row : rows) {

            drawRow(
                    contentStream,
                    row,
                    MARGIN,
                    y,
                    columnWidth,
                    dataFont,
                    DATA_FONT_SIZE
            );

            y -= ROW_HEIGHT;
        }
    }


    private void drawRow(
            PDPageContentStream contentStream,
            String[] values,
            float x,
            float y,
            float columnWidth,
            PDType1Font font,
            float fontSize)
            throws IOException {

        for (int i = 0;
             i < values.length;
             i++) {

            float columnX =
                    x + (i * columnWidth);

            // Draw cell
            contentStream.addRect(
                    columnX,
                    y - ROW_HEIGHT,
                    columnWidth,
                    ROW_HEIGHT
            );

            contentStream.stroke();

            // Draw text
            contentStream.beginText();

            contentStream.setFont(
                    font,
                    fontSize
            );

            contentStream.newLineAtOffset(
                    columnX + 5,
                    y - 15
            );

            String value =
                    values[i] == null
                            ? ""
                            : values[i];

            contentStream.showText(
                    value
            );

            contentStream.endText();
        }
    }

    private void openPdf(
            File file)
            throws IOException {

        if (!file.exists()) {

            throw new IOException(
                    "The PDF file was not found."
            );
        }

        if (!Desktop.isDesktopSupported()) {

            throw new IOException(
                    "Desktop operations are not supported."
            );
        }

        Desktop desktop =
                Desktop.getDesktop();

        if (!desktop.isSupported(
                Desktop.Action.OPEN)) {

            throw new IOException(
                    "Opening PDF files is not supported."
            );
        }

        desktop.open(file);
    }

    public String generateFileName(
            String reportName) {

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern(
                        "yyyyMMdd_HHmmss"
                );

        String timestamp =
                LocalDateTime.now()
                        .format(formatter);

        return reportName
                + "_"
                + timestamp
                + ".pdf";
    }

}
