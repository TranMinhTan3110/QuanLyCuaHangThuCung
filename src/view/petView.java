package view;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;
import javax.swing.SwingConstants;

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
	private JTable Pet_Table;

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
		Hover.addPlaceholder(Species_textField, "Enter Species");
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
		lblBreed.setBounds(222, 107, 76, 33);
		panel_top.add(lblBreed);
		
		JComboBox Breed_comboBox = new JComboBox();
		Breed_comboBox.setModel(new DefaultComboBoxModel(new String[] {"Male", "Female"}));
		Breed_comboBox.setFont(new Font("Arial", Font.PLAIN, 16));
		Breed_comboBox.setBounds(307, 107, 120, 33);
		panel_top.add(Breed_comboBox);
		
		JLabel lblAge = new JLabel("Age");
		lblAge.setFont(new Font("Arial", Font.PLAIN, 16));
		lblAge.setBounds(493, 107, 76, 33);
		panel_top.add(lblAge);
		
		Age_textField = new JTextField();
		Age_textField.setFont(new Font("Arial", Font.PLAIN, 16));
		Age_textField.setColumns(10);
		Age_textField.setBounds(560, 107, 120, 33);
		panel_top.add(Age_textField);
		Hover.addPlaceholder(Age_textField, "Enter Age");
		Hover.roundTextField(Age_textField, 15, Color.WHITE, Color.LIGHT_GRAY);
		
		JButton btnEdit = new JButton("Edit");
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

		JButton btnAdd = new JButton("Add");
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

		JButton btnDel = new JButton("Delete");
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

		JScrollPane Pet_ScrollPane = new JScrollPane();
		Pet_ScrollPane.setBounds(0, 240, 950, 510);
		add(Pet_ScrollPane);
		
		Pet_Table = new JTable();
		Pet_Table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Name", "Species", "Price", "Breed", "Age"
			}
		));
		Pet_Table.setFont(new Font("Arial", Font.PLAIN, 16));
		Hover.customizeTableHeader(Pet_Table);
		Pet_ScrollPane.setViewportView(Pet_Table);
	}
}
