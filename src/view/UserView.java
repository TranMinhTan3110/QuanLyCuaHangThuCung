package view;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import view.UI.Hover;

import java.awt.*;
import java.awt.event.*;

public class UserView extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField idField, phoneField, usernameField, nameField, addressField, passwordField;
	private JComboBox<String> roleComboBox;
	private JTable table;
	private DefaultTableModel model;
	private JButton btnAdd, btnEdit, btnDel;

	public UserView() {
		setLayout(null);
		setBounds(0, 0, 950, 750);

		JPanel panel_top = new JPanel();
		panel_top.setBackground(new Color(255, 255, 223));
		panel_top.setBounds(0, 0, 950, 240);
		panel_top.setLayout(null);
		add(panel_top);

		JLabel lblPhone = new JLabel("Phone:");
		lblPhone.setFont(new Font("Arial", Font.PLAIN, 16));
		lblPhone.setBounds(641, 61, 68, 21);
		panel_top.add(lblPhone);

		phoneField = new JTextField();
		phoneField.setBounds(742, 60, 125, 32);
		Hover.addPlaceholder(phoneField, "Enter Phone");
		Hover.roundTextField(phoneField, 15, Color.WHITE, Color.LIGHT_GRAY);
		panel_top.add(phoneField);

		JLabel lblUsername = new JLabel("UserName:");
		lblUsername.setFont(new Font("Arial", Font.PLAIN, 16));
		lblUsername.setBounds(95, 118, 88, 21);
		panel_top.add(lblUsername);

		usernameField = new JTextField();
		usernameField.setBounds(189, 115, 125, 32);
		Hover.addPlaceholder(usernameField, "Enter Username");
		Hover.roundTextField(usernameField, 15, Color.WHITE, Color.LIGHT_GRAY);
		panel_top.add(usernameField);

		JLabel lblName = new JLabel("Name:");
		lblName.setFont(new Font("Arial", Font.PLAIN, 16));
		lblName.setBounds(95, 61, 67, 21);
		panel_top.add(lblName);

		nameField = new JTextField();
		nameField.setBounds(189, 60, 125, 32);
		Hover.addPlaceholder(nameField, "Enter Name");
		Hover.roundTextField(nameField, 15, Color.WHITE, Color.LIGHT_GRAY);
		panel_top.add(nameField);

		JLabel lblAddress = new JLabel("Address:");
		lblAddress.setFont(new Font("Arial", Font.PLAIN, 16));
		lblAddress.setBounds(358, 61, 68, 21);
		panel_top.add(lblAddress);

		addressField = new JTextField();
		addressField.setBounds(469, 60, 125, 32);
		Hover.addPlaceholder(addressField, "Enter Address");
		Hover.roundTextField(addressField, 15, Color.WHITE, Color.LIGHT_GRAY);
		panel_top.add(addressField);

		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setFont(new Font("Arial", Font.PLAIN, 16));
		lblPassword.setBounds(358, 118, 88, 21);
		panel_top.add(lblPassword);

		passwordField = new JTextField();
		passwordField.setBounds(469, 115, 125, 32);
		Hover.addPlaceholder(passwordField, "Enter Password");
		Hover.roundTextField(passwordField, 15, Color.WHITE, Color.LIGHT_GRAY);
		panel_top.add(passwordField);

		JLabel lblRole = new JLabel("Role");
		lblRole.setFont(new Font("Arial", Font.PLAIN, 16));
		lblRole.setBounds(641, 118, 88, 21);
		panel_top.add(lblRole);

		roleComboBox = new JComboBox<>(new String[] { "Admin", "Employee" });
		roleComboBox.setFont(new Font("Arial", Font.PLAIN, 16));
		roleComboBox.setBounds(742, 115, 125, 32);
		panel_top.add(roleComboBox);

		btnAdd = new JButton("Add");
		btnAdd.setIcon(new ImageIcon(ProductView.class.getResource("/view/Icon/add_Icon.png")));
		btnAdd.setBackground(new Color(255, 255, 223));
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnAdd.setFont(new Font("Arial", Font.PLAIN, 16));
		btnAdd.setBounds(20, 167, 69, 63);
		btnAdd.setFocusPainted(false);
		btnAdd.setBorderPainted(false);
		btnAdd.setContentAreaFilled(false);
		btnAdd.setHorizontalTextPosition(SwingConstants.CENTER);
		btnAdd.setVerticalTextPosition(SwingConstants.BOTTOM);
		panel_top.add(btnAdd);
		Hover.addHoverButtonEffect(btnAdd, new Color(0, 102, 204), 0.8f);

		btnEdit = new JButton("Edit");
		btnEdit.setIcon(new ImageIcon(ProductView.class.getResource("/view/Icon/Edit_Icon.png")));
		btnEdit.setBackground(new Color(255, 255, 204));
		btnEdit.setFont(new Font("Arial", Font.PLAIN, 16));
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnEdit.setBounds(110, 167, 69, 63);
		btnEdit.setFocusPainted(false);
		btnEdit.setBorderPainted(false);
		btnEdit.setContentAreaFilled(false);
		btnEdit.setHorizontalTextPosition(SwingConstants.CENTER);
		btnEdit.setVerticalTextPosition(SwingConstants.BOTTOM);
		panel_top.add(btnEdit);
		Hover.addHoverButtonEffect(btnEdit, new Color(0, 102, 204), 0.8f);

		btnDel = new JButton("Delete");
		btnDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnDel.setIcon(new ImageIcon(ProductView.class.getResource("/view/Icon/delete_Icon.png")));
		btnDel.setBackground(new Color(255, 255, 204));
		btnDel.setFont(new Font("Arial", Font.PLAIN, 16));
		btnDel.setBounds(189, 167, 87, 63);
		btnDel.setFocusPainted(false);
		btnDel.setBorderPainted(false);
		btnDel.setContentAreaFilled(false);
		btnDel.setHorizontalTextPosition(SwingConstants.CENTER);
		btnDel.setVerticalTextPosition(SwingConstants.BOTTOM);
		panel_top.add(btnDel);
		Hover.addHoverButtonEffect(btnDel, new Color(0, 102, 204), 0.8f);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setFont(new Font("Arial", Font.PLAIN, 16));
		scrollPane.setBounds(0, 244, 950, 500);
		scrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK),
				"Manager Users", TitledBorder.CENTER, TitledBorder.TOP, new Font("Arial", Font.BOLD, 16), Color.BLACK));
		add(scrollPane);

