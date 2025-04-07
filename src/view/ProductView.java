package view;

import model.entity.Category;
import model.entity.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ProductView extends JPanel {
    private JTextField txtName, txtPrice, txtSearch;
    private JSpinner spinnerQuantity;
    private JComboBox<Category> comboCategory;
    private JButton btnAdd, btnDelete, btnUpdate, btnSearch;
    private JTable productTable;
    private DefaultTableModel tableModel;

    public ProductView() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);

        initTopPanel();       // Form input
        initMiddlePanel();    // Buttons + Search
        initTablePanel();     // Table
    }

    private void initTopPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);

        JLabel lblName = new JLabel("Tên sản phẩm:");
        txtName = new JTextField(10);

        JLabel lblPrice = new JLabel("Giá:");
        txtPrice = new JTextField(10);

        JLabel lblQuantity = new JLabel("Số lượng:");
        spinnerQuantity = new JSpinner(new SpinnerNumberModel(0, 0, 1000, 1));

        JLabel lblCategory = new JLabel("Loại hàng:");
        comboCategory = new JComboBox<>();
        comboCategory.addItem(new Category(1, "Đồ ăn"));
        comboCategory.addItem(new Category(2, "Thức uống"));
        comboCategory.addItem(new Category(3, "Đồ dùng"));
        // Row 1
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(lblName, gbc);
        gbc.gridx = 1;
        panel.add(txtName, gbc);
        gbc.gridx = 2;
        panel.add(lblQuantity, gbc);
        gbc.gridx = 3;
        panel.add(spinnerQuantity, gbc);

        // Row 2
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(lblPrice, gbc);
        gbc.gridx = 1;
        panel.add(txtPrice, gbc);
        gbc.gridx = 2;
        panel.add(lblCategory, gbc);
        gbc.gridx = 3;
        panel.add(comboCategory, gbc);

        add(panel, BorderLayout.NORTH);
    }

    private void initMiddlePanel() {
        JPanel middlePanel = new JPanel(new BorderLayout());
        middlePanel.setBackground(Color.WHITE);

        // Buttons
        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(Color.WHITE);
        btnAdd = createButton("Thêm", "plus.png");
        btnDelete = createButton("Xóa", "delete.png");
        btnUpdate = createButton("Sửa", "edit.png");

        btnPanel.add(btnAdd);
        btnPanel.add(btnDelete);
        btnPanel.add(btnUpdate);
        middlePanel.add(btnPanel, BorderLayout.WEST);

        // Search
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.setBackground(Color.WHITE);
        txtSearch = new JTextField(15);
        btnSearch = createButton("", "search.png");
        searchPanel.add(new JLabel("Tìm kiếm:"));
        searchPanel.add(txtSearch);
        searchPanel.add(btnSearch);
        middlePanel.add(searchPanel, BorderLayout.EAST);

        add(middlePanel, BorderLayout.CENTER);
    }

    private void initTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);
        JLabel lblTitle = new JLabel("📋 Danh sách sản phẩm");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitle.setHorizontalAlignment(JLabel.CENTER);

        tableModel = new DefaultTableModel(new String[]{"ID", "Tên", "Giá", "Số lượng", "Loại hàng"}, 0);
        productTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(productTable);

        tablePanel.add(lblTitle, BorderLayout.NORTH);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        add(tablePanel, BorderLayout.SOUTH);
    }

    private JButton createButton(String text, String iconFileName) {
        JButton button = new JButton(text);
        try {
            ImageIcon icon = new ImageIcon("src/icon/" + iconFileName);
            button.setIcon(icon);
        } catch (Exception e) {
            System.out.println("Không tìm thấy icon: " + iconFileName);
        }
        button.setFocusPainted(false);
        return button;
    }

    // ========== Getter cho controller dùng ==========
    public String getProductName() { return txtName.getText().trim(); }

    public double getProductPrice() {
        try {
            return Double.parseDouble(txtPrice.getText().trim());
        } catch (Exception e) {
            return 0;
        }
    }

    public int getQuantity() {
        return (int) spinnerQuantity.getValue();
    }

    public Category getSelectedCategory() {
        return (Category) comboCategory.getSelectedItem();
    }

    public String getSearchText() {
        return txtSearch.getText().trim();
    }

    public JTable getProductTable() {
        return productTable;
    }

    public JButton getBtnAdd() { return btnAdd; }

    public JButton getBtnDelete() { return btnDelete; }

    public JButton getBtnUpdate() { return btnUpdate; }

    public JButton getBtnSearch() { return btnSearch; }

    // ========== Dùng để hiển thị dữ liệu và thông báo ==========
    public void updateProductTable(List<Product> list) {
        tableModel.setRowCount(0);
        for (Product p : list) {
            tableModel.addRow(new Object[]{
                    p.getProductID(),
                    p.getName(),
                    p.getPrice(),
                    p.getQuantity(),
                    p.getCategory().getCategoryName()
            });
        }
    }

    public void setCategoryList(List<Category> categories) {
        comboCategory.removeAllItems();
        for (Category c : categories) {
            comboCategory.addItem(c);
        }
    }

    public void clearFields() {
        txtName.setText("");
        txtPrice.setText("");
        spinnerQuantity.setValue(0);
        if (comboCategory.getItemCount() > 0) {
            comboCategory.setSelectedIndex(0);
        }
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
}
