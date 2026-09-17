package controller;

import dataaccess.EmployeeDataAccess;
import dataaccess.ReservationDataAccess;
import model.entity.Employee;
import model.entity.Reservation;
import model.entity.User;
import view.EmployeeMenuView;
import view.ReservationView;
import java.util.List;
import dataaccess.CategoryDataAccess;
import dataaccess.EmployeeDataAccess;
import dataaccess.ResourceDataAccess;
import model.entity.Category;
import model.entity.Employee;
import model.entity.Resource;
import model.logic.ReservationLogic;
import view.ReservationFormView;
import report.ReservationReport;

public class ReservationController {

    private final ReservationView view;
    private final User user;
    private final CategoryDataAccess categoryDataAccess;
    private final EmployeeDataAccess employeeDataAccess;
    private final ResourceDataAccess resourceDataAccess;
    private final ReservationLogic reservationLogic;
    private final ReservationDataAccess reservationDataAccess;
    private final ReservationReport reservationReport;



    public ReservationController(
            ReservationView view,
            User user) {

        this.view = view;
        this.user = user;

        this.reservationDataAccess =
                new ReservationDataAccess();

        this.categoryDataAccess =
                new CategoryDataAccess();

        this.employeeDataAccess =
                new EmployeeDataAccess();

        this.resourceDataAccess =
                new ResourceDataAccess();

        this.reservationLogic =
                new ReservationLogic();

        this.reservationReport =
                new ReservationReport();

        initializeEvents();
        loadReservations();
    }

    private void initializeEvents() {

        view.setBackAction(
                e -> back()
        );
        view.setNewAction(
                e -> openNewReservationForm()
        );
        view.setCancelReservationAction(
                e -> cancelReservation()
        );
        view.setGeneratePdfAction(
                e -> generatePdf()
        );

    }

    private void loadReservations() {

        try {

            List<Reservation> reservations =
                    reservationDataAccess
                            .findByEmployee(
                                    user.getId()
                            );

            view.showReservations(
                    reservations
            );

        } catch (Exception e) {

            view.showError(
                    e.getMessage()
            );
        }
    }

    private void back() {

        view.dispose();

        EmployeeMenuView employeeMenuView =
                new EmployeeMenuView();

        new EmployeeMenuController(
                employeeMenuView,
                user
        );

        employeeMenuView.setVisible(true);
    }
    private void openNewReservationForm() {

        try {

            ReservationFormView formView =
                    new ReservationFormView();

            List<Category> categories =
                    categoryDataAccess
                            .findAll();

            formView.showCategories(
                    categories
            );

            formView.setSaveAction(
                    e -> prepareReservation(
                            formView
                    )
            );

            formView.setCancelAction(
                    e -> formView.dispose()
            );

            formView.setVisible(true);

        } catch (Exception e) {

            view.showError(
                    e.getMessage()
            );
        }
    }
    private void prepareReservation(
            ReservationFormView formView) {

        try {

            String activity =
                    formView
                            .getActivityInput();

            String date =
                    formView
                            .getDateInput();

            String startTime =
                    formView
                            .getStartTimeInput();

            String endTime =
                    formView
                            .getEndTimeInput();

            List<Category> categories =
                    formView
                            .getSelectedCategories();

            Employee employee =
                    employeeDataAccess
                            .findById(
                                    user.getId()
                            );

            List<Resource> resources =
                    resourceDataAccess
                            .findAll();

            List<Reservation> existingReservations =
                    reservationDataAccess
                            .findAll();

            int nextNumber =
                    reservationDataAccess
                            .getNextNumber();

            String reservationId =
                    reservationLogic
                            .generateId(
                                    nextNumber
                            );

            Reservation reservation =
                    reservationLogic
                            .createReservation(
                                    reservationId,
                                    employee,
                                    activity,
                                    date,
                                    startTime,
                                    endTime,
                                    categories,
                                    resources,
                                    existingReservations
                            );

            reservationDataAccess.save(
                    reservation
            );

            formView.showMessage(
                    "Reservation created successfully."
            );

            formView.dispose();

            loadReservations();

        } catch (Exception e) {

            formView.showError(
                    e.getMessage()
            );
        }
    }
    private void cancelReservation() {

        try {

            String reservationId =
                    view.getSelectedReservationId();



            reservationLogic
                    .validateReservationSelection(
                            reservationId
                    );



            Reservation reservation =
                    reservationDataAccess
                            .findById(
                                    reservationId
                            );

            reservationLogic
                    .validateCancellation(
                            reservation
                    );

            if (!view.confirmCancellation()) {
                return;
            }

            reservationDataAccess
                    .delete(
                            reservation.getId()
                    );

            view.showMessage(
                    "Reservation cancelled successfully."
            );

            loadReservations();

        } catch (Exception e) {

            view.showError(
                    e.getMessage()
            );
        }
    }
    private void generatePdf() {

        try {

            List<Reservation> reservations =
                    reservationDataAccess
                            .findByEmployee(
                                    user.getId()
                            );

            reservationReport.generate(
                    reservations
            );

        } catch (Exception e) {

            view.showError(
                    e.getMessage()
            );
        }
    }


}
