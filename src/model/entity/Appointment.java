package model.entity;

import java.util.Date;

public class Appointment {
    private int appointmentID;
    private int customerID;
    private String petName;
    private Date bookingDate;
    private Date completionDate;
    private Date appointmentDate;
    private String note;
    private String status; // Scheduled, Completed, Cancelled

    public Appointment() {}

    public Appointment(int appointmentID, int customerID, String petName, Date bookingDate, Date completionDate, Date appointmentDate, String note, String status) {
        this.appointmentID = appointmentID;
        this.customerID = customerID;
        this.petName = petName;
        this.bookingDate = bookingDate;
        this.completionDate = completionDate;
        this.appointmentDate = appointmentDate;
        this.note = note;
        this.status = status;
    }

    public int getAppointmentID() { return appointmentID; }
    public void setAppointmentID(int appointmentID) { this.appointmentID = appointmentID; }

    public int getCustomerID() { return customerID; }
    public void setCustomerID(int customerID) { this.customerID = customerID; }

    public String getPetName() { return petName; }
    public void setPetName(String petName) { this.petName = petName; }

    public Date getBookingDate() { return bookingDate; }
    public void setBookingDate(Date bookingDate) { this.bookingDate = bookingDate; }

    public Date getCompletionDate() { return completionDate; }
    public void setCompletionDate(Date completionDate) { this.completionDate = completionDate; }

    public Date getAppointmentDate() { return appointmentDate; }
    public void setAppointmentDate(Date appointmentDate) { this.appointmentDate = appointmentDate; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}