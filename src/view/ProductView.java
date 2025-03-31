package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ProductView extends JFrame {
    private JTextField txtProductID, txtProductName, txtPrice, txtCategoryID;
    private JButton btnAdd, btnEdit, btnDelete;
    private JTable table;
    private DefaultTableModel tableModel;

    public ProductView() {
        setTitle("Quản lý Sản phẩm");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(5, 2));

        panel.add(new JLabel("Mã sản phẩm:"));
        txtProductID = new JTextField();
        panel.add(txtProductID);

        panel.add(new JLabel("Tên sản phẩm:"));
        txtProductName = new JTextField();
        panel.add(txtProductName);

        panel.add(new JLabel("Giá:"));
        txtPrice = new JTextField();
        panel.add(txtPrice);

        panel.add(new JLabel("Mã danh mục:"));
        txtCategoryID = new JTextField();
        panel.add(txtCategoryID);

        btnAdd = new JButton("Thêm");
        btnEdit = new JButton("Sửa");
        btnDelete = new JButton("Xóa");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);

        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[]{"Mã SP", "Tên SP", "Giá", "Mã DM"});
        table = new JTable(tableModel);

        add(panel, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public JTextField getTxtProductID() {
        return txtProductID;
    }

    public JTextField getTxtProductName() {
        return txtProductName;
    }

    public JTextField getTxtPrice() {
        return txtPrice;
    }

    public JTextField getTxtCategoryID() {
        return txtCategoryID;
    }

    public JButton getBtnAdd() {
        return btnAdd;
    }

    public JButton getBtnEdit() {
        return btnEdit;
    }

    public JButton getBtnDelete() {
        return btnDelete;
    }

    public JTable getTable() {
        return table;
    }

    public void addProductToTable(String id, String name, String price, String categoryID) {
        tableModel.addRow(new Object[]{id, name, price, categoryID});
    }

    public void updateProductInTable(int row, String id, String name, String price, String categoryID) {
        tableModel.setValueAt(id, row, 0);
        tableModel.setValueAt(name, row, 1);
        tableModel.setValueAt(price, row, 2);
        tableModel.setValueAt(categoryID, row, 3);
    }

    public void removeProductFromTable(int row) {
        tableModel.removeRow(row);
    }

    public void clearForm() {
        txtProductID.setText("");
        txtProductName.setText("");
        txtPrice.setText("");
        txtCategoryID.setText("");
    }
}
