package service;

import respository.dao.CustomerDao;
import respository.dao.DaoInterface;
import respository.dao.ProductDAO;
import model.entity.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerService {
    private DaoInterface daoInterface;
    private CustomerDao customerDao;

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
        public String getRank(Customer customer){
            return customer.getMembershipLevel();
        }
        public boolean checkPhone(String phone){
            return customerDao.findByPhone(phone);
        }
        //hàm trả về một Customer
        public Customer  selectedByID(int id){
            return (Customer) daoInterface.selectByID(id);
        }

        //hàm trả về customer list searched theo name
        public List<Customer> searchByCustomerName(String name) {
          return customerDao.customerListByName(name);
        }
        //hàm trả về customer list searched  theo phone
        public List<Customer> searchByCustomerPhone(String phone) {
            return customerDao.customerListByPhone(phone);
        }

}





