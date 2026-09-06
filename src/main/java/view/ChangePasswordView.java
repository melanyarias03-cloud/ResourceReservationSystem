package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ChangePasswordView extends JFrame{

    private JPasswordField txtCurrentPassword;
    private JPasswordField txtNewPassword;
    private JPasswordField txtConfirmPassword;

    private JButton btnChange;
    private JButton btnCancel;

    public ChangePasswordView() {

        setTitle("Change Password");
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        initializeComponents();
    }

    private void initializeComponents() {

        JPanel mainPanel = new JPanel();

        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.WHITE);

        JLabel lblTitle =
                new JLabel("CHANGE PASSWORD");

        lblTitle.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        22
                )
        );

        lblTitle.setBounds(
                115,
                30,
                250,
                30
        );

        JLabel lblCurrent =
                new JLabel("Current password:");

        lblCurrent.setBounds(
                55,
                95,
                140,
                25
        );

        txtCurrentPassword =
                new JPasswordField();

        txtCurrentPassword.setBounds(
                200,
                95,
                180,
                30
        );

        JLabel lblNew =
                new JLabel("New password:");

        lblNew.setBounds(
                55,
                145,
                140,
                25
        );

        txtNewPassword =
                new JPasswordField();

        txtNewPassword.setBounds(
                200,
                145,
                180,
                30
        );

        JLabel lblConfirm =
                new JLabel("Confirm password:");

        lblConfirm.setBounds(
                55,
                195,
                140,
                25
        );

        txtConfirmPassword =
                new JPasswordField();

        txtConfirmPassword.setBounds(
                200,
                195,
                180,
                30
        );

        btnChange =
                new JButton("Change");

        btnChange.setBounds(
                100,
                255,
                110,
                35
        );

        btnCancel =
                new JButton("Cancel");

        btnCancel.setBounds(
                235,
                255,
                110,
                35
        );

        mainPanel.add(lblTitle);
        mainPanel.add(lblCurrent);
        mainPanel.add(txtCurrentPassword);
        mainPanel.add(lblNew);
        mainPanel.add(txtNewPassword);
        mainPanel.add(lblConfirm);
        mainPanel.add(txtConfirmPassword);
        mainPanel.add(btnChange);
        mainPanel.add(btnCancel);

        add(mainPanel);
    }

    public String getCurrentPasswordInput() {

        return new String(
                txtCurrentPassword.getPassword()
        );
    }

    public String getNewPasswordInput() {

        return new String(
                txtNewPassword.getPassword()
        );
    }

    public String getConfirmPasswordInput() {

        return new String(
                txtConfirmPassword.getPassword()
        );
    }

    public void clearFields() {

        txtCurrentPassword.setText("");
        txtNewPassword.setText("");
        txtConfirmPassword.setText("");

        txtCurrentPassword.requestFocus();
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

    public void setChangeAction(
            ActionListener action) {

        btnChange.addActionListener(action);
    }

    public void setCancelAction(
            ActionListener action) {

        btnCancel.addActionListener(action);
    }

}
