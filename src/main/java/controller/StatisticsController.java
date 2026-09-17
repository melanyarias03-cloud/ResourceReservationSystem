package controller;
import report.StatisticsReport;
import dataaccess.CategoryDataAccess;
import dataaccess.ReservationDataAccess;
import dataaccess.ResourceDataAccess;
import model.entity.ResourceUsageEntry;
import model.entity.Category;
import model.entity.Reservation;
import model.entity.Resource;
import model.entity.User;
import model.logic.StatisticsLogic;

import view.AdminMenuView;
import view.StatisticsView;

import java.util.List;
import java.util.Map;

public class StatisticsController {

    private final StatisticsView view;
    private final User user;
    private final StatisticsReport statisticsReport;

    private String currentFromDate;
    private String currentToDate;

    private int currentTotalReservations;
    private int currentTotalResources;

    private String currentMostUsedResource;
    private String currentMostRequestedCategory;

    private List<ResourceUsageEntry> currentUsageEntries;
    private final ReservationDataAccess reservationDataAccess;
    private final ResourceDataAccess resourceDataAccess;
    private final CategoryDataAccess categoryDataAccess;

    private final StatisticsLogic statisticsLogic;


    public StatisticsController(
            StatisticsView view,
            User user) {

        this.view = view;
        this.user = user;

        this.reservationDataAccess =
                new ReservationDataAccess();

        this.resourceDataAccess =
                new ResourceDataAccess();

        this.categoryDataAccess =
                new CategoryDataAccess();

        this.statisticsLogic =
                new StatisticsLogic();

        this.statisticsReport =
                new StatisticsReport();

        this.currentUsageEntries =
                List.of();

        this.currentMostUsedResource = "-";
        this.currentMostRequestedCategory = "-";

        initializeEvents();

    }


    private void initializeEvents() {

        view.setGenerateStatisticsAction(
                e -> generateStatistics()
        );


        view.setGeneratePdfAction(
                e -> generatePdf()
        );

        view.setBackAction(
                e -> back()
        );
    }

    private void generateStatistics() {

        try {

            // Dates received from View

            String fromDate =
                    view.getFromDate();

            String toDate =
                    view.getToDate();


            // Data received from DataAccess

            List<Reservation> reservations =
                    reservationDataAccess
                            .findAll();

            List<Resource> resources =
                    resourceDataAccess
                            .findAll();

            List<Category> categories =
                    categoryDataAccess
                            .findAll();


            // Filter reservations by period

            List<Reservation> filteredReservations =
                    statisticsLogic
                            .filterByPeriod(
                                    fromDate,
                                    toDate,
                                    reservations
                            );


            // Calculate statistics

            int totalReservations =
                    statisticsLogic
                            .getTotalReservations(
                                    filteredReservations
                            );

            int totalResources =
                    statisticsLogic
                            .getTotalResources(
                                    resources
                            );


            Map<Resource, Integer> resourceUsage =
                    statisticsLogic
                            .getResourceUsage(
                                    resources,
                                    filteredReservations
                            );


            Map<Category, Integer> categoryUsage =
                    statisticsLogic
                            .getCategoryUsage(
                                    categories,
                                    filteredReservations
                            );


            Map<String, Integer> resourceChartData =
                    statisticsLogic
                            .createResourceChartData(
                                    resourceUsage
                            );

            Map<String, Integer> categoryChartData =
                    statisticsLogic
                            .createCategoryChartData(
                                    categoryUsage
                            );
            String mostUsedResources =
                    statisticsLogic
                            .getMostUsedResourcesText(
                                    resourceUsage
                            );


            String mostRequestedCategories =
                    statisticsLogic
                            .getMostRequestedCategoriesText(
                                    categoryUsage
                            );


            List<ResourceUsageEntry> usageEntries =
                    statisticsLogic
                            .createResourceUsageEntries(
                                    resourceUsage
                            );


            // Send results to View

            currentFromDate =
                    fromDate;

            currentToDate =
                    toDate;

            currentTotalReservations =
                    totalReservations;

            currentTotalResources =
                    totalResources;

            currentMostUsedResource =
                    mostUsedResources;

            currentMostRequestedCategory =
                    mostRequestedCategories;

            currentUsageEntries =
                    usageEntries;

            view.setTotalReservations(
                    totalReservations
            );

            view.setTotalResources(
                    totalResources
            );

            view.setMostUsedResource(
                    mostUsedResources
            );

            view.setMostRequestedCategory(
                    mostRequestedCategories
            );

            view.showResourceUsage(
                    usageEntries
            );
            view.showResourceChart(
                    resourceChartData
            );

            view.showCategoryChart(
                    categoryChartData
            );

        } catch (Exception e) {

            view.showError(
                    e.getMessage()
            );
        }
    }

    private void generatePdf() {

        try {

            if (currentFromDate == null ||
                    currentToDate == null) {

                view.showMessage(
                        "Generate the statistics before creating the PDF."
                );

                return;
            }

            statisticsReport.generate(
                    currentFromDate,
                    currentToDate,
                    currentTotalReservations,
                    currentTotalResources,
                    currentMostUsedResource,
                    currentMostRequestedCategory,
                    currentUsageEntries
            );

        } catch (Exception e) {

            view.showError(
                    e.getMessage()
            );
        }
    }

    private void back() {

        view.dispose();

        AdminMenuView adminMenuView =
                new AdminMenuView();

        new AdminMenuController(
                adminMenuView,
                user
        );

        adminMenuView.setVisible(true);
    }


}
