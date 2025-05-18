package controller;

import model.entity.Category;
import model.entity.Product;
import service.ProductService;
import utils.NumberUtil;
import utils.inputUtil;
import view.ProductView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.List;

public class ProductController {
	private ProductView view;
	private ProductService service;

	public ProductController(ProductView view, ProductService service) {
		this.view = view;
		this.service = service;

		this.view.addAddButtonListener(new AddListener());
		this.view.addEditButtonListener(new EditListener());
		this.view.addDeleteButtonListener(new DeleteListener());
		this.view.addPlusButtonListener(new PlusListener());
		this.view.addMinusButtonListener(new MinusListener());
		this.view.addSearchKeyListener(new SearchListener());
		setupTableSelectionListener();

		loadTable();
	}

	private void loadTable() {
		List<Product> list = service.getAll();
		DefaultTableModel model = (DefaultTableModel) view.getProductTable().getModel();
		model.setRowCount(0);
		for (Product p : list) {
			model.addRow(new Object[] { p.getProductID(), p.getName(), p.getPrice(), p.getQuantity(),
					p.getCategory().getCategoryName() });
		}
	}

	class AddListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String name = view.getProductName().trim();
			String priceStr = view.getPrice().trim();
			String quantityStr = view.getQuantity().trim();

			// Kiểm tra nếu có ô nào bị để trống
			if (name.isEmpty() || priceStr.isEmpty() || quantityStr.isEmpty() || priceStr.equals("Enter Price")
					|| quantityStr.equals("Enter Quantity")) {
				JOptionPane.showMessageDialog(view, "Vui lòng nhập đầy đủ thông tin sản phẩm!");
				return;
			}
			if (!inputUtil.isValidProductName(name)) {
				JOptionPane.showMessageDialog(view, "Tên sản phẩm không hợp lệ!");
				return;
			}
			double price;
			int quantity;

