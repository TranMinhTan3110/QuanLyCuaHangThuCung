package controller;
import model.entity.Category;
import model.entity.Product;
import service.ProductService;
import view.ProductView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.List;

public class ProductController {
    private ProductView view;
    private ProductService  service;

    public ProductController(ProductView view, ProductService service) {
        this.view = view;
        this.service = service;

        this.view.addAddButtonListener(new AddListener());
        this.view.addEditButtonListener(new EditListener());
        this.view.addDeleteButtonListener(new DeleteListener());
        this.view.addPlusButtonListener(new PlusListener());
        this.view.addMinusButtonListener(new MinusListener());
        this.view.addSearchKeyListener(new SearchListener());

        loadTable();
    }

    private void loadTable() {
        List<Product> list = service.getAll();
        DefaultTableModel model = (DefaultTableModel) view.getProductTable().getModel();
        model.setRowCount(0);
        for (Product p : list) {
            model.addRow(new Object[]{
                    p.getProductID(),
                    p.getName(),
                    p.getPrice(),
                    p.getQuantity(),
                    p.getCategory().getCategoryName()
            });
        }
    }

    class AddListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String name = view.getProductName();
                double price = Double.parseDouble(view.getPrice());
                int quantity = Integer.parseInt(view.getQuantity());
                Category selectedCategory = view.getCategory();

                if (selectedCategory == null) {
                    JOptionPane.showMessageDialog(view, "Vui lòng chọn danh mục.");
                    return;
                }

                Product p = new Product(0, name, price, quantity, selectedCategory);
                service.insert(p);
                loadTable();
                view.clearFields();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Lỗi khi thêm sản phẩm: " + ex.getMessage());
            }
        }
    }

    class EditListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int row = view.getProductTable().getSelectedRow();
            if (row >= 0) {
                try {
                    int id = (int) view.getProductTable().getValueAt(row, 0);
                    String name = view.getProductName();
                    double price = Double.parseDouble(view.getPrice());
                    int quantity = Integer.parseInt(view.getQuantity());
                    Category selectedCategory = view.getCategory();

                    if (selectedCategory == null) {
                        JOptionPane.showMessageDialog(view, "Vui lòng chọn danh mục.");
                        return;
                    }

                    Product p = new Product(0, name, price, quantity, selectedCategory);
                    service.update(p);
                    loadTable();
                    view.clearFields();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(view, "Lỗi khi cập nhật sản phẩm: " + ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(view, "Vui lòng chọn sản phẩm để sửa.");
            }
        }
    }

    class DeleteListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int row = view.getProductTable().getSelectedRow();
            if (row >= 0) {
                int confirm = JOptionPane.showConfirmDialog(view, "Bạn có chắc chắn muốn xóa sản phẩm này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    int id = (int) view.getProductTable().getValueAt(row, 0);
                    Product p = service.selectByID(id);
                    service.delete(p);
                    loadTable();
                    view.clearFields();
                }
            } else {
                JOptionPane.showMessageDialog(view, "Vui lòng chọn sản phẩm để xóa.");
            }
        }
    }

    class PlusListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int quantity = Integer.parseInt(view.getQuantity());
                view.setQuantity(String.valueOf(quantity + 1));
            } catch (NumberFormatException ex) {
                view.setQuantity("1");
            }
        }
    }

    class MinusListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int quantity = Integer.parseInt(view.getQuantity());
                if (quantity > 0) {
                    view.setQuantity(String.valueOf(quantity - 1));
                }
            } catch (NumberFormatException ex) {
                view.setQuantity("0");
            }
        }
    }

    class SearchListener implements KeyListener {
        @Override
        public void keyReleased(KeyEvent e) {
            String keyword = view.getSearchKeyword();
            List<Product> list = service.searchByName(keyword);
            DefaultTableModel model = (DefaultTableModel) view.getProductTable().getModel();
            model.setRowCount(0);
            for (Product p : list) {
                model.addRow(new Object[]{
                        p.getProductID(),
                        p.getName(),
                        p.getPrice(),
                        p.getQuantity(),
                        p.getCategory().getCategoryName()
                });
            }
        }

        @Override public void keyTyped(KeyEvent e) {}
        @Override public void keyPressed(KeyEvent e) {}
    }
}
