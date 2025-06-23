package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

import model.entity.Category;
import view.ProductView;
import view.UI.Hover;

public class PetView extends JPanel {
	private static final long serialVersionUID = 1L;
	private JTextField Name_textField;
	private JTextField Species_textField;
	private JTextField Price_textField;
	private JTextField Age_textField;
	private JTextField Search_textField;
	private JTextField Breed_textField;
	private JTable Pet_Table;
	private JComboBox Breed_comboBox;
	private JComboBox Species_comboBox;
	private JComboBox Arrange_comboBox;
	private JComboBox gender_comboBox;
	private JButton btnAdd;
	private JButton btnDel;
	private JButton btnEdit;
	private String[] columns = {"ID", "Name", "Breed", "Species", "Gender", "Age", "Price"};
	private DefaultTableModel model;

	// Pagination controls
	private JButton btnPrevPage;
	private JButton btnNextPage;
	private JLabel lblPageInfo;

	public PetView() {
		setLayout(null);
		setBounds(0, 0, 950, 750);
		setBackground(new Color(200, 220, 240)); // Set main background

		JPanel panel_top = new JPanel();
		panel_top.setBackground(new Color(200, 220, 240));
		panel_top.setBounds(0, 0, 950, 240);
		panel_top.setLayout(null);
		add(panel_top);

		JLabel lblName = new JLabel("Name");
		lblName.setFont(new Font("Arial", Font.PLAIN, 16));
		lblName.setBounds(97, 42, 56, 33);
		panel_top.add(lblName);

		Name_textField = new JTextField();
		Name_textField.setFont(new Font("Arial", Font.PLAIN, 16));
		Name_textField.setBounds(163, 42, 120, 33);
		panel_top.add(Name_textField);
		Hover.addPlaceholder(Name_textField, "Enter Name");
		Hover.roundTextField(Name_textField, 15, Color.WHITE, Color.LIGHT_GRAY);

		JLabel lblSpecies = new JLabel("Species");
		lblSpecies.setFont(new Font("Arial", Font.PLAIN, 16));
		lblSpecies.setBounds(325, 42, 76, 33);
		panel_top.add(lblSpecies);

		Species_textField = new JTextField();
		Species_textField.setFont(new Font("Arial", Font.PLAIN, 16));
		Species_textField.setBounds(411, 42, 120, 33);
		panel_top.add(Species_textField);
		Hover.addPlaceholder(Species_textField, "Enter species");
		Hover.roundTextField(Species_textField, 15, Color.WHITE, Color.LIGHT_GRAY);

		JLabel lblPrice = new JLabel("Price");
		lblPrice.setFont(new Font("Arial", Font.PLAIN, 16));
		lblPrice.setBounds(583, 42, 56, 33);
		panel_top.add(lblPrice);

		Price_textField = new JTextField();
		Price_textField.setFont(new Font("Arial", Font.PLAIN, 16));
		Price_textField.setBounds(657, 42, 120, 33);
		panel_top.add(Price_textField);
		Hover.addPlaceholder(Price_textField, "Enter Price");
		Hover.roundTextField(Price_textField, 15, Color.WHITE, Color.LIGHT_GRAY);

		JLabel lblBreed = new JLabel("Breed");
		lblBreed.setFont(new Font("Arial", Font.PLAIN, 16));
		lblBreed.setBounds(97, 107, 76, 33);
		panel_top.add(lblBreed);

		Breed_textField = new JTextField();
		Breed_textField.setFont(new Font("Arial", Font.PLAIN, 16));
		Breed_textField.setBounds(163, 107, 120, 33);
		panel_top.add(Breed_textField);
		Hover.addPlaceholder(Breed_textField, "Enter breed");
		Hover.roundTextField(Breed_textField, 15, Color.WHITE, Color.LIGHT_GRAY);

		JLabel lblGender = new JLabel("Gender");
		lblGender.setFont(new Font("Arial", Font.PLAIN, 16));
		lblGender.setBounds(325, 107, 76, 33);
		panel_top.add(lblGender);

		gender_comboBox = new JComboBox();
		gender_comboBox.setModel(new DefaultComboBoxModel(new String[] {"Male", "Female"}));
		gender_comboBox.setFont(new Font("Arial", Font.PLAIN, 16));
		gender_comboBox.setBounds(411, 107, 120, 33);
		panel_top.add(gender_comboBox);

		JLabel lblAge = new JLabel("Age");
		lblAge.setFont(new Font("Arial", Font.PLAIN, 16));
		lblAge.setBounds(583, 107, 76, 33);
		panel_top.add(lblAge);

		Age_textField = new JTextField();
		Age_textField.setFont(new Font("Arial", Font.PLAIN, 16));
		Age_textField.setBounds(657, 107, 120, 33);
		panel_top.add(Age_textField);
		Hover.addPlaceholder(Age_textField, "Enter Age");
		Hover.roundTextField(Age_textField, 15, Color.WHITE, Color.LIGHT_GRAY);

		btnAdd = new JButton("Add");
		btnAdd.setIcon(new ImageIcon(ProductView.class.getResource("/view/Icon/add_Icon.png")));
		btnAdd.setBounds(20, 167, 69, 63);
		btnAdd.setContentAreaFilled(false);
		btnAdd.setBorderPainted(false);
		btnAdd.setFocusPainted(false);
		btnAdd.setFont(new Font("Arial", Font.PLAIN, 16));
		btnAdd.setHorizontalTextPosition(SwingConstants.CENTER);
		btnAdd.setVerticalTextPosition(SwingConstants.BOTTOM);
		Hover.addHoverButtonEffect(btnAdd, new Color(0, 102, 204), 0.8f);
		panel_top.add(btnAdd);

		btnEdit = new JButton("Edit");
		btnEdit.setIcon(new ImageIcon(ProductView.class.getResource("/view/Icon/Edit_Icon.png")));
		btnEdit.setBounds(110, 167, 69, 63);
		btnEdit.setContentAreaFilled(false);
		btnEdit.setBorderPainted(false);
		btnEdit.setFocusPainted(false);
		btnEdit.setFont(new Font("Arial", Font.PLAIN, 16));
		btnEdit.setHorizontalTextPosition(SwingConstants.CENTER);
		btnEdit.setVerticalTextPosition(SwingConstants.BOTTOM);
		Hover.addHoverButtonEffect(btnEdit, new Color(0, 102, 204), 0.8f);
		panel_top.add(btnEdit);

		btnDel = new JButton("Delete");
		btnDel.setIcon(new ImageIcon(ProductView.class.getResource("/view/Icon/delete_Icon.png")));
		btnDel.setBounds(189, 167, 87, 63);
		btnDel.setContentAreaFilled(false);
		btnDel.setBorderPainted(false);
		btnDel.setFocusPainted(false);
		btnDel.setFont(new Font("Arial", Font.PLAIN, 16));
		btnDel.setHorizontalTextPosition(SwingConstants.CENTER);
		btnDel.setVerticalTextPosition(SwingConstants.BOTTOM);
		Hover.addHoverButtonEffect(btnDel, new Color(0, 102, 204), 0.8f);
		panel_top.add(btnDel);

		Arrange_comboBox = new JComboBox();
		Arrange_comboBox.setModel(new DefaultComboBoxModel(new String[] {"Tăng dần", "Giảm dần", "Tất cả"}));
		Arrange_comboBox.setFont(new Font("Arial", Font.PLAIN, 16));
		Arrange_comboBox.setBounds(307, 178, 105, 33);
		panel_top.add(Arrange_comboBox);

		Species_comboBox = new JComboBox();
		Species_comboBox.setModel(new DefaultComboBoxModel(new String[] {"Tất cả", "Dog", "Cat", "Bird"}));
		Species_comboBox.setFont(new Font("Arial", Font.PLAIN, 16));
		Species_comboBox.setBounds(443, 178, 76, 33);
		panel_top.add(Species_comboBox);

		Breed_comboBox = new JComboBox();
		Breed_comboBox.setFont(new Font("Arial", Font.PLAIN, 16));
		Breed_comboBox.setBounds(563, 178, 76, 33);
		panel_top.add(Breed_comboBox);

		JPanel searchPanel = new JPanel(new BorderLayout());
		searchPanel.setBounds(684, 187, 234, 24);
		searchPanel.setBackground(Color.WHITE);
		JLabel searchLabel = new JLabel(new ImageIcon(ProductView.class.getResource("/view/Icon/Search_Icon.png")));
		searchLabel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
		searchPanel.add(searchLabel, BorderLayout.WEST);
		Search_textField = new JTextField();
		Search_textField.setBorder(null);
		searchPanel.add(Search_textField, BorderLayout.CENTER);
		Hover.addPlaceholder(Search_textField, "search...");
		Hover.roundPanel(searchPanel, 20, Color.WHITE, Color.GRAY);
		panel_top.add(searchPanel);

		JScrollPane Pet_ScrollPane = new JScrollPane();
		Pet_ScrollPane.setBounds(0, 240, 950, 390);
		Pet_ScrollPane.getViewport().setBackground(new Color(200, 220, 240));
		Pet_ScrollPane.setBackground(new Color(200, 220, 240));
		add(Pet_ScrollPane);

		model = new DefaultTableModel(null, columns) {
			@Override public boolean isCellEditable(int r, int c) { return false; }
		};
		Pet_Table = new JTable(model) {
			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component c = super.prepareRenderer(renderer, row, column);
				if (!isRowSelected(row)) c.setBackground(Color.WHITE);
				else c.setBackground(new Color(255, 255, 153)); // vàng nhạt


				c.setForeground(new Color(40, 40, 40));
				return c;
			}
		};
		Pet_Table.setFont(new Font("Arial", Font.PLAIN, 16));
		Pet_Table.setRowHeight(36);
		Pet_Table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 18));
		Pet_Table.getTableHeader().setBackground(new Color(255, 250, 245));
		Pet_Table.getTableHeader().setForeground(new Color(40, 40, 40));
		Pet_Table.setShowGrid(false);
		Pet_Table.setIntercellSpacing(new Dimension(0, 0));
		((DefaultTableCellRenderer) Pet_Table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.LEFT);
		Pet_ScrollPane.setViewportView(Pet_Table);

		// --- Pagination Panel ---
		JPanel paginationPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		paginationPanel.setBounds(0, 630, 950, 40);
		paginationPanel.setBackground(new Color(200, 220, 240));

		btnPrevPage = new JButton("Previous");
		btnNextPage = new JButton("Next");
		lblPageInfo = new JLabel("Page 1/1");

		Dimension btnSize = new Dimension(100, 30);
		btnPrevPage.setPreferredSize(btnSize);
		btnNextPage.setPreferredSize(btnSize);

		btnPrevPage.setFont(new Font("Arial", Font.PLAIN, 14));
		btnNextPage.setFont(new Font("Arial", Font.PLAIN, 14));
		btnPrevPage.setFocusPainted(false);
		btnNextPage.setFocusPainted(false);
		btnPrevPage.setBackground(Color.WHITE);
		btnNextPage.setBackground(Color.WHITE);
		Hover.addHoverButtonEffect(btnPrevPage, new Color(0, 102, 204), 0.8f);
		Hover.addHoverButtonEffect(btnNextPage, new Color(0, 102, 204), 0.8f);

		paginationPanel.add(btnPrevPage);
		paginationPanel.add(lblPageInfo);
		paginationPanel.add(btnNextPage);
		add(paginationPanel);
	}

	// Getter & Setter
	public String getNameTextField() { return Name_textField.getText(); }
	public String getSpeciesTextField() { return Species_textField.getText(); }
	public String getPriceTextField() { return Price_textField.getText(); }
	public String getAgeTextField() { return Age_textField.getText(); }
	public JTextField getSearchTextField() { return Search_textField; }
	public String getBreed_textField() { return Breed_textField.getText(); }
	public JComboBox getBreedComboBox() { return Breed_comboBox; }
	public JComboBox getArrangeComboBox() { return Arrange_comboBox; }
	public JComboBox getSpeciesComboBox() { return Species_comboBox; }
	public JComboBox getGenderComboBox() { return gender_comboBox; }
	public String getSearchKeyword() { return Search_textField.getText(); }
	public JTable getPetTable() { return Pet_Table; }
	public JButton getAddButton() { return btnAdd; }
	public JButton getEditButton() { return btnEdit; }
	public JButton getDeleteButton() { return btnDel; }
	public JButton getPrevPageButton() { return btnPrevPage; }
	public JButton getNextPageButton() { return btnNextPage; }
	public JLabel getPageInfoLabel() { return lblPageInfo; }

	public void clearFields() {
		Name_textField.setText("");
		Species_textField.setText("");
		Price_textField.setText("");
		Age_textField.setText("");
		Search_textField.setText("");
		gender_comboBox.setSelectedIndex(0);
		Breed_textField.setText("");
	}

	public void setPetData(String id, String name, String species, String price, String breed, String comboBox, String age) {
		Name_textField.setText(name);
		Species_textField.setText(species);
		Price_textField.setText(price);
		Breed_textField.setText(breed);
		gender_comboBox.setSelectedItem(comboBox);
		Age_textField.setText(age);
	}

	public String getValueAt(int row, int column) {
		Object value = Pet_Table.getValueAt(row, column);
		return value != null ? value.toString() : "";
	}

	public void setSelectedCategoryByName(String categoryName) {
		for (int i = 0; i < gender_comboBox.getItemCount(); i++) {
			Category cate = (Category) gender_comboBox.getItemAt(i);
			if (cate.getCategoryName().equals(categoryName)) {
				gender_comboBox.setSelectedIndex(i);
				return;
			}
		}
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

	public void addSearchKeyListener(KeyListener listener) {
		Search_textField.addKeyListener(listener);
	}
}