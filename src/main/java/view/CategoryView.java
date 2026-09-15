package view;

import model.entity.Category;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;


public class CategoryView extends JFrame{


    private JTextField txtSearch;

    private JTable tblCategories;
    private DefaultTableModel tableModel;

    private JButton btnSearch;
    private JButton btnNew;
    private JButton btnEdit;
    private JButton btnDelete;
    private JButton btnBack;

    public CategoryView() {

        setTitle("Category Management");
        setSize(700, 470);
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
                new JLabel("CATEGORY MANAGEMENT");

        lblTitle.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        22
                )
        );

        lblTitle.setBounds(
                220,
                25,
                300,
                30
        );

        JLabel lblSearch =
                new JLabel("Search:");

        lblSearch.setBounds(
                40,
                80,
                80,
                25
        );

        txtSearch =
                new JTextField();

        txtSearch.setBounds(
                105,
                80,
                300,
                30
        );

        btnSearch =
                new JButton("Search");

        btnSearch.setBounds(
                420,
                80,
                100,
                30
        );

        btnNew =
                new JButton("New");

        btnNew.setBounds(
                535,
                80,
                100,
                30
        );

        tableModel =
                new DefaultTableModel(
                        new Object[]{
                                "ID",
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

        tblCategories =
                new JTable(
                        tableModel
                );

        JScrollPane scrollPane =
                new JScrollPane(
                        tblCategories
                );

        scrollPane.setBounds(
                40,
                135,
                595,
                210
        );

        btnEdit =
                new JButton("Edit");

        btnEdit.setBounds(
                150,
                370,
                100,
                35
        );

        btnDelete =
                new JButton("Delete");

        btnDelete.setBounds(
                295,
                370,
                100,
                35
        );

        btnBack =
                new JButton("Back");

        btnBack.setBounds(
                440,
                370,
                100,
                35
        );

        mainPanel.add(lblTitle);
        mainPanel.add(lblSearch);
        mainPanel.add(txtSearch);

        mainPanel.add(btnSearch);
        mainPanel.add(btnNew);

        mainPanel.add(scrollPane);

        mainPanel.add(btnEdit);
        mainPanel.add(btnDelete);
        mainPanel.add(btnBack);

        add(mainPanel);
    }

    public String getSearchInput() {

        return txtSearch
                .getText()
                .trim();
    }

    public String getSelectedCategoryId() {

        int selectedRow =
                tblCategories
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

    public void showCategories(
            List<Category> categories) {

        tableModel.setRowCount(0);

        for (Category category
                : categories) {

            tableModel.addRow(
                    new Object[]{
                            category.getId(),
                            category.getDescription()
                    }
            );
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

    public boolean confirmDelete() {

        int option =
                JOptionPane
                        .showConfirmDialog(
                                this,
                                "Do you want to delete the selected category?",
                                "Confirm deletion",
                                JOptionPane.YES_NO_OPTION
                        );

        return option
                == JOptionPane.YES_OPTION;
    }

    public void setSearchAction(
            ActionListener action) {

        btnSearch.addActionListener(
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

}
