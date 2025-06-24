package service;

import respository.dao.BillDAO;
import respository.dao.DaoInterface;
import model.entity.Bill;
import model.entity.Customer;
import model.entity.Pet;
import model.entity.Product;

import java.util.ArrayList;

public class BillService {
    private DaoInterface<Bill> daoBill;

    public BillService(DaoInterface<Bill> daoBill) {
        this.daoBill = daoBill;
    }

    public boolean addBill(Bill bill) {
        return daoBill.insert(bill);
    }

    public boolean updateBill(Bill bill) {
        return daoBill.update(bill);
    }

    public boolean deleteBill(int billID) {
        Bill bill = new Bill();
        bill.setbillID(billID);
        return daoBill.delete(bill);
    }

    public ArrayList<Bill> getAllBills() {
        return daoBill.getAll();
    }

    public Bill getBillByID(int id) {
        return daoBill.selectByID(id);
    }

    public ArrayList<Product> getAllProduct() {
        if (daoBill instanceof BillDAO) {
            return ((BillDAO) daoBill).getAllProduct();
        }
        return new ArrayList<>();
    }

    public ArrayList<Pet> getAllPet() {
        if (daoBill instanceof BillDAO) {
            return ((BillDAO) daoBill).getAllPet();
        }
        return new ArrayList<>();
    }

    public ArrayList<Customer> getAllCustomer() {
        if (daoBill instanceof BillDAO) {
            return ((BillDAO) daoBill).getAllCustomer();
        }
        return new ArrayList<>();
    }
}