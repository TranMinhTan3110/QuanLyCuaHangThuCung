package service;

import dao.CustomerDao;
import dao.DaoInterface;
import model.entity.Customer;
import model.entity.User;

import java.util.ArrayList;

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

        public int getPoint(Customer customer){
            return customer.getLoyaltyPoints();
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
    }





