package view;

import model.entity.Employee;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class EmployeeView extends JFrame{

    private JTextField txtSearch;

    private JTable tblEmployees;
    private DefaultTableModel tableModel;
    private JButton btnSearch;
    private JButton btnNew;
    private JButton btnEdit;
    private JButton btnDelete;
    private JButton btnBack;
    private JButton btnGeneratePdf;


    public EmployeeView() {

        setTitle("Employee Management");
        setSize(750, 500);
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
                new JLabel("EMPLOYEE MANAGEMENT");

        lblTitle.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        22
                )
        );

        lblTitle.setBounds(
                245,
                25,
                300,
                30
        );

        JLabel lblSearch =
                new JLabel("Search by name:");

        lblSearch.setBounds(
                40,
                80,
                120,
                25
        );

        txtSearch =
                new JTextField();

        txtSearch.setBounds(
                160,
                80,
                300,
                30
        );

        btnSearch =
                new JButton("Search");

        btnSearch.setBounds(
                475,
                80,
                100,
                30
        );


        btnGeneratePdf =
                new JButton("Generate PDF");

        btnGeneratePdf.setBounds(
                20,
                20,
                140,
                35
        );




        btnNew =
                new JButton("New");

        btnNew.setBounds(
                590,
                80,
                100,
                30
        );

        tableModel =
                new DefaultTableModel(
                        new Object[]{
                                "ID",
                                "Name",
                                "Phone"
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

        tblEmployees =
                new JTable(
                        tableModel
                );

        JScrollPane scrollPane =
                new JScrollPane(
                        tblEmployees
                );

        scrollPane.setBounds(
                40,
                135,
                650,
                230
        );

        btnEdit =
                new JButton("Edit");

        btnEdit.setBounds(
                175,
                390,
                110,
                35
        );

        btnDelete =
                new JButton("Delete");

        btnDelete.setBounds(
                320,
                390,
                110,
                35
        );

        btnBack =
                new JButton("Back");

        btnBack.setBounds(
                465,
                390,
                110,
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
        mainPanel.add(btnGeneratePdf);
        add(mainPanel);
    }

    public String getSearchInput() {

        return txtSearch
                .getText()
                .trim();
    }

    public String getSelectedEmployeeId() {

        int selectedRow =
                tblEmployees
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

    public void showEmployees(
            List<Employee> employees) {

        tableModel.setRowCount(0);

        for (Employee employee
                : employees) {

            tableModel.addRow(
                    new Object[]{
                            employee
                                    .getUser()
                                    .getId(),

                            employee
                                    .getName(),

                            employee
                                    .getPhone()
                    }
            );
        }
    }

    public void clearSearch() {

        txtSearch.setText("");
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
                                "Do you want to delete the selected employee?",
                                "Confirm deletion",
                                JOptionPane.YES_NO_OPTION
                        );

        return option
                == JOptionPane.YES_OPTION;
    }

    public void setSearchAction(
            ActionListener action) {

        btnSearch
                .addActionListener(action);
    }

    public void setNewAction(
            ActionListener action) {

        btnNew
                .addActionListener(action);
    }

    public void setEditAction(
            ActionListener action) {

        btnEdit
                .addActionListener(action);
    }

    public void setDeleteAction(
            ActionListener action) {

        btnDelete
                .addActionListener(action);
    }

    public void setBackAction(
            ActionListener action) {

        btnBack
                .addActionListener(action);
    }

    public void setGeneratePdfAction(
            ActionListener action) {

        btnGeneratePdf.addActionListener(action);
    }

}