//		model = new DefaultTableModel(new String[]{"ID", "Name", "Phone", "Username", "Password", "Address", "Role"}, 0);
		String[] columnNames = { "ID", "Name", "Phone", "Username", "Password", "Address", "Role" };
		model = new DefaultTableModel(columnNames, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		table = new JTable(model);
		scrollPane.setViewportView(table);
		Hover.customizeTableHeader(table);
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
	}

	// Getters for Controller
	public String getIdField() {
		return idField.getText();
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

	public int getSelectedRow() {
		return table.getSelectedRow();
	}

	public String getSelectedUserId() {
		int row = getSelectedRow();
		if (row != -1)
			return table.getValueAt(row, 0).toString();
		return null;
	}

	public void clearFields() {

		JTextField[] fields = { phoneField, usernameField, nameField, addressField, passwordField };
		for (JTextField field : fields)
			field.setText("");
		roleComboBox.setSelectedIndex(0);
	}

	public void addUserToTable(String id, String name, String phone, String username, String password, String address,
			String role) {
		model.addRow(new Object[] { id, name, phone, username, password, address, role });
	}

	public void updateUserInTable(int row, String id, String name, String phone, String username, String password,
			String address, String role) {
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
		btnAdd.addActionListener(listener);
	}

	public void setEditButtonListener(ActionListener listener) {
		btnEdit.addActionListener(listener);
	}

	public void setDeleteButtonListener(ActionListener listener) {
		btnDel.addActionListener(listener);
	}

	public JTable getTable() {
		return table;
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