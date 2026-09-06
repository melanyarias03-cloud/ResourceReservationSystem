package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class EmployeeFormView extends JFrame{

    private JTextField txtId;
    private JTextField txtName;
    private JTextField txtPhone;

    private JButton btnSave;
    private JButton btnCancel;

    public EmployeeFormView() {

        setTitle("Employee Form");
        setSize(450, 380);
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
                new JLabel("EMPLOYEE FORM");

        lblTitle.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        22
                )
        );

        lblTitle.setBounds(
                135,
                30,
                220,
                30
        );

        JLabel lblId =
                new JLabel("Identification:");

        lblId.setBounds(
                60,
                100,
                120,
                25
        );

        txtId =
                new JTextField();

        txtId.setBounds(
                180,
                100,
                190,
                30
        );

        JLabel lblName =
                new JLabel("Name:");

        lblName.setBounds(
                60,
                155,
                120,
                25
        );

        txtName =
                new JTextField();

        txtName.setBounds(
                180,
                155,
                190,
                30
        );

        JLabel lblPhone =
                new JLabel("Phone:");

        lblPhone.setBounds(
                60,
                210,
                120,
                25
        );

        txtPhone =
                new JTextField();

        txtPhone.setBounds(
                180,
                210,
                190,
                30
        );

        btnSave =
                new JButton("Save");

        btnSave.setBounds(
                105,
                280,
                100,
                35
        );

        btnCancel =
                new JButton("Cancel");

        btnCancel.setBounds(
                240,
                280,
                100,
                35
        );

        mainPanel.add(lblTitle);

        mainPanel.add(lblId);
        mainPanel.add(txtId);

        mainPanel.add(lblName);
        mainPanel.add(txtName);

        mainPanel.add(lblPhone);
        mainPanel.add(txtPhone);

        mainPanel.add(btnSave);
        mainPanel.add(btnCancel);

        add(mainPanel);
    }

    public String getIdInput() {

        return txtId
                .getText()
                .trim();
    }

    public String getNameInput() {

        return txtName
                .getText()
                .trim();
    }

    public String getPhoneInput() {

        return txtPhone
                .getText()
                .trim();
    }

    public void setEmployeeData(
            String id,
            String name,
            String phone) {

        txtId.setText(id);
        txtName.setText(name);
        txtPhone.setText(phone);
    }

    public void setIdEditable(
            boolean editable) {

        txtId.setEditable(editable);
    }

    public void clearFields() {

        txtId.setText("");
        txtName.setText("");
        txtPhone.setText("");

        txtId.requestFocus();
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

        btnSave.addActionListener(action);
    }

    public void setCancelAction(
            ActionListener action) {

        btnCancel.addActionListener(action);
    }


}
