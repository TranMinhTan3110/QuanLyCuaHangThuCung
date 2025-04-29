package view;

import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.JTextField;
import javax.swing.SwingConstants;

import model.entity.Category;
import view.UI.Hover;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

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

	public ProductView() {
		setLayout(null);
		setBounds(0,0,950,750);

		JPanel panel_top = new JPanel();
		panel_top.setBackground(new Color(255, 255, 223));
		panel_top.setBounds(0, 0, 950, 240);
		add(panel_top);
		panel_top.setLayout(null);

		JLabel lblQuanti = new JLabel("Quantity");
		lblQuanti.setFont(new Font("Arial", Font.PLAIN, 16));
		lblQuanti.setBounds(444, 61, 77, 24);
		panel_top.add(lblQuanti);

		JPanel quantityPanel = new JPanel();
		quantityPanel.setLayout(new BorderLayout());
		quantityPanel.setBounds(596, 61, 132, 34);
		quantityPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		btnMinus = new JButton("−");
		btnMinus.setBackground(new Color(232, 150, 89));
		btnMinus.setMargin(new Insets(0, 5, 0, 5));
		btnMinus.setFocusPainted(false);
		btnMinus.setFont(new Font("Arial", Font.BOLD, 12));

		Quan_textField = new JTextField("0");
		Quan_textField.setHorizontalAlignment(JTextField.CENTER);
		Quan_textField.setFont(new Font("Arial", Font.PLAIN, 14));
		Quan_textField.setBorder(null);
		((AbstractDocument) Quan_textField.getDocument()).setDocumentFilter(new NumberOnlyFilter());

		btnPlus = new JButton("+");
		btnPlus.setBackground(new Color(232, 150, 89));
		btnPlus.setMargin(new Insets(0, 5, 0, 5));
		btnPlus.setFocusPainted(false);
		btnPlus.setFont(new Font("Arial", Font.BOLD, 12));

		quantityPanel.add(btnMinus, BorderLayout.WEST);
		quantityPanel.add(Quan_textField, BorderLayout.CENTER);
		quantityPanel.add(btnPlus, BorderLayout.EAST);
		Hover.roundPanel(quantityPanel, 15, Color.WHITE, Color.GRAY);
		panel_top.add(quantityPanel);

		JLabel lblPro_Name = new JLabel("Product Name");
		lblPro_Name.setFont(new Font("Arial", Font.PLAIN, 16));
		lblPro_Name.setBounds(78, 58, 122, 24);
		panel_top.add(lblPro_Name);

		ProName_textField = new JTextField();
		ProName_textField.setColumns(10);
		ProName_textField.setBounds(210, 56, 132, 34);
		panel_top.add(ProName_textField);
		Hover.addPlaceholder(ProName_textField, "Enter Name");
		Hover.roundTextField(ProName_textField, 15, Color.WHITE, Color.LIGHT_GRAY);

		JLabel lblPrice = new JLabel("Price");
		lblPrice.setFont(new Font("Arial", Font.PLAIN, 16));
		lblPrice.setBounds(78, 120, 77, 24);
		panel_top.add(lblPrice);

		Price_textField = new JTextField();
		Price_textField.setColumns(10);
		Price_textField.setBounds(210, 118, 132, 34);
		panel_top.add(Price_textField);
		Hover.addPlaceholder(Price_textField, "Enter Price");
		Hover.roundTextField(Price_textField, 10, Color.WHITE, Color.LIGHT_GRAY);
		((AbstractDocument) Price_textField.getDocument()).setDocumentFilter(new NumberOnlyFilter());

		JLabel lblCate_Name = new JLabel("Category Name");
		lblCate_Name.setFont(new Font("Arial", Font.PLAIN, 16));
		lblCate_Name.setBounds(444, 120, 120, 24);
		panel_top.add(lblCate_Name);

		// 1. Tạo danh sách Category
		Category cat1 = new Category(1, "Thức ăn");
		Category cat2 = new Category(2, "Thức uống");
		Category cat3 = new Category(3, "Đồ dùng");

		CateName_comboBox = new JComboBox<>();
		CateName_comboBox.addItem(cat1);
		CateName_comboBox.addItem(cat2);
		CateName_comboBox.addItem(cat3);
		CateName_comboBox.setFont(new Font("Arial", Font.PLAIN, 14));
		CateName_comboBox.setBounds(596, 118, 132, 34);
		panel_top.add(CateName_comboBox);
		Hover.roundComboBox(CateName_comboBox, 15, Color.WHITE, Color.LIGHT_GRAY);

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

		ImageIcon searchIcon = new ImageIcon(ProductView.class.getResource("/view/Icon/Search_Icon.png"));
		JPanel searchPanel = new JPanel(new BorderLayout());
		searchPanel.setBounds(684, 187, 234, 24);
		searchPanel.setBackground(Color.WHITE);

		JLabel searchLabel = new JLabel(searchIcon);
		searchLabel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
		searchPanel.add(searchLabel, BorderLayout.WEST);

		Search_textField = new JTextField();
		Search_textField.setBorder(null);
		Search_textField.setColumns(10);
		searchPanel.add(Search_textField, BorderLayout.CENTER);
		Hover.addPlaceholder(Search_textField,"search...");
		Hover.roundPanel(searchPanel, 20,  Color.WHITE, Color.GRAY);

		panel_top.add(searchPanel);

		JScrollPane Pro_list = new JScrollPane();
		Pro_list.setBounds(0, 244, 950, 500);
		add(Pro_list);

		Pro_table = new JTable();
		Hover.customizeTableHeader(Pro_table);
		Pro_table.setModel(new DefaultTableModel(
				new Object[][] {},
				new String[] {"ID", "Name", "Price","Quantity", "Category"}
		));
		Pro_list.setViewportView(Pro_table);
	}

	public String getProductName() {
		return ProName_textField.getText();
	}

	public String getPrice() {
		return Price_textField.getText();
	}

	public String getQuantity() {
		return Quan_textField.getText();
	}

	public Category getCategory() {
		Category selectedCategory = (Category) CateName_comboBox.getSelectedItem();
		return selectedCategory;
	}

	public String getSearchKeyword() {
		return Search_textField.getText();
	}

	public JTable getProductTable() {
		return Pro_table;
	}

	public void setProductName(String name) {
		ProName_textField.setText(name);
	}

	public void setPrice(String price) {
		Price_textField.setText(price);
	}

	public void setQuantity(String quantity) {
		Quan_textField.setText(quantity);
	}

	public void clearFields() {
		ProName_textField.setText("");
		Price_textField.setText("");
		Quan_textField.setText("0");
		CateName_comboBox.setSelectedIndex(0);
		Search_textField.setText("");
	}

	public void addAddButtonListener(ActionListener listener) {
		btnAdd.addActionListener(listener);
	}

	public void addEditButtonListener(ActionListener listener) {
		btnEdit.addActionListener(listener);
	}

	public void addDeleteButtonListener(ActionListener listener) {
		btnDel.addActionListener(listener);
	}

	public void addPlusButtonListener(ActionListener listener) {
		btnPlus.addActionListener(listener);
	}

	public void addMinusButtonListener(ActionListener listener) {
		btnMinus.addActionListener(listener);
	}

	public void addSearchKeyListener(java.awt.event.KeyListener listener) {
		Search_textField.addKeyListener(listener);
	}

	public int getSelectedRow() {
		return Pro_table.getSelectedRow();
	}

	public String getValueAt(int row, int column) {
		Object value = Pro_table.getValueAt(row, column);
		return value != null ? value.toString() : "";
	}

	public void setSelectedCategoryByName(String categoryName) {
		for (int i = 0; i < CateName_comboBox.getItemCount(); i++) {
			Category cate = CateName_comboBox.getItemAt(i);
			if (cate.getCategoryName().equals(categoryName)) {
				CateName_comboBox.setSelectedIndex(i);
				break;
			}
		}
	}

	// Cho Controller gắn listener vào
	public void addTableSelectionListener(ListSelectionListener listener) {
		Pro_table.getSelectionModel().addListSelectionListener(listener);
	}

	class NumberOnlyFilter extends DocumentFilter {
		@Override
		public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
			if (string.matches("\\d*")) {  // chỉ cho phép chữ số
				super.insertString(fb, offset, string, attr);
			}
		}

		@Override
		public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
			if (text.matches("\\d*")) {  // chỉ cho phép chữ số
				super.replace(fb, offset, length, text, attrs);
			}
		}
	}
	public void showMessage(String s) {
	}

}
