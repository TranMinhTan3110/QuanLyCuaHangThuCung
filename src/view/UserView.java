package view;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import view.UI.Hover;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class UserView extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTextField idField, phoneField, usernameField, nameField, addressField, passwordField, searchTextField;
    private JComboBox<String> roleComboBox;
    private JTable table;
    private DefaultTableModel model;
    private JButton btnAdd, btnEdit, btnDel;
    
    // Biến phân trang
    private int currentPage = 1;
    private int rowsPerPage = 21; // Số hàng trên mỗi trang
    private int totalRows = 0;
    private int totalPages = 0;
    private List<Object[]> allData = new ArrayList<>();
    private JLabel pageLabel;

    public UserView() {
        setLayout(null);
        setBounds(0, 0, 950, 750);

        // Panel trên cùng
        JPanel panel_top = new JPanel();
        panel_top.setBackground(new Color(200, 220, 240)); // Màu giống CustomerView
        panel_top.setBounds(0, 0, 950, 240);
        panel_top.setLayout(null);
        add(panel_top);

        // Nhãn và trường nhập liệu
        JLabel lblPhone = new JLabel("Phone:");
        lblPhone.setFont(new Font("Arial", Font.PLAIN, 16));
        lblPhone.setBounds(648, 55, 100, 25);
        panel_top.add(lblPhone);

        phoneField = new JTextField();
        phoneField.setBounds(758, 57, 100, 25);
        Hover.addPlaceholder(phoneField, "Enter Phone");
//        Hover.roundTextField(phoneField, 15, Color.WHITE, Color.LIGHT_GRAY);
        panel_top.add(phoneField);

        JLabel lblUsername = new JLabel("UserName:");
        lblUsername.setFont(new Font("Arial", Font.PLAIN, 16));
        lblUsername.setBounds(80, 109, 100, 25);
        panel_top.add(lblUsername);

        usernameField = new JTextField();
        usernameField.setBounds(181, 111, 100, 25);
        Hover.addPlaceholder(usernameField, "Enter Username");
//        Hover.roundTextField(usernameField, 15, Color.WHITE, Color.LIGHT_GRAY);
        panel_top.add(usernameField);

        JLabel lblName = new JLabel("Name:");
        lblName.setFont(new Font("Arial", Font.PLAIN, 16));
        lblName.setBounds(80, 55, 100, 25);
        panel_top.add(lblName);

        nameField = new JTextField();
        nameField.setBounds(181, 57, 100, 25);
        Hover.addPlaceholder(nameField, "Enter Name");
//        Hover.roundTextField(nameField, 15, Color.WHITE, Color.LIGHT_GRAY);
        panel_top.add(nameField);

        JLabel lblAddress = new JLabel("Address:");
        lblAddress.setFont(new Font("Arial", Font.PLAIN, 16));
        lblAddress.setBounds(349, 55, 100, 25);
        panel_top.add(lblAddress);

        addressField = new JTextField();
        addressField.setBounds(467, 57, 100, 25);
        Hover.addPlaceholder(addressField, "Enter Address");
//        Hover.roundTextField(addressField, 15, Color.WHITE, Color.LIGHT_GRAY);
        panel_top.add(addressField);

        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setFont(new Font("Arial", Font.PLAIN, 16));
        lblPassword.setBounds(349, 109, 100, 25);
        panel_top.add(lblPassword);

        passwordField = new JTextField();
        passwordField.setBounds(467, 111, 100, 25);
        Hover.addPlaceholder(passwordField, "Enter Password");
//        Hover.roundTextField(passwordField, 15, Color.WHITE, Color.LIGHT_GRAY);
        panel_top.add(passwordField);

        JLabel lblRole = new JLabel("Role:");
        lblRole.setFont(new Font("Arial", Font.PLAIN, 16));
        lblRole.setBounds(648, 109, 100, 25);
        panel_top.add(lblRole);

        roleComboBox = new JComboBox<>(new String[]{"Admin", "Employee"});
        roleComboBox.setFont(new Font("Arial", Font.PLAIN, 16));
        roleComboBox.setBounds(758, 111, 100, 25);
        panel_top.add(roleComboBox);

        // Nút chức năng
        btnAdd = new JButton("Thêm");
        btnAdd.setIcon(new ImageIcon(UserView.class.getResource("/view/Icon/save_Icon.png")));
        btnAdd.setBackground(new Color(144, 238, 144)); // Màu xanh lá nhạt
        btnAdd.setFont(new Font("Arial", Font.PLAIN, 16));
        btnAdd.setBounds(20, 164, 80, 57);
        btnAdd.setFocusPainted(false);
        btnAdd.setBorderPainted(false);
        btnAdd.setContentAreaFilled(false);
        btnAdd.setHorizontalTextPosition(SwingConstants.CENTER);
        btnAdd.setVerticalTextPosition(SwingConstants.BOTTOM);
        panel_top.add(btnAdd);
        Hover.addHoverButtonEffect(btnAdd, new Color(0, 100, 0), 0.8f);

        btnEdit = new JButton("Sửa");
        btnEdit.setIcon(new ImageIcon(UserView.class.getResource("/view/Icon/Edit_Icon.png")));
        btnEdit.setBackground(new Color(173, 216, 230)); // Màu xanh nhạt
        btnEdit.setFont(new Font("Arial", Font.PLAIN, 16));
        btnEdit.setBounds(100, 164, 80, 57);
        btnEdit.setFocusPainted(false);
        btnEdit.setBorderPainted(false);
        btnEdit.setContentAreaFilled(false);
        btnEdit.setHorizontalTextPosition(SwingConstants.CENTER);
        btnEdit.setVerticalTextPosition(SwingConstants.BOTTOM);
        panel_top.add(btnEdit);
        Hover.addHoverButtonEffect(btnEdit, new Color(0, 0, 139), 0.8f);

        btnDel = new JButton("Xóa");
        btnDel.setIcon(new ImageIcon(UserView.class.getResource("/view/Icon/delete_Icon.png")));
        btnDel.setBackground(new Color(255, 182, 193)); // Màu hồng nhạt
        btnDel.setFont(new Font("Arial", Font.PLAIN, 16));
        btnDel.setBounds(181, 164, 80, 57);
        btnDel.setFocusPainted(false);
        btnDel.setBorderPainted(false);
        btnDel.setContentAreaFilled(false);
        btnDel.setHorizontalTextPosition(SwingConstants.CENTER);
        btnDel.setVerticalTextPosition(SwingConstants.BOTTOM);
        panel_top.add(btnDel);
        Hover.addHoverButtonEffect(btnDel, new Color(139, 0, 0), 0.8f);

        // Panel tìm kiếm
        ImageIcon searchIcon = new ImageIcon(UserView.class.getResource("/view/Icon/Search_Icon.png"));
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setBounds(718, 191, 200, 30);
        searchPanel.setBackground(Color.WHITE);
        JLabel searchLabel = new JLabel(searchIcon);
        searchLabel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        searchPanel.add(searchLabel, BorderLayout.WEST);
        Hover.roundPanel(searchPanel, 15, Color.WHITE, Color.GRAY);
        panel_top.add(searchPanel);
        searchTextField = new JTextField();
        searchPanel.add(searchTextField, BorderLayout.CENTER);
        searchTextField.setBorder(null);
        searchTextField.setColumns(10);
        Hover.addPlaceholder(searchTextField, "Search...");

        // Nút Dark Mode
        JButton DMButton = new JButton("DARK MODE");
        DMButton.setBackground(new Color(240, 240, 240));
        DMButton.setFont(new Font("Tahoma", Font.PLAIN, 10));
        DMButton.setBounds(288, 182, 120, 30);
        DMButton.setFocusPainted(false);
        panel_top.add(DMButton);

        // Bảng người dùng
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(0, 250, 940, 395);
        scrollPane.setFont(new Font("Arial", Font.PLAIN, 16));
        scrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK),
                "Danh sách người dùng", TitledBorder.CENTER, TitledBorder.TOP, new Font("Arial", Font.BOLD, 16), Color.BLACK));
        add(scrollPane);

        String[] columnNames = {"ID", "NAME", "PHONE", "USERNAME", "PASSWORD", "ADDRESS", "ROLE"};
        model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(model);
        scrollPane.setViewportView(table);
        Hover.customizeTableHeader(table);

        // Sự kiện nhấp chuột vào bảng
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                if (row >= 0 && row < table.getRowCount()) {
                    showDetailsDialog(row);
                }
            }
        });

        // Panel phân trang
        JPanel paginationPanel = new JPanel();
        paginationPanel.setBounds(0, 643, 950, 50);
        paginationPanel.setBackground(new Color(240, 240, 240));
        add(paginationPanel);
        paginationPanel.setLayout(null);

        JButton btnPrev = new JButton("Prev");
        btnPrev.setBounds(679, 10, 70, 30);
        btnPrev.setFont(new Font("Arial", Font.PLAIN, 16));
        btnPrev.addActionListener(e -> {
            if (currentPage > 1) {
                currentPage--;
                updateTableForCurrentPage();
            }
        });
        btnPrev.setFocusPainted(false);
        paginationPanel.add(btnPrev);

        pageLabel = new JLabel("Page");
        pageLabel.setBounds(766, 10, 70, 30);
        pageLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        paginationPanel.add(pageLabel);

        JButton btnNext = new JButton("Next");
        btnNext.setBounds(846, 10, 70, 30);
        btnNext.setFont(new Font("Arial", Font.PLAIN, 16));
        btnNext.addActionListener(e -> {
            if (currentPage < totalPages) {
                currentPage++;
                updateTableForCurrentPage();
            }
        });
        btnNext.setFocusPainted(false);
        paginationPanel.add(btnNext);
    }


    private void updateTableForCurrentPage() {
        model.setRowCount(0);
        int startIndex = (currentPage - 1) * rowsPerPage;
        int endIndex = Math.min(startIndex + rowsPerPage, allData.size());

        for (int i = startIndex; i < endIndex; i++) {
            model.addRow(allData.get(i));
        }
        updatePaginationLabel();
    }

    private void updatePaginationLabel() {
        pageLabel.setText("Page " + currentPage + "/" + totalPages);
    }

    private void showDetailsDialog(int row) {
        Object[] data = allData.get((currentPage - 1) * rowsPerPage + row);

        JDialog dialog = new JDialog((JDialog) null, "User Details", true);
        dialog.getContentPane().setLayout(null);
        dialog.setSize(400, 350);
        dialog.setLocationRelativeTo(this);

        JLabel lblId = new JLabel("ID: " + data[0]);
        lblId.setBounds(20, 20, 250, 20);
        dialog.getContentPane().add(lblId);

        JLabel lblName = new JLabel("Name: " + data[1]);
        lblName.setBounds(20, 50, 250, 20);
        dialog.getContentPane().add(lblName);

        JLabel lblPhone = new JLabel("Phone: " + data[2]);
        lblPhone.setBounds(20, 80, 250, 20);
        dialog.getContentPane().add(lblPhone);

        JLabel lblUsername = new JLabel("UserName: " + data[3]);
        lblUsername.setBounds(20, 110, 250, 20);
        dialog.getContentPane().add(lblUsername);

        JLabel lblPassword = new JLabel("Password: " + data[4]);
        lblPassword.setBounds(20, 140, 250, 20);
        dialog.getContentPane().add(lblPassword);

        JLabel lblAddress = new JLabel("Address: " + data[5]);
        lblAddress.setBounds(20, 170, 250, 20);
        dialog.getContentPane().add(lblAddress);

        JLabel lblRole = new JLabel("Role: " + data[6]);
        lblRole.setBounds(20, 200, 250, 20);
        dialog.getContentPane().add(lblRole);

        JButton btnEDit = new JButton("Edit");
        btnEDit.setBounds(200, 270, 80, 25);
        btnEDit.setFocusPainted(false);
        dialog.getContentPane().add(btnEDit);

        JButton btnDelete = new JButton("Delete");
        btnDelete.setBounds(300, 270, 80, 25);
        btnDelete.setFocusPainted(false);
        dialog.getContentPane().add(btnDelete);

        dialog.setVisible(true);
    }

    // Getters và Setters
    public String getIdField() {
        return idField != null ? idField.getText() : "";
    }

    public JTextField getIdFieldJ() {
        return idField;
    }

    public String getPhoneField() {
        return phoneField.getText();
    }

    public String getUsernameField() {
        return usernameField.getText();
    }

    public String getNameField() {
        return nameField.getText();
    }

    public String getAddressField() {
        return addressField.getText();
    }

    public String getPasswordField() {
        return passwordField.getText();
    }

    public String getRoleField() {
        return roleComboBox.getSelectedItem().toString();
    }

    public String getSearchTextField() {
        return searchTextField.getText();
    }

    public int getSelectedRow() {
        return table.getSelectedRow();
    }

    public String getSelectedUserId() {
        int row = getSelectedRow();
        if (row != -1) return table.getValueAt(row, 0).toString();
        return null;
    }

    public void clearFields() {
        JTextField[] fields = {phoneField, usernameField, nameField, addressField, passwordField};
        for (JTextField field : fields) field.setText("");
        roleComboBox.setSelectedIndex(0);
    }

    public void addUserToTable(String id, String name, String phone, String username, String password, String address, String role) {
        allData.add(new Object[]{id, name, phone, username, password, address, role});
        totalRows = allData.size();
        totalPages = (int) Math.ceil((double) totalRows / rowsPerPage);
        updateTableForCurrentPage();
    }

    public void updateUserInTable(int row, String id, String name, String phone, String username, String password, String address, String role) {
        int globalIndex = (currentPage - 1) * rowsPerPage + row;
        if (globalIndex >= 0 && globalIndex < allData.size()) {
            allData.set(globalIndex, new Object[]{id, name, phone, username, password, address, role});
            updateTableForCurrentPage();
        }
    }

    public void removeUserFromTable(int row) {
        int globalIndex = (currentPage - 1) * rowsPerPage + row;
        if (globalIndex >= 0 && globalIndex < allData.size()) {
            allData.remove(globalIndex);
            totalRows = allData.size();
            totalPages = (int) Math.ceil((double) totalRows / rowsPerPage);
            if (currentPage > totalPages && currentPage > 1) currentPage--;
            updateTableForCurrentPage();
        }
    }

    public void setAddButtonListener(ActionListener listener) {
        btnAdd.addActionListener(listener);
    }

    public void setEditButtonListener(ActionListener listener) {
        btnEdit.addActionListener(listener);
    }

    public void setDeleteButtonListener(ActionListener listener) {
        btnDel.addActionListener(listener);
    }

    public void setSearchListener(DocumentListener listener) {
        searchTextField.getDocument().addDocumentListener(listener);
    }

    public JTable getTable() {
        return table;
    }

    public void setUserData(String id, String name, String phone, String username, String password, String address, String role) {
        if (idField != null) idField.setText(id);
        nameField.setText(name);
        phoneField.setText(phone);
        usernameField.setText(username);
        passwordField.setText(password);
        addressField.setText(address);
        roleComboBox.setSelectedItem(role);
    }

    public void clear() {
        allData.clear();
        totalRows = 0;
        totalPages = 0;
        currentPage = 1;
        updateTableForCurrentPage();
        clearFields();
    }

    public void setEmployeeData(String id, String name, String phone, String address, String username, String password,
            String role) {
//		idField.setText(id);
        phoneField.setText(phone);
        usernameField.setText(username);
        nameField.setText(name);
        addressField.setText(address);
        passwordField.setText(password);
        roleComboBox.setSelectedItem(role);
    }
}