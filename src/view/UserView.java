package view;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class UserView extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField idField, phoneField, usernameField, nameField, addressField, passwordField;
	private JComboBox<String> roleComboBox;
	private JTable table;
	private DefaultTableModel model;
	private JButton addButton, editButton, deleteButton;

	public UserView() {
		setLayout(null);
		setBounds(0, 0, 950, 750);

		JPanel panel_top = new JPanel();
		panel_top.setBackground(new Color(255, 255, 223));
		panel_top.setBounds(0, 0, 950, 240);
		panel_top.setLayout(null);
		add(panel_top);

		JLabel lblId = new JLabel("ID:");
		lblId.setFont(new Font("Arial", Font.PLAIN, 16));
		lblId.setBounds(95, 46, 67, 21);
		panel_top.add(lblId);

		idField = new JTextField();
		idField.setBounds(203, 43, 125, 32);
		addPlaceholder(idField, "Enter ID");
		panel_top.add(idField);

		JLabel lblPhone = new JLabel("Phone:");
		lblPhone.setFont(new Font("Arial", Font.PLAIN, 16));
		lblPhone.setBounds(95, 96, 68, 21);
		panel_top.add(lblPhone);

		phoneField = new JTextField();
		phoneField.setBounds(203, 93, 125, 32);
		addPlaceholder(phoneField, "Enter Phone");
		panel_top.add(phoneField);

		JLabel lblUsername = new JLabel("UserName:");
		lblUsername.setFont(new Font("Arial", Font.PLAIN, 16));
		lblUsername.setBounds(95, 148, 88, 21);
		panel_top.add(lblUsername);

		usernameField = new JTextField();
		usernameField.setBounds(203, 145, 125, 32);
		addPlaceholder(usernameField, "Enter Username");
		panel_top.add(usernameField);

		JLabel lblName = new JLabel("Name:");
		lblName.setFont(new Font("Arial", Font.PLAIN, 16));
		lblName.setBounds(419, 46, 67, 21);
		panel_top.add(lblName);

		nameField = new JTextField();
		nameField.setBounds(530, 43, 125, 32);
		addPlaceholder(nameField, "Enter Name");
		panel_top.add(nameField);

		JLabel lblAddress = new JLabel("Address:");
		lblAddress.setFont(new Font("Arial", Font.PLAIN, 16));
		lblAddress.setBounds(419, 96, 68, 21);
		panel_top.add(lblAddress);

		addressField = new JTextField();
		addressField.setBounds(530, 93, 125, 32);
		addPlaceholder(addressField, "Enter Address");
		panel_top.add(addressField);

		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setFont(new Font("Arial", Font.PLAIN, 16));
		lblPassword.setBounds(419, 148, 88, 21);
		panel_top.add(lblPassword);

		passwordField = new JTextField();
		passwordField.setBounds(530, 145, 125, 32);
		addPlaceholder(passwordField, "Enter Password");
		panel_top.add(passwordField);

		JLabel lblRole = new JLabel("Role");
		lblRole.setFont(new Font("Arial", Font.PLAIN, 16));
		lblRole.setBounds(95, 190, 88, 21);
		panel_top.add(lblRole);

		roleComboBox = new JComboBox<>(new String[]{"Admin", "Employee"});
		roleComboBox.setFont(new Font("Arial", Font.PLAIN, 16));
		roleComboBox.setBounds(203, 192, 125, 32);
		panel_top.add(roleComboBox);

		addButton = new JButton("Add");
		addButton.setBackground(new Color(255, 255, 204));
		addButton.setFont(new Font("Arial", Font.PLAIN, 16));
		addButton.setBounds(727, 40, 88, 32);
		addButton.setFocusPainted(false);
		addHoverEffect(addButton, new Color(128, 128, 100), new Color(255, 255, 204));
		panel_top.add(addButton);

		editButton = new JButton("Edit");
		editButton.setBackground(new Color(255, 255, 204));
		editButton.setFont(new Font("Arial", Font.PLAIN, 16));
		editButton.setBounds(727, 93, 88, 32);
		editButton.setFocusPainted(false);
		addHoverEffect(editButton, new Color(128, 128, 100), new Color(255, 255, 204));
		panel_top.add(editButton);

		deleteButton = new JButton("Delete");
		deleteButton.setBackground(new Color(255, 255, 204));
		deleteButton.setFont(new Font("Arial", Font.PLAIN, 16));
		deleteButton.setBounds(727, 145, 88, 32);
		deleteButton.setFocusPainted(false);
		addHoverEffect(deleteButton, new Color(128, 128, 100), new Color(255, 255, 204));
		panel_top.add(deleteButton);

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

//		model = new DefaultTableModel(new String[]{"ID", "Name", "Phone", "Username", "Password", "Address", "Role"}, 0);
		String[] columnNames = {"ID", "Name", "Phone", "Username", "Password", "Address", "Role"};
		model = new DefaultTableModel(columnNames, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};


		table = new JTable(model);
		scrollPane.setViewportView(table);
	}

	private void addHoverEffect(JButton button, Color hoverColor, Color defaultColor) {
		button.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				button.setBackground(hoverColor);
			}

			public void mouseExited(MouseEvent evt) {
				button.setBackground(defaultColor);
			}
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
	public String getIdField() {
		return idField.getText();
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

	public int getSelectedRow() {
		return table.getSelectedRow();
	}

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

	public void setAddButtonListener(ActionListener listener) {
		addButton.addActionListener(listener);
	}

	public void setEditButtonListener(ActionListener listener) {
		editButton.addActionListener(listener);
	}

	public void setDeleteButtonListener(ActionListener listener) {
		deleteButton.addActionListener(listener);
	}

	public JTable getTable() {
		return table;
	}

	public void setEmployeeData(String id, String name, String phone, String address, String username, String password, String role) {

		idField.setText(id);
		phoneField.setText(phone);
		usernameField.setText(username);
		nameField.setText(name);
		addressField.setText(address);
		passwordField.setText(password);
		roleComboBox.setSelectedItem(role);
	}
}