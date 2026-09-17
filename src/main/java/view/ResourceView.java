package view;
import model.entity.Category;
import model.entity.Resource;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;



public class ResourceView extends JFrame{

    private JComboBox<Category> cmbCategories;

    private JTable tblResources;
    private DefaultTableModel tableModel;

    private JButton btnFilter;
    private JButton btnShowAll;
    private JButton btnNew;
    private JButton btnEdit;
    private JButton btnDelete;
    private JButton btnBack;
    private JButton btnGeneratePdf;


    public ResourceView() {

        setTitle("Resource Management");
        setSize(780, 500);
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
                new JLabel("RESOURCE MANAGEMENT");

        lblTitle.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        22
                )
        );

        lblTitle.setBounds(
                250,
                25,
                300,
                30
        );

        // Category filter
        JLabel lblCategory =
                new JLabel("Category:");

        lblCategory.setBounds(
                40,
                80,
                80,
                25
        );

        cmbCategories =
                new JComboBox<>();

        cmbCategories.setBounds(
                120,
                80,
                230,
                30
        );

        btnFilter =
                new JButton("Filter");

        btnFilter.setBounds(
                365,
                80,
                90,
                30
        );

        btnShowAll =
                new JButton("Show All");

        btnShowAll.setBounds(
                465,
                80,
                100,
                30
        );

        btnNew =
                new JButton("New");

        btnNew.setBounds(
                575,
                80,
                100,
                30
        );

        // Table
        tableModel =
                new DefaultTableModel(
                        new Object[]{
                                "ID",
                                "Category",
                                "Description"
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

        tblResources =
                new JTable(
                        tableModel
                );

        JScrollPane scrollPane =
                new JScrollPane(
                        tblResources
                );

        scrollPane.setBounds(
                40,
                135,
                675,
                230
        );

        // Bottom buttons
        btnEdit =
                new JButton("Edit");

        btnEdit.setBounds(
                175,
                395,
                110,
                35
        );

        btnDelete =
                new JButton("Delete");

        btnDelete.setBounds(
                335,
                395,
                110,
                35
        );

        btnBack =
                new JButton("Back");

        btnBack.setBounds(
                495,
                395,
                110,
                35
        );
        btnGeneratePdf =
                new JButton("Generate PDF");

        btnGeneratePdf.setBounds(
                20,
                20,
                140,
                35
        );


        mainPanel.add(btnGeneratePdf);
        mainPanel.add(lblTitle);

        mainPanel.add(lblCategory);
        mainPanel.add(cmbCategories);

        mainPanel.add(btnFilter);
        mainPanel.add(btnShowAll);
        mainPanel.add(btnNew);

        mainPanel.add(scrollPane);

        mainPanel.add(btnEdit);
        mainPanel.add(btnDelete);
        mainPanel.add(btnBack);

        add(mainPanel);
    }

    // -------------------------
    // Information from the view
    // -------------------------

    public Category getSelectedCategory() {

        return (Category)
                cmbCategories
                        .getSelectedItem();
    }

    public String getSelectedResourceId() {

        int selectedRow =
                tblResources
                        .getSelectedRow();

        if (selectedRow == -1) {

            return null;
        }

        return tableModel
                .getValueAt(
                        selectedRow,
                        0
                )
                .toString();
    }

    // -------------------------
    // Information shown by view
    // -------------------------

    public void showCategories(
            List<Category> categories) {

        cmbCategories.removeAllItems();

        for (Category category
                : categories) {

            cmbCategories.addItem(
                    category
            );
        }
    }

    public void showResources(
            List<Resource> resources) {

        tableModel.setRowCount(0);

        for (Resource resource
                : resources) {

            tableModel.addRow(
                    new Object[]{
                            resource.getId(),
                            resource
                                    .getCategory()
                                    .getDescription(),
                            resource.getDescription()
                    }
            );
        }
    }

    // -------------------------
    // Messages
    // -------------------------

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

    public boolean confirmDelete() {

        int option =
                JOptionPane.showConfirmDialog(
                        this,
                        "Do you want to delete the selected resource?",
                        "Confirm deletion",
                        JOptionPane.YES_NO_OPTION
                );

        return option
                == JOptionPane.YES_OPTION;
    }

    // -------------------------
    // Actions
    // -------------------------

    public void setFilterAction(
            ActionListener action) {

        btnFilter.addActionListener(
                action
        );
    }

    public void setShowAllAction(
            ActionListener action) {

        btnShowAll.addActionListener(
                action
        );
    }

    public void setNewAction(
            ActionListener action) {

        btnNew.addActionListener(
                action
        );
    }

    public void setEditAction(
            ActionListener action) {

        btnEdit.addActionListener(
                action
        );
    }

    public void setDeleteAction(
            ActionListener action) {

        btnDelete.addActionListener(
                action
        );
    }

    public void setBackAction(
            ActionListener action) {

        btnBack.addActionListener(
                action
        );
    }

    public void setGeneratePdfAction(
            ActionListener action) {

        btnGeneratePdf.addActionListener(action);
    }


}
