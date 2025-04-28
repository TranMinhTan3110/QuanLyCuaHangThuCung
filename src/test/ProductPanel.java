package test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import dao.ProductDAO;
import model.entity.Category;
import model.entity.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ProductPanel extends JPanel {
    private JTextField txtName, txtPrice, txtQuantity, txtCategory, txtSearch;
    private JButton btnAdd, btnDelete, btnEdit, btnIncrease, btnDecrease, btnSearch;
    private JTable productTable;

    public ProductPanel() {
        setLayout(new BorderLayout());

        // Menu trái
        JPanel menuPanel = new JPanel();
        menuPanel.setBackground(new Color(255, 255, 200));
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setPreferredSize(new Dimension(150, getHeight()));

        JLabel userInfo = new JLabel("<html><br>Name:<br>ID:</html>");
        userInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuPanel.add(userInfo);
        menuPanel.add(Box.createVerticalStrut(20));

        String[] menuItems = {"Pets", "Admin", "Product", "Customers", "Bills", "Home"};
        for (String item : menuItems) {
            JButton btn = new JButton(item);
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            menuPanel.add(btn);
            menuPanel.add(Box.createVerticalStrut(10));
        }

        add(menuPanel, BorderLayout.WEST);

        // Panel nhập liệu & thao tác
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(null);
        inputPanel.setPreferredSize(new Dimension(800, 250));

        JLabel lblName = new JLabel("Name:");
        lblName.setBounds(200, 20, 100, 25);
        inputPanel.add(lblName);
        txtName = new JTextField();
        txtName.setBounds(260, 20, 150, 25);
        inputPanel.add(txtName);

        JLabel lblPrice = new JLabel("Giá:");
        lblPrice.setBounds(200, 60, 100, 25);
        inputPanel.add(lblPrice);
        txtPrice = new JTextField();
        txtPrice.setBounds(260, 60, 150, 25);
        inputPanel.add(txtPrice);

        JLabel lblQuantity = new JLabel("Số lượng:");
        lblQuantity.setBounds(440, 20, 100, 25);
        inputPanel.add(lblQuantity);
        txtQuantity = new JTextField("0");
        txtQuantity.setBounds(520, 20, 50, 25);
        inputPanel.add(txtQuantity);

        btnDecrease = new JButton("➖");
        btnDecrease.setBounds(580, 20, 45, 25);
        inputPanel.add(btnDecrease);

        btnIncrease = new JButton("➕");
        btnIncrease.setBounds(630, 20, 45, 25);
        inputPanel.add(btnIncrease);

        JLabel lblCategory = new JLabel("Loại Hàng:");
        lblCategory.setBounds(440, 60, 100, 25);
        inputPanel.add(lblCategory);
        txtCategory = new JTextField();
        txtCategory.setBounds(520, 60, 150, 25);
        inputPanel.add(txtCategory);

        // Nút chức năng
        btnAdd = new JButton("➕ Add");
        btnAdd.setBounds(260, 110, 100, 30);
        inputPanel.add(btnAdd);

        btnDelete = new JButton("➖ Xóa");
        btnDelete.setBounds(370, 110, 100, 30);
        inputPanel.add(btnDelete);

        btnEdit = new JButton("✏️ Sửa");
        btnEdit.setBounds(480, 110, 100, 30);
        inputPanel.add(btnEdit);

        // Tìm kiếm
        JLabel lblSearch = new JLabel("Tìm kiếm");
        lblSearch.setBounds(520, 150, 70, 30);
        inputPanel.add(lblSearch);

        txtSearch = new JTextField();
        txtSearch.setBounds(600, 150, 150, 30);
        inputPanel.add(txtSearch);

        btnSearch = new JButton("🔍");
        btnSearch.setBounds(760, 150, 50, 30);
        inputPanel.add(btnSearch);

        add(inputPanel, BorderLayout.NORTH);

        // Bảng sản phẩm
        String[] columns = {"ID", "Name", "Giá", "Số lượng", "Loại hàng"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        productTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(productTable);
        add(scrollPane, BorderLayout.CENTER);
    }

    // Getter cho controller gắn sự kiện
    public JTextField getTxtName() { return txtName; }
    public JTextField getTxtPrice() { return txtPrice; }
    public JTextField getTxtQuantity() { return txtQuantity; }
    public JTextField getTxtCategory() { return txtCategory; }
    public JTextField getTxtSearch() { return txtSearch; }
    public JButton getBtnAdd() { return btnAdd; }
    public JButton getBtnDelete() { return btnDelete; }
    public JButton getBtnEdit() { return btnEdit; }
    public JButton getBtnIncrease() { return btnIncrease; }
    public JButton getBtnDecrease() { return btnDecrease; }
    public JButton getBtnSearch() { return btnSearch; }
    public JTable getProductTable() { return productTable; }
}

