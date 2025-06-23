package view;

import java.awt.*;
import java.awt.event.KeyListener;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;

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
	private JComboBox<String> Breed_comboBox;
	private JComboBox<String> Species_comboBox;
	private JComboBox<String> Arrange_comboBox;
	private JComboBox<String> gender_comboBox;
	private JButton btnAdd;
	private JButton btnDel;
	private JButton btnEdit;
	private String[] columns = {"ID", "Name", "Breed", "Price", "Species", "Gender", "Age"};
	private DefaultTableModel model;

	// Pagination controls
	private JButton btnPrevPage;
	private JButton btnNextPage;
	private JLabel lblPageInfo;

	public PetView() {
		setLayout(null);
		setBounds(0, 0, 950, 750);
		setBackground(new Color(200, 220, 240));

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

		JLabel lblName = new JLabel("Name");
		lblName.setFont(labelFont);
		lblName.setBounds(97, 42, 56, 33);
		panel_top.add(lblName);

		Name_textField = new JTextField();
		Name_textField.setFont(inputFont);
		Name_textField.setBounds(163, 42, 140, 36);
		Name_textField.setBorder(inputBorder);
		Name_textField.setBackground(Color.WHITE);
		panel_top.add(Name_textField);

		JLabel lblSpecies = new JLabel("Species");
		lblSpecies.setFont(labelFont);
		lblSpecies.setBounds(325, 42, 76, 33);
		panel_top.add(lblSpecies);

		Species_textField = new JTextField();
		Species_textField.setFont(inputFont);
		Species_textField.setBounds(411, 42, 140, 36);
		Species_textField.setBorder(inputBorder);
		Species_textField.setBackground(Color.WHITE);
		panel_top.add(Species_textField);

		JLabel lblPrice = new JLabel("Price");
		lblPrice.setFont(labelFont);
		lblPrice.setBounds(583, 42, 56, 33);
		panel_top.add(lblPrice);

		Price_textField = new JTextField();
		Price_textField.setFont(inputFont);
		Price_textField.setBounds(657, 42, 140, 36);
		Price_textField.setBorder(inputBorder);
		Price_textField.setBackground(Color.WHITE);
		panel_top.add(Price_textField);

		JLabel lblBreed = new JLabel("Breed");
		lblBreed.setFont(labelFont);
		lblBreed.setBounds(97, 107, 76, 33);
		panel_top.add(lblBreed);

		Breed_textField = new JTextField();
		Breed_textField.setFont(inputFont);
		Breed_textField.setBounds(163, 107, 140, 36);
		Breed_textField.setBorder(inputBorder);
		Breed_textField.setBackground(Color.WHITE);
		panel_top.add(Breed_textField);

		JLabel lblGender = new JLabel("Gender");
		lblGender.setFont(labelFont);
		lblGender.setBounds(325, 107, 76, 33);
		panel_top.add(lblGender);

		gender_comboBox = new JComboBox<>(new String[] {"Male", "Female"});
		gender_comboBox.setFont(inputFont);
		gender_comboBox.setBounds(411, 107, 140, 36);
		gender_comboBox.setBackground(Color.WHITE);
		gender_comboBox.setBorder(inputBorder);
		gender_comboBox.setUI(new ModernComboBoxUI());
		panel_top.add(gender_comboBox);

		JLabel lblAge = new JLabel("Age");
		lblAge.setFont(labelFont);
		lblAge.setBounds(583, 107, 76, 33);
		panel_top.add(lblAge);

		Age_textField = new JTextField();
		Age_textField.setFont(inputFont);
		Age_textField.setBounds(657, 107, 140, 36);
		Age_textField.setBorder(inputBorder);
		Age_textField.setBackground(Color.WHITE);
		panel_top.add(Age_textField);

		// --- BUTTON WITH ICON ---
		btnAdd = new JButton("Add");
		btnAdd.setIcon(new ImageIcon(getClass().getResource("/view/Icon/add_Icon.png")));
		btnAdd.setBounds(20, 167, 69, 63);
		btnAdd.setFont(labelFont);
		btnAdd.setHorizontalTextPosition(SwingConstants.CENTER);
		btnAdd.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnAdd.setContentAreaFilled(false);
		btnAdd.setBorderPainted(false);
		btnAdd.setFocusPainted(false);
		panel_top.add(btnAdd);

		btnEdit = new JButton("Edit");
		btnEdit.setIcon(new ImageIcon(getClass().getResource("/view/Icon/edit_Icon.png")));
		btnEdit.setBounds(110, 167, 69, 63);
		btnEdit.setFont(labelFont);
		btnEdit.setHorizontalTextPosition(SwingConstants.CENTER);
		btnEdit.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnEdit.setContentAreaFilled(false);
		btnEdit.setBorderPainted(false);
		btnEdit.setFocusPainted(false);
		panel_top.add(btnEdit);

		btnDel = new JButton("Delete");
		btnDel.setIcon(new ImageIcon(getClass().getResource("/view/Icon/delete_Icon.png")));
		btnDel.setBounds(189, 167, 87, 63);
		btnDel.setFont(labelFont);
		btnDel.setHorizontalTextPosition(SwingConstants.CENTER);
		btnDel.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnDel.setContentAreaFilled(false);
		btnDel.setBorderPainted(false);
		btnDel.setFocusPainted(false);
		panel_top.add(btnDel);

		Arrange_comboBox = new JComboBox<>(new String[] {"Tăng dần", "Giảm dần", "Tất cả"});
		Arrange_comboBox.setFont(inputFont);
		Arrange_comboBox.setBounds(307, 178, 120, 36);
		Arrange_comboBox.setBackground(Color.WHITE);
		Arrange_comboBox.setBorder(inputBorder);
		Arrange_comboBox.setUI(new ModernComboBoxUI());
		panel_top.add(Arrange_comboBox);

		Species_comboBox = new JComboBox<>(new String[] {"Tất cả", "Dog", "Cat", "Bird"});
		Species_comboBox.setFont(inputFont);
		Species_comboBox.setBounds(443, 178, 120, 36);
		Species_comboBox.setBackground(Color.WHITE);
		Species_comboBox.setBorder(inputBorder);
		Species_comboBox.setUI(new ModernComboBoxUI());
		panel_top.add(Species_comboBox);

		Breed_comboBox = new JComboBox<>();
		Breed_comboBox.setFont(inputFont);
		Breed_comboBox.setBounds(583, 178, 120, 36);
		Breed_comboBox.setBackground(Color.WHITE);
		Breed_comboBox.setBorder(inputBorder);
		Breed_comboBox.setUI(new ModernComboBoxUI());
		panel_top.add(Breed_comboBox);

		// --- SEARCH PANEL (rounded, with icon and placeholder) ---
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

		// Table
		model = new DefaultTableModel(null, columns) {
			@Override public boolean isCellEditable(int r, int c) { return false; }
		};
		Pet_Table = new JTable(model) {
			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component c = super.prepareRenderer(renderer, row, column);
				if (!isRowSelected(row)) c.setBackground(Color.WHITE);
				else c.setBackground(new Color(255, 255, 153));
				c.setForeground(new Color(40, 40, 40));
				return c;
			}
		};
		Pet_Table.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		Pet_Table.setRowHeight(36);
		Pet_Table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 18));
		Pet_Table.getTableHeader().setBackground(new Color(240, 245, 250));
		Pet_Table.getTableHeader().setForeground(new Color(33, 70, 120));
		Pet_Table.setShowGrid(false);
		Pet_Table.setIntercellSpacing(new Dimension(0, 0));
		((DefaultTableCellRenderer) Pet_Table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.LEFT);

		JScrollPane Pet_ScrollPane = new JScrollPane(Pet_Table);
		Pet_ScrollPane.setBounds(0, 240, 950, 390);
		Pet_ScrollPane.getViewport().setBackground(new Color(200, 220, 240));
		Pet_ScrollPane.setBackground(new Color(200, 220, 240));
		add(Pet_ScrollPane);

		// Pagination Panel
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

	// ==== GETTERS for Controller ====
	public JButton getAddButton() { return btnAdd; }
	public JButton getDeleteButton() { return btnDel; }
	public JButton getEditButton() { return btnEdit; }
	public JButton getPrevPageButton() { return btnPrevPage; }
	public JButton getNextPageButton() { return btnNextPage; }
	public JLabel getPageInfoLabel() { return lblPageInfo; }
	public JTable getPetTable() { return Pet_Table; }
	public JComboBox<String> getSpeciesComboBox() { return Species_comboBox; }
	public JComboBox<String> getBreedComboBox() { return Breed_comboBox; }
	public JComboBox<String> getArrangeComboBox() { return Arrange_comboBox; }
	public JComboBox<String> getGenderComboBox() { return gender_comboBox; }

	// ==== GET TEXT VALUE (String) ====
	public String getNameTextField() { return Name_textField.getText(); }
	public String getSpeciesTextField() { return Species_textField.getText(); }
	public String getPriceTextField() { return Price_textField.getText(); }
	public String getAgeTextField() { return Age_textField.getText(); }
	public String getBreed_textField() { return Breed_textField.getText(); }
	public String getSearchKeyword() { return Search_textField.getText(); }

	// ==== ADD SEARCH LISTENER ====
	public void addSearchKeyListener(KeyListener listener) {
		Search_textField.addKeyListener(listener);
	}

	// ==== SET DATA TO FORM ====
	public void setPetData(String id, String name, String species, String price, String breed, String gender, String age) {
		Name_textField.setText(name);
		Species_textField.setText(species);
		Price_textField.setText(price);
		Breed_textField.setText(breed);
		Age_textField.setText(age);
		gender_comboBox.setSelectedItem(gender);
	}

	// ==== CLEAR FORM ====
	public void clearFields() {
		Name_textField.setText("");
		Species_textField.setText("");
		Price_textField.setText("");
		Breed_textField.setText("");
		Age_textField.setText("");
		gender_comboBox.setSelectedIndex(0);
	}
}