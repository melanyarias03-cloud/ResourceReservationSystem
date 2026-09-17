package model.entity;


public class ActivityScheduleEntry {
    private String date;
    private String day;
    private String startTime;
    private String endTime;
    private String activity;
    private String employee;
    private String resources;

    public ActivityScheduleEntry(
            String date,
            String day,
            String startTime,
            String endTime,
            String activity,
            String employee,
            String resources) {

        this.date = date;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.activity = activity;
        this.employee = employee;
        this.resources = resources;
    }

    public String getDate() {
        return date;
    }

    public String getDay() {
        return day;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getActivity() {
        return activity;
    }

    public String getEmployee() {
        return employee;
    }

    public String getResources() {
        return resources;
    }

}
