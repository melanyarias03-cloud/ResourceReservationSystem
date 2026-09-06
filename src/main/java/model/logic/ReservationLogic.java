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

        List<Resource> assignedResources = new ArrayList<>();

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
                throw new ResourceNotAvailableException(
                        "No resource available for category: "
                                + category.getDescription()
                );
            }

            assignedResources.add(availableResource);
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
}
