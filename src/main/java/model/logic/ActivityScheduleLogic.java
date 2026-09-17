package model.logic;

import model.entity.ActivityScheduleEntry;
import model.entity.Reservation;
import model.entity.Resource;
import model.exception.EmptyFieldException;
import model.exception.InvalidDataException;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ActivityScheduleLogic {

    private final DateTimeFormatter inputDateFormatter =
            DateTimeFormatter.ofPattern("dd-MM-uuuu");

    private final DateTimeFormatter outputDateFormatter =
            DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private final DateTimeFormatter timeFormatter =
            DateTimeFormatter.ofPattern("HH:mm");


    // Converts the date entered by the user
    public LocalDate parseDate(String date)
            throws EmptyFieldException, InvalidDataException {

        if (date == null || date.trim().isEmpty()) {
            throw new EmptyFieldException(
                    "The date cannot be empty."
            );
        }

        try {

            return LocalDate.parse(
                    date.trim(),
                    inputDateFormatter
            );

        } catch (DateTimeParseException e) {

            throw new InvalidDataException(
                    "The date must have the format DD-MM-YYYY."
            );
        }
    }


    // Gets the Monday of the selected week
    public LocalDate getStartOfWeek(
            LocalDate date) {

        return date.with(
                DayOfWeek.MONDAY
        );
    }


    // Gets the Sunday of the selected week
    public LocalDate getEndOfWeek(
            LocalDate date) {

        return date.with(
                DayOfWeek.SUNDAY
        );
    }


    // Filters reservations that belong to the selected week
    public List<Reservation> findByWeek(
            String date,
            List<Reservation> reservations)
            throws EmptyFieldException, InvalidDataException {

        LocalDate selectedDate =
                parseDate(date);

        LocalDate startOfWeek =
                getStartOfWeek(selectedDate);

        LocalDate endOfWeek =
                getEndOfWeek(selectedDate);

        List<Reservation> result =
                new ArrayList<>();

        for (Reservation reservation :
                reservations) {

            LocalDate reservationDate =
                    reservation.getDate();

            if (!reservationDate.isBefore(startOfWeek)
                    &&
                    !reservationDate.isAfter(endOfWeek)) {

                result.add(reservation);
            }
        }

        result.sort(
                Comparator
                        .comparing(Reservation::getDate)
                        .thenComparing(Reservation::getStartTime)
        );

        return result;
    }


    // Converts reservations into schedule entries
    public List<ActivityScheduleEntry> createScheduleEntries(
            List<Reservation> reservations) {

        List<ActivityScheduleEntry> entries =
                new ArrayList<>();

        for (Reservation reservation :
                reservations) {

            ActivityScheduleEntry entry =
                    new ActivityScheduleEntry(
                            reservation.getDate()
                                    .format(outputDateFormatter),

                            getDayName(
                                    reservation.getDate()
                            ),

                            reservation.getStartTime()
                                    .format(timeFormatter),

                            reservation.getEndTime()
                                    .format(timeFormatter),

                            reservation.getActivity(),

                            reservation.getEmployee()
                                    .getName(),

                            getResourcesText(
                                    reservation.getResources()
                            )
                    );

            entries.add(entry);
        }

        return entries;
    }


    // Gets the name of the day
    private String getDayName(
            LocalDate date) {

        return switch (
                date.getDayOfWeek()
                ) {

            case MONDAY -> "Monday";
            case TUESDAY -> "Tuesday";
            case WEDNESDAY -> "Wednesday";
            case THURSDAY -> "Thursday";
            case FRIDAY -> "Friday";
            case SATURDAY -> "Saturday";
            case SUNDAY -> "Sunday";
        };
    }


    // Converts the resource list into text
    private String getResourcesText(
            List<Resource> resources) {

        StringBuilder text =
                new StringBuilder();

        for (Resource resource : resources) {

            if (text.length() > 0) {
                text.append(", ");
            }

            text.append(
                    resource.getDescription()
            );
        }

        return text.toString();
    }

}
