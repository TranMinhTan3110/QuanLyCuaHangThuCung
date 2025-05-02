package view;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JTextField;
import javax.swing.SwingConstants;

import model.entity.Category;
import view.UI.Hover;

import javax.swing.JComboBox;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

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
	/**
	 * Create the panel.
	 */
	public PetView() {
		setLayout(null);
		setBounds(0,0,950,750);

		JPanel panel_top = new JPanel();
		panel_top.setBackground(new Color(255, 255, 223));
		panel_top.setBounds(0, 0, 950, 240);
		add(panel_top);
		panel_top.setLayout(null);

		JLabel lblName = new JLabel("Name");
		lblName.setFont(new Font("Arial", Font.PLAIN, 16));
		lblName.setBounds(97, 42, 56, 33);
		panel_top.add(lblName);

		Name_textField = new JTextField();
		Name_textField.setFont(new Font("Arial", Font.PLAIN, 16));
		Name_textField.setBounds(163, 42, 120, 33);
		panel_top.add(Name_textField);
		Name_textField.setColumns(10);
		Hover.addPlaceholder(Name_textField, "Enter Name");
		Hover.roundTextField(Name_textField, 15, Color.WHITE, Color.LIGHT_GRAY);

		JLabel lblSpecies = new JLabel("Species");
		lblSpecies.setFont(new Font("Arial", Font.PLAIN, 16));
		lblSpecies.setBounds(325, 42, 76, 33);
		panel_top.add(lblSpecies);

		Species_textField = new JTextField();
		Species_textField.setFont(new Font("Arial", Font.PLAIN, 16));
		Species_textField.setColumns(10);
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
		Price_textField.setColumns(10);
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
		Breed_textField.setColumns(10);
		Breed_textField.setBounds(163, 107, 120, 33);
		panel_top.add(Breed_textField);
		Hover.addPlaceholder(Breed_textField, "Enter breed");
		Hover.roundTextField(Breed_textField, 15, Color.WHITE, Color.LIGHT_GRAY);


		JLabel lblAge = new JLabel("Age");
		lblAge.setFont(new Font("Arial", Font.PLAIN, 16));
		lblAge.setBounds(583, 107, 76, 33);
		panel_top.add(lblAge);

		Age_textField = new JTextField();
		Age_textField.setFont(new Font("Arial", Font.PLAIN, 16));
		Age_textField.setColumns(10);
		Age_textField.setBounds(657, 107, 120, 33);
		panel_top.add(Age_textField);
		Hover.addPlaceholder(Age_textField, "Enter Age");
		Hover.roundTextField(Age_textField, 15, Color.WHITE, Color.LIGHT_GRAY);

		btnEdit = new JButton("Edit");
		btnEdit.setIcon(new ImageIcon(ProductView.class.getResource("/view/Icon/Edit_Icon.png")));
		btnEdit.setBackground(new Color(255, 255, 204));
		btnEdit.setFont(new Font("Arial", Font.PLAIN, 16));
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

		Arrange_comboBox = new JComboBox();
		Arrange_comboBox.setModel(new DefaultComboBoxModel(new String[] {"Tăng dần", "Giảm dần", "Tất cả"}));
		Arrange_comboBox.setFont(new Font("Arial", Font.PLAIN, 16));
		Arrange_comboBox.setBounds(307, 178, 105, 33);
		panel_top.add(Arrange_comboBox);

		Species_comboBox = new JComboBox();
		Species_comboBox.setModel(new DefaultComboBoxModel(new String[] {"Chó", "Mèo", "Tất cả" }));
		Species_comboBox.setFont(new Font("Arial", Font.PLAIN, 16));
		Species_comboBox.setBounds(443, 178, 76, 33);
		panel_top.add(Species_comboBox);



		JLabel lblGender = new JLabel("Gender");
		lblGender.setFont(new Font("Arial", Font.PLAIN, 16));
		lblGender.setBounds(325, 107, 76, 33);
		panel_top.add(lblGender);

		gender_comboBox = new JComboBox();
		gender_comboBox.setModel(new DefaultComboBoxModel(new String[] {"Male", "Female"}));
		gender_comboBox.setFont(new Font("Arial", Font.PLAIN, 16));
		gender_comboBox.setBounds(411, 107, 120, 33);
		panel_top.add(gender_comboBox);

		Breed_comboBox = new JComboBox();
		Breed_comboBox.setFont(new Font("Arial", Font.PLAIN, 16));
		Breed_comboBox.setBounds(563, 178, 76, 33);
		panel_top.add(Breed_comboBox);

		JScrollPane Pet_ScrollPane = new JScrollPane();
		Pet_ScrollPane.setBounds(0, 240, 950, 510);
		add(Pet_ScrollPane);

		Pet_Table = new JTable();
		Pet_Table.setModel(new DefaultTableModel(
				new Object[][] {
						// dữ liệu nếu có
				},
				new String[] {
						"ID", "Name", "Breed", "Price", "Species", "Gender", "Age"
				}
		) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // không cho sửa ô nào cả
			}
		});

		Pet_Table.setFont(new Font("Arial", Font.PLAIN, 16));
		Hover.customizeTableHeader(Pet_Table);
		Pet_ScrollPane.setViewportView(Pet_Table);
	}

	// Getters cho các TextField
	public String getNameTextField() {
		return Name_textField.getText();
	}

	public String getSpeciesTextField() {
		return Species_textField.getText();
	}

	public String getPriceTextField() {
		return Price_textField.getText();
	}

	public String getAgeTextField() {
		return Age_textField.getText();
	}

	public JTextField getSearchTextField() {
		return Search_textField;
	}

	public String getBreed_textField() {
		return Breed_textField.getText();
	}
	// Getters cho các ComboBox
	public JComboBox getBreedComboBox() {
		return Breed_comboBox;
	}

	public JComboBox getArrangeComboBox() {
		return Arrange_comboBox;
	}

	public JComboBox getSpeciesComboBox() {
		return Species_comboBox;
	}

	public JComboBox getGenderComboBox() {
		return gender_comboBox;
	}

	public String getSearchKeyword() {
		return Search_textField.getText();
	}

	public void addSearchKeyListener(java.awt.event.KeyListener listener) {
		Search_textField.addKeyListener(listener);
	}
	// Getter cho Table
	public JTable getPetTable() {
		return Pet_Table;
	}

	// Getters cho Buttons
	public JButton getAddButton() {
		return btnAdd;
	}

	public JButton getEditButton() {
		return btnEdit;
	}

	public JButton getDeleteButton() {
		return btnDel;
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

	public void clearFields() {
		Name_textField.setText("");
		Species_textField.setText("");
		Price_textField.setText("");
		Age_textField.setText("");
		Search_textField.setText("");
		gender_comboBox.setSelectedIndex(0);
		Breed_textField.setText("");

	}

	public void setAge_textField(String age) {
		Age_textField.setText(age);
	}

	public void setBreed_textField(String breed) {
		Breed_textField.setText(breed);
}

	public void setGender_comboBox(int index) {
		this.gender_comboBox.setSelectedIndex(index);
	}

	public void setName_textField(String name) {
		this.Name_textField.setText(name);
	}

	public void setPrice_textField(String price) {
		this.Price_textField.setText(price);
	}

	public void setSpecies_textField(String  species) {
		this.Species_textField.setText(species);
	}

	public String getValueAt(int row, int column) {
		Object value = Pet_Table.getValueAt(row, column);
		return value != null ? value.toString() : "";
	}
	//đẩy combobox từ dòng dc chọn lên
	public void setSelectedCategoryByName(String categoryName) {
		for (int i = 0; i < gender_comboBox.getItemCount(); i++) {
			Category cate = (Category) gender_comboBox.getItemAt(i);
			if (cate.getCategoryName().equals(categoryName)) {
				gender_comboBox.setSelectedIndex(i);
				return;
			}
		}
	}
	//set data cho ô nhập liệu
	public void setPetData(String id, String name, String species, String price, String breed, String comboBox, String age) {
		Name_textField.setText(name);
		Species_textField.setText(species);
		Price_textField.setText(price);
		Breed_textField.setText(breed);
		gender_comboBox.setSelectedItem(comboBox); // giới tính
		Age_textField.setText(age);                // tuổi
	}

}
