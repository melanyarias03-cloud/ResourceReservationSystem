package view;

import model.entity.Category;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;


public class ReservationFormView extends JFrame{

    private JTextField txtActivity;
    private JTextField txtDate;
    private JTextField txtStartTime;
    private JTextField txtEndTime;

    private JList<Category> lstCategories;
    private DefaultListModel<Category> categoryListModel;

    private JButton btnSave;
    private JButton btnCancel;

    public ReservationFormView() {

        setTitle("New Reservation");
        setSize(560, 520);
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

        JLabel lblTitle =
                new JLabel("NEW RESERVATION");

        lblTitle.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        22
                )
        );

        lblTitle.setBounds(
                180,
                25,
                250,
                30
        );

        JLabel lblActivity =
                new JLabel("Activity:");

        lblActivity.setBounds(
                50,
                85,
                100,
                25
        );

        txtActivity =
                new JTextField();

        txtActivity.setBounds(
                170,
                85,
                300,
                30
        );

        JLabel lblDate =
                new JLabel("Date:");

        lblDate.setBounds(
                50,
                135,
                100,
                25
        );

        txtDate =
                new JTextField();

        txtDate.setBounds(
                170,
                135,
                300,
                30
        );

        JLabel lblDateFormat =
                new JLabel("DD-MM-YYYY");

        lblDateFormat.setBounds(
                375,
                165,
                100,
                20
        );

        JLabel lblStartTime =
                new JLabel("Start Time:");

        lblStartTime.setBounds(
                50,
                195,
                100,
                25
        );

        txtStartTime =
                new JTextField();

        txtStartTime.setBounds(
                170,
                195,
                130,
                30
        );

        JLabel lblStartFormat =
                new JLabel("HH:mm");

        lblStartFormat.setBounds(
                310,
                195,
                60,
                25
        );

        JLabel lblEndTime =
                new JLabel("End Time:");

        lblEndTime.setBounds(
                50,
                245,
                100,
                25
        );

        txtEndTime =
                new JTextField();

        txtEndTime.setBounds(
                170,
                245,
                130,
                30
        );

        JLabel lblEndFormat =
                new JLabel("HH:mm");

        lblEndFormat.setBounds(
                310,
                245,
                60,
                25
        );

        JLabel lblCategories =
                new JLabel("Categories:");

        lblCategories.setBounds(
                50,
                300,
                100,
                25
        );

        categoryListModel =
                new DefaultListModel<>();

        lstCategories =
                new JList<>(
                        categoryListModel
                );

        lstCategories.setSelectionMode(
                ListSelectionModel
                        .MULTIPLE_INTERVAL_SELECTION
        );

        JScrollPane categoryScrollPane =
                new JScrollPane(
                        lstCategories
                );

        categoryScrollPane.setBounds(
                170,
                300,
                300,
                90
        );

        btnSave =
                new JButton("Save");

        btnSave.setBounds(
                140,
                420,
                110,
                35
        );

        btnCancel =
                new JButton("Cancel");

        btnCancel.setBounds(
                300,
                420,
                110,
                35
        );

        mainPanel.add(lblTitle);

        mainPanel.add(lblActivity);
        mainPanel.add(txtActivity);

        mainPanel.add(lblDate);
        mainPanel.add(txtDate);
        mainPanel.add(lblDateFormat);

        mainPanel.add(lblStartTime);
        mainPanel.add(txtStartTime);
        mainPanel.add(lblStartFormat);

        mainPanel.add(lblEndTime);
        mainPanel.add(txtEndTime);
        mainPanel.add(lblEndFormat);

        mainPanel.add(lblCategories);
        mainPanel.add(categoryScrollPane);

        mainPanel.add(btnSave);
        mainPanel.add(btnCancel);

        add(mainPanel);
    }

    public String getActivityInput() {

        return txtActivity
                .getText()
                .trim();
    }

    public String getDateInput() {

        return txtDate
                .getText()
                .trim();
    }

    public String getStartTimeInput() {

        return txtStartTime
                .getText()
                .trim();
    }

    public String getEndTimeInput() {

        return txtEndTime
                .getText()
                .trim();
    }

    public List<Category> getSelectedCategories() {

        return new ArrayList<>(
                lstCategories
                        .getSelectedValuesList()
        );
    }

    public void showCategories(
            List<Category> categories) {

        categoryListModel.clear();

        for (Category category
                : categories) {

            categoryListModel.addElement(
                    category
            );
        }
    }

    public void clearFields() {

        txtActivity.setText("");
        txtDate.setText("");
        txtStartTime.setText("");
        txtEndTime.setText("");

        lstCategories.clearSelection();
    }

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

    public void setSaveAction(
            ActionListener action) {

        btnSave.addActionListener(
                action
        );
    }

    public void setCancelAction(
            ActionListener action) {

        btnCancel.addActionListener(
                action
        );
    }
}


