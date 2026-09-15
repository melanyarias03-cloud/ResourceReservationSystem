package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class CategoryFormView extends JFrame {

    private JTextField txtDescription;

    private JButton btnSave;
    private JButton btnCancel;

    public CategoryFormView() {

        setTitle("Category Form");
        setSize(450, 260);
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
                new JLabel("CATEGORY");

        lblTitle.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        22
                )
        );

        lblTitle.setBounds(
                160,
                25,
                200,
                30
        );

        JLabel lblDescription =
                new JLabel("Description:");

        lblDescription.setBounds(
                45,
                90,
                100,
                25
        );

        txtDescription =
                new JTextField();

        txtDescription.setBounds(
                145,
                90,
                240,
                30
        );

        btnSave =
                new JButton("Save");

        btnSave.setBounds(
                100,
                155,
                100,
                35
        );

        btnCancel =
                new JButton("Cancel");

        btnCancel.setBounds(
                245,
                155,
                100,
                35
        );

        mainPanel.add(lblTitle);
        mainPanel.add(lblDescription);
        mainPanel.add(txtDescription);
        mainPanel.add(btnSave);
        mainPanel.add(btnCancel);

        add(mainPanel);
    }

    public String getDescriptionInput() {

        return txtDescription
                .getText()
                .trim();
    }

    public void setCategoryData(
            String description) {

        txtDescription.setText(
                description
        );
    }

    public void clearFields() {

        txtDescription.setText("");
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
