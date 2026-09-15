package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class EmployeeMenuView extends JFrame{

    private JButton btnReservations;
    private JButton btnCalendar;
    private JButton btnActivities;
    private JButton btnChangePassword;
    private JButton btnLogout;

    public EmployeeMenuView() {

        setTitle("Employee Menu");
        setSize(500, 430);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        initializeComponents();
    }

    private void initializeComponents() {

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.WHITE);

        JLabel lblTitle =
                new JLabel("EMPLOYEE MENU");

        lblTitle.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        22
                )
        );

        lblTitle.setBounds(
                160,
                30,
                250,
                30
        );

        btnReservations =
                new JButton("My Reservations");

        btnReservations.setBounds(
                140,
                90,
                220,
                40
        );

        btnCalendar =
                new JButton("Resource Calendar");

        btnCalendar.setBounds(
                140,
                140,
                220,
                40
        );

        btnActivities =
                new JButton("Activity Schedule");

        btnActivities.setBounds(
                140,
                190,
                220,
                40
        );

        btnChangePassword =
                new JButton("Change Password");

        btnChangePassword.setBounds(
                140,
                240,
                220,
                40
        );

        btnLogout =
                new JButton("Logout");

        btnLogout.setBounds(
                140,
                300,
                220,
                40
        );

        mainPanel.add(lblTitle);
        mainPanel.add(btnReservations);
        mainPanel.add(btnCalendar);
        mainPanel.add(btnActivities);
        mainPanel.add(btnChangePassword);
        mainPanel.add(btnLogout);

        add(mainPanel);
    }

    public JButton getBtnReservations() {
        return btnReservations;
    }

    public JButton getBtnCalendar() {
        return btnCalendar;
    }

    public JButton getBtnActivities() {
        return btnActivities;
    }

    public JButton getBtnChangePassword() {
        return btnChangePassword;
    }

    public JButton getBtnLogout() {
        return btnLogout;
    }


    public void setChangePasswordAction(
            ActionListener action) {

        btnChangePassword.addActionListener(action);
    }

    public void setLogoutAction(
            ActionListener action) {

        btnLogout.addActionListener(action);
    }

    public void setReservationsAction(
            ActionListener action) {

        btnReservations.addActionListener(
                action
        );
    }

}
