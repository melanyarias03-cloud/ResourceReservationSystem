package controller;

import dataaccess.ReservationDataAccess;

import model.entity.ActivityScheduleEntry;
import model.entity.Reservation;
import model.entity.Role;
import model.entity.User;
import report.ActivityScheduleReport;
import model.logic.ActivityScheduleLogic;

import view.ActivityScheduleView;
import view.AdminMenuView;
import view.EmployeeMenuView;

import java.util.List;

public class ActivityScheduleController {

    private final ActivityScheduleView view;
    private final ReservationDataAccess reservationDataAccess;
    private final ActivityScheduleLogic scheduleLogic;
    private final User user;
    private final ActivityScheduleReport scheduleReport;

    private List<ActivityScheduleEntry> currentEntries;

    public ActivityScheduleController(
            ActivityScheduleView view,
            User user) {

        this.view = view;
        this.user = user;

        this.reservationDataAccess =
                new ReservationDataAccess();

        this.scheduleLogic =
                new ActivityScheduleLogic();

        this.scheduleReport =
                new ActivityScheduleReport();

        this.currentEntries =
                List.of();

        initializeEvents();
    }


    private void initializeEvents() {

        view.setSearchAction(
                e -> searchWeek()
        );

        view.setBackAction(
                e -> back()
        );

        view.setGeneratePdfAction(
                e -> generatePdf()
        );
    }


    private void searchWeek() {

        try {

            String date =
                    view.getDateInput();

            List<Reservation> reservations =
                    reservationDataAccess
                            .findAll();

            List<Reservation> result =
                    scheduleLogic
                            .findByWeek(
                                    date,
                                    reservations
                            );

            currentEntries =
                    scheduleLogic
                            .createScheduleEntries(
                                    result
                            );

            view.showScheduleEntries(
                    currentEntries
            );

        } catch (Exception e) {

            view.showError(
                    e.getMessage()
            );
        }
    }


    private void back() {

        view.dispose();

        if (user.getRole() == Role.ADMIN) {

            AdminMenuView adminMenuView =
                    new AdminMenuView();

            new AdminMenuController(
                    adminMenuView,
                    user
            );

            adminMenuView.setVisible(true);

        } else {

            EmployeeMenuView employeeMenuView =
                    new EmployeeMenuView();

            new EmployeeMenuController(
                    employeeMenuView,
                    user
            );

            employeeMenuView.setVisible(true);
        }
    }
    private void generatePdf() {

        try {

            if (currentEntries.isEmpty()) {

                view.showMessage(
                        "There is no activity information to generate the PDF."
                );

                return;
            }

            scheduleReport.generate(
                    currentEntries
            );

        } catch (Exception e) {

            view.showError(
                    e.getMessage()
            );
        }
    }

}
