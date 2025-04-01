package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class ProductView extends JPanel {
    private JTextField txtProductID, txtProductName, txtPrice, txtCategoryID;
    private JButton btnAdd, btnEdit, btnDelete;
    private JTable table;
    private DefaultTableModel tableModel;

    public ProductView() {
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

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

    // ===== Thêm phương thức để đăng ký sự kiện =====
    public void addAddProductListener(ActionListener listener) {
        btnAdd.addActionListener(listener);
    }

    public void addEditProductListener(ActionListener listener) {
        btnEdit.addActionListener(listener);
    }

    public void addDeleteProductListener(ActionListener listener) {
        btnDelete.addActionListener(listener);
    }

    public JTextField getTxtProductID() { return txtProductID; }
    public JTextField getTxtProductName() { return txtProductName; }
    public JTextField getTxtPrice() { return txtPrice; }
    public JTextField getTxtCategoryID() { return txtCategoryID; }
    public JButton getBtnAdd() { return btnAdd; }
    public JButton getBtnEdit() { return btnEdit; }
    public JButton getBtnDelete() { return btnDelete; }
    public JTable getTable() { return table; }

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

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
}
