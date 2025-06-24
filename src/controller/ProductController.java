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

	// Pagination state
	private int currentPage = 1;
	private int pageSize = 10;
	private int totalPage = 1;
	private List<Product> currentProductList;

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

		// Pagination listeners
		this.view.getPrevPageButton().addActionListener(e -> {
			if (currentPage > 1) {
				currentPage--;
				updateTablePage();
			}
		});
		this.view.getNextPageButton().addActionListener(e -> {
			if (currentPage < totalPage) {
				currentPage++;
				updateTablePage();
			}
		});

		this.view.getShowDiscontinuedButton().addActionListener(e -> {
			currentProductList = service.getAllDiscontinued();
			currentPage = 1;
			updateTablePage();
		});

		this.view.getShowAvailableButton().addActionListener(e -> {
			currentProductList = service.getAll();
			currentPage = 1;
			updateTablePage();
		});
		this.view.getShowDiscontinuedButton().addActionListener(e -> {
			currentProductList = service.getAllDiscontinued();
			currentPage = 1;
			updateTablePage();
			view.getAddButton().setEnabled(false);
			view.getDeleteButton().setEnabled(false);
		});

		this.view.getShowAvailableButton().addActionListener(e -> {
			currentProductList = service.getAll();
			currentPage = 1;
			updateTablePage();
			view.getAddButton().setEnabled(true);
			view.getDeleteButton().setEnabled(true);
		});
		loadTable();
	}

	private void loadTable() {
		currentProductList = service.getAll();
		currentPage = 1;
		updateTablePage();
	}


	private void updateTablePage() {
		int total = currentProductList.size();
		totalPage = (int) Math.ceil((double) total / pageSize);
		if (totalPage == 0) totalPage = 1;
		if (currentPage > totalPage) currentPage = totalPage;
		if (currentPage < 1) currentPage = 1;
		int start = (currentPage - 1) * pageSize;
		int end = Math.min(start + pageSize, total);

		DefaultTableModel model = (DefaultTableModel) view.getProductTable().getModel();
		model.setRowCount(0);
		for (int i = start; i < end; i++) {
			Product p = currentProductList.get(i);
			model.addRow(new Object[]{
					p.getProductID(),
					p.getName(),
					p.getQuantity(), // Quantity column
					p.getPrice(),    // Price column
					p.getCategory().getCategoryName()
			});
		}

		view.getPageInfoLabel().setText("Page " + currentPage + "/" + totalPage);
		view.getPrevPageButton().setEnabled(currentPage > 1);
		view.getNextPageButton().setEnabled(currentPage < totalPage);
	}

	class AddListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String name = view.getProductName().trim();
			String priceStr = view.getPrice().trim();
			String quantityStr = view.getQuantity().trim();

			if (name.isEmpty() || priceStr.isEmpty() || quantityStr.isEmpty()
					|| priceStr.equals("Enter Price") || quantityStr.equals("Enter Quantity")) {
				JOptionPane.showMessageDialog(view, "Vui lòng nhập đầy đủ thông tin sản phẩm!");
				return;
			}
			if(!inputUtil.isValidProductName(name)){
				JOptionPane.showMessageDialog(view,"Tên sản phẩm không hợp lệ!");
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
				return;
			}
			Category selectedCategory = view.getCategory();
			if (service.isProductExist(name)) {
				JOptionPane.showMessageDialog(view, "Sản phẩm này đã tồn tại!");
				return;
			}
			if (selectedCategory == null) {
				JOptionPane.showMessageDialog(view, "Vui lòng chọn danh mục.");
				return;
			}
			if (service.isProductExist(name)) {
				JOptionPane.showMessageDialog(view, "Sản phẩm này đã tồn tại!");
				return;
			}
			Product product = new Product();
			product.setName(name);
			product.setPrice(price);
			product.setQuantity(quantity);
			product.setCategory(selectedCategory);

			service.insert(product);
			JOptionPane.showMessageDialog(view, "Thêm sản phẩm thành công!");
			loadTable();
			view.clearFields();
		}
	}

	// In ProductController.java, inside EditListener
	class EditListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			int row = view.getProductTable().getSelectedRow();
			if (row >= 0) {
				int id = (int) view.getProductTable().getValueAt(row, 0);
				String name = view.getProductName();
				String priceStr = view.getPrice();
				String quantityStr = view.getQuantity();

				if (name == null || name.trim().isEmpty() || name.equals("Enter Name")
						|| priceStr == null || priceStr.trim().isEmpty() || priceStr.equals("Enter Price")
						|| quantityStr == null || quantityStr.trim().isEmpty() || quantityStr.equals("Enter Quantity")) {
					JOptionPane.showMessageDialog(view, "Vui lòng nhập đầy đủ thông tin sản phẩm!");
					return;
				}
				if (!inputUtil.isValidProductName(name)) {
					JOptionPane.showMessageDialog(view, "Tên sản phẩm không hợp lệ!");
					return;
				}
				double price;
				int quantity;
				try {
					price = Double.parseDouble(priceStr);
					quantity = Integer.parseInt(quantityStr);
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(view, "Vui lòng nhập đúng định dạng số cho giá và số lượng!");
					return;
				}
				if (!validateQuantity(quantity)) {
					return;
				}
				Category selectedCategory = view.getCategory();
				if (selectedCategory == null) {
					JOptionPane.showMessageDialog(view, "Vui lòng chọn danh mục.");
					return;
				}

				// Check if current list is discontinued
				boolean isDiscontinuedView = currentProductList.size() > 0 &&
						"Ngừng bán".equals(currentProductList.get(0).gettrangThai());
				if (isDiscontinuedView && quantity < 1) {
					JOptionPane.showMessageDialog(view, "Khi sửa sản phẩm ngừng bán, số lượng phải từ 1 trở lên!");
					return;
				}

				Product p = new Product(id, name, price, quantity, selectedCategory);
				if (quantity > 0) {
					service.update(p);
					JOptionPane.showMessageDialog(view, "Cập nhật sản phẩm thành công");
				} else {
					service.delete(p);
					JOptionPane.showMessageDialog(view, "Cập nhật sản phẩm thành công");
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
				int confirm = JOptionPane.showConfirmDialog(view, "Bạn có chắc chắn muốn xóa sản phẩm này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
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
				view.setQuantity("1");
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
				view.setQuantity("0");
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

	// Trong ProductController.java, trong SearchListener
	class SearchListener implements KeyListener {
		@Override
		public void keyReleased(KeyEvent e) {
			String keyword = view.getSearchKeyword();
			boolean isDiscontinuedView = currentProductList.size() > 0 &&
					"Ngừng bán".equals(currentProductList.get(0).gettrangThai());
			if (isDiscontinuedView) {
				currentProductList = service.searchDiscontinuedByName(keyword);
			} else {
				currentProductList = service.searchByName(keyword);
			}
			currentPage = 1;
			updateTablePage();
		}
		@Override public void keyTyped(KeyEvent e) {}
		@Override public void keyPressed(KeyEvent e) {}
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
		JTable table = view.getProductTable();
		view.setProductName(table.getValueAt(selectedRow, 1).toString());
		view.setQuantity(table.getValueAt(selectedRow, 2).toString()); // Quantity
		view.setPrice(table.getValueAt(selectedRow, 3).toString());    // Price
		view.setSelectedCategoryByName(table.getValueAt(selectedRow, 4).toString());
	}
}