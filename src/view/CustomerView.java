      package view;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import model.entity.Customer;
import view.UI.Hover;

public class CustomerView extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTable table;
    private JTextField Name_textField;
    private JTextField Phone_textField;
    private JTextField Address_textField;
    private JTextField Score_textField;
    private JTextField Search_textField;
    private DefaultTableModel model;
    private JButton btnSave, btnEdit, btnDel;
    private JButton btnActive, btnInactive;
    // Pagination
    private int currentPage = 1;
    private int rowsPerPage = 21;
    private int totalRows = 0;
    private int totalPages = 0;
    private List<Object[]> allData = new ArrayList<>();
    private JLabel pageLabel;

    public CustomerView() {
        this.btnActive = btnActive;
        this.btnInactive = btnInactive;
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

        JLabel lblName = new JLabel("Name");
        lblName.setFont(labelFont);
        lblName.setBounds(161, 32, 100, 25);
        panel_top.add(lblName);

        JLabel lblPhone = new JLabel("Phone");
        lblPhone.setFont(labelFont);
        lblPhone.setBounds(497, 32, 100, 25);
        panel_top.add(lblPhone);

        JLabel lblAddress = new JLabel("Address");
        lblAddress.setFont(labelFont);
        lblAddress.setBounds(161, 80, 100, 25);
        panel_top.add(lblAddress);

        JLabel lblScore = new JLabel("Score");
        lblScore.setFont(labelFont);
        lblScore.setBounds(497, 80, 100, 25);
        panel_top.add(lblScore);

        Border inputBorder = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 200, 220), 1, true),
                new EmptyBorder(6, 12, 6, 12)
        );

        Name_textField = new JTextField();
        Name_textField.setBounds(288, 32, 140, 30);
        Name_textField.setFont(inputFont);
        Name_textField.setBorder(inputBorder);
        Name_textField.setBackground(Color.WHITE);
        panel_top.add(Name_textField);
        Hover.addPlaceholder(Name_textField, "Enter Name");

        Phone_textField = new JTextField();
        Phone_textField.setBounds(607, 30, 140, 30);
        Phone_textField.setFont(inputFont);
        Phone_textField.setBorder(inputBorder);
        Phone_textField.setBackground(Color.WHITE);
        panel_top.add(Phone_textField);
        Hover.addPlaceholder(Phone_textField, "Enter Phone");

        Address_textField = new JTextField();
        Address_textField.setBounds(288, 82, 140, 30);
        Address_textField.setFont(inputFont);
        Address_textField.setBorder(inputBorder);
        Address_textField.setBackground(Color.WHITE);
        panel_top.add(Address_textField);
        Hover.addPlaceholder(Address_textField, "Enter Address");

        Score_textField = new JTextField();
        Score_textField.setBounds(607, 82, 140, 30);
        Score_textField.setFont(inputFont);
        Score_textField.setBorder(inputBorder);
        Score_textField.setBackground(Color.WHITE);
        panel_top.add(Score_textField);
        Hover.addPlaceholder(Score_textField, "Enter Score");

        Dimension actionBtnSize = new Dimension(87, 63);

        btnSave = new JButton("Save");
        btnSave.setIcon(new ImageIcon(getClass().getResource("/view/Icon/save_Icon.png")));
        btnSave.setFont(labelFont);
        btnSave.setBounds(20, 164, 87, 63);
        btnSave.setPreferredSize(actionBtnSize);
        btnSave.setFocusPainted(false);
        btnSave.setBorderPainted(false);
        btnSave.setContentAreaFilled(false);
        btnSave.setHorizontalTextPosition(SwingConstants.CENTER);
        btnSave.setVerticalTextPosition(SwingConstants.BOTTOM);
        panel_top.add(btnSave);
        Hover.addHoverButtonEffect(btnSave, new Color(0, 102, 204), 0.8f);

        btnEdit = new JButton("Edit");
        btnEdit.setIcon(new ImageIcon(getClass().getResource("/view/Icon/edit_Icon.png")));
        btnEdit.setFont(labelFont);
        btnEdit.setBounds(110, 164, 87, 63);
        btnEdit.setPreferredSize(actionBtnSize);
        btnEdit.setFocusPainted(false);
        btnEdit.setBorderPainted(false);
        btnEdit.setContentAreaFilled(false);
        btnEdit.setHorizontalTextPosition(SwingConstants.CENTER);
        btnEdit.setVerticalTextPosition(SwingConstants.BOTTOM);
        panel_top.add(btnEdit);
        Hover.addHoverButtonEffect(btnEdit, new Color(0, 102, 204), 0.8f);

        btnDel = new JButton("Delete");
        btnDel.setIcon(new ImageIcon(getClass().getResource("/view/Icon/delete_Icon.png")));
        btnDel.setFont(labelFont);
        btnDel.setBounds(209, 164, 87, 63);
        btnDel.setPreferredSize(actionBtnSize);
        btnDel.setFocusPainted(false);
        btnDel.setBorderPainted(false);
        btnDel.setContentAreaFilled(false);
        btnDel.setHorizontalTextPosition(SwingConstants.CENTER);
        btnDel.setVerticalTextPosition(SwingConstants.BOTTOM);
        panel_top.add(btnDel);
        Hover.addHoverButtonEffect(btnDel, new Color(0, 102, 204), 0.8f);

        // Search panel
        ImageIcon searchIcon = new ImageIcon(getClass().getResource("/view/Icon/Search_Icon.png"));
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setBounds(718, 191, 200, 36);
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setBorder(inputBorder);
        JLabel searchLabel = new JLabel(searchIcon);
        searchLabel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        searchPanel.add(searchLabel, BorderLayout.WEST);
        Hover.roundPanel(searchPanel, 15, Color.WHITE, Color.GRAY);
        panel_top.add(searchPanel);
        Search_textField = new JTextField();
        searchPanel.add(Search_textField, BorderLayout.CENTER);
        Search_textField.setBorder(null);
        Search_textField.setColumns(10);
        Hover.addPlaceholder(Search_textField, "search...");

        // Table & Scroll
        JScrollPane cus_List = new JScrollPane();
        cus_List.setBounds(0, 250, 950, 390);
        cus_List.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        cus_List.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK),
                "Customer List", TitledBorder.CENTER, TitledBorder.TOP, new Font("Arial", Font.BOLD, 16), Color.BLACK));
        cus_List.setBackground(new Color(200, 220, 240));
        cus_List.getViewport().setBackground(new Color(200, 220, 240));
        add(cus_List);

        String[] columnNames = {"ID", "NAME", "PHONE", "ADDRESS", "SCORE", "RANK"};
        model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(model) {
            @Override
            public Component prepareRenderer(javax.swing.table.TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (isRowSelected(row)) {
                    c.setBackground(new Color(255, 255, 153)); // light yellow
                } else {
                    c.setBackground(Color.WHITE); // all rows white
                }
                c.setForeground(new Color(40, 40, 40));
                return c;
            }
        };
        table.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        table.setRowHeight(36);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setSelectionBackground(new Color(255, 255, 153));
        table.setSelectionForeground(new Color(40, 40, 40));
        table.setBackground(Color.WHITE);

        JTableHeader th = table.getTableHeader();
        th.setFont(new Font("Segoe UI", Font.BOLD, 18));
        th.setBackground(Color.WHITE);
        th.setForeground(new Color(33, 70, 120));
        ((DefaultTableCellRenderer) th.getDefaultRenderer()).setHorizontalAlignment(JLabel.LEFT);

        cus_List.setViewportView(table);

        // Pagination controls
        JPanel paginationPanel = new JPanel(null);
        paginationPanel.setBounds(0, 643, 950, 50);
        paginationPanel.setBackground(new Color(200, 220, 240));
        add(paginationPanel);

        int btnWidth = 100;
        int btnHeight = 38;
        int spacing = 10;
        int rightPadding = 24;
        int startX = 950 - rightPadding - (btnWidth * 2 + spacing + 80);

        JButton btnPrev = new JButton("Prev");
        btnPrev.setFont(new Font("Arial", Font.PLAIN, 15));
        btnPrev.setFocusPainted(false);
        btnPrev.setBackground(Color.WHITE);
        btnPrev.setBounds(startX, 10, btnWidth, btnHeight);
        btnPrev.setEnabled(true);
        paginationPanel.add(btnPrev);

        pageLabel = new JLabel("Page 1/1");
        pageLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        pageLabel.setBounds(startX + btnWidth, 10, 80, btnHeight);
        pageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        paginationPanel.add(pageLabel);

        JButton btnNext = new JButton("Next");
        btnNext.setFont(new Font("Arial", Font.PLAIN, 15));
        btnNext.setFocusPainted(false);
        btnNext.setBackground(Color.WHITE);
        btnNext.setBounds(startX + btnWidth + 80 + spacing, 10, btnWidth, btnHeight);
        btnNext.setEnabled(true);
        paginationPanel.add(btnNext);

        btnInactive = new JButton("Ngưng HD");
        btnInactive.setFont(new Font("Arial", Font.PLAIN, 15));
        btnInactive.setFocusPainted(false);
        btnInactive.setBackground(Color.WHITE);
        btnInactive.setBounds(startX - 280, 10, 140, btnHeight);
        paginationPanel.add(btnInactive);

        btnActive = new JButton("Hoạt động");
        btnActive.setFont(new Font("Arial", Font.PLAIN, 15));
        btnActive.setFocusPainted(false);
        btnActive.setBackground(Color.WHITE);
        btnActive.setBounds(startX - 120 - 10, 10, 120, btnHeight); // Move left by 60px more
        paginationPanel.add(btnActive);
        // More space between buttons        paginationPanel.add(btnActive);
        btnPrev.addActionListener(e -> {
            if (currentPage > 1) {
                currentPage--;
                updateTableForCurrentPage();
            }
        });
        btnNext.addActionListener(e -> {
            if (currentPage < totalPages) {
                currentPage++;
                updateTableForCurrentPage();
            }
        });
    }

    public void setSaveEnabled(boolean enabled) {
        btnSave.setEnabled(enabled);
    }
    public void setDeleteEnabled(boolean enabled) {
        btnDel.setEnabled(enabled);
    }

    public void setCustomerTableData(List<Object[]> tableRows) {
        allData.clear();
        allData.addAll(tableRows);
        currentPage = 1;
        updateTableForCurrentPage();
    }

    public void setActiveButtonListener(ActionListener listener) {
        btnActive.addActionListener(listener);
    }
    public void setInactiveButtonListener(ActionListener listener) {
        btnInactive.addActionListener(listener);
    }
    private void updateTableForCurrentPage() {
        model.setRowCount(0);
        int startIndex = (currentPage - 1) * rowsPerPage;
        int endIndex = Math.min(startIndex + rowsPerPage, allData.size());
        for (int i = startIndex; i < endIndex; i++) {
            model.addRow(allData.get(i));
        }
        updatePaginationLabel();
    }

    private void updatePaginationLabel() {
        pageLabel.setText("Page " + currentPage + "/" + (totalPages == 0 ? 1 : totalPages));
    }

    public void removeCustomerFromTable(int row) {
        int globalIndex = (currentPage - 1) * rowsPerPage + row;
        if (globalIndex >= 0 && globalIndex < allData.size()) {
            allData.remove(globalIndex);
            totalRows = allData.size();
            totalPages = (int) Math.ceil((double) totalRows / rowsPerPage);
            if (currentPage > totalPages && currentPage > 1) currentPage--;
            updateTableForCurrentPage();
        }
    }

    public void setAddButtonListener(ActionListener listener) {
        btnSave.addActionListener(listener);
    }

    public void setEditButtonListener(ActionListener listener) {
        btnEdit.addActionListener(listener);
    }

    public void setDeleteButtonListener(ActionListener listener) {
        btnDel.addActionListener(listener);
    }

    public void setSearchListener(DocumentListener listener) {
        Search_textField.getDocument().addDocumentListener(listener);
    }

    public JTable getTable() {
        return table;
    }

    public int getSelectedRow() {
        return table.getSelectedRow();
    }

    public void clear() {
        allData.clear();
        totalRows = 0;
        totalPages = 0;
        currentPage = 1;
        updateTableForCurrentPage();
        Name_textField.setText("");
        Phone_textField.setText("");
        Address_textField.setText("");
        Score_textField.setText("");
    }

    public void addCustomerToTable(String id, String name, String phone, String address, String score, String rank) {
        allData.add(new Object[]{id, name, phone, address, score, rank});
        totalRows = allData.size();
        totalPages = (int) Math.ceil((double) totalRows / rowsPerPage);
        updateTableForCurrentPage();
    }

    public void updateCustomerInTable(int row, String id, String name, String phone, String address, String score, String rank) {
        int globalIndex = (currentPage - 1) * rowsPerPage + row;
        if (globalIndex >= 0 && globalIndex < allData.size()) {
            allData.set(globalIndex, new Object[]{id, name, phone, address, score, rank});
            updateTableForCurrentPage();
        }
    }

    public void setEmployeeData(String id, String name, String phone, String address, int point) {
        Name_textField.setText(name);
        Phone_textField.setText(phone);
        Address_textField.setText(address);
        Score_textField.setText(String.valueOf(point));
    }

    public JTextField getScoreTextField() {
        return Score_textField;
    }

    public void setScore_textField(String score) {
        this.Score_textField.setText(score);
    }

    public String getName_textField() {
        return Name_textField.getText();
    }

    public String getAddress_textField() {
        return Address_textField.getText();
    }

    public String getPhone_textField() {
        return Phone_textField.getText();
    }

    public String getSearch_textField() {
        return Search_textField.getText();
    }

    public String getScore_textField() {
        return Score_textField.getText();
    }

    public JTable getCustomerTable() {
        return table;
    }

    public int getSeclectedRow() {
        return table.getSelectedRow();
    }
}