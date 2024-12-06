package models;

public class Booking {

    private TimeFrame timeFrame;
    private String description;
    private Customer customer;

    public Booking(TimeFrame timeFrame, String description) {
        this.timeFrame = timeFrame;
        this.description = description;
        this.customer = null;
    }

    //om man bokar en kund direkt
    public Booking(TimeFrame timeFrame, String description, Customer customer) {
        this.timeFrame = timeFrame;
        this.description = description;
        this.customer = customer;
    }

    public boolean isBooked() {
        return customer != null;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
