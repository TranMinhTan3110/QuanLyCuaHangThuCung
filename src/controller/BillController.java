package controller;

import dao.BillDAO;
import dao.OrderDAO;
import dao.OrderDetailDAO;
import model.entity.*;
import view.BillView;

import service.BillService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class BillController {
    private BillView billView;
    private BillService billService;


    public BillController(BillView view, BillService service) {
        this.billView = view;
        this.billService = service;
//
        addEventHandlers();
        loadProductTable();
        loadPetTable();
        loadCustomerTable();
    }

    private void addEventHandlers() {
        billView.getProductButton().addActionListener(e -> {
            System.out.println("load product table");
            toggleTable("product");
            loadProductTable();
            System.out.println("load product table done");
        });

        billView.getPetButton().addActionListener(e -> {
            System.out.println("load pet table");
            toggleTable("pet");
            loadPetTable();
        });

        billView.getCustomerButton().addActionListener(e -> {
            System.out.println("load customer table");
            toggleTable("customer");
            loadCustomerTable();
        });

        billView.getTableProductList().addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = billView.getTableProductList().getSelectedRow();
                if (row >= 0) {
                    DefaultTableModel model = (DefaultTableModel) billView.getTableProductList().getModel();
                    String id = model.getValueAt(row, 0).toString();
                    String name = model.getValueAt(row, 1).toString();
                    double price = Double.parseDouble(model.getValueAt(row, 2).toString());
                    addBillItem(id, name, 1, price); // mặc định 1 sản phẩm
                }
            }
        });

        billView.getTablePetList().addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = billView.getTablePetList().getSelectedRow();
                if (row >= 0) {
                    DefaultTableModel billModel = (DefaultTableModel) billView.getTableBillItems().getModel();
                    String petID = billView.getTablePetList().getValueAt(row, 0).toString(); // ví dụ: 3PET
                    String petName = billView.getTablePetList().getValueAt(row, 1).toString();
                    double price = Double.parseDouble(billView.getTablePetList().getValueAt(row, 3).toString());

                    // Kiểm tra petID đã tồn tại chưa
                    boolean alreadyAdded = false;
                    for (int i = 0; i < billModel.getRowCount(); i++) {
                        String existingID = billModel.getValueAt(i, 0).toString();
                        if (existingID.equals(petID)) {
                            alreadyAdded = true;
                            break;
                        }
                    }

                    if (!alreadyAdded) {
                        addBillItem(petID, petName, 1, price);
                    } else {
                        JOptionPane.showMessageDialog(billView, "Thú cưng này đã được chọn trong hóa đơn!");
                    }
                }
            }
        });


        billView.getBtnDel().addActionListener(e -> {
            int row = billView.getTableBillItems().getSelectedRow();
            if (row >= 0) {
                String id = billView.getTableBillItems().getValueAt(row, 0).toString();
                decBillItem(id);
            } else {
                JOptionPane.showMessageDialog(billView, "Vui lòng chọn một mặt hàng để giảm số lượng.");
            }
        });

        billView.getBtnAdd().addActionListener(e -> {
            int row = billView.getTableBillItems().getSelectedRow();
            if (row >= 0) {
                String id = billView.getTableBillItems().getValueAt(row, 0).toString();
                BillBtnButton(id);
            } else {
                JOptionPane.showMessageDialog(billView, "Vui lòng chọn một mặt hàng để tăng số lượng.");
            }
        });


        billView.getBtnSave().addActionListener(e -> saveBill());

        billView.getBtnExport().addActionListener(e -> exportBill());
    }

    private void toggleTable(String type) {
        billView.getScrollPaneProduct().setVisible("product".equals(type));
        billView.getScrollPanePet().setVisible("pet".equals(type));
        billView.getScrollPaneCustomer().setVisible("customer".equals(type));

        billView.getSearchProductField().setVisible("product".equals(type));
        billView.getSearchPetField().setVisible("pet".equals(type));
        billView.getSearchCustomerField().setVisible("customer".equals(type));
    }

    private void loadProductTable() {
        ArrayList<Product> products = billService.getAllProduct();
        DefaultTableModel model = (DefaultTableModel) billView.getTableProductList().getModel();
        model.setRowCount(0);
        for (Product p : products) {
            model.addRow(new Object[]{
                    p.getProductID(),
                    p.getName(),
                    p.getPrice(),
                    p.getQuantity(),
                    p.getCategory().getCategoryName()
            });
        }
    }

    private void loadPetTable() {
        List<Pet> pets = billService.getAllPet();
        DefaultTableModel model = (DefaultTableModel) billView.getTablePetList().getModel();
        model.setRowCount(0);
        for (Pet p : pets) {
            model.addRow(new Object[]{(p.getPetID() + "PET"),p.getName(), p.getSpecies(), p.getPrice(), p.getBreed(), p.getAge()});
        }
    }

    private void loadCustomerTable() {
        List<Customer> customers = billService.getAllCustomer();
        DefaultTableModel model = (DefaultTableModel) billView.getTableCustomerList().getModel();
        model.setRowCount(0);
        for (Customer c : customers) {
            model.addRow(new Object[]{c.getId(), c.getName(), c.getAddress(), c.getPhone(), c.getMembershipLevel(), c.getLoyaltyPoints()});
        }
    }

    private void addBillItem(String id, String name, int quantity, double price) {
        DefaultTableModel billModel = (DefaultTableModel) billView.getTableBillItems().getModel();
        DefaultTableModel productModel = (DefaultTableModel) billView.getTableProductList().getModel();

        int quantityInStock = -1;
        for (int i = 0; i < productModel.getRowCount(); i++) {
            if (productModel.getValueAt(i, 0).toString().equals(id)) {
                quantityInStock = Integer.parseInt(productModel.getValueAt(i, 3).toString());
                break;
            }
        }

        if (quantityInStock == -1) {
            JOptionPane.showMessageDialog(billView, "Không tìm thấy thông tin sản phẩm trong kho.");
            return;
        }

        boolean exists = false;

        for (int i = 0; i < billModel.getRowCount(); i++) {
            String existingID = billModel.getValueAt(i, 0).toString();
            if (existingID.equals(id)) {
                int currentQty = Integer.parseInt(billModel.getValueAt(i, 2).toString());

                if (currentQty + quantity > quantityInStock) {
                    JOptionPane.showMessageDialog(billView, "Vượt quá số lượng sản phẩm còn lại trong kho!");
                    return;
                }

                int newQty = currentQty + quantity;
                billModel.setValueAt(newQty, i, 2);
                billModel.setValueAt(newQty * price, i, 4);
                exists = true;
                break;
            }
        }

        if (!exists) {
            if (quantity > quantityInStock) {
                JOptionPane.showMessageDialog(billView, "Không đủ hàng trong kho!");
                return;
            }
            double total = quantity * price;
            billModel.addRow(new Object[]{id, name, quantity, price, total});
        }
    }


    private void BillBtnButton(String id) {
        DefaultTableModel billModel = (DefaultTableModel) billView.getTableBillItems().getModel();
        DefaultTableModel productModel = (DefaultTableModel) billView.getTableProductList().getModel();

        for (int i = 0; i < billModel.getRowCount(); i++) {
            String existingID = billModel.getValueAt(i, 0).toString();

            if (existingID.equals(id)) {
                if (id.endsWith("PET")) {
                    JOptionPane.showMessageDialog(billView, "Mỗi thú cưng chỉ có thể chọn 1 lần!");
                    return;
                }

                int quantityInStock = -1;
                for (int j = 0; j < productModel.getRowCount(); j++) {
                    if (productModel.getValueAt(j, 0).toString().equals(id)) {
                        quantityInStock = Integer.parseInt(productModel.getValueAt(j, 3).toString());
                        break;
                    }
                }

                if (quantityInStock == -1) {
                    JOptionPane.showMessageDialog(billView, "Không tìm thấy số lượng trong kho.");
                    return;
                }

                int quantity = Integer.parseInt(billModel.getValueAt(i, 2).toString());
                if (quantity + 1 > quantityInStock) {
                    JOptionPane.showMessageDialog(billView, "Không đủ hàng trong kho!");
                    return;
                }

                double price = Double.parseDouble(billModel.getValueAt(i, 3).toString());
                billModel.setValueAt(quantity + 1, i, 2);
                billModel.setValueAt((quantity + 1) * price, i, 4);
                break;
            }
        }
    }


    private void decBillItem(String id) {
        DefaultTableModel billModel = (DefaultTableModel) billView.getTableBillItems().getModel();

        for (int i = 0; i < billModel.getRowCount(); i++) {
            String existingID = billModel.getValueAt(i, 0).toString();
            if (existingID.equals(id)) {
                int quantity = Integer.parseInt(billModel.getValueAt(i, 2).toString());
                double price = Double.parseDouble(billModel.getValueAt(i, 3).toString());

                if (quantity > 1) {
                    billModel.setValueAt(quantity - 1, i, 2);
                    billModel.setValueAt((quantity - 1) * price, i, 4);
                } else {
                    billModel.removeRow(i);
                }
                break;
            }
        }
    }

    private void saveBill() {
        String name = billView.getNameTextField().getText().trim();
        String phone = billView.getPhoneTextField().getText().trim();
        String address = billView.getAddressTextField().getText().trim();

        if (name.isEmpty() || phone.isEmpty() || address.isEmpty()) {
            JOptionPane.showMessageDialog(billView, "Vui lòng nhập đầy đủ thông tin khách hàng.");
            return;
        }

        DefaultTableModel billModel = (DefaultTableModel) billView.getTableBillItems().getModel();
        if (billModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(billView, "Chưa có sản phẩm/thú cưng nào được thêm vào hóa đơn.");
            return;
        }

//        boolean success = billService.saveBill(name, phone, address, billModel);
//        if (success) {
//            JOptionPane.showMessageDialog(billView, "Lưu hóa đơn thành công!");
//        } else {
//            JOptionPane.showMessageDialog(billView, "Lưu hóa đơn thất bại!");
//        }
    }
//
//    public boolean handleCheckout(Order order, List<OrderDetail> details, String method) {
//        try {
//            int orderID = orderDAO.saveOrder(order);
//            if (orderID == -1) return false;
//
//            orderDetailDAO.saveOrderDetails(details, orderID);
//
//            Bill bill = new Bill();
//            bill.setBillMethod(method);
//            bill.setAmount(order.getTotalPrice());
//            bill.setOrderID(orderID);
//
//            billDAO.saveBill(bill);
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }

    private void exportBill() {
        // Tạm thời in ra console
        System.out.println("Đang xuất hóa đơn...");
    }
}
