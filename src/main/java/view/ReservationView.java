package view;

import model.entity.Reservation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;
import java.time.format.DateTimeFormatter;


public class ReservationView extends JFrame{


    private JTable tblReservations;
    private DefaultTableModel tableModel;

    private JButton btnNew;
    private JButton btnCancelReservation;
    private JButton btnBack;

    public ReservationView() {

        setTitle("My Reservations");
        setSize(850, 500);
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
                new JLabel("MY RESERVATIONS");

        lblTitle.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        22
                )
        );

        lblTitle.setBounds(
                320,
                25,
                250,
                30
        );

        btnNew =
                new JButton("New Reservation");

        btnNew.setBounds(
                40,
                80,
                150,
                35
        );

        tableModel =
                new DefaultTableModel(
                        new Object[]{
                                "ID",
                                "Activity",
                                "Date",
                                "Start Time",
                                "End Time",
                                "Resources"
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

        tblReservations =
                new JTable(
                        tableModel
                );

        JScrollPane scrollPane =
                new JScrollPane(
                        tblReservations
                );

        scrollPane.setBounds(
                40,
                135,
                760,
                245
        );

        btnCancelReservation =
                new JButton("Cancel Reservation");

        btnCancelReservation.setBounds(
                235,
                405,
                170,
                35
        );

        btnBack =
                new JButton("Back");

        btnBack.setBounds(
                455,
                405,
                120,
                35
        );

        mainPanel.add(lblTitle);
        mainPanel.add(btnNew);

        mainPanel.add(scrollPane);

        mainPanel.add(btnCancelReservation);
        mainPanel.add(btnBack);

        add(mainPanel);
    }

    public String getSelectedReservationId() {

        int selectedRow =
                tblReservations
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

    public void showReservations(
            List<Reservation> reservations) {

        tableModel.setRowCount(0);

        for (Reservation reservation
                : reservations) {

            tableModel.addRow(
                    new Object[]{
                            reservation.getId(),
                            reservation.getActivity(),
                            formatDate(reservation),
                            reservation.getStartTime(),
                            reservation.getEndTime(),
                            getResourceDescriptions(
                                    reservation
                            )
                    }
            );
        }
    }

    private String getResourceDescriptions(
            Reservation reservation) {

        StringBuilder resources =
                new StringBuilder();

        for (int i = 0;
             i < reservation
                     .getResources()
                     .size();
             i++) {

            resources.append(
                    reservation
                            .getResources()
                            .get(i)
                            .getDescription()
            );

            if (i <
                    reservation
                            .getResources()
                            .size() - 1) {

                resources.append(", ");
            }
        }

        return resources.toString();
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

    public boolean confirmCancellation() {

        int option =
                JOptionPane.showConfirmDialog(
                        this,
                        "Do you want to cancel the selected reservation?",
                        "Confirm cancellation",
                        JOptionPane.YES_NO_OPTION
                );

        return option
                == JOptionPane.YES_OPTION;
    }

    public void setNewAction(
            ActionListener action) {

        btnNew.addActionListener(
                action
        );
    }

    public void setCancelReservationAction(
            ActionListener action) {

        btnCancelReservation.addActionListener(
                action
        );
    }

    public void setBackAction(
            ActionListener action) {

        btnBack.addActionListener(
                action
        );
    }
    private String formatDate(Reservation reservation) {

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd-MM-yyyy");

        return reservation
                .getDate()
                .format(formatter);
    }
}



