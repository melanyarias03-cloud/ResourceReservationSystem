package model.entity;


public class ResourceUsageEntry {

    private String resource;
    private String category;
    private int reservations;


    public ResourceUsageEntry(
            String resource,
            String category,
            int reservations) {

        this.resource = resource;
        this.category = category;
        this.reservations = reservations;
    }


    public String getResource() {
        return resource;
    }

    public String getCategory() {
        return category;
    }

    public int getReservations() {
        return reservations;
    }

}
