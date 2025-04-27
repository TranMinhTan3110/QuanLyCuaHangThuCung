package view;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.JLabel;
import view.UI.Hover;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;

public class BillView extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTextField Name_textField;
    private JTextField SDT_textField;
    private JTextField Add_textField;
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
    private TableRowSorter<DefaultTableModel> sorterProduct;
    private TableRowSorter<DefaultTableModel> sorterPet;
    private TableRowSorter<DefaultTableModel> sorterCustomer;
    private JButton ProductButton;
    private JButton PetButton;
    private JButton CustomerButton;
    private JLabel lblProductList;

    public JTextField getNameTextField() {
        return Name_textField;
    }

    public JTextField getSDTTextField() {
        return SDT_textField;
    }

    public JTextField getAddTextField() {
        return Add_textField;
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

        // Tiêu đề "Hóa đơn"
        JLabel lblTitle = new JLabel("Hóa đơn");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitle.setBounds(600, 10, 100, 30);
        add(lblTitle);

        // Panel chứa thông tin khách hàng
        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 506, 345);
        add(panel);
        panel.setLayout(null);

        // Nhãn và trường nhập "Nhập Tên Khách hàng"
        JLabel lblCustomerName = new JLabel("Nhập Tên Khách hàng");
        lblCustomerName.setFont(new Font("Arial", Font.PLAIN, 14));
        lblCustomerName.setBounds(25, 20, 150, 20);
        panel.add(lblCustomerName);

        Name_textField = new JTextField();
        Name_textField.setBounds(25, 43, 150, 30);
        Hover.addPlaceholder(Name_textField, "Enter Customer's Name");
        panel.add(Name_textField);
        Name_textField.setColumns(10);

        // Nhãn và trường nhập "Số điện thoại"
        JLabel lblPhone = new JLabel("Số điện thoại");
        lblPhone.setFont(new Font("Arial", Font.PLAIN, 14));
        lblPhone.setBounds(245, 20, 150, 20);
        panel.add(lblPhone);

        SDT_textField = new JTextField();
        SDT_textField.setColumns(10);
        SDT_textField.setBounds(245, 43, 150, 30);
        Hover.addPlaceholder(SDT_textField, "Enter Phone Number");
        panel.add(SDT_textField);

        // Nhãn và trường nhập "Địa chỉ"
        JLabel lblAddress = new JLabel("Địa chỉ");
        lblAddress.setFont(new Font("Arial", Font.PLAIN, 14));
        lblAddress.setBounds(25, 88, 150, 20);
        panel.add(lblAddress);

        Add_textField = new JTextField();
        Add_textField.setColumns(10);
        Add_textField.setBounds(25, 111, 370, 30);
        Hover.addPlaceholder(Add_textField, "Enter Address");
        panel.add(Add_textField);

        // Nút "Product"
        ProductButton = new JButton("Product");
        ProductButton.setFont(new Font("Arial", Font.BOLD, 16));
        ProductButton.setBounds(49, 219, 110, 30);
        ProductButton.setFocusPainted(false);
        panel.add(ProductButton);

        // Nút "Pet"
        PetButton = new JButton("Pet");
        PetButton.setFont(new Font("Arial", Font.BOLD, 16));
        PetButton.setBounds(201, 219, 110, 30);
        PetButton.setFocusPainted(false);
        panel.add(PetButton);

        // Nút "Customer"
        CustomerButton = new JButton("Customer");
        CustomerButton.setFont(new Font("Arial", Font.BOLD, 16));
        CustomerButton.setBounds(351, 219, 110, 30);
        CustomerButton.setFocusPainted(false);
        panel.add(CustomerButton);

        // Thanh tìm kiếm cho Product
        searchProductField = new JTextField();
        searchProductField.setBounds(49, 260, 412, 30);
        Hover.addPlaceholder(searchProductField, "Search Product ID...");
        searchProductField.setVisible(true); // Hiển thị mặc định
        panel.add(searchProductField);
        searchProductField.setColumns(10);

        // Thanh tìm kiếm cho Pet
        searchPetField = new JTextField();
        searchPetField.setBounds(49, 260, 412, 30);
        Hover.addPlaceholder(searchPetField, "Search Pet ID...");
        searchPetField.setVisible(false);
        panel.add(searchPetField);
        searchPetField.setColumns(10);

        // Thanh tìm kiếm cho Customer
        searchCustomerField = new JTextField();
        searchCustomerField.setBounds(49, 260, 412, 30);
        Hover.addPlaceholder(searchCustomerField, "Search Customer ID...");
        searchCustomerField.setVisible(false);
        panel.add(searchCustomerField);
        searchCustomerField.setColumns(10);

        // Nhãn "Danh sách hàng hóa"
        lblProductList = new JLabel("Danh sách hàng hóa");
        lblProductList.setFont(new Font("Arial", Font.BOLD, 14));
        lblProductList.setBounds(10, 320, 200, 25);
        add(lblProductList);

        // Bảng danh sách Product
        String[] productColumns = {"ID", "Tên", "Giá", "Số lượng", "Loại"};
        DefaultTableModel productModel = new DefaultTableModel(productColumns, 0);
        tableProductList = new JTable(productModel);
        sorterProduct = new TableRowSorter<>(productModel);
        tableProductList.setRowSorter(sorterProduct);
        scrollPaneProduct = new JScrollPane(tableProductList);
        scrollPaneProduct.setBounds(0, 346, 506, 395);
        scrollPaneProduct.setVisible(true); // Hiển thị mặc định
        add(scrollPaneProduct);

        // Bảng danh sách Pet
        String[] petColumns = {"Name", "Species", "Price", "Breed", "Age"};
        DefaultTableModel petModel = new DefaultTableModel(petColumns, 0);
        tablePetList = new JTable(petModel);
        sorterPet = new TableRowSorter<>(petModel);
        tablePetList.setRowSorter(sorterPet);
        scrollPanePet = new JScrollPane(tablePetList);
        scrollPanePet.setBounds(0, 346, 506, 395);
        scrollPanePet.setVisible(false); // Ẩn mặc định
        add(scrollPanePet);

        // Bảng danh sách Customer
        String[] customerColumns = {"ID", "Tên", "Số điện thoại", "Địa chỉ"};
        DefaultTableModel customerModel = new DefaultTableModel(customerColumns, 0);
        tableCustomerList = new JTable(customerModel);
        sorterCustomer = new TableRowSorter<>(customerModel);
        tableCustomerList.setRowSorter(sorterCustomer);
        scrollPaneCustomer = new JScrollPane(tableCustomerList);
        scrollPaneCustomer.setBounds(0, 346, 506, 395);
        scrollPaneCustomer.setVisible(false); // Ẩn mặc định
        add(scrollPaneCustomer);

        // Bảng hóa đơn
        String[] billColumns = {"ID", "Tên hàng", "Số lượng", "Giá", "Tổng"};
        DefaultTableModel billModel = new DefaultTableModel(billColumns, 0);
        tableBillItems = new JTable(billModel);
        scrollPaneBill = new JScrollPane(tableBillItems);
        scrollPaneBill.setBounds(506, 0, 450, 514);
        add(scrollPaneBill);

        // Nút "Lưu"
        btnSave = new JButton("Lưu");
        btnSave.setFont(new Font("Arial", Font.BOLD, 14));
        btnSave.setBounds(550, 530, 100, 30);
        btnSave.setFocusPainted(false);
        add(btnSave);

        // Nút "Xuất hóa đơn"
        btnExport = new JButton("Xuất hóa đơn");
        btnExport.setFont(new Font("Arial", Font.BOLD, 14));
        btnExport.setBounds(700, 530, 120, 30);
        btnExport.setFocusPainted(false);
        add(btnExport);

        // Thiết lập ActionListener cho các nút
        setupButtonListeners();

        // Thiết lập KeyListener cho các thanh tìm kiếm
        setupSearchListeners();
    }

    private void setupButtonListeners() {
        // ActionListener cho nút "Product"
        ProductButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                scrollPaneProduct.setVisible(true);
                scrollPanePet.setVisible(false);
                scrollPaneCustomer.setVisible(false);
                searchProductField.setVisible(true);
                searchPetField.setVisible(false);
                searchCustomerField.setVisible(false);
                lblProductList.setText("Danh sách hàng hóa - Product");
                searchProductField.setText("");
                sorterProduct.setRowFilter(null);
                Hover.addPlaceholder(searchProductField, "Search Product ID...");
            }
        });

        // ActionListener cho nút "Pet"
        PetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                scrollPaneProduct.setVisible(false);
                scrollPanePet.setVisible(true);
                scrollPaneCustomer.setVisible(false);
                searchProductField.setVisible(false);
                searchPetField.setVisible(true);
                searchCustomerField.setVisible(false);
                lblProductList.setText("Danh sách hàng hóa - Pet");
                searchPetField.setText("");
                sorterPet.setRowFilter(null);
                Hover.addPlaceholder(searchPetField, "Search Pet ID...");
            }
        });

        // ActionListener cho nút "Customer"
        CustomerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                scrollPaneProduct.setVisible(false);
                scrollPanePet.setVisible(false);
                scrollPaneCustomer.setVisible(true);
                searchProductField.setVisible(false);
                searchPetField.setVisible(false);
                searchCustomerField.setVisible(true);
                lblProductList.setText("Danh sách khách hàng");
                searchCustomerField.setText("");
                sorterCustomer.setRowFilter(null);
                Hover.addPlaceholder(searchCustomerField, "Search Customer ID...");
            }
        });
    }

    private void setupSearchListeners() {
        // Tìm kiếm cho bảng Product
        searchProductField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String searchText = searchProductField.getText().trim();
                if (searchText.isEmpty()) {
                    sorterProduct.setRowFilter(null);
                } else {
                    sorterProduct.setRowFilter(RowFilter.regexFilter("(?i)" + searchText, 0)); // Lọc theo cột "ID"
                }
            }
        });

        // Tìm kiếm cho bảng Pet
        searchPetField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String searchText = searchPetField.getText().trim();
                if (searchText.isEmpty()) {
                    sorterPet.setRowFilter(null);
                } else {
                    sorterPet.setRowFilter(RowFilter.regexFilter("(?i)" + searchText, 0)); // Lọc theo cột "ID"
                }
            }
        });

        // Tìm kiếm cho bảng Customer
        searchCustomerField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String searchText = searchCustomerField.getText().trim();
                if (searchText.isEmpty()) {
                    sorterCustomer.setRowFilter(null);
                } else {
                    sorterCustomer.setRowFilter(RowFilter.regexFilter("(?i)" + searchText, 0)); // Lọc theo cột "ID"
                }
            }
        });
    }

    
}