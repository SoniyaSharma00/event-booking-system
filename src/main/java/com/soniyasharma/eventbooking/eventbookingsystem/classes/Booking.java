package com.soniyasharma.eventbooking.eventbookingsystem.classes;


public class Booking {

    private int id;
    private String name;
    private String contact_no;
    private int noOfReservations;

    private int eventId;

    private String eventName;

    private String eventLocation;

    private String eventDate;

    private int status;

    public Booking(int id, String name, String contact_no, int noOfReservations, int eventId, String eventName, String eventLocation, String eventDate, int status) {
        this.id = id;
        this.name = name;
        this.contact_no = contact_no;
        this.noOfReservations = noOfReservations;
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventLocation = eventLocation;
        this.eventDate = eventDate;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactNo() {
        return contact_no;
    }

    public void setContactNo(String contact_no) {
        this.contact_no = contact_no;
    }

    public int getNoOfReservations() {
        return noOfReservations;
    }

    public void setNoOfReservations(int noOfReservations) {
        this.noOfReservations = noOfReservations;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getStatus() {
        if(status == 0) {
            return "pending";
        }else{
            return "approved";
        }
    }

    public void setStatus(int eventId) {
        this.status = status;
    }
}
