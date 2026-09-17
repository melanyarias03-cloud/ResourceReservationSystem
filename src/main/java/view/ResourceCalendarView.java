package view;

import model.entity.Category;
import model.entity.Resource;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class ResourceCalendarView
        extends JFrame {

    private JComboBox<Category> cmbCategories;

    private JTextField txtDate;

    private JButton btnSearch;
    private JButton btnGeneratePdf;
    private JButton btnBack;

    private JTable tblCalendar;

    private DefaultTableModel tableModel;

    private final DateTimeFormatter timeFormatter =
            DateTimeFormatter.ofPattern("HH:mm");


    public ResourceCalendarView() {

        setTitle("Resource Calendar");

        setSize(
                1000,
                650
        );

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        setLocationRelativeTo(null);

        setResizable(false);

        initializeComponents();
    }


    private void initializeComponents() {

        JPanel mainPanel =
                new JPanel();

        mainPanel.setLayout(null);

        mainPanel.setBackground(
                Color.WHITE
        );


        // ============================
        // Title
        // ============================

        JLabel lblTitle =
                new JLabel(
                        "RESOURCE CALENDAR"
                );

        lblTitle.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        22
                )
        );

        lblTitle.setBounds(
                365,
                25,
                280,
                30
        );


        // ============================
        // Category
        // ============================

        JLabel lblCategory =
                new JLabel(
                        "Category:"
                );

        lblCategory.setBounds(
                100,
                85,
                80,
                25
        );


        cmbCategories =
                new JComboBox<>();

        cmbCategories.setBounds(
                180,
                85,
                220,
                30
        );


        // ============================
        // Date
        // ============================

        JLabel lblDate =
                new JLabel("Date:");

        lblDate.setBounds(
                440,
                85,
                50,
                25
        );


        txtDate =
                new JTextField();

        txtDate.setBounds(
                490,
                85,
                130,
                30
        );


        JLabel lblFormat =
                new JLabel(
                        "DD-MM-YYYY"
                );

        lblFormat.setBounds(
                490,
                115,
                120,
                20
        );


        // ============================
        // Search
        // ============================

        btnSearch =
                new JButton(
                        "Show Calendar"
                );

        btnSearch.setBounds(
                660,
                85,
                150,
                30
        );


        // ============================
        // Calendar table
        // ============================

        tableModel =
                new DefaultTableModel();

        tblCalendar =
                new JTable(
                        tableModel
                );

        tblCalendar.setAutoResizeMode(
                JTable.AUTO_RESIZE_OFF
        );

        tblCalendar.setRowHeight(28);


        JScrollPane scrollPane =
                new JScrollPane(
                        tblCalendar
                );

        scrollPane.setBounds(
                50,
                155,
                880,
                370
        );


        // ============================
        // Buttons
        // ============================

        btnGeneratePdf =
                new JButton(
                        "Generate PDF"
                );

        btnGeneratePdf.setBounds(
                340,
                550,
                140,
                35
        );


        btnBack =
                new JButton(
                        "Back"
                );

        btnBack.setBounds(
                520,
                550,
                120,
                35
        );


        // ============================
        // Add components
        // ============================

        mainPanel.add(lblTitle);

        mainPanel.add(lblCategory);

        mainPanel.add(
                cmbCategories
        );

        mainPanel.add(lblDate);

        mainPanel.add(
                txtDate
        );

        mainPanel.add(
                lblFormat
        );

        mainPanel.add(
                btnSearch
        );

        mainPanel.add(
                scrollPane
        );

        mainPanel.add(
                btnGeneratePdf
        );

        mainPanel.add(
                btnBack
        );

        add(mainPanel);
    }


    // ============================
    // Input
    // ============================

    public Category getSelectedCategory() {

        return (Category)
                cmbCategories
                        .getSelectedItem();
    }


    public String getDateInput() {

        return txtDate
                .getText()
                .trim();
    }


    // ============================
    // Categories
    // ============================

    public void showCategories(
            List<Category> categories) {

        cmbCategories
                .removeAllItems();

        for (Category category :
                categories) {

            cmbCategories.addItem(
                    category
            );
        }
    }


    // ============================
    // Matrix
    // ============================

    public void showCalendarMatrix(
            List<Resource> resources,
            List<LocalTime> hours,
            Map<String, Map<LocalTime, String>> matrix) {

        // Columns

        String[] columns =
                new String[
                        resources.size() + 1
                        ];

        columns[0] =
                "Hour";

        for (int i = 0;
             i < resources.size();
             i++) {

            columns[i + 1] =
                    resources
                            .get(i)
                            .getDescription();
        }


        tableModel.setColumnIdentifiers(
                columns
        );

        tableModel.setRowCount(0);


        // Rows

        for (LocalTime hour :
                hours) {

            Object[] row =
                    new Object[
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

            tableModel.addRow(
                    row
            );
        }


        // Column width

        tblCalendar
                .getColumnModel()
                .getColumn(0)
                .setPreferredWidth(80);

        for (int i = 1;
             i < tblCalendar
                     .getColumnCount();
             i++) {

            tblCalendar
                    .getColumnModel()
                    .getColumn(i)
                    .setPreferredWidth(170);
        }
    }


    public void clearCalendar() {

        tableModel.setRowCount(0);
        tableModel.setColumnCount(0);
    }


    // ============================
    // Messages
    // ============================

    public void showMessage(
            String message) {

        JOptionPane.showMessageDialog(
                this,
                message
        );
    }


    public void showError(
            String message) {

        JOptionPane.showMessageDialog(
                this,
                message,
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }


    // ============================
    // Actions
    // ============================

    public void setSearchAction(
            ActionListener action) {

        btnSearch.addActionListener(
                action
        );
    }


    public void setGeneratePdfAction(
            ActionListener action) {

        btnGeneratePdf.addActionListener(
                action
        );
    }


    public void setBackAction(
            ActionListener action) {

        btnBack.addActionListener(
                action
        );
    }
}