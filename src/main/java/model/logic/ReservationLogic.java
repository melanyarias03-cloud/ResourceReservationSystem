package model.logic;

import model.entity.Category;
import model.entity.Employee;
import model.entity.Reservation;
import model.entity.Resource;
import model.exception.EmptyFieldException;
import model.exception.InvalidDataException;
import model.exception.ResourceNotAvailableException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.time.format.DateTimeParseException;
import java.time.format.DateTimeFormatter;

public class ReservationLogic {

    public void validateReservationData(
            Employee employee,
            String activity,
            LocalDate date,
            LocalTime startTime,
            LocalTime endTime,
            List<Category> categories)
            throws EmptyFieldException, InvalidDataException {

        if (employee == null) {
            throw new InvalidDataException(
                    "The employee is required."
            );
        }

        if (activity == null || activity.trim().isEmpty()) {
            throw new EmptyFieldException(
                    "The activity cannot be empty."
            );
        }

        if (date == null) {
            throw new InvalidDataException(
                    "The date is required."
            );
        }



        if (startTime == null || endTime == null) {
            throw new InvalidDataException(
                    "The start and end times are required."
            );
        }


        if (date.isBefore(LocalDate.now())) {

            throw new InvalidDataException(
                    "Reservations cannot be made for past dates."
            );
        }

        if (date.equals(LocalDate.now())
                && !startTime.isAfter(LocalTime.now())) {

            throw new InvalidDataException(
                    "The start time must be later than the current time."
            );
        }

        if (!endTime.isAfter(startTime)) {
            throw new InvalidDataException(
                    "The end time must be after the start time."
            );
        }





        if (categories == null || categories.isEmpty()) {
            throw new InvalidDataException(
                    "At least one resource category must be selected."
            );
        }
    }


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

            DateTimeFormatter formatter =
                    DateTimeFormatter.ofPattern(
                            "dd-MM-uuuu"
                    );

            return LocalDate.parse(
                    date.trim(),
                    formatter
            );

        } catch (DateTimeParseException e) {

            throw new InvalidDataException(
                    "The date format must be DD-MM-YYYY."
            );
        }
    }
    public LocalTime parseTime(
            String time)
            throws EmptyFieldException,
            InvalidDataException {

        if (time == null ||
                time.trim().isEmpty()) {

            throw new EmptyFieldException(
                    "The time cannot be empty."
            );
        }

        try {

            return LocalTime.parse(
                    time.trim()
            );

        } catch (DateTimeParseException e) {

            throw new InvalidDataException(
                    "The time format must be HH:mm."
            );
        }
    }



    public boolean timeOverlaps(
            LocalTime start1,
            LocalTime end1,
            LocalTime start2,
            LocalTime end2) {

        return start1.isBefore(end2)
                && end1.isAfter(start2);
    }

    public boolean isResourceAvailable(
            Resource resource,
            LocalDate date,
            LocalTime startTime,
            LocalTime endTime,
            List<Reservation> reservations) {

        for (Reservation reservation : reservations) {

            if (!reservation.getDate().equals(date)) {
                continue;
            }

            boolean resourceFound = false;

            for (Resource reservedResource :
                    reservation.getResources()) {

                if (reservedResource.getId()
                        .equals(resource.getId())) {

                    resourceFound = true;
                    break;
                }
            }

            if (!resourceFound) {
                continue;
            }

            if (timeOverlaps(
                    startTime,
                    endTime,
                    reservation.getStartTime(),
                    reservation.getEndTime())) {

                return false;
            }
        }

        return true;
    }

    public Resource findFirstAvailableResource(
            Category category,
            LocalDate date,
            LocalTime startTime,
            LocalTime endTime,
            List<Resource> resources,
            List<Reservation> reservations) {

        for (Resource resource : resources) {

            if (resource.getCategory().getId()
                    .equals(category.getId())
                    && isResourceAvailable(
                    resource,
                    date,
                    startTime,
                    endTime,
                    reservations)) {

                return resource;
            }
        }

        return null;
    }



    public String generateId(int nextNumber) {

        return String.format(
                "RES-%06d",
                nextNumber
        );
    }



    public Reservation createReservation(
            String id,
            Employee employee,
            String activity,
            LocalDate date,
            LocalTime startTime,
            LocalTime endTime,
            List<Category> categories,
            List<Resource> resources,
            List<Reservation> reservations)
            throws EmptyFieldException,
            InvalidDataException,
            ResourceNotAvailableException {

        validateReservationData(
                employee,
                activity,
                date,
                startTime,
                endTime,
                categories
        );

        List<Resource> assignedResources =
                new ArrayList<>();

        List<String> unavailableCategories =
                new ArrayList<>();

        for (Category category : categories) {

            Resource availableResource =
                    findFirstAvailableResource(
                            category,
                            date,
                            startTime,
                            endTime,
                            resources,
                            reservations
                    );

            if (availableResource == null) {

                unavailableCategories.add(
                        category.getDescription()
                );

            } else {

                assignedResources.add(
                        availableResource
                );
            }
        }

        if (!unavailableCategories.isEmpty()) {

            throw new ResourceNotAvailableException(
                    "No resources available for categories: "
                            + String.join(
                            ", ",
                            unavailableCategories
                    )
            );
        }

        return new Reservation(
                id,
                employee,
                activity.trim(),
                date,
                startTime,
                endTime,
                assignedResources
        );
    }
    public Reservation createReservation(
            String id,
            Employee employee,
            String activity,
            String date,
            String startTime,
            String endTime,
            List<Category> categories,
            List<Resource> resources,
            List<Reservation> reservations)
            throws EmptyFieldException,
            InvalidDataException,
            ResourceNotAvailableException {

        LocalDate parsedDate =
                parseDate(date);

        LocalTime parsedStartTime =
                parseTime(startTime);

        LocalTime parsedEndTime =
                parseTime(endTime);

        return createReservation(
                id,
                employee,
                activity,
                parsedDate,
                parsedStartTime,
                parsedEndTime,
                categories,
                resources,
                reservations
        );
    }

    public void validateCancellation(
            Reservation reservation)
            throws InvalidDataException {

        if (reservation == null) {
            throw new InvalidDataException(
                    "Reservation not found."
            );
        }

        LocalDate today =
                LocalDate.now();

        LocalTime currentTime =
                LocalTime.now();

        if (reservation.getDate().isBefore(today)) {

            throw new InvalidDataException(
                    "Past reservations cannot be cancelled."
            );
        }

        if (reservation.getDate().equals(today) && !reservation.getStartTime().isAfter(currentTime)) {

            throw new InvalidDataException(
                    "A reservation that has already started cannot be cancelled."
            );
        }
    }
    public void validateReservationSelection(
            String reservationId)
            throws InvalidDataException {

        if (reservationId == null
                || reservationId.trim().isEmpty()) {

            throw new InvalidDataException(
                    "Select a reservation first."
            );
        }
    }
}