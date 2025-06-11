package service;

import respository.dao.DaoInterface;
import respository.dao.AppointmentDAO;
import model.entity.Appointment;

import java.util.ArrayList;

public class AppointmentService {
    private DaoInterface<Appointment> daoAppointment;
    private AppointmentDAO appointmentDao;

    public AppointmentService(DaoInterface<Appointment> appointmentRepo) {
        this.daoAppointment = appointmentRepo;
        this.appointmentDao = new AppointmentDAO();
    }

    public ArrayList<Appointment> getAll() {
        return daoAppointment.getAll();
    }

    public boolean insert(Appointment appointment) {
        return daoAppointment.insert(appointment);
    }

    public boolean update(Appointment appointment) {
        return daoAppointment.update(appointment);
    }

    public boolean delete(Appointment appointment) {
        return daoAppointment.delete(appointment);
    }

    public Appointment selectByID(int id) {
        return daoAppointment.selectByID(id);
    }

    // Add custom methods here if needed
}