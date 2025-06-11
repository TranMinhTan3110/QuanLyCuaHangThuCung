package service;

import respository.dao.DaoInterface;
import respository.dao.AppointmentServiceDAO;
import model.entity.AppointmentService;

import java.util.ArrayList;

public class AppointmentServiceService {
    private DaoInterface<AppointmentService> daoAppointmentService;
    private AppointmentServiceDAO appointmentServiceDao;

    public AppointmentServiceService(DaoInterface<AppointmentService> appointmentServiceRepo) {
        this.daoAppointmentService = appointmentServiceRepo;
        this.appointmentServiceDao = new AppointmentServiceDAO();
    }

    public ArrayList<AppointmentService> getAll() {
        return daoAppointmentService.getAll();
    }

    public boolean insert(AppointmentService as) {
        return daoAppointmentService.insert(as);
    }

    public boolean delete(AppointmentService as) {
        return daoAppointmentService.delete(as);
    }

    // Custom method for composite key
    public ArrayList<AppointmentService> getByAppointmentID(int appointmentID) {
        return appointmentServiceDao.getByAppointmentID(appointmentID);
    }
}