package service;

import respository.dao.BillDAO;
import respository.dao.CustomerDao;
import respository.dao.DaoInterface;
import model.entity.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerService {
    private DaoInterface daoInterface;
    private CustomerDao customerDao;
    private BillDAO billDAO;

    public CustomerService(BillDAO billDAO) {
        this.billDAO = billDAO;
    }

    public Customer getCustomerById(int id) {
        return billDAO.getCustomerById(id);
    }

    public void addPoints(int customerId, int points) {
        billDAO.addPoints(customerId, points);
    }

    public void deductPoints(int customerId, int points) {
        billDAO.deductPoints(customerId, points);
    }

    public CustomerService(DaoInterface userRepo) {
        this.daoInterface = userRepo;
        this.customerDao = new CustomerDao();
    }

    public ArrayList<Customer> getAll() {
        return daoInterface.getAll();
    }

    public boolean insert(Customer customer) {
        return daoInterface.insert(customer);
    }

    public boolean update(Customer customer) {
        return daoInterface.update(customer);
    }

    public boolean delete(Customer customer) {
        return daoInterface.delete(customer);
    }

    public String getPoint(Customer customer) {
        return String.valueOf(customer.getLoyaltyPoints());
    }

    public String getRank(Customer customer) {
        return customer.getMembershipLevel();
    }

    public boolean checkPhone(String phone) {
        return customerDao.findByPhone(phone);
    }

    public Customer selectedByID(int id) {
        return (Customer) daoInterface.selectByID(id);
    }

    public List<Customer> searchByCustomerName(String name) {
        return customerDao.customerListByName(name);
    }

    public List<Customer> searchByCustomerPhone(String phone) {
        return customerDao.customerListByPhone(phone);
    }

    public List<Customer> getByStatus(String status) {
        return customerDao.getByStatus(status);
    }

    public List<Customer> searchByCustomerNameAndStatus(String name, String status) {
        return customerDao.customerListByNameAndStatus(name, status);
    }
    public List<Customer> searchByCustomerPhoneAndStatus(String phone, String status) {
        return customerDao.customerListByPhoneAndStatus(phone, status);
    }

}