			// Kiểm tra định dạng số
			try {
				price = Double.parseDouble(priceStr);
				quantity = Integer.parseInt(quantityStr);
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(view, "Vui lòng nhập đúng định dạng số cho giá và số lượng!");
				return;
			}
			if (!validateQuantity(quantity)) {
				return; // Không cho thêm/xóa/sửa tiếp
			}
			Category selectedCategory = view.getCategory();
			if (service.isProductExist(name)) {
				JOptionPane.showMessageDialog(view, "Sản phẩm này đã tồn tại!");
//                view.getTable().clearSelection();
				return;
			}
			if (selectedCategory != null) {
				int id = selectedCategory.getCategoryID();
				String nameCat = selectedCategory.getCategoryName();
//                System.out.println("ID: " + id + ", Name: " + name);
				JOptionPane.showMessageDialog(view, "Thêm sản phẩm thành công!");
			}
			if (name == null || name.trim().isEmpty() || name.equals("Enter Price") || String.valueOf(price) == null
					|| String.valueOf(price).trim().isEmpty()) {
				JOptionPane.showMessageDialog(view, "Vui lòng nhập đầy đủ thông tin sản phẩm!");
				return;
			}
			if (selectedCategory == null) {
				JOptionPane.showMessageDialog(view, "Vui lòng chọn danh mục.");
				return;
			}
			if (service.isProductExist(name)) {
				JOptionPane.showMessageDialog(view, "Sản phẩm này đã tồn tại!");
//                view.getTable().clearSelection();
				return;
			}
			Product product = new Product(); // Không set ID
			product.setName(name);
			product.setPrice(price);
			product.setQuantity(quantity);
			product.setCategory(selectedCategory);
			System.out.println(product.getCategory().getCategoryID());
			service.insert(product);
			loadTable();
			view.clearFields();
		}
	}

	class EditListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			int row = view.getProductTable().getSelectedRow();
			if (row >= 0) {
				int id = (int) view.getProductTable().getValueAt(row, 0);
				String name = view.getProductName();
				String priceStr = view.getPrice();
				String quantityStr = view.getQuantity();

				// Kiểm tra nếu có ô nào bị để trống
				if (name == null || name.trim().isEmpty() || name.equals("Enter Name") || priceStr == null
						|| priceStr.trim().isEmpty() || priceStr.equals("Enter Price") || quantityStr == null
						|| quantityStr.trim().isEmpty() || quantityStr.equals("Enter Quantity")) {
					JOptionPane.showMessageDialog(view, "Vui lòng nhập đầy đủ thông tin sản phẩm!");
					return;
				}

				if (!inputUtil.isValidProductName(name)) {
					JOptionPane.showMessageDialog(view, "Tên sản phẩm không hợp lệ!");
					return;
				}
				double price;
				int quantity;
				// Kiểm tra định dạng số
				try {
					price = Double.parseDouble(priceStr);
					quantity = Integer.parseInt(quantityStr);
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(view, "Vui lòng nhập đúng định dạng số cho giá và số lượng!");
					return;
				}

				if (!validateQuantity(quantity)) {
					return; // Không cho thêm/xóa/sửa tiếp
				}
				Category selectedCategory = view.getCategory();
				if (selectedCategory == null) {
					JOptionPane.showMessageDialog(view, "Vui lòng chọn danh mục.");
					return;
				}

				Product p = new Product(id, name, price, quantity, selectedCategory);
				if (quantity > 0) {
					service.update(p);
					JOptionPane.showMessageDialog(view, "Cập nhật sản phẩm thành công");
				} else {
					service.delete(p);
					System.out.println("Xóa sản phẩm thành công");
				}

				loadTable();
				view.clearFields();
			} else {
				JOptionPane.showMessageDialog(view, "Vui lòng chọn sản phẩm để sửa.");
			}
		}
	}

	class DeleteListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			int row = view.getProductTable().getSelectedRow();
			if (row >= 0) {
				int confirm = JOptionPane.showConfirmDialog(view, "Bạn có chắc chắn muốn xóa sản phẩm này?", "Xác nhận",
						JOptionPane.YES_NO_OPTION);
				if (confirm == JOptionPane.YES_OPTION) {
					int id = (int) view.getProductTable().getValueAt(row, 0);
					Product p = service.selectByID(id);
					service.delete(p);
					loadTable();
					view.clearFields();
				}
			} else {
				JOptionPane.showMessageDialog(view, "Vui lòng chọn sản phẩm để xóa.");
			}
		}
	}

	class PlusListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				int quantity = Integer.parseInt(view.getQuantity());
				quantity++;
				view.setQuantity(String.valueOf(quantity));
			} catch (NumberFormatException ex) {
				view.setQuantity("1"); // Nếu nhập linh tinh thì reset về 1
			}
		}
	}

	class MinusListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				int quantity = Integer.parseInt(view.getQuantity());
				if (quantity > 0) {
					quantity--;
					view.setQuantity(String.valueOf(quantity));
				} else {
					showWarning("Số lượng không thể nhỏ hơn 0!");
				}
			} catch (NumberFormatException ex) {
				view.setQuantity("0"); // Nếu nhập linh tinh thì reset về 0
				showWarning("Giá trị nhập không hợp lệ! Đã đặt về 0.");
			}
		}
	}

	private boolean validateQuantity(int quantity) {
		try {
			if (!NumberUtil.isValidQuantity(quantity)) {
				showWarning("Số lượng không được nhỏ hơn 0!");
				view.setQuantity("0");
				return false;
			}
			return true;
		} catch (NumberFormatException ex) {
			showWarning("Vui lòng nhập số hợp lệ!");
			view.setQuantity("0");
			return false;
		}
	}

	private void showWarning(String message) {
		JOptionPane.showMessageDialog(view, message, "Cảnh báo", JOptionPane.WARNING_MESSAGE);
	}

	class SearchListener implements KeyListener {
		@Override
		public void keyReleased(KeyEvent e) {
			String keyword = view.getSearchKeyword();
			List<Product> list = service.searchByName(keyword);
			DefaultTableModel model = (DefaultTableModel) view.getProductTable().getModel();
			model.setRowCount(0);
			for (Product p : list) {
				model.addRow(new Object[] { p.getProductID(), p.getName(), p.getPrice(), p.getQuantity(),
						p.getCategory().getCategoryName() });
			}
		}

		@Override
		public void keyTyped(KeyEvent e) {
		}

		@Override
		public void keyPressed(KeyEvent e) {
		}
	}

	private void setupTableSelectionListener() {
		view.addTableSelectionListener(event -> {
			if (!event.getValueIsAdjusting()) {
				int selectedRow = view.getSelectedRow();
				if (selectedRow != -1) {
					fillFormFromSelectedRow(selectedRow);
				}
			}
		});
	}

	// Hàm này Controller tự xử lý
	private void fillFormFromSelectedRow(int selectedRow) {
		String productName = view.getValueAt(selectedRow, 1);
		String price = view.getValueAt(selectedRow, 2);
		String quantity = view.getValueAt(selectedRow, 3);
		String categoryName = view.getValueAt(selectedRow, 4);
		view.setProductName(productName);
		view.setPrice(price);
		view.setQuantity(quantity);
		view.setSelectedCategoryByName(categoryName);
	}
}