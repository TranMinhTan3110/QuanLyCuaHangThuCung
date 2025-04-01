package view;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class UserView extends JPanel {
    //bản test *****************************************************************************
    // Các trường nhập liệu
    private JTextField txtId;
    private JTextField txtName;
    private JTextField txtPhone;
    private JTextField txtAddress;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JComboBox<String> comboRole;

    private JButton btnAdd;
    private JButton btnEdit;
    private JButton btnDelete;

    // Bảng hiển thị danh sách nhân viên
    private DefaultTableModel tableModel;
    private JTable table;

    public UserView() {
        setLayout(new BorderLayout());

        // ===== Header =====
        JLabel header = new JLabel("Manage Employees", SwingConstants.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 20));
        add(header, BorderLayout.NORTH);

        // ===== Form nhập liệu =====
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Hàng 0: ID
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1;
        txtId = new JTextField(10);
        formPanel.add(txtId, gbc);

        // Hàng 0: Name
        gbc.gridx = 2;
        formPanel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 3;
        txtName = new JTextField(10);
        formPanel.add(txtName, gbc);

        // Hàng 1: Phone
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Phone:"), gbc);
        gbc.gridx = 1;
        txtPhone = new JTextField(10);
        formPanel.add(txtPhone, gbc);

        // Hàng 1: Address
        gbc.gridx = 2;
        formPanel.add(new JLabel("Address:"), gbc);
        gbc.gridx = 3;
        txtAddress = new JTextField(10);
        formPanel.add(txtAddress, gbc);

        // Hàng 2: Username
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        txtUsername = new JTextField(10);
        formPanel.add(txtUsername, gbc);

        // Hàng 2: Password
        gbc.gridx = 2;
        formPanel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 3;
        txtPassword = new JPasswordField(10);
        formPanel.add(txtPassword, gbc);

        // Hàng 3: Role
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(new JLabel("Role:"), gbc);
        gbc.gridx = 1;
        comboRole = new JComboBox<>(new String[]{"ADMIN", "EMPLOYEE"});
        formPanel.add(comboRole, gbc);

        // Hàng 3: Các nút CRUD
        gbc.gridx = 2;
        gbc.gridy = 3;
        btnAdd = new JButton("Add");
        formPanel.add(btnAdd, gbc);
        gbc.gridx = 3;
        btnEdit = new JButton("Edit");
        formPanel.add(btnEdit, gbc);
        gbc.gridx = 4;
        btnDelete = new JButton("Delete");
        formPanel.add(btnDelete, gbc);

        add(formPanel, BorderLayout.SOUTH);

        // ===== Bảng hiển thị nhân viên =====
        String[] columns = {"ID", "Name", "Phone", "Address", "Username", "Password", "Role"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }

    // Các getter cho các trường nhập liệu
    public String getEmployeeId() {
        return txtId.getText().trim();
    }

    public String getEmployeeName() {
        return txtName.getText().trim();
    }

    public String getEmployeePhone() {
        return txtPhone.getText().trim();
    }

    public String getEmployeeAddress() {
        return txtAddress.getText().trim();
    }

    public String getEmployeeUsername() {
        return txtUsername.getText().trim();
    }

    public String getEmployeePassword() {
        return new String(txtPassword.getPassword());
    }

    public String getEmployeeRole() {
        return (String) comboRole.getSelectedItem();
    }

    // Các hàm thao tác bảng
    public void addEmployeeToTable(String id, String name, String phone, String address, String username, String password, String role) {
        tableModel.addRow(new Object[]{id, name, phone, address, username, password, role});
    }

    public void setEmployeeData(String id, String name, String phone, String address, String username, String password, String role) {
        txtId.setText(id);
        txtName.setText(name);
        txtPhone.setText(phone);
        txtAddress.setText(address);
        txtUsername.setText(username);
        txtPassword.setText(password);
        comboRole.setSelectedItem(role);
    }

    public void updateEmployeeInTable(int row, String id, String name, String phone, String address, String username, String password, String role) {
        tableModel.setValueAt(id, row, 0);
        tableModel.setValueAt(name, row, 1);
        tableModel.setValueAt(phone, row, 2);
        tableModel.setValueAt(address, row, 3);
        tableModel.setValueAt(username, row, 4);
        tableModel.setValueAt(password, row, 5);
        tableModel.setValueAt(role, row, 6);
    }

    public void removeEmployeeFromTable(int row) {
        tableModel.removeRow(row);
    }


    public JTable getTable() {
        return table;
    }

    public void clearForm() {
        txtId.setText("");
        txtName.setText("");
        txtPhone.setText("");
        txtAddress.setText("");
        txtUsername.setText("");
        txtPassword.setText("");
        comboRole.setSelectedIndex(0);

    }

    // Các getter cho nút để Controller đăng ký sự kiện
    public JButton getBtnAdd() {
        return btnAdd;
    }

    public JButton getBtnEdit() {
        return btnEdit;
    }

    public JButton getBtnDelete() {
        return btnDelete;
    }
    //đóng bản test *****************************************************************************
}
