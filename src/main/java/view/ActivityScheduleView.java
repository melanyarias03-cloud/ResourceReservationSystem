package view;

import model.entity.ActivityScheduleEntry;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class ActivityScheduleView extends JFrame{

    private JTextField txtDate;

    private JButton btnSearch;
    private JButton btnGeneratePdf;
    private JButton btnBack;

    private JTable tblSchedule;
    private DefaultTableModel tableModel;


    public ActivityScheduleView() {

        setTitle("Weekly Activity Schedule");
        setSize(950, 520);
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


        // Title

        JLabel lblTitle =
                new JLabel(
                        "WEEKLY ACTIVITY SCHEDULE"
                );

        lblTitle.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        22
                )
        );

        lblTitle.setBounds(
                310,
                25,
                350,
                30
        );


        // Date

        JLabel lblDate =
                new JLabel("Date:");

        lblDate.setBounds(
                250,
                85,
                50,
                25
        );

        txtDate =
                new JTextField();

        txtDate.setBounds(
                300,
                85,
                140,
                30
        );

        JLabel lblFormat =
                new JLabel("DD-MM-YYYY");

        lblFormat.setBounds(
                300,
                115,
                120,
                20
        );


        // Search button

        btnSearch =
                new JButton("Search Week");

        btnSearch.setBounds(
                470,
                85,
                130,
                30
        );


        // Table

        tableModel =
                new DefaultTableModel(
                        new Object[]{
                                "Date",
                                "Day",
                                "Start",
                                "End",
                                "Activity",
                                "Employee",
                                "Resources"
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

        tblSchedule =
                new JTable(tableModel);

        JScrollPane scrollPane =
                new JScrollPane(tblSchedule);

        scrollPane.setBounds(
                30,
                155,
                875,
                250
        );


        // PDF button

        btnGeneratePdf =
                new JButton("Generate PDF");

        btnGeneratePdf.setBounds(
                320,
                425,
                140,
                35
        );


        // Back button

        btnBack =
                new JButton("Back");

        btnBack.setBounds(
                490,
                425,
                120,
                35
        );


        // Add components

        mainPanel.add(lblTitle);

        mainPanel.add(lblDate);
        mainPanel.add(txtDate);
        mainPanel.add(lblFormat);

        mainPanel.add(btnSearch);

        mainPanel.add(scrollPane);

        mainPanel.add(btnGeneratePdf);
        mainPanel.add(btnBack);

        add(mainPanel);
    }


    // Data received from user

    public String getDateInput() {

        return txtDate
                .getText()
                .trim();
    }


    // Data shown by view

    public void showScheduleEntries(
            List<ActivityScheduleEntry> entries) {

        tableModel.setRowCount(0);

        for (ActivityScheduleEntry entry :
                entries) {

            tableModel.addRow(
                    new Object[]{
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
    }


    public void clearSchedule() {

        tableModel.setRowCount(0);
    }


    // Messages

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


    // Actions

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
