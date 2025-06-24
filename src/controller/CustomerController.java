package controller;

import model.entity.Customer;
import model.entity.Role;
import model.entity.User;
import service.CustomerService;
import model.entity.User;
import service.UserService;
import utils.RoleUtil;
import utils.inputUtil;
import view.CustomerView;
import view.MainView;
import view.UserView;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class CustomerController {
	private CustomerView customerView;
	private CustomerService customerService;
	private MainView mainView;
	private UserView userView;
	private UserService userService;
	private Customer currentCustomer;
	private String currentStatus = "hoạt động";

	public CustomerController(CustomerView customerView, CustomerService customerService) {
		this.customerService = customerService;
		this.customerView = customerView;
		initController();
		loadEmployeesFromDB();
		this.customerView.setAddButtonListener(e -> addCustomer());
		this.customerView.setEditButtonListener(e -> editCustomer());
		this.customerView.setDeleteButtonListener(e -> deleteCustomer());
		this.customerView.setSearchListener(new searchCustomer());
		this.customerView.setActiveButtonListener(e -> showActiveCustomers());
		this.customerView.setInactiveButtonListener(e -> showInactiveCustomers());
	}


	private void showActiveCustomers() {
		List<Customer> customers = customerService.getByStatus("hoạt động");
		List<Object[]> tableRows = new ArrayList<>();
		for (Customer customer : customers) {
			tableRows.add(new Object[]{
					customer.getId(),
					customer.getName(),
					customer.getPhone(),
					customer.getAddress(),
					customer.getLoyaltyPoints(),
					customer.getMembershipLevel()
			});
		}
		customerView.setCustomerTableData(tableRows);
		customerView.setSaveEnabled(true);
		customerView.setDeleteEnabled(true);
		currentStatus = "hoạt động"; // Add this field to track status
	}

	private void showInactiveCustomers() {
		List<Customer> customers = customerService.getByStatus("ngưng hoạt động");
		List<Object[]> tableRows = new ArrayList<>();
		for (Customer customer : customers) {
			tableRows.add(new Object[]{
					customer.getId(),
					customer.getName(),
					customer.getPhone(),
					customer.getAddress(),
					customer.getLoyaltyPoints(),
					customer.getMembershipLevel()
			});
		}
		customerView.setCustomerTableData(tableRows);
		customerView.setSaveEnabled(false);
		customerView.setDeleteEnabled(false);
		currentStatus = "ngưng hoạt động"; // Add this field to track status
	}

	private void initController() {
		customerView.getTable().getSelectionModel().addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting() && customerView.getTable().getSelectedRow() != -1) {
				loadSelectedEmployeeIntoForm();
			}
		});
	}

	private void loadSelectedEmployeeIntoForm() {
		int selectedRow = customerView.getTable().getSelectedRow();
		if (selectedRow != -1) {
			try {
				String idStr = customerView.getTable().getValueAt(selectedRow, 0).toString().trim();
				String name = customerView.getTable().getValueAt(selectedRow, 1).toString().trim();
				String phone = customerView.getTable().getValueAt(selectedRow, 2).toString().trim();
				String address = customerView.getTable().getValueAt(selectedRow, 3).toString().trim();
				String scoreStr = customerView.getTable().getValueAt(selectedRow, 4).toString().trim();

				int id = Integer.parseInt(idStr);
				int score = Integer.parseInt(scoreStr);

				customerView.setEmployeeData(idStr, name, phone, address, score);

				currentCustomer = customerService.selectedByID(id);

			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(customerView,
						"Dữ liệu số không hợp lệ (ID hoặc Score)!\nChi tiết: " + ex.getMessage(), "Lỗi",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void loadEmployeesFromDB() {
		ArrayList<Customer> customers = new ArrayList<>();
		customers = customerService.getAll();
		for (Customer customer : customers) {
			customerView.addCustomerToTable(String.valueOf(customer.getId()), customer.getName(), customer.getPhone(),
					customer.getAddress(), customerService.getPoint(customer), customerService.getRank(customer));
		}
	}

	public void addCustomer() {
		String name = (customerView.getName_textField());
		String phone = customerView.getPhone_textField();
		String address = customerView.getAddress_textField();

		if (name == null || name.trim().isEmpty() || name.equals("Enter Name") || phone == null
				|| phone.trim().isEmpty() || phone.equals("Enter Phone") || address == null || address.trim().isEmpty()
				|| address.equals("Enter Address")) {

			JOptionPane.showMessageDialog(customerView, "Vui lòng nhập đầy đủ thông tin Customer!");
			return;
		}

		if (name.trim().isEmpty()) {
			JOptionPane.showMessageDialog(customerView, "Tên không được để trống!");
			return;
		}
		if (phone.trim().isEmpty()) {
			JOptionPane.showMessageDialog(customerView, "Số điện thoại không được để trống!");
			return;
		}

		if (address.trim().isEmpty()) {
			JOptionPane.showMessageDialog(customerView, "Địa chỉ không được để trống!");
			return;
		}

		if (!inputUtil.isValidPhoneNumber(phone)) {
			JOptionPane.showMessageDialog(customerView, "Số điện thoại không hợp lệ!");
			return;
		}

		if (customerService.checkPhone(phone)) {
			JOptionPane.showMessageDialog(customerView, "Số điện thoại đã tồn tại!");
			return;
		}

		Customer customer = new Customer();
		customer.setName(name);
		customer.setPhone(phone);
		customer.setAddress(address);
		int loyaltyPoints = Integer.parseInt(customerView.getScore_textField());
		String membershipLevel = convertPointMembershipLV(loyaltyPoints);
		customer.setLoyaltyPoints(loyaltyPoints);
		customer.setMembershipLevel(membershipLevel);

		if (customerService.insert(customer)) {
			refreshCustomerTableFromDB();
			JOptionPane.showMessageDialog(customerView, "Thêm khách hàng thành công");
			customerView.clear();
		} else {
			JOptionPane.showMessageDialog(customerView, "Thêm khách hàng thất bại");
		}
	}

	private void refreshCustomerTableFromDB() {
		customerView.clear();
		ArrayList<Customer> customers = customerService.getAll();
		for (Customer customer : customers) {
			customerView.addCustomerToTable(
					String.valueOf(customer.getId()),
					customer.getName(),
					customer.getPhone(),
					customer.getAddress(),
					customerService.getPoint(customer),
					customerService.getRank(customer)
			);
		}
	}

	public void editCustomer() {
		int selectedRow = customerView.getSeclectedRow();
		if (selectedRow == -1) {
			JOptionPane.showMessageDialog(customerView, "Chọn khách hàng cần chỉnh sửa!");
			return;
		}

		String name = customerView.getName_textField();
		String phone = customerView.getPhone_textField();
		String address = customerView.getAddress_textField();

		if (name.trim().isEmpty()) {
			JOptionPane.showMessageDialog(customerView, "Tên không được để trống!");
			return;
		}
		if (phone.trim().isEmpty()) {
			JOptionPane.showMessageDialog(customerView, "Số điện thoại không được để trống!");
			return;
		}
		if (address.trim().isEmpty()) {
			JOptionPane.showMessageDialog(customerView, "Địa chỉ không được để trống!");
			return;
		}

		if (!currentCustomer.getPhone().equals(phone)) {
			if (!inputUtil.isValidPhoneNumber(phone)) {
				JOptionPane.showMessageDialog(customerView, "Số điện thoại không hơp lệ!");
				return;
			}
		}
		if (!currentCustomer.getAddress().equals(address)) {
			if (!inputUtil.isValidAddress(address)) {
				JOptionPane.showMessageDialog(customerView, "Địa chỉ không hơp lệ!");
				return;
			}
		}
		if (!currentCustomer.getName().equals(name)) {
			if (!inputUtil.isValidName(name)) {
				JOptionPane.showMessageDialog(customerView, "Tên không hơp lệ!");
				return;
			}
		}
		Customer customer = new Customer();
		customer.setName(name);
		customer.setPhone(phone);
		customer.setAddress(address);
		int loyaltyPoints = Integer.parseInt(customerView.getScore_textField());
		String membershipLevel = convertPointMembershipLV(loyaltyPoints);
		customer.setLoyaltyPoints(loyaltyPoints);
		customer.setMembershipLevel(membershipLevel);
		int id = Integer.parseInt(customerView.getTable().getValueAt(selectedRow, 0).toString());
		customer.setId(id);
		if (customerService.update(customer)) {
			JOptionPane.showMessageDialog(customerView, "Cập nhật  khách hàng thành công");
			customerView.updateCustomerInTable(selectedRow, String.valueOf(customer.getId()), name, phone, address,
					String.valueOf(loyaltyPoints), membershipLevel);
			customerView.getTable().clearSelection();
		} else {
			JOptionPane.showMessageDialog(customerView, "Cập nhật khách hàng không thành công");
		}
	}

	public void deleteCustomer() {
		try {
			int selectedRow = customerView.getSeclectedRow();
			if (selectedRow == -1) {
				JOptionPane.showMessageDialog(customerView, "Chọn khách hàng cần xóa");
				return;
			}
			{
				String idStr = (String) customerView.getTable().getValueAt(selectedRow, 0);
				int id = Integer.parseInt(idStr);
				String phone = customerView.getPhone_textField();
				String address = customerView.getAddress_textField();
				String name = customerView.getName_textField();
				Customer customer = new Customer(id, name, phone, address);
				if (customerService.delete(customer)) {
					JOptionPane.showMessageDialog(customerView, "Xóa khách hàng thành công");
					customerView.removeCustomerFromTable(selectedRow);
				} else {
					JOptionPane.showMessageDialog(customerView, "Xóa khách hàng thất bại");
				}
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(customerView, "ID không hợp lệ");
		}
	}

	public class searchCustomer implements DocumentListener {
		@Override
		public void insertUpdate(DocumentEvent e) { search(); }
		@Override
		public void removeUpdate(DocumentEvent e) { search(); }
		@Override
		public void changedUpdate(DocumentEvent e) { search(); }

		public void search() {
			String str = customerView.getSearch_textField().toLowerCase();
			List<Customer> customers = new ArrayList<>();
			if (str.matches("\\d+")) {
				customers = customerService.searchByCustomerPhoneAndStatus(str, currentStatus);
			} else {
				customers = customerService.searchByCustomerNameAndStatus(str, currentStatus);
			}
			DefaultTableModel model = (DefaultTableModel) customerView.getCustomerTable().getModel();
			model.setRowCount(0);
			for (Customer c : customers) {
				model.addRow(new Object[] { c.getId(), c.getName(), c.getPhone(), c.getAddress(), c.getLoyaltyPoints(),
						c.getMembershipLevel() });
			}
		}
	}

	private String convertPointMembershipLV(int loyaltyPoints) {
		String membershipLevel;
		if (loyaltyPoints >= 1000)
			membershipLevel = "Platinum";
		else if (loyaltyPoints >= 500)
			membershipLevel = "Gold";
		else if (loyaltyPoints >= 100)
			membershipLevel = "Silver";
		else
			membershipLevel = "Basic";

		return membershipLevel;
	}
}