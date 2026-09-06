package model.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Reservation {

    private String id;
    private Employee employee;
    private String activity;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private List<Resource> resources;

    public Reservation() {
        this.resources = new ArrayList<>();
    }

    public Reservation(String id,
                       Employee employee,
                       String activity,
                       LocalDate date,
                       LocalTime startTime,
                       LocalTime endTime,
                       List<Resource> resources) {

        this.id = id;
        this.employee = employee;
        this.activity = activity;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.resources = resources;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public List<Resource> getResources() {
        return resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

    public void addResource(Resource resource) {
        resources.add(resource);
    }

    public void removeResource(Resource resource) {
        resources.remove(resource);
    }

    @Override
    public String toString() {
        return activity + " - " + date + " " + startTime + " - " + endTime;
    }

}
