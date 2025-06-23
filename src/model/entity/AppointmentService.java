package model.entity;

public class AppointmentService {
    private int appointmentID;
    private int serviceID;

    public AppointmentService() {}

    public AppointmentService(int appointmentID, int serviceID) {
        this.appointmentID = appointmentID;
        this.serviceID = serviceID;
    }

    public int getAppointmentID() { return appointmentID; }
    public void setAppointmentID(int appointmentID) { this.appointmentID = appointmentID; }

    public int getServiceID() { return serviceID; }
    public void setServiceID(int serviceID) { this.serviceID = serviceID; }
}