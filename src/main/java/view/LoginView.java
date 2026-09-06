package view;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame{

    private JTextField txtId;
    private JPasswordField txtPassword;

    private JButton btnLogin;
    private JButton btnClear;

    public LoginView() {

        setTitle("Resource Reservation System");
        setSize(420, 320);
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
                new JLabel("LOGIN");

        lblTitle.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        24
                )
        );

        lblTitle.setBounds(
                165,
                30,
                150,
                30
        );

        JLabel lblId =
                new JLabel("Identification:");

        lblId.setBounds(
                60,
                90,
                120,
                25
        );

        txtId =
                new JTextField();

        txtId.setBounds(
                180,
                90,
                160,
                30
        );

        JLabel lblPassword =
                new JLabel("Password:");

        lblPassword.setBounds(
                60,
                135,
                120,
                25
        );

        txtPassword =
                new JPasswordField();

        txtPassword.setBounds(
                180,
                135,
                160,
                30
        );

        btnLogin =
                new JButton("Login");

        btnLogin.setBounds(
                100,
                200,
                100,
                35
        );

        btnClear =
                new JButton("Clear");

        btnClear.setBounds(
                220,
                200,
                100,
                35
        );

        mainPanel.add(lblTitle);
        mainPanel.add(lblId);
        mainPanel.add(txtId);
        mainPanel.add(lblPassword);
        mainPanel.add(txtPassword);
        mainPanel.add(btnLogin);
        mainPanel.add(btnClear);

        add(mainPanel);
    }


    public String getIdInput() {
        return txtId.getText().trim();
    }

    public String getPasswordInput() {
        return new String(
                txtPassword.getPassword()
        );
    }

    public void clearFields() {

        txtId.setText("");
        txtPassword.setText("");

        txtId.requestFocus();
    }
    public void showMessage(String message) {

        JOptionPane.showMessageDialog(
                this,
                message
        );
    }

    public void showError(String message) {

        JOptionPane.showMessageDialog(
                this,
                message,
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
    public void setLoginAction(
            java.awt.event.ActionListener action) {

        btnLogin.addActionListener(action);
    }
    public void setClearAction(
            java.awt.event.ActionListener action) {

        btnClear.addActionListener(action);
    }




}
