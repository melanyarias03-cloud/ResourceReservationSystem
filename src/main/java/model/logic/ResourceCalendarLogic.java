package model.logic;

import model.entity.Category;
import model.entity.Reservation;
import model.entity.Resource;
import model.exception.EmptyFieldException;
import model.exception.InvalidDataException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class ResourceCalendarLogic {

    private final DateTimeFormatter inputFormatter =
            DateTimeFormatter.ofPattern("dd-MM-uuuu");


    public LocalDate parseDate(
            String date)
            throws EmptyFieldException,
            InvalidDataException {

        if (date == null ||
                date.trim().isEmpty()) {

            throw new EmptyFieldException(
                    "The date cannot be empty."
            );
        }

        try {

            return LocalDate.parse(
                    date.trim(),
                    inputFormatter
            );

        } catch (DateTimeParseException e) {

            throw new InvalidDataException(
                    "The date format must be DD-MM-YYYY."
            );
        }
    }


    // Gets all resources that belong to a category
    public List<Resource> findResourcesByCategory(
            Category category,
            List<Resource> resources)
            throws InvalidDataException {

        if (category == null) {

            throw new InvalidDataException(
                    "A category must be selected."
            );
        }

        List<Resource> result =
                new ArrayList<>();

        if (resources == null) {
            return result;
        }

        for (Resource resource : resources) {

            if (resource.getCategory() != null &&
                    resource.getCategory()
                            .getId()
                            .equals(category.getId())) {

                result.add(resource);
            }
        }

        return result;
    }


    // Returns the activity assigned to a resource
    // during a specific hour
    public String getActivityAtHour(
            Resource resource,
            LocalDate date,
            LocalTime hour,
            List<Reservation> reservations) {

        if (reservations == null) {
            return "Available";
        }

        for (Reservation reservation :
                reservations) {

            if (!reservation.getDate()
                    .equals(date)) {

                continue;
            }

            if (!reservationContainsResource(
                    reservation,
                    resource)) {

                continue;
            }

            // The hour belongs to the reservation when:
            // start <= hour < end

            boolean occupied =
                    !hour.isBefore(
                            reservation.getStartTime()
                    )
                            &&
                            hour.isBefore(
                                    reservation.getEndTime()
                            );

            if (occupied) {

                return reservation
                        .getActivity();
            }
        }

        return "Available";
    }


    public boolean reservationContainsResource(
            Reservation reservation,
            Resource resource) {

        if (reservation == null ||
                resource == null) {

            return false;
        }

        for (Resource reservedResource :
                reservation.getResources()) {

            if (reservedResource
                    .getId()
                    .equals(resource.getId())) {

                return true;
            }
        }

        return false;
    }


    // Hours displayed in the calendar
    public List<LocalTime> createHours() {

        List<LocalTime> hours =
                new ArrayList<>();

        LocalTime current =
                LocalTime.of(7, 0);

        LocalTime end =
                LocalTime.of(22, 0);

        while (current.isBefore(end)) {

            hours.add(current);

            current =
                    current.plusHours(1);
        }

        return hours;
    }
    public Map<String, Map<LocalTime, String>>
    createCalendarMatrix(
            List<Resource> resources,
            LocalDate date,
            List<LocalTime> hours,
            List<Reservation> reservations) {

        Map<String, Map<LocalTime, String>>
                matrix =
                new LinkedHashMap<>();


        for (Resource resource :
                resources) {

            Map<LocalTime, String>
                    resourceSchedule =
                    new LinkedHashMap<>();


            for (LocalTime hour :
                    hours) {

                String activity =
                        getActivityAtHour(
                                resource,
                                date,
                                hour,
                                reservations
                        );

                resourceSchedule.put(
                        hour,
                        activity
                );
            }


            matrix.put(
                    resource.getId(),
                    resourceSchedule
            );
        }

        return matrix;
    }
}