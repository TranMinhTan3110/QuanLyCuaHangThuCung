    package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.Border;
import javax.swing.table.*;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import view.UI.Hover;

public class UserView extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTextField idField, phoneField, usernameField, nameField, addressField, passwordField, searchTextField;
    private JComboBox<String> roleComboBox;
    private JTable table;
    private DefaultTableModel model;
    private JButton btnAdd, btnEdit, btnDel;

    // Pagination
    private int currentPage = 1;
    private int rowsPerPage = 21;
    private int totalRows = 0;
    private int totalPages = 0;
    private List<Object[]> allData = new ArrayList<>();
    private JLabel pageLabel;

    // Color scheme
    private final Color MAIN_BG = new Color(200, 220, 240);
    private final Color TOP_BG = new Color(200, 220, 240);
    private final Color TABLE_BG = new Color(200, 220, 240);
    private final Color PAGIN_BG = new Color(200, 220, 240);

    public UserView() {
        setLayout(null);
        setBounds(0, 0, 950, 750);
        setBackground(MAIN_BG);

        JPanel panel_top = new JPanel();
        panel_top.setBackground(TOP_BG);
        panel_top.setBounds(0, 0, 950, 230);
        panel_top.setLayout(null);
        panel_top.setBorder(null);
        add(panel_top);

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 16);
        Font inputFont = new Font("Segoe UI", Font.PLAIN, 16);

        Border inputBorder = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 200, 220), 1, true),
                new EmptyBorder(6, 12, 6, 12)
        );

        // Row 1
        JLabel lblName = new JLabel("Name");
        lblName.setFont(labelFont);
        lblName.setBounds(40, 30, 70, 36);
        panel_top.add(lblName);

        nameField = new JTextField();
        nameField.setFont(inputFont);
        nameField.setBounds(110, 30, 150, 36);
        nameField.setBorder(inputBorder);
        nameField.setBackground(Color.WHITE);
        panel_top.add(nameField);
        Hover.addPlaceholder(nameField, "Enter Name");

        JLabel lblAddress = new JLabel("Address");
        lblAddress.setFont(labelFont);
        lblAddress.setBounds(280, 30, 70, 36);
        panel_top.add(lblAddress);

        addressField = new JTextField();
        addressField.setFont(inputFont);
        addressField.setBounds(355, 30, 150, 36);
        addressField.setBorder(inputBorder);
        addressField.setBackground(Color.WHITE);
        panel_top.add(addressField);
        Hover.addPlaceholder(addressField, "Enter Address");

        JLabel lblPhone = new JLabel("Phone");
        lblPhone.setFont(labelFont);
        lblPhone.setBounds(525, 30, 70, 36);
        panel_top.add(lblPhone);

        phoneField = new JTextField();
        phoneField.setFont(inputFont);
        phoneField.setBounds(590, 30, 150, 36);
        phoneField.setBorder(inputBorder);
        phoneField.setBackground(Color.WHITE);
        panel_top.add(phoneField);
        Hover.addPlaceholder(phoneField, "Enter Phone");

        // Row 2
        JLabel lblUsername = new JLabel("UserName");
        lblUsername.setFont(labelFont);
        lblUsername.setBounds(40, 80, 70, 36);
        panel_top.add(lblUsername);

        usernameField = new JTextField();
        usernameField.setFont(inputFont);
        usernameField.setBounds(110, 80, 150, 36);
        usernameField.setBorder(inputBorder);
        usernameField.setBackground(Color.WHITE);
        panel_top.add(usernameField);
        Hover.addPlaceholder(usernameField, "Enter Username");

        JLabel lblPassword = new JLabel("Password");
        lblPassword.setFont(labelFont);
        lblPassword.setBounds(280, 80, 70, 36);
        panel_top.add(lblPassword);

        passwordField = new JTextField();
        passwordField.setFont(inputFont);
        passwordField.setBounds(355, 80, 150, 36);
        passwordField.setBorder(inputBorder);
        passwordField.setBackground(Color.WHITE);
        panel_top.add(passwordField);
        Hover.addPlaceholder(passwordField, "Enter Password");

        JLabel lblRole = new JLabel("Role");
        lblRole.setFont(labelFont);
        lblRole.setBounds(525, 80, 70, 36);
        panel_top.add(lblRole);

        roleComboBox = new JComboBox<>(new String[]{"Admin", "Employee"});
        roleComboBox.setFont(inputFont);
        roleComboBox.setBounds(590, 80, 150, 36);
        roleComboBox.setBackground(Color.WHITE);
        roleComboBox.setBorder(inputBorder);
        roleComboBox.setUI(new ModernComboBoxUI());
        panel_top.add(roleComboBox);

        // Buttons
        btnAdd = new JButton("Add");
        btnAdd.setIcon(new ImageIcon(UserView.class.getResource("/view/Icon/add_Icon.png")));
        btnAdd.setBounds(60, 135, 69, 63);
        btnAdd.setContentAreaFilled(false);
        btnAdd.setBorderPainted(false);
        btnAdd.setFocusPainted(false);
        btnAdd.setFont(labelFont);
        btnAdd.setHorizontalTextPosition(SwingConstants.CENTER);
        btnAdd.setVerticalTextPosition(SwingConstants.BOTTOM);
        Hover.addHoverButtonEffect(btnAdd, new Color(0, 102, 204), 0.8f);
        panel_top.add(btnAdd);

        btnEdit = new JButton("Edit");
        btnEdit.setIcon(new ImageIcon(UserView.class.getResource("/view/Icon/Edit_Icon.png")));
        btnEdit.setBounds(150, 135, 69, 63);
        btnEdit.setContentAreaFilled(false);
        btnEdit.setBorderPainted(false);
        btnEdit.setFocusPainted(false);
        btnEdit.setFont(labelFont);
        btnEdit.setHorizontalTextPosition(SwingConstants.CENTER);
        btnEdit.setVerticalTextPosition(SwingConstants.BOTTOM);
        Hover.addHoverButtonEffect(btnEdit, new Color(0, 102, 204), 0.8f);
        panel_top.add(btnEdit);

        btnDel = new JButton("Delete");
        btnDel.setIcon(new ImageIcon(UserView.class.getResource("/view/Icon/delete_Icon.png")));
        btnDel.setBounds(240, 135, 87, 63);
        btnDel.setContentAreaFilled(false);
        btnDel.setBorderPainted(false);
        btnDel.setFocusPainted(false);
        btnDel.setFont(labelFont);
        btnDel.setHorizontalTextPosition(SwingConstants.CENTER);
        btnDel.setVerticalTextPosition(SwingConstants.BOTTOM);
        Hover.addHoverButtonEffect(btnDel, new Color(0, 102, 204), 0.8f);
        panel_top.add(btnDel);

        // Search (rounded, like CustomerView)
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setBounds(690, 150, 200, 36);
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setBorder(inputBorder);
        Hover.roundPanel(searchPanel, 15, Color.WHITE, Color.GRAY);

        JLabel searchLabel = new JLabel(new ImageIcon(UserView.class.getResource("/view/Icon/Search_Icon.png")));
        searchLabel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        searchPanel.add(searchLabel, BorderLayout.WEST);

        searchTextField = new JTextField();
        searchTextField.setBorder(null);
        searchTextField.setFont(inputFont);
        searchTextField.setBackground(Color.WHITE);
        searchTextField.setForeground(new Color(40, 40, 40));
        searchTextField.setCaretColor(new Color(66, 133, 244));
        searchPanel.add(searchTextField, BorderLayout.CENTER);
        Hover.addPlaceholder(searchTextField, "search...");
        panel_top.add(searchPanel);

        // Table & table area background
        JPanel tablePanel = new JPanel(null);
        tablePanel.setBounds(0, 230, 950, 390);
        tablePanel.setBackground(TABLE_BG);
        add(tablePanel);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(0, 0, 950, 390);
        scrollPane.setBackground(TABLE_BG);
        scrollPane.getViewport().setBackground(TABLE_BG);
        scrollPane.setBorder(null);
        tablePanel.add(scrollPane);

        String[] columnNames = {"ID", "NAME", "USERNAME", "PASSWORD", "ADDRESS", "PHONE", "ROLE"};
        model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row)) c.setBackground(Color.WHITE);
                else c.setBackground(new Color(255, 255, 153)); // light yellow
                c.setForeground(new Color(40, 40, 40));
                return c;
            }
        };

        table.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        table.setRowHeight(32);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setSelectionBackground(new Color(255, 255, 153));
        table.setSelectionForeground(new Color(40, 40, 40));
        table.setBackground(Color.WHITE);

        JTableHeader th = table.getTableHeader();
        th.setFont(new Font("Segoe UI", Font.BOLD, 17));
        th.setBackground(new Color(240, 245, 250));
        th.setForeground(new Color(33, 70, 120));
        ((DefaultTableCellRenderer) th.getDefaultRenderer()).setHorizontalAlignment(JLabel.LEFT);

        scrollPane.setViewportView(table);

        // Pagination Panel
        JPanel paginationPanel = new JPanel(null);
        paginationPanel.setBounds(0, 620, 950, 56);
        paginationPanel.setBackground(PAGIN_BG);
        add(paginationPanel);

        int btnWidth = 120;
        int btnHeight = 38;
        int spacing = 10;
        int rightPadding = 24;
        int startX = 950 - rightPadding - (btnWidth * 2 + spacing + 80);

        JButton btnPrev = new JButton("Previous");
        btnPrev.setFont(new Font("Arial", Font.PLAIN, 15));
        btnPrev.setFocusPainted(false);
        btnPrev.setBackground(Color.WHITE);
        btnPrev.setBounds(startX, 10, btnWidth, btnHeight);
        btnPrev.setEnabled(false);
        Hover.addHoverButtonEffect(btnPrev, new Color(0, 102, 204), 0.8f);
        paginationPanel.add(btnPrev);

        pageLabel = new JLabel("Page 1/1");
        pageLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        pageLabel.setBounds(startX + btnWidth, 10, 80, btnHeight);
        pageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        paginationPanel.add(pageLabel);

        JButton btnNext = new JButton("Next");
        btnNext.setFont(new Font("Arial", Font.PLAIN, 15));
        btnNext.setFocusPainted(false);
        btnNext.setBackground(Color.WHITE);
        btnNext.setBounds(startX + btnWidth + 80 + spacing, 10, btnWidth, btnHeight);
        Hover.addHoverButtonEffect(btnNext, new Color(0, 102, 204), 0.8f);
        paginationPanel.add(btnNext);

        // Pagination events
        btnPrev.addActionListener(e -> {
            if (currentPage > 1) {
                currentPage--;
                updateTableForCurrentPage();
                btnPrev.setEnabled(currentPage > 1);
                btnNext.setEnabled(currentPage < totalPages);
            }
        });
        btnNext.addActionListener(e -> {
            if (currentPage < totalPages) {
                currentPage++;
                updateTableForCurrentPage();
                btnPrev.setEnabled(currentPage > 1);
                btnNext.setEnabled(currentPage < totalPages);
            }
        });
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
        pageLabel.setText("Page " + currentPage + "/" + (totalPages == 0 ? 1 : totalPages));
    }

    // --- Getters, setters, and utility methods ---

    public String getIdField() { return idField != null ? idField.getText() : ""; }
    public JTextField getIdFieldJ() { return idField; }
    public String getPhoneField() { return phoneField.getText(); }
    public String getUsernameField() { return usernameField.getText(); }
    public String getNameField() { return nameField.getText(); }
    public String getAddressField() { return addressField.getText(); }
    public String getPasswordField() { return passwordField.getText(); }
    public String getRoleField() { return roleComboBox.getSelectedItem().toString(); }
    public String getSearchTextField() { return searchTextField.getText(); }
    public int getSelectedRow() { return table.getSelectedRow(); }
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
    public void addUserToTable(String id, String name, String username, String password, String address, String phone, String role) {
        allData.add(new Object[]{id, name, username, password, address, phone, role});
        totalRows = allData.size();
        totalPages = (int) Math.ceil((double) totalRows / rowsPerPage);
        updateTableForCurrentPage();
    }
    public void updateUserInTable(int row, String id, String name, String username, String password, String address, String phone, String role) {
        int globalIndex = (currentPage - 1) * rowsPerPage + row;
        if (globalIndex >= 0 && globalIndex < allData.size()) {
            allData.set(globalIndex, new Object[]{id, name, username, password, address, phone, role});
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
    public void setAddButtonListener(ActionListener listener) { btnAdd.addActionListener(listener); }
    public void setEditButtonListener(ActionListener listener) { btnEdit.addActionListener(listener); }
    public void setDeleteButtonListener(ActionListener listener) { btnDel.addActionListener(listener); }
    public void setSearchListener(DocumentListener listener) { searchTextField.getDocument().addDocumentListener(listener); }
    public JTable getTable() { return table; }
    public void setUserData(String id, String name, String username, String password, String address, String phone, String role) {
        if (idField != null) idField.setText(id);
        nameField.setText(name);
        usernameField.setText(username);
        passwordField.setText(password);
        addressField.setText(address);
        phoneField.setText(phone);
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
        phoneField.setText(phone);
        usernameField.setText(username);
        nameField.setText(name);
        addressField.setText(address);
        passwordField.setText(password);
        roleComboBox.setSelectedItem(role);
    }
}