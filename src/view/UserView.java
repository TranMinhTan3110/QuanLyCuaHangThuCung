package view;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class UserView extends JPanel {
    private static final long serialVersionUID = 1L;
    private JTextField idField, phoneField, usernameField, nameField, addressField, passwordField;
    private JTable table;
    private DefaultTableModel model;
    private JComboBox<String> roleComboBox;
    private JButton addButton, editButton, deleteButton;

    public UserView() {
        setupUI();
    }

    private void setupUI() {
        setLayout(null);
        setBounds(0, 0, 950, 750);
        setupTopPanel();
        setupTable();
    }

    private void setupTopPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(255, 255, 223));
        panel.setBounds(0, 0, 950, 240);
        add(panel);

        String[] labels = {"ID:", "Phone:", "Username:", "Name:", "Address:", "Password:", "Role:"};
        JTextField[] fields = new JTextField[6];
        String[] placeholders = {"Enter ID", "Enter Phone", "Enter Username", "Enter Name", "Enter Address", "Enter Password"};

        int x1 = 95, x2 = 203, y = 46, width = 125, height = 32, gap = 50;
        for (int i = 0; i < 6; i++) {
            JLabel label = new JLabel(labels[i]);
            label.setFont(new Font("Arial", Font.PLAIN, 16));
            label.setBounds(x1, y, 100, 25);
            panel.add(label);

            fields[i] = new JTextField();
            fields[i].setBounds(x2, y, width, height);
            addPlaceholder(fields[i], placeholders[i]);
            panel.add(fields[i]);

            y += (i == 2) ? -100 : gap;
            if (i == 2) {
                x1 = 419;
                x2 = 530;
            }
        }
        idField = fields[0]; phoneField = fields[1]; usernameField = fields[2];
        nameField = fields[3]; addressField = fields[4]; passwordField = fields[5];

        JLabel roleLabel = new JLabel(labels[6]);
        roleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        roleLabel.setBounds(95, 190, 100, 25);
        panel.add(roleLabel);

        roleComboBox = new JComboBox<>(new String[]{"Admin", "User"});
        roleComboBox.setFont(new Font("Arial", Font.PLAIN, 16));
        roleComboBox.setBounds(203, 190, 125, 32);
        panel.add(roleComboBox);

        addButton = createButton("Add", 727, 40);
        editButton = createButton("Edit", 727, 93);
        deleteButton = createButton("Delete", 727, 146);

        panel.add(addButton);
        panel.add(editButton);
        panel.add(deleteButton);
    }

    private JButton createButton(String text, int x, int y) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        button.setBackground(new Color(255, 255, 204));
        button.setBounds(x, y, 88, 32);
        button.setFocusPainted(false);
        addHoverEffect(button, new Color(128, 128, 100), new Color(255, 255, 204));
        return button;
    }

    private void setupTable() {
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setFont(new Font("Arial", Font.PLAIN, 16));
        scrollPane.setBounds(0, 244, 950, 500);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                "Manager Users",
                TitledBorder.CENTER,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 16),
                Color.BLACK
        ));
        add(scrollPane);

        model = new DefaultTableModel(new String[]{"ID", "Name", "Phone", "Username", "Password", "Address", "Role"}, 0);
        table = new JTable(model);
        scrollPane.setViewportView(table);
    }

    private void addHoverEffect(JButton button, Color hoverColor, Color defaultColor) {
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) { button.setBackground(hoverColor); }
            public void mouseExited(java.awt.event.MouseEvent evt) { button.setBackground(defaultColor); }
        });
    }

    private void addPlaceholder(JTextField textField, String placeholder) {
        textField.setText(placeholder);
        textField.setForeground(Color.GRAY);
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setText(placeholder);
                    textField.setForeground(Color.GRAY);
                }
            }
        });
    }

    // Getters for Controller
    public String getIdField() { return idField.getText(); }
    public String getPhoneField() { return phoneField.getText(); }
    public String getUsernameField() { return usernameField.getText(); }
    public String getNameField() { return nameField.getText(); }
    public String getAddressField() { return addressField.getText(); }
    public String getPasswordField() { return passwordField.getText(); }
    public String getRoleField() { return roleComboBox.getSelectedItem().toString(); }
    public int getSelectedRow() { return table.getSelectedRow(); }
    public String getSelectedUserId() {
        int row = getSelectedRow();
        if (row != -1) return table.getValueAt(row, 0).toString();
        return null;
    }

    public void clearFields() {
        JTextField[] fields = {idField, phoneField, usernameField, nameField, addressField, passwordField};
        for (JTextField field : fields) field.setText("");
        roleComboBox.setSelectedIndex(0);
    }

    // Methods to manipulate JTable
    public void addUserToTable(String id, String name, String phone, String username, String password, String address, String role) {
        model.addRow(new Object[]{id, name, phone, username, password, address, role});
    }

    public void updateUserInTable(int row, String id, String name, String phone, String username, String password, String address, String role) {
        model.setValueAt(id, row, 0);
        model.setValueAt(name, row, 1);
        model.setValueAt(phone, row, 2);
        model.setValueAt(username, row, 3);
        model.setValueAt(password, row, 4);
        model.setValueAt(address, row, 5);
        model.setValueAt(role, row, 6);
    }

    public void removeUserFromTable(int row) {
        model.removeRow(row);
    }

    // Setters for controller to attach action listeners
    public void setAddButtonListener(ActionListener listener) { addButton.addActionListener(listener); }
    public void setEditButtonListener(ActionListener listener) { editButton.addActionListener(listener); }
    public void setDeleteButtonListener(ActionListener listener) { deleteButton.addActionListener(listener); }
}
