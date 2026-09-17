package view;

import model.entity.ResourceUsageEntry;
import java.util.Map;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;



public class StatisticsView extends JFrame {

    private JTextField txtFromDate;
    private JTextField txtToDate;

    private JLabel lblTotalReservations;
    private JLabel lblTotalResources;
    private JLabel lblMostUsedResource;
    private JLabel lblMostRequestedCategory;

    private JTable tblResourceUsage;
    private DefaultTableModel tableModel;

    private JButton btnGenerateStatistics;
    private JButton btnGeneratePdf;
    private JButton btnBack;
    private BarChartPanel resourceChart;
    private BarChartPanel categoryChart;


    public StatisticsView() {

        setTitle("System Statistics");
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        initializeComponents();
    }


    private void initializeComponents() {

        JPanel mainPanel =
                new JPanel();

        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.WHITE);


        // =====================================
        // Title
        // =====================================

        JLabel lblTitle =
                new JLabel("SYSTEM STATISTICS");

        lblTitle.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        22
                )
        );

        lblTitle.setBounds(
                330,
                20,
                300,
                30
        );


        // =====================================
        // Period
        // =====================================

        JLabel lblFrom =
                new JLabel("From:");

        lblFrom.setBounds(
                170,
                75,
                50,
                25
        );

        txtFromDate =
                new JTextField();

        txtFromDate.setBounds(
                220,
                75,
                130,
                30
        );

        JLabel lblFromFormat =
                new JLabel("DD-MM-YYYY");

        lblFromFormat.setBounds(
                220,
                105,
                120,
                20
        );


        JLabel lblTo =
                new JLabel("To:");

        lblTo.setBounds(
                390,
                75,
                30,
                25
        );

        txtToDate =
                new JTextField();

        txtToDate.setBounds(
                420,
                75,
                130,
                30
        );

        JLabel lblToFormat =
                new JLabel("DD-MM-YYYY");

        lblToFormat.setBounds(
                420,
                105,
                120,
                20
        );


        btnGenerateStatistics =
                new JButton(
                        "Generate Statistics"
                );

        btnGenerateStatistics.setBounds(
                585,
                75,
                170,
                30
        );


        // =====================================
        // Total reservations
        // =====================================

        JLabel lblReservationsTitle =
                new JLabel(
                        "Reservations in Period:"
                );

        lblReservationsTitle.setBounds(
                60,
                150,
                180,
                25
        );

        lblTotalReservations =
                new JLabel("0");

        lblTotalReservations.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        16
                )
        );

        lblTotalReservations.setBounds(
                240,
                150,
                100,
                25
        );


        // =====================================
        // Total resources
        // =====================================

        JLabel lblResourcesTitle =
                new JLabel(
                        "Total Registered Resources:"
                );

        lblResourcesTitle.setBounds(
                470,
                150,
                200,
                25
        );

        lblTotalResources =
                new JLabel("0");

        lblTotalResources.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        16
                )
        );

        lblTotalResources.setBounds(
                680,
                150,
                100,
                25
        );


        // =====================================
        // Most used resource
        // =====================================

        JLabel lblResourceTitle =
                new JLabel(
                        "Most Used Resource:"
                );

        lblResourceTitle.setBounds(
                60,
                195,
                170,
                25
        );

        lblMostUsedResource =
                new JLabel("-");

        lblMostUsedResource.setBounds(
                230,
                195,
                200,
                25
        );


        // =====================================
        // Most requested category
        // =====================================

        JLabel lblCategoryTitle =
                new JLabel(
                        "Most Requested Category:"
                );

        lblCategoryTitle.setBounds(
                470,
                195,
                190,
                25
        );

        lblMostRequestedCategory =
                new JLabel("-");

        lblMostRequestedCategory.setBounds(
                660,
                195,
                180,
                25
        );


        // =====================================
        // Resource usage table
        // =====================================

        JLabel lblTableTitle =
                new JLabel("Resource Usage");

        lblTableTitle.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        16
                )
        );

        lblTableTitle.setBounds(
                60,
                245,
                200,
                25
        );


        tableModel =
                new DefaultTableModel(
                        new Object[]{
                                "Resource",
                                "Category",
                                "Reservations"
                        },
                        0
                ) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column) {

                        return false;
                    }
                };


        tblResourceUsage =
                new JTable(
                        tableModel
                );

        JScrollPane scrollPane =
                new JScrollPane(
                        tblResourceUsage
                );

        scrollPane.setBounds(
                60,
                280,
                770,
                230
        );

        JLabel lblResourceChart =
                new JLabel("Resource Usage Chart");

        lblResourceChart.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        14
                )
        );

        lblResourceChart.setBounds(
                60,
                525,
                200,
                25
        );


        resourceChart =
                new BarChartPanel();

        resourceChart.setBounds(
                60,
                555,
                400,
                150
        );


        JLabel lblCategoryChart =
                new JLabel("Category Usage Chart");

        lblCategoryChart.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        14
                )
        );

        lblCategoryChart.setBounds(
                520,
                525,
                200,
                25
        );


        categoryChart =
                new BarChartPanel();

        categoryChart.setBounds(
                520,
                555,
                400,
                150
        );


        // =====================================
        // Buttons
        // =====================================

        btnGeneratePdf =
                new JButton(
                        "Generate PDF"
                );

        btnGeneratePdf.setBounds(
                295,
                540,
                140,
                35
        );

        btnGeneratePdf.setBounds(
                350,
                720,
                140,
                35
        );

        btnBack.setBounds(
                510,
                720,
                120,
                35
        );
        btnBack =
                new JButton("Back");

        btnBack.setBounds(
                465,
                540,
                120,
                35
        );


        // =====================================
        // Add components
        // =====================================

        mainPanel.add(lblTitle);
        mainPanel.add(lblResourceChart);
        mainPanel.add(resourceChart);

        mainPanel.add(lblCategoryChart);
        mainPanel.add(categoryChart);
        mainPanel.add(lblFrom);
        mainPanel.add(txtFromDate);
        mainPanel.add(lblFromFormat);

        mainPanel.add(lblTo);
        mainPanel.add(txtToDate);
        mainPanel.add(lblToFormat);

        mainPanel.add(
                btnGenerateStatistics
        );

        mainPanel.add(
                lblReservationsTitle
        );

        mainPanel.add(
                lblTotalReservations
        );

        mainPanel.add(
                lblResourcesTitle
        );

        mainPanel.add(
                lblTotalResources
        );

        mainPanel.add(
                lblResourceTitle
        );

        mainPanel.add(
                lblMostUsedResource
        );

        mainPanel.add(
                lblCategoryTitle
        );

        mainPanel.add(
                lblMostRequestedCategory
        );

        mainPanel.add(lblTableTitle);
        mainPanel.add(scrollPane);

        mainPanel.add(btnGeneratePdf);
        mainPanel.add(btnBack);

        add(mainPanel);
    }


    // =========================================
    // Input
    // =========================================

    public String getFromDate() {

        return txtFromDate
                .getText()
                .trim();
    }


    public String getToDate() {

        return txtToDate
                .getText()
                .trim();
    }


    // =========================================
    // Statistics
    // =========================================

    public void setTotalReservations(
            int total) {

        lblTotalReservations.setText(
                String.valueOf(total)
        );
    }


    public void setTotalResources(
            int total) {

        lblTotalResources.setText(
                String.valueOf(total)
        );
    }


    public void setMostUsedResource(
            String resource) {

        lblMostUsedResource.setText(
                resource
        );
    }


    public void setMostRequestedCategory(
            String category) {

        lblMostRequestedCategory.setText(
                category
        );
    }


    // =========================================
    // Resource usage
    // =========================================

    public void showResourceUsage(
            List<ResourceUsageEntry> entries) {

        tableModel.setRowCount(0);

        for (ResourceUsageEntry entry :
                entries) {

            tableModel.addRow(
                    new Object[]{
                            entry.getResource(),
                            entry.getCategory(),
                            entry.getReservations()
                    }
            );
        }
    }


    public void clearResourceUsage() {

        tableModel.setRowCount(0);
    }


    // =========================================
    // Messages
    // =========================================

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


    // =========================================
    // Actions
    // =========================================

    public void setGenerateStatisticsAction(
            ActionListener action) {

        btnGenerateStatistics
                .addActionListener(
                        action
                );
    }


    public void setGeneratePdfAction(
            ActionListener action) {

        btnGeneratePdf
                .addActionListener(
                        action
                );
    }


    public void setBackAction(
            ActionListener action) {

        btnBack.addActionListener(
                action
        );
    }

    public void showResourceChart(
            Map<String, Integer> data) {

        resourceChart.setData(data);
    }


    public void showCategoryChart(
            Map<String, Integer> data) {

        categoryChart.setData(data);
    }
}