package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import model.entity.Product;
import view.UI.Hover;

public class BillView extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField nameTextField;
	private JTextField phoneTextField;
	private JTextField addressTextField;
	private JTable tableProductList;
	private JTable tablePetList;
	private JTable tableCustomerList;
	private JTable tableBillItems;
	private JButton btnSave;
	private JButton btnExport;
	private JScrollPane scrollPaneProduct;
	private JScrollPane scrollPanePet;
	private JScrollPane scrollPaneCustomer;
	private JScrollPane scrollPaneBill;
	private JTextField searchProductField;
	private JTextField searchPetField;
	private JTextField searchCustomerField;
	private JTextField totaltextField;
	private JButton productButton;
	private JButton petButton;
	private JButton customerButton;
	private JLabel lblProductList;
	private JButton btnAdd, btnDel;
	private JComboBox<String> comboPaymentMethod;
	private JTextField txtTotalAmount;
	private JComboBox pay_comboBox;
	private int idCustomer;
	private JButton btnUsePoints;
	private JButton btnReload;

	private static final Color PRIMARY_COLOR = new Color(121, 162, 219);
	private static final Color PRIMARY_HOVER = new Color(100, 140, 200);
	private static final Color BTN_ADD = new Color(46, 204, 113);
	private static final Color BTN_ADD_HOVER = new Color(36, 170, 90);
	private static final Color BTN_DEL = new Color(231, 76, 60);
	private static final Color BTN_DEL_HOVER = new Color(200, 50, 40);
	private static final Color BG_FORM = new Color(245, 250, 255);
	private static final Color TEXT_MAIN = new Color(40, 40, 40);
	private static final Color TABLE_HEADER_BG = new Color(210, 225, 250); // đổi màu header
	private static final Color TABLE_HEADER_FG = new Color(33, 70, 120); // màu chữ header

	public BillView() {
		setLayout(null);
		setBounds(0, 0, 950, 750);
		setBackground(new Color(200, 220, 240));

		UIManager.put("ScrollBar.thumb", new Color(200, 220, 240));
		UIManager.put("ScrollBar.thumbHighlight", PRIMARY_COLOR);
		UIManager.put("ScrollBar.thumbShadow", PRIMARY_COLOR);
		UIManager.put("ScrollBar.thumbDarkShadow", PRIMARY_COLOR);
		UIManager.put("ScrollBar.track", BG_FORM);

		JPanel panel = new JPanel() {
			@Override protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setColor(BG_FORM);
				g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
				g2.dispose();
			}
		};
		panel.setBackground(BG_FORM);
		panel.setBounds(15, 15, 480, 340);
		add(panel);
		panel.setLayout(null);

		JLabel lblCustomerName = new JLabel("Tên Khách hàng");
		lblCustomerName.setFont(new Font("SansSerif", Font.BOLD, 15));
		lblCustomerName.setForeground(TEXT_MAIN);
		lblCustomerName.setBounds(30, 22, 150, 20);
		panel.add(lblCustomerName);

		nameTextField = createRoundTextField("Enter Customer's Name");
		nameTextField.setBounds(30, 45, 170, 32);
		panel.add(nameTextField);

		JLabel lblPhone = new JLabel("Số điện thoại");
		lblPhone.setFont(new Font("SansSerif", Font.BOLD, 15));
		lblPhone.setForeground(TEXT_MAIN);
		lblPhone.setBounds(250, 22, 120, 20);
		panel.add(lblPhone);

		phoneTextField = createRoundTextField("Enter Phone Number");
		phoneTextField.setBounds(250, 45, 170, 32);
		panel.add(phoneTextField);

		JLabel lblAddress = new JLabel("Địa chỉ");
		lblAddress.setFont(new Font("SansSerif", Font.BOLD, 15));
		lblAddress.setForeground(TEXT_MAIN);
		lblAddress.setBounds(30, 90, 150, 20);
		panel.add(lblAddress);

		addressTextField = createRoundTextField("Enter Address");
		addressTextField.setBounds(30, 115, 390, 32);
		panel.add(addressTextField);

		productButton = createTabButton("Product", true);
		productButton.setBounds(38, 175, 110, 36);
		panel.add(productButton);

		petButton = createTabButton("Pet", false);
		petButton.setBounds(178, 175, 110, 36);
		panel.add(petButton);

		customerButton = createTabButton("Customer", false);
		customerButton.setBounds(318, 175, 110, 36);
		panel.add(customerButton);

		searchProductField = createRoundTextField("Search Product ID...");
		searchProductField.setBounds(38, 222, 390, 32);
		searchProductField.setVisible(true);
		panel.add(searchProductField);

		searchPetField = createRoundTextField("Search Pet ID...");
		searchPetField.setBounds(38, 222, 390, 32);
		searchPetField.setVisible(false);
		panel.add(searchPetField);

		searchCustomerField = createRoundTextField("Search Customer ID...");
		searchCustomerField.setBounds(38, 222, 390, 32);
		searchCustomerField.setVisible(false);
		panel.add(searchCustomerField);

		lblProductList = new JLabel("Danh sách hàng hóa");
		lblProductList.setFont(new Font("SansSerif", Font.BOLD, 16));
		lblProductList.setForeground(PRIMARY_COLOR);
		lblProductList.setBounds(35, 365, 250, 25);
		add(lblProductList);

		tableProductList = createModernTable(new String[] { "ID", "Name", "Price", "Quantity", "Category" });
		scrollPaneProduct = createModernScrollPane(tableProductList);
		scrollPaneProduct.setBounds(15, 400, 480, 320);
		scrollPaneProduct.setVisible(true);
		add(scrollPaneProduct);

		tablePetList = createModernTable(new String[] { "ID", "Name", "Species", "Price", "Breed", "Age" });
		scrollPanePet = createModernScrollPane(tablePetList);
		scrollPanePet.setBounds(15, 400, 480, 320);
		scrollPanePet.setVisible(false);
		add(scrollPanePet);

		tableCustomerList = createModernTable(new String[] { "ID", "Name", "Address", "Phone", "Rank", "Score" });
		scrollPaneCustomer = createModernScrollPane(tableCustomerList);
		scrollPaneCustomer.setBounds(15, 400, 480, 320);
		scrollPaneCustomer.setVisible(false);
		add(scrollPaneCustomer);

		tableCustomerList.getSelectionModel().addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting()) {
				int selectedRow = tableCustomerList.getSelectedRow();
				if (selectedRow != -1) {
					String name = tableCustomerList.getValueAt(selectedRow, 1).toString();
					String address = tableCustomerList.getValueAt(selectedRow, 2).toString();
					String phone = tableCustomerList.getValueAt(selectedRow, 3).toString();
					int id = Integer.parseInt(tableCustomerList.getValueAt(selectedRow, 0).toString());
					idCustomer = id;
					nameTextField.setText(name);
					phoneTextField.setText(phone);
					addressTextField.setText(address);

					nameTextField.setForeground(Color.BLACK);
					phoneTextField.setForeground(Color.BLACK);
					addressTextField.setForeground(Color.BLACK);
				}
			}
		});

		JPanel billPanel = new JPanel() {
			@Override protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setColor(BG_FORM);
				g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
				g2.dispose();
			}
		};
		billPanel.setBounds(510, 15, 420, 705);
		billPanel.setLayout(null);
		add(billPanel);

		JLabel billHeader = new JLabel("Hóa Đơn");
		billHeader.setFont(new Font("SansSerif", Font.BOLD, 22));
		billHeader.setForeground(PRIMARY_COLOR);
		billHeader.setBounds(140, 18, 160, 32);
		billPanel.add(billHeader);

		tableBillItems = createModernTable(new String[] { "ID", "Name", "Quantity", "Price", "Total" });
		scrollPaneBill = createModernScrollPane(tableBillItems);
		scrollPaneBill.setBounds(20, 65, 380, 280);
		billPanel.add(scrollPaneBill);

		JLabel totallbl = new JLabel("Total");
		totallbl.setFont(new Font("SansSerif", Font.BOLD, 16));
		totallbl.setForeground(TEXT_MAIN);
		totallbl.setBounds(60, 370, 60, 25);
		billPanel.add(totallbl);

		totaltextField = createRoundTextField("");
		totaltextField.setBounds(140, 370, 180, 30);
		billPanel.add(totaltextField);

		JLabel paylbl = new JLabel("Phương thức thanh toán");
		paylbl.setFont(new Font("SansSerif", Font.BOLD, 16));
		paylbl.setForeground(TEXT_MAIN);
		paylbl.setBounds(40, 415, 200, 25);
		billPanel.add(paylbl);

		pay_comboBox = new JComboBox(new String[] { "Tiền mặt", "Chuyển Khoản" });
		pay_comboBox.setUI(new ModernComboBoxUI());
		pay_comboBox.setFont(new Font("SansSerif", Font.PLAIN, 15));
		pay_comboBox.setBackground(Color.WHITE);
		pay_comboBox.setBounds(240, 415, 110, 27);
		billPanel.add(pay_comboBox);

		btnAdd = createCircleButton("+", BTN_ADD, BTN_ADD_HOVER);
		btnAdd.setBounds(150, 470, 50, 50);
		billPanel.add(btnAdd);

		btnReload = createModernButton("Reload", PRIMARY_COLOR, PRIMARY_HOVER);
		btnReload.setFont(new Font("SansSerif", Font.BOLD, 15));
		btnReload.setBounds(60, 470, 80, 50);
		billPanel.add(btnReload);

		btnDel = createCircleButton("-", BTN_DEL, BTN_DEL_HOVER);
		btnDel.setBounds(210, 470, 50, 50);
		billPanel.add(btnDel);

		btnSave = createModernButton("Lưu", PRIMARY_COLOR, PRIMARY_HOVER);
		btnSave.setFont(new Font("SansSerif", Font.BOLD, 15));
		btnSave.setBounds(60, 550, 130, 48);
		billPanel.add(btnSave);

		btnExport = createModernButton("Xuất hóa đơn", PRIMARY_COLOR, PRIMARY_HOVER);
		btnExport.setFont(new Font("SansSerif", Font.BOLD, 15));
		btnExport.setBounds(220, 550, 150, 48);
		billPanel.add(btnExport);

		btnUsePoints = createModernButton("Dùng điểm", BTN_ADD, BTN_ADD_HOVER);
		btnUsePoints.setFont(new Font("SansSerif", Font.BOLD, 15));
		btnUsePoints.setBounds(270, 470, 110, 50);
		billPanel.add(btnUsePoints);

		setupButtonListeners();
	}

	public JButton getBtnReload() { return btnReload; }
	public JButton getBtnUsePoints() { return btnUsePoints; }

	private JTextField createRoundTextField(String placeholder) {
		JTextField tf = new JTextField();
		tf.setFont(new Font("SansSerif", Font.PLAIN, 15));
		tf.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
		tf.setBackground(Color.WHITE);
		tf.setOpaque(true);
		Hover.addPlaceholder(tf, placeholder);
		return tf;
	}

	private JButton createTabButton(String text, boolean selected) {
		JButton btn = new JButton(text);
		btn.setFont(new Font("SansSerif", Font.BOLD, 15));
		btn.setFocusPainted(false);
		btn.setBorderPainted(false);
		btn.setOpaque(true);
		btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btn.setBackground(selected ? PRIMARY_COLOR : new Color(230, 240, 250));
		btn.setForeground(selected ? Color.WHITE : PRIMARY_COLOR);
		btn.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) { if (!btn.getBackground().equals(PRIMARY_COLOR)) btn.setBackground(PRIMARY_HOVER);}
			public void mouseExited(MouseEvent e) { if (!btn.getBackground().equals(PRIMARY_COLOR)) btn.setBackground(new Color(230, 240, 250)); }
		});
		return btn;
	}

	private JButton createModernButton(String text, Color color, Color hover) {
		return new FancyButton(text, color, hover, 15);
	}

	private JButton createCircleButton(String text, Color color, Color hover) {
		FancyButton btn = new FancyButton(text, color, hover, 30);
		btn.setFont(new Font("SansSerif", Font.BOLD, 22));
		btn.setForeground(Color.WHITE);
		btn.setPreferredSize(new Dimension(48, 48));
		return btn;
	}

	private JScrollPane createModernScrollPane(JTable table) {
		JScrollPane sp = new JScrollPane(table);
		sp.setBorder(BorderFactory.createEmptyBorder());
		sp.setBackground(BG_FORM);
		sp.getViewport().setBackground(BG_FORM);
		sp.getVerticalScrollBar().setUI(new ModernScrollBarUI(PRIMARY_COLOR));
		sp.getHorizontalScrollBar().setUI(new ModernScrollBarUI(PRIMARY_COLOR));
		return sp;
	}

	private JTable createModernTable(String[] columns) {
		JTable table = new JTable();
		table.setModel(new DefaultTableModel(new Object[][] {}, columns) {
			@Override public boolean isCellEditable(int row, int column) { return false; }
		});
		table.setFont(new Font("SansSerif", Font.PLAIN, 16));
		table.setRowHeight(38);
		table.setSelectionBackground(new Color(230, 240, 255));
		table.setSelectionForeground(TEXT_MAIN);

		JTableHeader header = table.getTableHeader();
		header.setFont(new Font("SansSerif", Font.BOLD, 17));
		header.setBackground(TABLE_HEADER_BG);
		header.setForeground(TABLE_HEADER_FG);

		return table;
	}

	class FancyButton extends JButton {
		private final Color baseColor, hoverColor;
		private final int arc;
		private boolean hover = false;
		public FancyButton(String text, Color baseColor, Color hoverColor, int arc) {
			super(text);
			this.baseColor = baseColor;
			this.hoverColor = hoverColor;
			this.arc = arc;
			setFocusPainted(false);
			setContentAreaFilled(false);
			setBorderPainted(false);
			setOpaque(false);
			setCursor(new Cursor(Cursor.HAND_CURSOR));
			addMouseListener(new MouseAdapter() {
				public void mouseEntered(MouseEvent e) { hover = true; repaint(); }
				public void mouseExited(MouseEvent e) { hover = false; repaint(); }
			});
		}
		@Override
		protected void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D)g.create();
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			Color fill = hover ? hoverColor : baseColor;
			g2.setColor(fill);
			g2.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);
			FontMetrics fm = g2.getFontMetrics();
			String txt = getText();
			int textWidth = fm.stringWidth(txt);
			int x = (getWidth() - textWidth) / 2;
			int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
			g2.setFont(getFont());
			g2.setColor(getForeground());
			g2.drawString(txt, x, y);
			g2.dispose();
		}
	}

	class ModernComboBoxUI extends javax.swing.plaf.basic.BasicComboBoxUI {
		@Override
		protected JButton createArrowButton() {
			JButton button = new JButton("▼");
			button.setBorder(BorderFactory.createEmptyBorder());
			button.setBackground(Color.WHITE);
			button.setForeground(PRIMARY_COLOR);
			button.setContentAreaFilled(false);
			button.setFocusPainted(false);
			return button;
		}
		@Override
		public void paintCurrentValueBackground(Graphics g, Rectangle bounds, boolean hasFocus) {
			g.setColor(Color.WHITE);
			g.fillRoundRect(bounds.x, bounds.y, bounds.width, bounds.height, 15, 15);
		}
	}

	class ModernScrollBarUI extends javax.swing.plaf.basic.BasicScrollBarUI {
		private final Color thumbColor;
		public ModernScrollBarUI(Color thumbColor) { this.thumbColor = thumbColor; }
		@Override
		protected void configureScrollBarColors() {
			trackColor = new Color(240, 240, 245);
		}
		@Override
		protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
			Graphics2D g2 = (Graphics2D) g.create();
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setColor(thumbColor);
			g2.fillRoundRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height, 10, 10);
			g2.dispose();
		}
		@Override
		protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
			Graphics2D g2 = (Graphics2D) g.create();
			g2.setColor(new Color(240, 240, 245));
			g2.fillRoundRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height, 10, 10);
			g2.dispose();
		}
	}
	private void setupButtonListeners() {
		productButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				productButton.setBackground(PRIMARY_COLOR);
				productButton.setForeground(Color.WHITE);
				petButton.setBackground(new Color(230, 240, 250));
				petButton.setForeground(PRIMARY_COLOR);
				customerButton.setBackground(new Color(230, 240, 250));
				customerButton.setForeground(PRIMARY_COLOR);

				scrollPaneProduct.setVisible(true);
				scrollPanePet.setVisible(false);
				scrollPaneCustomer.setVisible(false);

				searchProductField.setVisible(true);
				searchPetField.setVisible(false);
				searchCustomerField.setVisible(false);

				lblProductList.setText("Danh sách hàng hóa");
			}
		});
		petButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				productButton.setBackground(new Color(230, 240, 250));
				productButton.setForeground(PRIMARY_COLOR);
				petButton.setBackground(PRIMARY_COLOR);
				petButton.setForeground(Color.WHITE);
				customerButton.setBackground(new Color(230, 240, 250));
				customerButton.setForeground(PRIMARY_COLOR);

				scrollPaneProduct.setVisible(false);
				scrollPanePet.setVisible(true);
				scrollPaneCustomer.setVisible(false);

				searchProductField.setVisible(false);
				searchPetField.setVisible(true);
				searchCustomerField.setVisible(false);

				lblProductList.setText("Danh sách thú cưng");
			}
		});
		customerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				productButton.setBackground(new Color(230, 240, 250));
				productButton.setForeground(PRIMARY_COLOR);
				petButton.setBackground(new Color(230, 240, 250));
				petButton.setForeground(PRIMARY_COLOR);
				customerButton.setBackground(PRIMARY_COLOR);
				customerButton.setForeground(Color.WHITE);

				scrollPaneProduct.setVisible(false);
				scrollPanePet.setVisible(false);
				scrollPaneCustomer.setVisible(true);

				searchProductField.setVisible(false);
				searchPetField.setVisible(false);
				searchCustomerField.setVisible(true);

				lblProductList.setText("Danh sách khách hàng");
			}
		});

		searchProductField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				DefaultTableModel model = (DefaultTableModel) tableProductList.getModel();
				TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
				tableProductList.setRowSorter(sorter);
				sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchProductField.getText().trim(), 0));
			}
		});
		searchPetField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				DefaultTableModel model = (DefaultTableModel) tablePetList.getModel();
				TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
				tablePetList.setRowSorter(sorter);
				sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchPetField.getText().trim(), 0));
			}
		});
		searchCustomerField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				DefaultTableModel model = (DefaultTableModel) tableCustomerList.getModel();
				TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
				tableCustomerList.setRowSorter(sorter);
				sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchCustomerField.getText().trim(), 0));
			}
		});
	}

	// Getter methods
	public JTextField getTotaltextField() { return totaltextField; }
	public void setTotaltextField(Double price) { this.totaltextField.setText(String.valueOf(price)); }
	public JComboBox getPay_comboBox() { return pay_comboBox; }
	public int getIDCustomer() { return idCustomer; }
	public JButton getBtnAdd() { return btnAdd; }
	public JButton getBtnDel() { return btnDel; }
	public JTextField getNameTextField() { return nameTextField; }
	public JTextField getPhoneTextField() { return phoneTextField; }
	public JTextField getAddressTextField() { return addressTextField; }
	public JTable getTableProductList() { return tableProductList; }
	public JTable getTablePetList() { return tablePetList; }
	public JTable getTableCustomerList() { return tableCustomerList; }
	public JTable getTableBillItems() { return tableBillItems; }
	public JButton getBtnSave() { return btnSave; }
	public JButton getBtnExport() { return btnExport; }
	public JButton getProductButton() { return productButton; }
	public JButton getPetButton() { return petButton; }
	public JButton getCustomerButton() { return customerButton; }
	public JScrollPane getScrollPaneProduct() { return scrollPaneProduct; }
	public JScrollPane getScrollPanePet() { return scrollPanePet; }
	public JScrollPane getScrollPaneCustomer() { return scrollPaneCustomer; }
	public JTextField getSearchProductField() { return searchProductField; }
	public JTextField getSearchPetField() { return searchPetField; }
	public JTextField getSearchCustomerField() { return searchCustomerField; }
	public void setProductTableData(List<Product> products) {
		DefaultTableModel model = (DefaultTableModel) tableProductList.getModel();
		model.setRowCount(0); // clear existing data
		for (Product p : products) {
			model.addRow(new Object[] { p.getProductID(), p.getName(), p.getPrice(), p.getQuantity() });
		}
	}
}