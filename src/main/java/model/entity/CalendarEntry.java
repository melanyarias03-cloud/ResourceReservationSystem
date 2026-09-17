package model.entity;

public class CalendarEntry {

    private String resource;
    private String category;
    private String date;
    private String startTime;
    private String endTime;
    private String activity;


    public CalendarEntry(
            String resource,
            String category,
            String date,
            String startTime,
            String endTime,
            String activity) {

        this.resource = resource;
        this.category = category;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.activity = activity;
    }


    public String getResource() {
        return resource;
    }

    public String getCategory() {
        return category;
    }

    public String getDate() {
        return date;
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


}
