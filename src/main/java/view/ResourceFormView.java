package view;
import model.entity.Category;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;


public class ResourceFormView extends JFrame{

    private JTextField txtId;
    private JComboBox<Category> cmbCategory;
    private JTextField txtDescription;

    private JButton btnSave;
    private JButton btnCancel;

    public ResourceFormView() {

        setTitle("Resource Form");
        setSize(500, 340);
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
                new JLabel("RESOURCE");

        lblTitle.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        22
                )
        );

        lblTitle.setBounds(
                190,
                25,
                180,
                30
        );

        JLabel lblId =
                new JLabel("ID:");

        lblId.setBounds(
                60,
                85,
                100,
                25
        );

        txtId =
                new JTextField();

        txtId.setBounds(
                170,
                85,
                250,
                30
        );

        JLabel lblCategory =
                new JLabel("Category:");

        lblCategory.setBounds(
                60,
                135,
                100,
                25
        );

        cmbCategory =
                new JComboBox<>();

        cmbCategory.setBounds(
                170,
                135,
                250,
                30
        );

        JLabel lblDescription =
                new JLabel("Description:");

        lblDescription.setBounds(
                60,
                185,
                100,
                25
        );

        txtDescription =
                new JTextField();

        txtDescription.setBounds(
                170,
                185,
                250,
                30
        );

        btnSave =
                new JButton("Save");

        btnSave.setBounds(
                120,
                245,
                110,
                35
        );

        btnCancel =
                new JButton("Cancel");

        btnCancel.setBounds(
                270,
                245,
                110,
                35
        );

        mainPanel.add(lblTitle);

        mainPanel.add(lblId);
        mainPanel.add(txtId);

        mainPanel.add(lblCategory);
        mainPanel.add(cmbCategory);

        mainPanel.add(lblDescription);
        mainPanel.add(txtDescription);

        mainPanel.add(btnSave);
        mainPanel.add(btnCancel);

        add(mainPanel);
    }

    public String getIdInput() {

        return txtId
                .getText()
                .trim();
    }

    public Category getSelectedCategory() {

        return (Category)
                cmbCategory
                        .getSelectedItem();
    }

    public String getDescriptionInput() {

        return txtDescription
                .getText()
                .trim();
    }

    public void showCategories(
            List<Category> categories) {

        cmbCategory.removeAllItems();

        for (Category category
                : categories) {

            cmbCategory.addItem(
                    category
            );
        }
    }

    public void setResourceData(
            String id,
            Category category,
            String description) {

        txtId.setText(id);

        cmbCategory.setSelectedItem(
                category
        );

        txtDescription.setText(
                description
        );
    }

    public void setIdEditable(
            boolean editable) {

        txtId.setEditable(
                editable
        );
    }

    public void clearFields() {

        txtId.setText("");

        txtDescription.setText("");

        if (cmbCategory.getItemCount() > 0) {

            cmbCategory.setSelectedIndex(0);
        }
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


