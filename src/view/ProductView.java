		package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.*;
import javax.swing.text.*;

import model.entity.Category;
import view.UI.Hover;

public class ProductView extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField Quan_textField;
	private JTextField ProName_textField;
	private JTextField Price_textField;
	private JTable Pro_table;
	private JTextField Search_textField;
	private JButton btnAdd, btnEdit, btnDel;
	private JButton btnPlus, btnMinus;
	private JComboBox<Category> CateName_comboBox;

	// Pagination controls
	private JButton btnPrevPage;
	private JButton btnNextPage;
	private JLabel lblPageInfo;

	public ProductView() {
		setLayout(null);
		setBounds(0, 0, 950, 750);
		setBackground(new Color(200, 220, 240)); // Main background

		// Top panel
		JPanel panel_top = new JPanel();
		panel_top.setBackground(new Color(200, 220, 240));
		panel_top.setBounds(0, 0, 950, 240);
		panel_top.setLayout(null);
		add(panel_top);

		Font labelFont = new Font("Segoe UI", Font.PLAIN, 16);
		Font inputFont = new Font("Segoe UI", Font.PLAIN, 16);

		Border inputBorder = BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(new Color(180, 200, 220), 1, true),
				new EmptyBorder(6, 12, 6, 12)
		);

		JLabel lblPro_Name = new JLabel("Product Name");
		lblPro_Name.setFont(labelFont);
		lblPro_Name.setBounds(78, 58, 122, 24);
		panel_top.add(lblPro_Name);

		ProName_textField = new JTextField();
		ProName_textField.setBounds(210, 56, 140, 36);
		ProName_textField.setFont(inputFont);
		ProName_textField.setBorder(inputBorder);
		ProName_textField.setBackground(Color.WHITE);
		Hover.addPlaceholder(ProName_textField, "Enter Name");
		panel_top.add(ProName_textField);

		JLabel lblQuanti = new JLabel("Quantity");
		lblQuanti.setFont(labelFont);
		lblQuanti.setBounds(444, 61, 77, 24);
		panel_top.add(lblQuanti);

		JPanel quantityPanel = new JPanel();
		quantityPanel.setLayout(new BorderLayout());
		quantityPanel.setBounds(596, 61, 140, 36);
		quantityPanel.setBorder(inputBorder);
		quantityPanel.setBackground(Color.WHITE);

		btnMinus = new JButton("−");
		btnMinus.setBackground(new Color(232, 150, 89));
		btnMinus.setMargin(new Insets(0, 5, 0, 5));
		btnMinus.setFocusPainted(false);
		btnMinus.setFont(new Font("Segoe UI", Font.BOLD, 16));
		btnMinus.setPreferredSize(new Dimension(40, 36));

		Quan_textField = new JTextField("0");
		Quan_textField.setHorizontalAlignment(JTextField.CENTER);
		Quan_textField.setFont(inputFont);
		Quan_textField.setBorder(null);
		Quan_textField.setBackground(Color.WHITE);

		btnPlus = new JButton("+");
		btnPlus.setBackground(new Color(232, 150, 89));
		btnPlus.setMargin(new Insets(0, 5, 0, 5));
		btnPlus.setFocusPainted(false);
		btnPlus.setFont(new Font("Segoe UI", Font.BOLD, 16));
		btnPlus.setPreferredSize(new Dimension(40, 36));

		quantityPanel.add(btnMinus, BorderLayout.WEST);
		quantityPanel.add(Quan_textField, BorderLayout.CENTER);
		quantityPanel.add(btnPlus, BorderLayout.EAST);
		panel_top.add(quantityPanel);

		JLabel lblPrice = new JLabel("Price");
		lblPrice.setFont(labelFont);
		lblPrice.setBounds(78, 120, 77, 24);
		panel_top.add(lblPrice);

		Price_textField = new JTextField();
		Price_textField.setBounds(210, 118, 140, 36);
		Price_textField.setFont(inputFont);
		Price_textField.setBorder(inputBorder);
		Price_textField.setBackground(Color.WHITE);
		Hover.addPlaceholder(Price_textField, "Enter Price");
		panel_top.add(Price_textField);

		JLabel lblCate_Name = new JLabel("Category Name");
		lblCate_Name.setFont(labelFont);
		lblCate_Name.setBounds(444, 120, 120, 24);
		panel_top.add(lblCate_Name);

		Category cat1 = new Category(1, "Thức ăn");
		Category cat2 = new Category(2, "Thức uống");
		Category cat3 = new Category(3, "Đồ dùng");

		CateName_comboBox = new JComboBox<>();
		CateName_comboBox.addItem(cat1);
		CateName_comboBox.addItem(cat2);
		CateName_comboBox.addItem(cat3);
		CateName_comboBox.setFont(inputFont);
		CateName_comboBox.setBounds(596, 118, 140, 36);
		CateName_comboBox.setBackground(Color.WHITE);
		CateName_comboBox.setBorder(inputBorder);
		CateName_comboBox.setUI(new ModernComboBoxUI());
		panel_top.add(CateName_comboBox);

		Dimension actionBtnSize = new Dimension(87, 63);

		btnAdd = new JButton("Add");
		btnAdd.setIcon(new ImageIcon(getClass().getResource("/view/Icon/add_Icon.png")));
		btnAdd.setFont(labelFont);
		btnAdd.setBounds(20, 167, 87, 63);
		btnAdd.setPreferredSize(actionBtnSize);
		btnAdd.setFocusPainted(false);
		btnAdd.setBorderPainted(false);
		btnAdd.setContentAreaFilled(false);
		btnAdd.setHorizontalTextPosition(SwingConstants.CENTER);
		btnAdd.setVerticalTextPosition(SwingConstants.BOTTOM);
		panel_top.add(btnAdd);

		btnEdit = new JButton("Edit");
		btnEdit.setIcon(new ImageIcon(getClass().getResource("/view/Icon/edit_Icon.png")));
		btnEdit.setFont(labelFont);
		btnEdit.setBounds(110, 167, 87, 63);
		btnEdit.setPreferredSize(actionBtnSize);
		btnEdit.setFocusPainted(false);
		btnEdit.setBorderPainted(false);
		btnEdit.setContentAreaFilled(false);
		btnEdit.setHorizontalTextPosition(SwingConstants.CENTER);
		btnEdit.setVerticalTextPosition(SwingConstants.BOTTOM);
		panel_top.add(btnEdit);

		btnDel = new JButton("Delete");
		btnDel.setIcon(new ImageIcon(getClass().getResource("/view/Icon/delete_Icon.png")));
		btnDel.setFont(labelFont);
		btnDel.setBounds(209, 167, 87, 63);
		btnDel.setPreferredSize(actionBtnSize);
		btnDel.setFocusPainted(false);
		btnDel.setBorderPainted(false);
		btnDel.setContentAreaFilled(false);
		btnDel.setHorizontalTextPosition(SwingConstants.CENTER);
		btnDel.setVerticalTextPosition(SwingConstants.BOTTOM);
		panel_top.add(btnDel);

		// Beautiful rounded search box (like CustomerView, PetView)
		ImageIcon searchIcon = new ImageIcon(getClass().getResource("/view/Icon/Search_Icon.png"));
		JPanel searchPanel = new JPanel(new BorderLayout());
		searchPanel.setBounds(714, 187, 200, 36);
		searchPanel.setBackground(Color.WHITE);
		searchPanel.setBorder(inputBorder);
		Hover.roundPanel(searchPanel, 15, Color.WHITE, Color.GRAY);

		JLabel searchLabel = new JLabel(searchIcon);
		searchLabel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
		searchPanel.add(searchLabel, BorderLayout.WEST);

		Search_textField = new JTextField();
		Search_textField.setBorder(null);
		Search_textField.setFont(inputFont);
		Search_textField.setBackground(Color.WHITE);
		searchPanel.add(Search_textField, BorderLayout.CENTER);
		Hover.addPlaceholder(Search_textField, "search...");
		panel_top.add(searchPanel);

		// Table panel
		JScrollPane Pro_list = new JScrollPane();
		Pro_list.setBounds(0, 240, 950, 390);
		Pro_list.getViewport().setBackground(new Color(200, 220, 240));
		Pro_list.setBackground(new Color(200, 220, 240));
		add(Pro_list);

		// Table columns
		String[] columnNames = {"ID", "NAME", "QUANTITY", "PRICE", "CATEGORY"};
		DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		Pro_table = new JTable(model) {
			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component c = super.prepareRenderer(renderer, row, column);
				if (!isRowSelected(row)) c.setBackground(Color.WHITE);
				else c.setBackground(new Color(255, 255, 153)); // light yellow
				c.setForeground(new Color(40, 40, 40));
				return c;
			}
		};
		Pro_table.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		Pro_table.setRowHeight(36);
		Pro_table.setShowGrid(false);
		Pro_table.setIntercellSpacing(new Dimension(0, 0));
		Pro_table.setSelectionBackground(new Color(255, 255, 153));
		Pro_table.setSelectionForeground(new Color(40, 40, 40));
		Pro_table.setBackground(Color.WHITE);

		JTableHeader th = Pro_table.getTableHeader();
		th.setFont(new Font("Segoe UI", Font.BOLD, 18));
		th.setBackground(Color.WHITE);
		th.setForeground(new Color(33, 70, 120));
		((DefaultTableCellRenderer) th.getDefaultRenderer()).setHorizontalAlignment(JLabel.LEFT);

		Pro_list.setViewportView(Pro_table);

		// Pagination panel
		JPanel paginationPanel = new JPanel(null);
		paginationPanel.setBounds(0, 630, 950, 56);
		paginationPanel.setBackground(new Color(200, 220, 240));
		add(paginationPanel);

		int btnWidth = 120;
		int btnHeight = 38;
		int spacing = 10;
		int rightPadding = 24;
		int startX = 950 - rightPadding - (btnWidth * 2 + spacing + 80);

		btnPrevPage = new JButton("Previous");
		btnPrevPage.setFont(new Font("Arial", Font.PLAIN, 15));
		btnPrevPage.setFocusPainted(false);
		btnPrevPage.setBackground(Color.WHITE);
		btnPrevPage.setBounds(startX, 10, btnWidth, btnHeight);
		btnPrevPage.setEnabled(false);
		paginationPanel.add(btnPrevPage);

		lblPageInfo = new JLabel("Page 1/1");
		lblPageInfo.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblPageInfo.setBounds(startX + btnWidth, 10, 80, btnHeight);
		lblPageInfo.setHorizontalAlignment(SwingConstants.CENTER);
		paginationPanel.add(lblPageInfo);

		btnNextPage = new JButton("Next");
		btnNextPage.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNextPage.setFocusPainted(false);
		btnNextPage.setBackground(Color.WHITE);
		btnNextPage.setBounds(startX + btnWidth + 80 + spacing, 10, btnWidth, btnHeight);
		paginationPanel.add(btnNextPage);
	}

	public String getProductName() { return ProName_textField.getText(); }
	public String getPrice() { return Price_textField.getText(); }
	public String getQuantity() { return Quan_textField.getText(); }
	public Category getCategory() { return (Category) CateName_comboBox.getSelectedItem(); }
	public String getSearchKeyword() { return Search_textField.getText(); }
	public JTable getProductTable() { return Pro_table; }
	public void setProductName(String name) { ProName_textField.setText(name); }
	public void setPrice(String price) { Price_textField.setText(price); }
	public void setQuantity(String quantity) { Quan_textField.setText(quantity); }
	public void clearFields() {
		ProName_textField.setText("");
		Price_textField.setText("");
		Quan_textField.setText("0");
		CateName_comboBox.setSelectedIndex(0);
		Search_textField.setText("");
	}
	public void addAddButtonListener(ActionListener listener) { btnAdd.addActionListener(listener); }
	public void addEditButtonListener(ActionListener listener) { btnEdit.addActionListener(listener); }
	public void addDeleteButtonListener(ActionListener listener) { btnDel.addActionListener(listener); }
	public void addPlusButtonListener(ActionListener listener) { btnPlus.addActionListener(listener); }
	public void addMinusButtonListener(ActionListener listener) { btnMinus.addActionListener(listener); }
	public void addSearchKeyListener(java.awt.event.KeyListener listener) { Search_textField.addKeyListener(listener); }
	public int getSelectedRow() { return Pro_table.getSelectedRow(); }
	public String getValueAt(int row, int column) {
		Object value = Pro_table.getValueAt(row, column);
		return value != null ? value.toString() : "";
	}
	public void setSelectedCategoryByName(String categoryName) {
		for (int i = 0; i < CateName_comboBox.getItemCount(); i++) {
			Category cat = CateName_comboBox.getItemAt(i);
			if (cat.getName().equals(categoryName)) {
				CateName_comboBox.setSelectedIndex(i);
				break;
			}
		}
	}
	public void addTableSelectionListener(ListSelectionListener listener) {
		Pro_table.getSelectionModel().addListSelectionListener(listener);
	}
	public JTable getTable() { return Pro_table; }

	// Pagination controls getters
	public JButton getPrevPageButton() { return btnPrevPage; }
	public JButton getNextPageButton() { return btnNextPage; }
	public JLabel getPageInfoLabel() { return lblPageInfo; }

	class NumberOnlyFilter extends DocumentFilter {
		@Override
		public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
			if (string.matches("\\d+")) {
				super.insertString(fb, offset, string, attr);
			}
		}
		@Override
		public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
			if (text.matches("\\d+")) {
				super.replace(fb, offset, length, text, attrs);
			}
		}
	}

	public void showMessage(String s) {}
}