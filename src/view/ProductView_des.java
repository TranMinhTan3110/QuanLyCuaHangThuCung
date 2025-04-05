package view;

import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;

import view.UI.Hover;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ProductView_des extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField ProID_textField;
	private JTextField ProName_textField;
	private JTextField Price_textField;
	private JTextField CateID_textField;
	private JTable Pro_table;

	/**
	 * Create the panel.
	 */
	public ProductView_des() {
		setLayout(null);
		setBounds(0,0,950,750);
		
		JPanel panel_top = new JPanel();
		panel_top.setBackground(new Color(255, 255, 223));
		panel_top.setBounds(0, 0, 950, 240);
		add(panel_top);
		panel_top.setLayout(null);
		
		JLabel lblPro_ID = new JLabel("Product ID");
		lblPro_ID.setFont(new Font("Arial", Font.PLAIN, 16));
		lblPro_ID.setBounds(151, 61, 77, 24);
		panel_top.add(lblPro_ID);
		
		ProID_textField = new JTextField();
		ProID_textField.setBounds(294, 61, 114, 24);
		panel_top.add(ProID_textField);
		ProID_textField.setColumns(10);
		Hover.addPlaceholder(ProID_textField, "Enter ID");
		
		JLabel lblPro_Name = new JLabel("Product Name");
		lblPro_Name.setFont(new Font("Arial", Font.PLAIN, 16));
		lblPro_Name.setBounds(151, 126, 122, 24);
		panel_top.add(lblPro_Name);
		
		ProName_textField = new JTextField();
		ProName_textField.setColumns(10);
		ProName_textField.setBounds(294, 126, 114, 24);
		panel_top.add(ProName_textField);
		Hover.addPlaceholder(ProName_textField, "Enter Name");
		
		JLabel lblPrice = new JLabel("Price");
		lblPrice.setFont(new Font("Arial", Font.PLAIN, 16));
		lblPrice.setBounds(510, 61, 77, 24);
		panel_top.add(lblPrice);
		
		Price_textField = new JTextField();
		Price_textField.setColumns(10);
		Price_textField.setBounds(625, 61, 114, 24);
		panel_top.add(Price_textField);
		Hover.addPlaceholder(Price_textField, "Enter Price");
		
		JLabel lblCate_ID_1 = new JLabel("Category ID");
		lblCate_ID_1.setFont(new Font("Arial", Font.PLAIN, 16));
		lblCate_ID_1.setBounds(510, 126, 94, 24);
		panel_top.add(lblCate_ID_1);
		
		CateID_textField = new JTextField();
		CateID_textField.setColumns(10);
		CateID_textField.setBounds(625, 126, 114, 24);
		panel_top.add(CateID_textField);
		Hover.addPlaceholder(CateID_textField, "Enter ID");
		
		JButton btnEdit = new JButton("Edit");
		btnEdit.setBackground(new Color(255, 255, 204));
		btnEdit.setFont(new Font("Arial", Font.PLAIN, 16));
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnEdit.setBounds(210, 191, 99, 29);
		btnEdit.setFocusPainted(false);
		panel_top.add(btnEdit);
		Hover.addHoverEffect(btnEdit, new Color(128, 128, 100), new Color(255, 255, 204));
		
		JButton btnAdd = new JButton("Add");
		btnAdd.setBackground(new Color(255, 255, 204));
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnAdd.setFont(new Font("Arial", Font.PLAIN, 16));
		btnAdd.setBounds(409, 191, 99, 29);
		btnAdd.setFocusPainted(false);
		panel_top.add(btnAdd);
		Hover.addHoverEffect(btnAdd, new Color(128, 128, 100), new Color(255, 255, 204));
		
		JButton btnDel = new JButton("Delete");
		btnDel.setBackground(new Color(255, 255, 204));
		btnDel.setFont(new Font("Arial", Font.PLAIN, 16));
		btnDel.setBounds(599, 191, 99, 29);
		btnDel.setFocusPainted(false);
		panel_top.add(btnDel);
		Hover.addHoverEffect(btnDel, new Color(128, 128, 100), new Color(255, 255, 204));
		
		JScrollPane Pro_list = new JScrollPane();
		Pro_list.setBounds(0, 244, 950, 500);
		add(Pro_list);
		
		Pro_table = new JTable();
		Pro_table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Product ID", "Product Name", "Price", "Category ID"
			}
		));
		Pro_list.setViewportView(Pro_table);
	}
}
