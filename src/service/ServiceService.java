package service;

import respository.dao.DaoInterface;
import respository.dao.ServiceDAO;
import model.entity.Service;

import java.util.ArrayList;

public class ServiceService {
    private DaoInterface<Service> daoService;
    private ServiceDAO serviceDao;

    public ServiceService(DaoInterface<Service> serviceRepo) {
        this.daoService = serviceRepo;
        this.serviceDao = new ServiceDAO();
    }

    public ArrayList<Service> getAll() {
        return daoService.getAll();
    }

    public boolean insert(Service service) {
        return daoService.insert(service);
    }

    public boolean update(Service service) {
        return daoService.update(service);
    }

    public boolean delete(Service service) {
        return daoService.delete(service);
    }

    public Service selectByID(int id) {
        return daoService.selectByID(id);
    }

    // Add custom methods here, e.g., search by name, if ServiceDAO supports it
}