package view;

import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.JTextField;
import javax.swing.SwingConstants;

import view.UI.Hover;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.ImageIcon;

public class ProductView extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField Quan_textField;
	private JTextField ProName_textField;
	private JTextField Price_textField;
	private JTextField CateID_textField;
	private JTable Pro_table;
	private JTextField Search_textField;

	/**
	 * Create the panel.
	 */
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

		// Tạo panel chứa nút -, text field, nút +
		JPanel quantityPanel = new JPanel();
		quantityPanel.setLayout(new BorderLayout());
		quantityPanel.setBounds(596, 61, 132, 34);
		quantityPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		// Nút giảm
		JButton btnMinus = new JButton("−");
		btnMinus.setBackground(new Color(232, 150, 89));
		btnMinus.setMargin(new Insets(0, 5, 0, 5));
		btnMinus.setFocusPainted(false);
		btnMinus.setFont(new Font("Arial", Font.BOLD, 12));

		// Text field
		JTextField Quan_textField = new JTextField("0");
		Quan_textField.setHorizontalAlignment(JTextField.CENTER);
		Quan_textField.setFont(new Font("Arial", Font.PLAIN, 14));
		Quan_textField.setBorder(null);

		// Nút tăng
		JButton btnPlus = new JButton("+");
		btnPlus.setBackground(new Color(232, 150, 89));
		btnPlus.setMargin(new Insets(0, 5, 0, 5));
		btnPlus.setFocusPainted(false);
		btnPlus.setFont(new Font("Arial", Font.BOLD, 12));

		// Thêm vào panel theo BorderLayout
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
		
		JLabel lblCate_ID_1 = new JLabel("Category ID");
		lblCate_ID_1.setFont(new Font("Arial", Font.PLAIN, 16));
		lblCate_ID_1.setBounds(444, 120, 94, 24);
		panel_top.add(lblCate_ID_1);
		
		CateID_textField = new JTextField();
		CateID_textField.setColumns(10);
		CateID_textField.setBounds(596, 118, 132, 34);
		panel_top.add(CateID_textField);
		Hover.addPlaceholder(CateID_textField, "Enter ID");
		Hover.roundTextField(CateID_textField, 15, Color.WHITE, Color.LIGHT_GRAY);
		
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
		
		JScrollPane Pro_list = new JScrollPane();
		Pro_list.setBounds(0, 244, 950, 500);
		add(Pro_list);
		
		Pro_table = new JTable();
		Pro_table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "Name", "Price","Quantity", "Category"
			}
		));
		Pro_list.setViewportView(Pro_table);
	}
}
