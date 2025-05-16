package service;

import respository.dao.BillDAO;
import respository.dao.DaoInterface;
import model.entity.Bill;
import model.entity.Customer;
import model.entity.Pet;
import model.entity.Product;

import java.util.ArrayList;

public class BillService {
    private DaoInterface daoBill;

    public BillService(DaoInterface reooBill) {
        this.daoBill = reooBill;
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
        return (Bill) daoBill.selectByID(id);
    }

    public ArrayList<Product> getAllProduct(){
        System.out.println(">> Vào BillService.getAllProduct()");
        if(daoBill == null) {
            System.out.println("daoBill is NULL!");
            return new ArrayList<>();
        }

        if(daoBill instanceof BillDAO){
            System.out.println("daoBill đúng kiểu BillDAO");
            return ((BillDAO) daoBill).getAllProduct();
        } else {
            System.out.println("daoBill KHÔNG phải BillDAO");
        }
        return new ArrayList<>();
    }

    public ArrayList<Pet> getAllPet(){
        if(daoBill instanceof BillDAO){
            return ((BillDAO) daoBill).getAllPet();
        }
        return new ArrayList<>();
    }

     public ArrayList<Customer> getAllCustomer(){
        if(daoBill instanceof BillDAO){
            return ((BillDAO) daoBill).getAllCustomer();
        }
        return new ArrayList<>();
     }
}
