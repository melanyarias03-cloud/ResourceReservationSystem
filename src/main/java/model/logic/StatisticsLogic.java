package model.logic;

import model.entity.Category;
import model.entity.Reservation;
import model.entity.Resource;
import model.entity.ResourceUsageEntry;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import model.exception.EmptyFieldException;
import model.exception.InvalidDataException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


public class StatisticsLogic {

    private final DateTimeFormatter dateFormatter =
            DateTimeFormatter.ofPattern("dd-MM-uuuu");

    // Returns the total number of reservations
    public int getTotalReservations(
            List<Reservation> reservations) {

        if (reservations == null) {
            return 0;
        }

        return reservations.size();
    }


    // Returns the total number of registered resources
    public int getTotalResources(
            List<Resource> resources) {

        if (resources == null) {
            return 0;
        }

        return resources.size();
    }


    // Counts how many reservations use each resource
    public Map<Resource, Integer> getResourceUsage(
            List<Resource> resources,
            List<Reservation> reservations) {

        Map<Resource, Integer> usage =
                new LinkedHashMap<>();

        if (resources == null) {
            return usage;
        }

        // Start every resource with 0 reservations
        for (Resource resource : resources) {

            usage.put(
                    resource,
                    0
            );
        }

        if (reservations == null) {
            return usage;
        }

        // Count the resources assigned to each reservation
        for (Reservation reservation : reservations) {

            for (Resource reservedResource :
                    reservation.getResources()) {

                for (Resource resource : resources) {

                    if (resource.getId().equals(
                            reservedResource.getId())) {

                        usage.put(
                                resource,
                                usage.get(resource) + 1
                        );

                        break;
                    }
                }
            }
        }

        return usage;
    }


    // Finds the resource with the highest usage
    public Resource getMostUsedResource(
            Map<Resource, Integer> resourceUsage) {

        Resource mostUsed = null;
        int highestUsage = 0;

        for (Map.Entry<Resource, Integer> entry :
                resourceUsage.entrySet()) {

            if (entry.getValue() > highestUsage) {

                highestUsage =
                        entry.getValue();

                mostUsed =
                        entry.getKey();
            }
        }

        return mostUsed;
    }


    // Counts how many times each category was requested
    public Map<Category, Integer> getCategoryUsage(
            List<Category> categories,
            List<Reservation> reservations) {

        Map<Category, Integer> usage =
                new LinkedHashMap<>();

        if (categories == null) {
            return usage;
        }

        // Start every category with 0
        for (Category category : categories) {

            usage.put(
                    category,
                    0
            );
        }

        if (reservations == null) {
            return usage;
        }

        for (Reservation reservation : reservations) {

            for (Resource resource :
                    reservation.getResources()) {

                Category reservedCategory =
                        resource.getCategory();

                for (Category category : categories) {

                    if (category.getId().equals(
                            reservedCategory.getId())) {

                        usage.put(
                                category,
                                usage.get(category) + 1
                        );

                        break;
                    }
                }
            }
        }

        return usage;
    }


    // Finds the category with the highest usage
    public Category getMostRequestedCategory(
            Map<Category, Integer> categoryUsage) {

        Category mostRequested = null;
        int highestUsage = 0;

        for (Map.Entry<Category, Integer> entry :
                categoryUsage.entrySet()) {

            if (entry.getValue() > highestUsage) {

                highestUsage =
                        entry.getValue();

                mostRequested =
                        entry.getKey();
            }
        }

        return mostRequested;
    }

    public List<ResourceUsageEntry> createResourceUsageEntries(
            Map<Resource, Integer> resourceUsage) {

        List<ResourceUsageEntry> entries =
                new ArrayList<>();

        for (Map.Entry<Resource, Integer> entry :
                resourceUsage.entrySet()) {

            Resource resource =
                    entry.getKey();

            ResourceUsageEntry usageEntry =
                    new ResourceUsageEntry(
                            resource.getDescription(),
                            resource.getCategory()
                                    .getDescription(),
                            entry.getValue()
                    );

            entries.add(
                    usageEntry
            );
        }

        return entries;
    }

    public String getMostUsedResourcesText(
            Map<Resource, Integer> resourceUsage) {

        int highestUsage = 0;

        for (Integer usage : resourceUsage.values()) {

            if (usage > highestUsage) {
                highestUsage = usage;
            }
        }

        if (highestUsage == 0) {
            return "-";
        }

        StringBuilder result =
                new StringBuilder();

        for (Map.Entry<Resource, Integer> entry :
                resourceUsage.entrySet()) {

            if (entry.getValue() == highestUsage) {

                if (result.length() > 0) {
                    result.append(", ");
                }

                result.append(
                        entry.getKey()
                                .getDescription()
                );
            }
        }

        return result.toString();
    }

    public String getMostRequestedCategoriesText(
            Map<Category, Integer> categoryUsage) {

        int highestUsage = 0;

        for (Integer usage : categoryUsage.values()) {

            if (usage > highestUsage) {
                highestUsage = usage;
            }
        }

        if (highestUsage == 0) {
            return "-";
        }

        StringBuilder result =
                new StringBuilder();

        for (Map.Entry<Category, Integer> entry :
                categoryUsage.entrySet()) {

            if (entry.getValue() == highestUsage) {

                if (result.length() > 0) {
                    result.append(", ");
                }

                result.append(
                        entry.getKey()
                                .getDescription()
                );
            }
        }

        return result.toString();
    }
    public List<Reservation> filterByPeriod(
            String fromDate,
            String toDate,
            List<Reservation> reservations)
            throws EmptyFieldException, InvalidDataException {

        if (fromDate == null ||
                fromDate.trim().isEmpty()) {

            throw new EmptyFieldException(
                    "The start date cannot be empty."
            );
        }

        if (toDate == null ||
                toDate.trim().isEmpty()) {

            throw new EmptyFieldException(
                    "The end date cannot be empty."
            );
        }

        LocalDate from;
        LocalDate to;

        try {

            from = LocalDate.parse(
                    fromDate.trim(),
                    dateFormatter
            );

            to = LocalDate.parse(
                    toDate.trim(),
                    dateFormatter
            );

        } catch (DateTimeParseException e) {

            throw new InvalidDataException(
                    "Dates must have the format DD-MM-YYYY."
            );
        }

        if (to.isBefore(from)) {

            throw new InvalidDataException(
                    "The end date cannot be before the start date."
            );
        }

        List<Reservation> result =
                new ArrayList<>();

        if (reservations == null) {
            return result;
        }

        for (Reservation reservation :
                reservations) {

            LocalDate reservationDate =
                    reservation.getDate();

            if (!reservationDate.isBefore(from)
                    &&
                    !reservationDate.isAfter(to)) {

                result.add(reservation);
            }
        }

        return result;
    }

    public Map<String, Integer> createResourceChartData(
            Map<Resource, Integer> resourceUsage) {

        Map<String, Integer> data =
                new LinkedHashMap<>();

        for (Map.Entry<Resource, Integer> entry :
                resourceUsage.entrySet()) {

            data.put(
                    entry.getKey().getDescription(),
                    entry.getValue()
            );
        }

        return data;
    }


    public Map<String, Integer> createCategoryChartData(
            Map<Category, Integer> categoryUsage) {

        Map<String, Integer> data =
                new LinkedHashMap<>();

        for (Map.Entry<Category, Integer> entry :
                categoryUsage.entrySet()) {

            data.put(
                    entry.getKey().getDescription(),
                    entry.getValue()
            );
        }

        return data;
    }

}
