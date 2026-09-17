package controller;

import dataaccess.CategoryDataAccess;
import dataaccess.ReservationDataAccess;
import dataaccess.ResourceDataAccess;
import report.ResourceCalendarReport;
import model.entity.Category;
import model.entity.Reservation;
import model.entity.Resource;
import model.entity.Role;
import model.entity.User;

import model.logic.ResourceCalendarLogic;

import view.AdminMenuView;
import view.EmployeeMenuView;
import view.ResourceCalendarView;

import java.time.LocalDate;
import java.time.LocalTime;

import java.util.List;
import java.util.Map;


public class ResourceCalendarController {

    private final ResourceCalendarView view;

    private final ResourceDataAccess
            resourceDataAccess;

    private final ReservationDataAccess
            reservationDataAccess;

    private final CategoryDataAccess
            categoryDataAccess;

    private final ResourceCalendarLogic
            calendarLogic;

    private final User user;
    private final ResourceCalendarReport calendarReport;

    private Category currentCategory;
    private String currentDate;

    private List<Resource> currentResources;
    private List<LocalTime> currentHours;

    private Map<String, Map<LocalTime, String>>
            currentMatrix;

    public ResourceCalendarController(
            ResourceCalendarView view,
            User user) {

        this.view = view;
        this.user = user;

        this.resourceDataAccess =
                new ResourceDataAccess();

        this.reservationDataAccess =
                new ReservationDataAccess();

        this.categoryDataAccess =
                new CategoryDataAccess();

        this.calendarLogic =
                new ResourceCalendarLogic();

        this.calendarReport =
                new ResourceCalendarReport();

        this.currentResources =
                List.of();

        this.currentHours =
                List.of();

        this.currentMatrix =
                Map.of();

        initializeEvents();

        loadCategories();
    }


    private void initializeEvents() {

        view.setSearchAction(
                e -> showCalendar()
        );

        view.setGeneratePdfAction(
                e -> generatePdf()
        );

        view.setBackAction(
                e -> back()
        );
    }


    private void loadCategories() {

        try {

            List<Category> categories =
                    categoryDataAccess
                            .findAll();

            view.showCategories(
                    categories
            );

        } catch (Exception e) {

            view.showError(
                    e.getMessage()
            );
        }
    }


    private void showCalendar() {

        try {

            // View information

            Category category =
                    view.getSelectedCategory();

            String dateText =
                    view.getDateInput();


            // DataAccess information

            List<Resource> allResources =
                    resourceDataAccess
                            .findAll();

            List<Reservation> reservations =
                    reservationDataAccess
                            .findAll();


            // Logic

            LocalDate date =
                    calendarLogic
                            .parseDate(
                                    dateText
                            );


            List<Resource> resources =
                    calendarLogic
                            .findResourcesByCategory(
                                    category,
                                    allResources
                            );


            List<LocalTime> hours =
                    calendarLogic
                            .createHours();


            Map<String, Map<LocalTime, String>>
                    matrix =
                    calendarLogic
                            .createCalendarMatrix(
                                    resources,
                                    date,
                                    hours,
                                    reservations
                            );
            currentCategory =
                    category;

            currentDate =
                    dateText;

            currentResources =
                    resources;

            currentHours =
                    hours;

            currentMatrix =
                    matrix;

            // View

            view.showCalendarMatrix(
                    resources,
                    hours,
                    matrix
            );


        } catch (Exception e) {

            view.showError(
                    e.getMessage()
            );
        }
    }


    private void back() {

        view.dispose();


        if (user.getRole()
                == Role.ADMIN) {

            AdminMenuView adminMenuView =
                    new AdminMenuView();

            new AdminMenuController(
                    adminMenuView,
                    user
            );

            adminMenuView
                    .setVisible(true);

        } else {

            EmployeeMenuView employeeMenuView =
                    new EmployeeMenuView();

            new EmployeeMenuController(
                    employeeMenuView,
                    user
            );

            employeeMenuView
                    .setVisible(true);
        }
    }
    private void generatePdf() {

        try {

            if (currentCategory == null ||
                    currentResources.isEmpty() ||
                    currentHours.isEmpty() ||
                    currentMatrix.isEmpty()) {

                view.showMessage(
                        "Show a resource calendar before generating the PDF."
                );

                return;
            }

            calendarReport.generate(
                    currentCategory,
                    currentDate,
                    currentResources,
                    currentHours,
                    currentMatrix
            );

        } catch (Exception e) {

            view.showError(
                    e.getMessage()
            );
        }
    }

}