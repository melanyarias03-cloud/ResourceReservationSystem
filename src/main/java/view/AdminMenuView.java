package view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class AdminMenuView extends JFrame{

    private JButton btnEmployees;
    private JButton btnCategories;
    private JButton btnResources;
    private JButton btnCalendar;
    private JButton btnStatistics;
    private JButton btnChangePassword;
    private JButton btnLogout;

    public AdminMenuView() {

        setTitle("Administrator Menu");
        setSize(500, 520);
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
                new JLabel("ADMINISTRATOR MENU");

        lblTitle.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        22
                )
        );

        lblTitle.setBounds(
                125,
                30,
                300,
                30
        );

        btnEmployees =
                new JButton("Employees");

        btnEmployees.setBounds(
                140,
                90,
                220,
                40
        );

        btnCategories =
                new JButton("Resource Categories");

        btnCategories.setBounds(
                140,
                140,
                220,
                40
        );

        btnResources =
                new JButton("Resources");

        btnResources.setBounds(
                140,
                190,
                220,
                40
        );

        btnCalendar =
                new JButton("Calendar");

        btnCalendar.setBounds(
                140,
                240,
                220,
                40
        );

        btnStatistics =
                new JButton("Statistics");

        btnStatistics.setBounds(
                140,
                290,
                220,
                40
        );

        btnChangePassword =
                new JButton("Change Password");

        btnChangePassword.setBounds(
                140,
                340,
                220,
                40
        );

        btnLogout =
                new JButton("Logout");

        btnLogout.setBounds(
                140,
                400,
                220,
                40
        );

        mainPanel.add(lblTitle);
        mainPanel.add(btnEmployees);
        mainPanel.add(btnCategories);
        mainPanel.add(btnResources);
        mainPanel.add(btnCalendar);
        mainPanel.add(btnStatistics);
        mainPanel.add(btnChangePassword);
        mainPanel.add(btnLogout);

        add(mainPanel);
    }

    public JButton getBtnEmployees() {
        return btnEmployees;
    }

    public JButton getBtnCategories() {
        return btnCategories;
    }

    public JButton getBtnResources() {
        return btnResources;
    }

    public JButton getBtnCalendar() {
        return btnCalendar;
    }

    public JButton getBtnStatistics() {
        return btnStatistics;
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

    public void setEmployeesAction(
            ActionListener action) {

        btnEmployees.addActionListener(
                action
        );
    }

}
