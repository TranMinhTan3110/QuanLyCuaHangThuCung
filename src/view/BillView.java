package view;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.JLabel;
import view.UI.Hover;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;

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
    private JButton productButton;
    private JButton petButton;
    private JButton customerButton;
    private JLabel lblProductList;
    private JTextField totaltextField;

    public JTextField getNameTextField() {
        return nameTextField;
    }

    public JTextField getPhoneTextField() {
        return phoneTextField;
    }

    public JTextField getAddressTextField() {
        return addressTextField;
    }

    public JTable getTableProductList() {
        return tableProductList;
    }

    public JTable getTablePetList() {
        return tablePetList;
    }

    public JTable getTableCustomerList() {
        return tableCustomerList;
    }

    public JTable getTableBillItems() {
        return tableBillItems;
    }

    public JButton getBtnSave() {
        return btnSave;
    }

    public JButton getBtnExport() {
        return btnExport;
    }

    public BillView() {
        setLayout(null);
        setBounds(0, 0, 950, 750);

        

        // Panel chứa thông tin khách hàng
        JPanel panel = new JPanel();
        panel.setBackground(new Color(255, 255, 223));
        panel.setBounds(0, 0, 508, 345);
        add(panel);
        panel.setLayout(null);

        // Nhãn và trường nhập "Nhập Tên Khách hàng"
        JLabel lblCustomerName = new JLabel("Tên Khách hàng");
        lblCustomerName.setFont(new Font("Arial", Font.PLAIN, 14));
        lblCustomerName.setBounds(25, 20, 150, 20);
        panel.add(lblCustomerName);

        nameTextField = new JTextField();
        nameTextField.setBounds(25, 43, 150, 30);
        Hover.addPlaceholder(nameTextField, "Enter Customer's Name");
        panel.add(nameTextField);
        nameTextField.setColumns(10);
        Hover.roundTextField(nameTextField, 15, Color.WHITE, Color.LIGHT_GRAY);

        // Nhãn và trường nhập "Số điện thoại"
        JLabel lblPhone = new JLabel("Số điện thoại");
        lblPhone.setFont(new Font("Arial", Font.PLAIN, 14));
        lblPhone.setBounds(245, 20, 150, 20);
        panel.add(lblPhone);

        phoneTextField = new JTextField();
        phoneTextField.setColumns(10);
        phoneTextField.setBounds(245, 43, 150, 30);
        Hover.addPlaceholder(phoneTextField, "Enter Phone Number");
        panel.add(phoneTextField);
        Hover.roundTextField(phoneTextField, 15, Color.WHITE, Color.LIGHT_GRAY);

        // Nhãn và trường nhập "Địa chỉ"
        JLabel lblAddress = new JLabel("Địa chỉ");
        lblAddress.setFont(new Font("Arial", Font.PLAIN, 14));
        lblAddress.setBounds(25, 88, 150, 20);
        panel.add(lblAddress);

        addressTextField = new JTextField();
        addressTextField.setColumns(10);
        addressTextField.setBounds(25, 111, 370, 30);
        Hover.addPlaceholder(addressTextField, "Enter Address");
        panel.add(addressTextField);
        Hover.roundTextField(addressTextField, 15, Color.WHITE, Color.LIGHT_GRAY);

        // Nút "Product"
        productButton = new JButton("Product");
        productButton.setForeground(new Color(255, 255, 255));
        productButton.setBackground(new Color(135, 206, 250));
        productButton.setFont(new Font("Arial", Font.BOLD, 16));
        productButton.setBounds(49, 219, 110, 30);
        productButton.setFocusPainted(false);
        Hover.addHoverEffect(productButton, new Color(50,50,50), new Color(135,206,250));
        panel.add(productButton);

        // Nút "Pet"
        petButton = new JButton("Pet");
        petButton.setForeground(new Color(255, 255, 255));
        petButton.setBackground(new Color(135, 206, 250));
        petButton.setFont(new Font("Arial", Font.BOLD, 16));
        petButton.setBounds(201, 219, 110, 30);
        petButton.setFocusPainted(false);
        Hover.addHoverEffect(petButton, new Color(50,50,50), new Color(135,206,250));
        panel.add(petButton);

        // Nút "Customer"
        customerButton = new JButton("Customer");
        customerButton.setForeground(new Color(255, 255, 255));
        customerButton.setBackground(new Color(135, 206, 250));
        customerButton.setFont(new Font("Arial", Font.BOLD, 16));
        customerButton.setBounds(351, 219, 110, 30);
        customerButton.setFocusPainted(false);
        Hover.addHoverEffect(customerButton, new Color(50,50,50), new Color(135,206,250));
        panel.add(customerButton);

        // Thanh tìm kiếm cho Product
        searchProductField = new JTextField();
        searchProductField.setBounds(49, 260, 412, 30);
        Hover.addPlaceholder(searchProductField, "Search Product ID...");
        searchProductField.setVisible(true);
        panel.add(searchProductField);
        searchProductField.setColumns(10);
        Hover.roundTextField(searchProductField, 15, Color.WHITE, Color.LIGHT_GRAY);

        // Thanh tìm kiếm cho Pet
        searchPetField = new JTextField();
        searchPetField.setBounds(49, 260, 412, 30);
        Hover.addPlaceholder(searchPetField, "Search Pet ID...");
        searchPetField.setVisible(false);
        panel.add(searchPetField);
        searchPetField.setColumns(10);
        Hover.roundTextField(searchPetField, 15, Color.WHITE, Color.LIGHT_GRAY);
        

        // Thanh tìm kiếm cho Customer
        searchCustomerField = new JTextField();
        searchCustomerField.setBounds(49, 260, 412, 30);
        Hover.addPlaceholder(searchCustomerField, "Search Customer ID...");
        searchCustomerField.setVisible(false);
        panel.add(searchCustomerField);
        searchCustomerField.setColumns(10);
        Hover.roundTextField(searchCustomerField, 15, Color.WHITE, Color.LIGHT_GRAY);

        // Nhãn "Danh sách hàng hóa"
        lblProductList = new JLabel("Danh sách hàng hóa");
        lblProductList.setFont(new Font("Arial", Font.BOLD, 14));
        lblProductList.setBounds(10, 320, 200, 25);
        add(lblProductList);

        // Bảng danh sách Product
        String[] productColumns = {"ID", "Name", "Price", "Quantity", "Species"};
        DefaultTableModel productModel = new DefaultTableModel(productColumns, 0);
        tableProductList = new JTable(productModel);
        
        scrollPaneProduct = new JScrollPane(tableProductList);
        scrollPaneProduct.setBounds(0, 346, 508, 395);
        scrollPaneProduct.setVisible(true);
        Hover.customizeTableHeader(tableProductList);
        add(scrollPaneProduct);

        // Bảng danh sách Pet
        String[] petColumns = {"Name", "Species", "Price", "Breed", "Age"};
        DefaultTableModel petModel = new DefaultTableModel(petColumns, 0);
        tablePetList = new JTable(petModel);
        
        scrollPanePet = new JScrollPane(tablePetList);
        scrollPanePet.setBounds(0, 346, 506, 395);
        scrollPanePet.setVisible(false);
        Hover.customizeTableHeader(tablePetList);
        add(scrollPanePet);

        // Bảng danh sách Customer
        String[] customerColumns = {"ID", "Name", "Address", "Phone", "Rank", "Score"};
        DefaultTableModel customerModel = new DefaultTableModel(customerColumns, 0);
        tableCustomerList = new JTable(customerModel);
        
        scrollPaneCustomer = new JScrollPane(tableCustomerList);
        scrollPaneCustomer.setBounds(0, 346, 506, 395);
        scrollPaneCustomer.setVisible(false);
        Hover.customizeTableHeader(tableCustomerList);
        add(scrollPaneCustomer);
        
 
        tableCustomerList.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = tableCustomerList.getSelectedRow();
                if (selectedRow != -1) {
                    String name = tableCustomerList.getValueAt(selectedRow, 1).toString();
                    String address = tableCustomerList.getValueAt(selectedRow, 2).toString();
                    String phone = tableCustomerList.getValueAt(selectedRow, 3).toString();

                    nameTextField.setText(name);
                    phoneTextField.setText(phone);
                    addressTextField.setText(address);

                    nameTextField.setForeground(Color.BLACK);
                    phoneTextField.setForeground(Color.BLACK);
                    addressTextField.setForeground(Color.BLACK);
                }
            }
        });

        // Bảng hóa đơn
        String[] billColumns = {"ID", "Name", "Quantity", "Price", "Total"};
        DefaultTableModel billModel = new DefaultTableModel(billColumns, 0);
        tableBillItems = new JTable(billModel);
        scrollPaneBill = new JScrollPane(tableBillItems);
        scrollPaneBill.setBounds(508, 0, 450, 426);
        scrollPaneBill.setBorder(BorderFactory.createTitledBorder(
        	    BorderFactory.createEtchedBorder(),
        	    "Hóa Đơn",
        	    TitledBorder.CENTER, // Canh giữa tiêu đề
        	    TitledBorder.DEFAULT_POSITION,
        	    new Font("Arial", Font.BOLD, 20) // Cỡ chữ 16, đậm
        	));
        Hover.customizeTableHeader(tableBillItems);
        add(scrollPaneBill);

        // Nút "Lưu"
        btnSave = new JButton("Lưu");
        btnSave.setIcon(new ImageIcon(BillView.class.getResource("/view/Icon/save_ICon.png")));
        btnSave.setFont(new Font("Arial", Font.BOLD, 14));
        btnSave.setBounds(551, 609, 150, 70);
        btnSave.setFocusPainted(false);
        btnSave.setFocusPainted(false);
		btnSave.setBorderPainted(false);
		btnSave.setContentAreaFilled(false);
		btnSave.setHorizontalTextPosition(SwingConstants.CENTER);
		btnSave.setVerticalTextPosition(SwingConstants.BOTTOM);
		Hover.addHoverButtonEffect(btnSave, new Color(0, 102, 204), 0.8f);
        add(btnSave);

        // Nút "Xuất hóa đơn"
        btnExport = new JButton("Xuất hóa đơn");
        btnExport.setIcon(new ImageIcon(BillView.class.getResource("/view/Icon/file_Icon.png")));
        btnExport.setFont(new Font("Arial", Font.BOLD, 14));
        btnExport.setBounds(712, 611, 150, 67);
        btnExport.setFocusPainted(false);
        btnExport.setFocusPainted(false);
        btnExport.setBorderPainted(false);
        btnExport.setContentAreaFilled(false);
        btnExport.setHorizontalTextPosition(SwingConstants.CENTER);
        btnExport.setVerticalTextPosition(SwingConstants.BOTTOM);
        Hover.addHoverButtonEffect(btnExport, new Color(0, 102, 204), 0.8f);
        add(btnExport);
        
        JButton btnAdd = new JButton("");
		btnAdd.setIcon(new ImageIcon(ProductView.class.getResource("/view/Icon/add_Icon.png")));
		btnAdd.setBackground(new Color(255, 255, 223));
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnAdd.setFont(new Font("Arial", Font.PLAIN, 16));
		btnAdd.setBounds(748, 530, 45, 45);
		btnAdd.setFocusPainted(false);
		btnAdd.setBorderPainted(false);
		btnAdd.setContentAreaFilled(false);
		btnAdd.setHorizontalTextPosition(SwingConstants.CENTER);
		btnAdd.setVerticalTextPosition(SwingConstants.BOTTOM);
		add(btnAdd);
		Hover.addHoverButtonEffect(btnAdd, new Color(0, 102, 204), 0.8f);
        
		JButton btnDel = new JButton("");
		btnDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnDel.setIcon(new ImageIcon(ProductView.class.getResource("/view/Icon/delete_Icon.png")));
		btnDel.setBackground(new Color(255, 255, 204));
		btnDel.setFont(new Font("Arial", Font.PLAIN, 16));
		btnDel.setBounds(817, 530, 45, 45);
		btnDel.setFocusPainted(false);
		btnDel.setBorderPainted(false);
		btnDel.setContentAreaFilled(false);
		btnDel.setHorizontalTextPosition(SwingConstants.CENTER);
		btnDel.setVerticalTextPosition(SwingConstants.BOTTOM);
		add(btnDel);
		Hover.addHoverButtonEffect(btnDel, new Color(0, 102, 204), 0.8f);
		
		JLabel totallbl = new JLabel("Total");
		totallbl.setFont(new Font("Arial", Font.PLAIN, 16));
		totallbl.setBounds(656, 465, 45, 19);
		add(totallbl);
		
		totaltextField = new JTextField();
		totaltextField.setBounds(732, 459, 119, 25);
		add(totaltextField);
		totaltextField.setColumns(10);
        setupButtonListeners();
    }

    private void setupButtonListeners() {
        // ActionListener cho nút "Product"
        productButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                scrollPaneProduct.setVisible(true);
                scrollPanePet.setVisible(false);
                scrollPaneCustomer.setVisible(false);
                searchProductField.setVisible(true);
                searchPetField.setVisible(false);
                searchCustomerField.setVisible(false);
                lblProductList.setText("Danh sách hàng hóa - Product");
                searchProductField.setText("");
                Hover.addPlaceholder(searchProductField, "Search Product ID...");
            }
        });

        // ActionListener cho nút "Pet"
        petButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                scrollPaneProduct.setVisible(false);
                scrollPanePet.setVisible(true);
                scrollPaneCustomer.setVisible(false);
                searchProductField.setVisible(false);
                searchPetField.setVisible(true);
                searchCustomerField.setVisible(false);
                lblProductList.setText("Danh sách hàng hóa - Pet");
                searchPetField.setText("");
                Hover.addPlaceholder(searchPetField, "Search Pet ID...");
            }
        });

        // ActionListener cho nút "Customer"
        customerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                scrollPaneProduct.setVisible(false);
                scrollPanePet.setVisible(false);
                scrollPaneCustomer.setVisible(true);
                searchProductField.setVisible(false);
                searchPetField.setVisible(false);
                searchCustomerField.setVisible(true);
                lblProductList.setText("Danh sách khách hàng");
                searchCustomerField.setText("");
                Hover.addPlaceholder(searchCustomerField, "Search Customer ID...");
            }
        });
    }
}