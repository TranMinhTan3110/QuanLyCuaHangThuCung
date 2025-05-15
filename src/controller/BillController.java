package controller;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import respository.dao.OrderDAO;
import respository.dao.OrderDetailDAO;
import respository.dao.UserSession;
import model.entity.*;
import service.CustomerService;
import service.PetService;
import service.ProductService;
import view.BillView;

import service.BillService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BillController {
	private BillView billView;
	private BillService billService;
	private ProductService productService;
	private PetService petService;
	private CustomerService customerService;
	private int orderIDExport;
	private String customerNameExprt;
	private String staffNameEx;
	private BigDecimal totalAmountEx;
	private LocalDateTime createdDateEX;
	private List<OrderDetail> orderDetailsEX;

	public BillController(BillView view, BillService service, ProductService productService, PetService petService,
			CustomerService customerService) {
		this.billView = view;
		this.billService = service;
		this.productService = productService;
		this.petService = petService;
		this.customerService = customerService;
//
		addEventHandlers();
		loadProductTable();
		loadPetTable();
		loadCustomerTable();
	}

	private void addEventHandlers() {
		billView.getProductButton().addActionListener(e -> {
			System.out.println("load product table");
			toggleTable("product");
			loadProductTable();
			System.out.println("load product table done");
		});

		billView.getPetButton().addActionListener(e -> {
			System.out.println("load pet table");
			toggleTable("pet");
			loadPetTable();
		});

		billView.getCustomerButton().addActionListener(e -> {
			System.out.println("load customer table");
			toggleTable("customer");
			loadCustomerTable();
		});

		billView.getTableProductList().addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row = billView.getTableProductList().getSelectedRow();
				if (row >= 0) {
					DefaultTableModel model = (DefaultTableModel) billView.getTableProductList().getModel();
					String id = model.getValueAt(row, 0).toString();
					String name = model.getValueAt(row, 1).toString();
					double price = Double.parseDouble(model.getValueAt(row, 2).toString());
					addBillItem(id, name, 1, price); // mặc định 1 sản phẩm
					updateTotalAmountField();
				}
			}
		});

		billView.getTablePetList().addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row = billView.getTablePetList().getSelectedRow();
				if (row >= 0) {
					DefaultTableModel billModel = (DefaultTableModel) billView.getTableBillItems().getModel();
					String petID = billView.getTablePetList().getValueAt(row, 0).toString(); // ví dụ: 3PET
					String petName = billView.getTablePetList().getValueAt(row, 1).toString();
					double price = Double.parseDouble(billView.getTablePetList().getValueAt(row, 3).toString());

					// Kiểm tra petID đã tồn tại chưa
					boolean alreadyAdded = false;
					for (int i = 0; i < billModel.getRowCount(); i++) {
						String existingID = billModel.getValueAt(i, 0).toString();
						if (existingID.equals(petID)) {
							alreadyAdded = true;
							break;
						}
					}

					if (!alreadyAdded) {
						addBillItem(petID, petName, 1, price);
						updateTotalAmountField();
					} else {
						JOptionPane.showMessageDialog(billView, "Thú cưng này đã được chọn trong hóa đơn!");
					}
				}
			}
		});

		billView.getBtnDel().addActionListener(e -> {
			int row = billView.getTableBillItems().getSelectedRow();
			if (row >= 0) {
				String id = billView.getTableBillItems().getValueAt(row, 0).toString();
				decBillItem(id);
				updateTotalAmountField();
			} else {
				JOptionPane.showMessageDialog(billView, "Vui lòng chọn một mặt hàng để giảm số lượng.");
			}
		});

		billView.getBtnAdd().addActionListener(e -> {
			int row = billView.getTableBillItems().getSelectedRow();
			if (row >= 0) {
				String id = billView.getTableBillItems().getValueAt(row, 0).toString();
				BillBtnButton(id);
			} else {
				JOptionPane.showMessageDialog(billView, "Vui lòng chọn một mặt hàng để tăng số lượng.");
			}
		});

		billView.getBtnSave().addActionListener(e -> saveBill());

		billView.getBtnExport().addActionListener(e -> exportBill(orderIDExport, customerNameExprt, staffNameEx,
				totalAmountEx, createdDateEX, orderDetailsEX));
	}

	private void toggleTable(String type) {
		billView.getScrollPaneProduct().setVisible("product".equals(type));
		billView.getScrollPanePet().setVisible("pet".equals(type));
		billView.getScrollPaneCustomer().setVisible("customer".equals(type));

		billView.getSearchProductField().setVisible("product".equals(type));
		billView.getSearchPetField().setVisible("pet".equals(type));
		billView.getSearchCustomerField().setVisible("customer".equals(type));
	}

	private void loadProductTable() {
		ArrayList<Product> products = billService.getAllProduct();
		DefaultTableModel model = (DefaultTableModel) billView.getTableProductList().getModel();
		model.setRowCount(0);
		for (Product p : products) {
			model.addRow(new Object[] { p.getProductID(), p.getName(), p.getPrice(), p.getQuantity(),
					p.getCategory().getCategoryName() });
		}
	}

	private void loadPetTable() {
		List<Pet> pets = billService.getAllPet();
		DefaultTableModel model = (DefaultTableModel) billView.getTablePetList().getModel();
		model.setRowCount(0);
		for (Pet p : pets) {
			model.addRow(new Object[] { (p.getPetID() + "PET"), p.getName(), p.getSpecies(), p.getPrice(), p.getBreed(),
					p.getAge() });
		}
	}

	private void loadCustomerTable() {
		List<Customer> customers = billService.getAllCustomer();
		DefaultTableModel model = (DefaultTableModel) billView.getTableCustomerList().getModel();
		model.setRowCount(0);
		for (Customer c : customers) {
			model.addRow(new Object[] { c.getId(), c.getName(), c.getAddress(), c.getPhone(), c.getMembershipLevel(),
					c.getLoyaltyPoints() });
		}
	}

	private void addBillItem(String id, String name, int quantity, double price) {
		DefaultTableModel billModel = (DefaultTableModel) billView.getTableBillItems().getModel();

		boolean isPet = id.endsWith("PET");

		if (!isPet) {
			// xử lý sản phẩm như cũ
			DefaultTableModel productModel = (DefaultTableModel) billView.getTableProductList().getModel();
			int quantityInStock = -1;
			for (int i = 0; i < productModel.getRowCount(); i++) {
				if (productModel.getValueAt(i, 0).toString().equals(id)) {
					quantityInStock = Integer.parseInt(productModel.getValueAt(i, 3).toString());
					break;
				}
			}

			if (quantityInStock == -1) {
				JOptionPane.showMessageDialog(billView, "Không tìm thấy thông tin sản phẩm trong kho.");
				return;
			}

			// kiểm tra số lượng vượt quá tồn kho
			for (int i = 0; i < billModel.getRowCount(); i++) {
				String existingID = billModel.getValueAt(i, 0).toString();
				if (existingID.equals(id)) {
					int currentQty = Integer.parseInt(billModel.getValueAt(i, 2).toString());
					if (currentQty + quantity > quantityInStock) {
						JOptionPane.showMessageDialog(billView, "Vượt quá số lượng sản phẩm trong kho!");
						return;
					}
					int newQty = currentQty + quantity;
					billModel.setValueAt(newQty, i, 2);
					billModel.setValueAt(newQty * price, i, 4);
					return;
				}
			}

			// chưa có trong bill, thêm mới
			billModel.addRow(new Object[] { id, name, quantity, price, quantity * price });

		} else {
			// xử lý thêm thú cưng - chỉ cho thêm 1 lần duy nhất
			for (int i = 0; i < billModel.getRowCount(); i++) {
				String existingID = billModel.getValueAt(i, 0).toString();
				if (existingID.equals(id)) {
					JOptionPane.showMessageDialog(billView, "Thú cưng này đã được thêm!");
					return;
				}
			}

			billModel.addRow(new Object[] { id, name, 1, price, price });
		}
	}

	private void BillBtnButton(String id) {
		DefaultTableModel billModel = (DefaultTableModel) billView.getTableBillItems().getModel();
		DefaultTableModel productModel = (DefaultTableModel) billView.getTableProductList().getModel();

		for (int i = 0; i < billModel.getRowCount(); i++) {
			String existingID = billModel.getValueAt(i, 0).toString();

			if (existingID.equals(id)) {
				if (id.endsWith("PET")) {
					JOptionPane.showMessageDialog(billView, "Mỗi thú cưng chỉ có thể chọn 1 lần!");
					return;
				}

				int quantityInStock = -1;
				for (int j = 0; j < productModel.getRowCount(); j++) {
					if (productModel.getValueAt(j, 0).toString().equals(id)) {
						quantityInStock = Integer.parseInt(productModel.getValueAt(j, 3).toString());
						break;
					}
				}

				if (quantityInStock == -1) {
					JOptionPane.showMessageDialog(billView, "Không tìm thấy số lượng trong kho.");
					return;
				}

				int quantity = Integer.parseInt(billModel.getValueAt(i, 2).toString());
				if (quantity + 1 > quantityInStock) {
					JOptionPane.showMessageDialog(billView, "Không đủ hàng trong kho!");
					return;
				}

				double price = Double.parseDouble(billModel.getValueAt(i, 3).toString());
				billModel.setValueAt(quantity + 1, i, 2);
				billModel.setValueAt((quantity + 1) * price, i, 4);
				break;
			}
		}
	}

	private void decBillItem(String id) {
		DefaultTableModel billModel = (DefaultTableModel) billView.getTableBillItems().getModel();

		for (int i = 0; i < billModel.getRowCount(); i++) {
			String existingID = billModel.getValueAt(i, 0).toString();
			if (existingID.equals(id)) {
				int quantity = Integer.parseInt(billModel.getValueAt(i, 2).toString());
				double price = Double.parseDouble(billModel.getValueAt(i, 3).toString());

				if (quantity > 1) {
					billModel.setValueAt(quantity - 1, i, 2);
					billModel.setValueAt((quantity - 1) * price, i, 4);
				} else {
					billModel.removeRow(i);
				}
				break;
			}
		}
	}

	private void updateTotalAmountField() {
		double totalAmount = calculateTotalAmount();
		billView.getTotaltextField().setText(String.format("%.2f", totalAmount));
	}

	private double calculateTotalAmount() {
		DefaultTableModel billModel = (DefaultTableModel) billView.getTableBillItems().getModel();
		double total = 0;

		for (int i = 0; i < billModel.getRowCount(); i++) {
			double lineTotal = Double.parseDouble(billModel.getValueAt(i, 4).toString());
			total += lineTotal;
		}

		return total;
	}

	private void saveBill() {
		String name = billView.getNameTextField().getText().trim();
		String phone = billView.getPhoneTextField().getText().trim();
		String address = billView.getAddressTextField().getText().trim();
		int id = billView.getIDCustomer();

		Order order = new Order();
		order.setUserID(UserSession.getInstance().getUser().getId()); // hoặc từ session hiện tại
		staffNameEx = UserSession.getInstance().getUser().getName();
		order.setCustomerID(billView.getIDCustomer());
		order.setOrderDate(new Date()); // ngày hiện tại
		System.out.println(order.getOrderDate());
		order.setTotalPrice(calculateTotalAmount());// lấy từ view
		totalAmountEx = BigDecimal.valueOf(order.getTotalPrice());
		OrderDAO orderDAO = new OrderDAO();
		try {
			orderIDExport = orderDAO.insert(order);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		List<OrderDetail> detailList = new ArrayList<>();

		DefaultTableModel model = (DefaultTableModel) billView.getTableBillItems().getModel();
		int rowCount = model.getRowCount();

		for (int i = 0; i < rowCount; i++) {
			String itemID = model.getValueAt(i, 0).toString(); // cột ID
			double price = Double.parseDouble(model.getValueAt(i, 3).toString()); // cột Giá
			int quantity = Integer.parseInt(model.getValueAt(i, 2).toString()); // cột Số lượng

			OrderDetail detail = new OrderDetail();
			detail.setOrderID(orderIDExport);
			detail.setPrice(BigDecimal.valueOf(price));
			detail.setQuantity(quantity);

			if (itemID.endsWith("PET")) {
				int petId = Integer.parseInt(itemID.replace("PET", ""));
				detail.setPetID(petId);
				detail.setProductID(null); // phải dùng null
			} else {
				int productId = Integer.parseInt(itemID);
				detail.setProductID(productId);
				detail.setPetID(null); // phải dùng null
			}

			detailList.add(detail);
		}
		orderDetailsEX = detailList;
		OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
		orderDetailDAO.insertOrderDetails(detailList);
		customerNameExprt = billView.getNameTextField().getText();
		createdDateEX = LocalDateTime.now();

		Bill bill = new Bill();
		bill.setOrderID(orderIDExport);
		bill.setAmount(calculateTotalAmount());
		String method = billView.getPay_comboBox().getSelectedItem().toString();
		bill.setbillMethod(method); // từ combo box
		bill.setbillTime(new java.sql.Date(System.currentTimeMillis()));
		billService.addBill(bill);

		for (OrderDetail detail : detailList) {
			Integer productID = detail.getProductID();
			Integer petID = detail.getPetID();
			int quantity = detail.getQuantity();

			if (productID != null) {
				productService.updateByQua(productID, quantity);
			} else if (petID != null) {
				petService.updateTrangThai(petID, "Đã bán");
			}
		}
		loadPetTable();
		loadProductTable();
	}

	private void exportBill(int orderID, String customerName, String staffName, BigDecimal totalAmount,
			LocalDateTime createdDate, List<OrderDetail> orderDetails) {

		// Tạo thư mục nếu chưa có
		File billDir = new File("bills");
		if (!billDir.exists()) {
			billDir.mkdir();
		}

		// Đường dẫn file PDF
		String fileName = "Bill_Order_" + orderID + ".pdf";
		File file = new File(billDir, fileName);

		try {
			// Khởi tạo PdfWriter và PdfDocument
			PdfWriter writer = new PdfWriter(file);
			PdfDocument pdfDoc = new PdfDocument(writer);
			Document document = new Document(pdfDoc);

			// Tạo các font
			com.itextpdf.kernel.font.PdfFont normalFont = PdfFontFactory.createFont(StandardFonts.HELVETICA);
			com.itextpdf.kernel.font.PdfFont boldFont = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
			com.itextpdf.kernel.font.PdfFont italicFont = PdfFontFactory.createFont(StandardFonts.HELVETICA_OBLIQUE);

			// Tiêu đề
			Text titleText = new Text("HÓA ĐƠN THANH TOÁN").setFont(boldFont);
			Paragraph title = new Paragraph(titleText).setTextAlignment(TextAlignment.CENTER).setFontSize(18)
					.setBorder(new SolidBorder(1));
			document.add(title);

			document.add(new Paragraph("Mã hóa đơn: " + orderID).setFont(normalFont));
			document.add(new Paragraph("Khách hàng: " + customerName).setFont(normalFont));
			document.add(new Paragraph("Nhân viên: " + staffName).setFont(normalFont));

			// Xử lý createdDate null
			String dateStr = (createdDate != null) ? createdDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
					: "Chưa xác định";
			document.add(new Paragraph("Ngày tạo: " + dateStr).setFont(normalFont));

			document.add(new Paragraph(" "));

			// Bảng chi tiết hóa đơn
			Table table = new Table(UnitValue.createPercentArray(new float[] { 1, 4, 2, 3 })).useAllAvailableWidth();

			// Header bảng
			table.addHeaderCell(new Cell().add(new Paragraph("STT").setFont(boldFont)));
			table.addHeaderCell(new Cell().add(new Paragraph("Tên mặt hàng").setFont(boldFont)));
			table.addHeaderCell(new Cell().add(new Paragraph("SL").setFont(boldFont)));
			table.addHeaderCell(new Cell().add(new Paragraph("Giá").setFont(boldFont)));

			int stt = 1;
			for (OrderDetail detail : orderDetails) {
				String itemName;
				if (detail.getProductID() != null) {
					itemName = productService.getProductName(detail.getProductID());
				} else {
					itemName = petService.getPetName(detail.getPetID());
				}

				table.addCell(new Cell().add(new Paragraph(String.valueOf(stt++)).setFont(normalFont))
						.setBorder(Border.NO_BORDER));
				table.addCell(new Cell().add(new Paragraph(itemName).setFont(normalFont)).setBorder(Border.NO_BORDER));
				table.addCell(new Cell().add(new Paragraph(String.valueOf(detail.getQuantity())).setFont(normalFont))
						.setBorder(Border.NO_BORDER));
				table.addCell(
						new Cell().add(new Paragraph(String.format("%,.2f VNĐ", detail.getPrice())).setFont(normalFont))
								.setBorder(Border.NO_BORDER));
			}

			document.add(table);
			document.add(new Paragraph(" "));
			document.add(new LineSeparator(new SolidLine()));

			// Tổng tiền
			Text totalText = new Text("Tổng cộng: " + String.format("%,.2f VNĐ", totalAmount)).setFont(boldFont);
			Paragraph total = new Paragraph(totalText).setTextAlignment(TextAlignment.RIGHT);
			document.add(total);

			// Cảm ơn
			Text thanksText = new Text("Cảm ơn quý khách!").setFont(italicFont);
			Paragraph thanks = new Paragraph(thanksText).setTextAlignment(TextAlignment.CENTER);
			document.add(thanks);

			document.close();

			System.out.println("Đã xuất hóa đơn ra file: " + file.getAbsolutePath());

			// Mở file trên máy tính nếu hỗ trợ
			if (Desktop.isDesktopSupported()) {
				Desktop.getDesktop().open(file);
			}

		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Lỗi khi tạo file PDF.");
		}
	}

}
