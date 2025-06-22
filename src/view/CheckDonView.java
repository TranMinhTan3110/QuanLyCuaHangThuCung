package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Consumer;

public class CheckDonView extends JPanel {
    private DefaultTableModel model;
    private JTable table;
    private JTextField searchField;
    private TableRowSorter<DefaultTableModel> sorter;
    private int hoveredRow = -1;
    private boolean showCompleted = false;
    private JButton btnShowCompleted;
    private JPopupMenu completedMenu;
    private boolean showCanceled = false;
    private boolean showPaid = false;
    private int lastHoveredRow = -1;

    // Pagination
    private int rowsPerPage = 10;
    private int currentPage = 1;
    private JLabel lblPageInfo;
    private JButton btnPrev, btnNext, btnEdit, btnDelete;
    private Object[][] allData;
    private String[] columns;

    // Dark mode
    private boolean isDarkMode = false;
    private JButton btnToggleDarkMode;

    // --------- BUTTON ĐẸP ---------
    private JButton createFancyButton(String text, Color bgColor, Color hoverColor, int arc, Color fg) {
        FancyButton btn = new FancyButton(text, bgColor, hoverColor, arc);
        btn.setFont(new Font("SansSerif", Font.BOLD, 15));
        btn.setForeground(fg);
        return btn;
    }

    static class FancyButton extends JButton {
        private final Color baseColor;
        private final Color hoverColor;
        private final int arc;
        private boolean hover = false;

        public FancyButton(String text, Color baseColor, Color hoverColor, int arc) {
            super(text);
            this.baseColor = baseColor;
            this.hoverColor = hoverColor;
            this.arc = arc;
            setFocusPainted(false);
            setContentAreaFilled(false);
            setBorderPainted(false);
            setOpaque(false);
            addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) { hover = true; repaint(); }
                public void mouseExited(MouseEvent e) { hover = false; repaint(); }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D)g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            Color fill = hover ? hoverColor : baseColor;
            g2.setColor(fill);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);

            FontMetrics fm = g2.getFontMetrics();
            String txt = getText();
            int textWidth = fm.stringWidth(txt);
            int x = (getWidth() - textWidth) / 2;
            int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();

            g2.setFont(getFont());
            g2.setColor(getForeground());
            g2.drawString(txt, x, y);

            g2.dispose();
        }
    }
    // --------- END BUTTON ĐẸP ---------

    public CheckDonView() {
        setBackground(new Color(200, 220, 240));
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Title
        JLabel title = new JLabel("Pet Services Orders");
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        title.setForeground(new Color(40, 40, 40));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.insets = new Insets(18, 38, 6, 0);
        gbc.anchor = GridBagConstraints.WEST;
        add(title, gbc);

        // Search panel with icon inside
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setBackground(new Color(245, 245, 250));
        searchPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        searchPanel.setPreferredSize(new Dimension(260, 34));

        JLabel searchIcon = new JLabel("\uD83D\uDD0D");
        searchIcon.setBorder(new EmptyBorder(0, 8, 0, 0));
        searchPanel.add(searchIcon, BorderLayout.WEST);

        searchField = new JTextField();
        searchField.setFont(new Font("SansSerif", Font.PLAIN, 15));
        searchField.setBorder(BorderFactory.createEmptyBorder(6, 8, 6, 8));
        searchField.setBackground(new Color(245, 245, 250));
        searchField.setForeground(new Color(60, 60, 60));
        searchField.setToolTipText("Type to search orders...");
        searchPanel.add(searchField, BorderLayout.CENTER);

        // Dark mode toggle button (Đổi sang button đẹp)
        btnToggleDarkMode = createFancyButton("Dark Mode", new Color(220, 220, 220), new Color(180, 180, 180), 14, Color.BLACK);
        btnToggleDarkMode.setFont(new Font("SansSerif", Font.PLAIN, 13));
        btnToggleDarkMode.setPreferredSize(new Dimension(90, 29));
        btnToggleDarkMode.addActionListener(e -> toggleDarkMode());

        // Completed/canceled filter dropdown
        completedMenu = new JPopupMenu();
        JMenuItem showCurrentItem = new JMenuItem("Hiển thị đơn hiện tại");
        JMenuItem showCompletedItem = new JMenuItem("Hiển thị đơn hoàn thành");
        JMenuItem showPaidItem = new JMenuItem("Hiển thị đơn đã thanh toán");
        JMenuItem showCanceledItem = new JMenuItem("Hiển thị đơn đã hủy");

        showCurrentItem.addActionListener(e -> {
            showCompleted = false;
            showCanceled = false;
            showPaid = false;
            updateCompletedButtonState();
            updateFilter();
        });
        showCompletedItem.addActionListener(e -> {
            if (showCompleted) {
                showCompleted = false;
            } else {
                showCompleted = true;
                showCanceled = false;
                showPaid = false;
            }
            updateCompletedButtonState();
            updateFilter();
        });
        showPaidItem.addActionListener(e -> {
            if (showPaid) {
                showPaid = false;
            } else {
                showPaid = true;
                showCompleted = false;
                showCanceled = false;
            }
            updateCompletedButtonState();
            updateFilter();
        });
        showCanceledItem.addActionListener(e -> {
            if (showCanceled) {
                showCanceled = false;
            } else {
                showCanceled = true;
                showCompleted = false;
                showPaid = false;
            }
            updateCompletedButtonState();
            updateFilter();
        });

        completedMenu.add(showCurrentItem);
        completedMenu.add(showCompletedItem);
        completedMenu.add(showPaidItem);
        completedMenu.add(showCanceledItem);

        // btnShowCompleted đẹp
        btnShowCompleted = createFancyButton("Đơn đã xử lý ▼", new Color(220, 220, 220), new Color(180, 220, 255), 14, new Color(60, 85, 170));
        btnShowCompleted.setFont(new Font("SansSerif", Font.PLAIN, 12));
        btnShowCompleted.setPreferredSize(new Dimension(130, 29));
        btnShowCompleted.addActionListener(e -> completedMenu.show(btnShowCompleted, 0, btnShowCompleted.getHeight()));

        JPanel topRightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 7, 0));
        topRightPanel.setOpaque(false);
        topRightPanel.add(searchPanel);
        topRightPanel.add(btnToggleDarkMode);
        topRightPanel.add(btnShowCompleted);

        gbc.gridx = 1; gbc.gridy = 0; gbc.gridwidth = 1;
        gbc.insets = new Insets(18, 0, 6, 38);
        gbc.anchor = GridBagConstraints.NORTHEAST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        add(topRightPanel, gbc);

        // Table panel with rounded corners
        JPanel tablePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(isDarkMode ? new Color(60, 63, 65) : new Color(255, 250, 245));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 18, 18);
                g2d.dispose();
            }
        };
        tablePanel.setOpaque(false);
        tablePanel.setLayout(new BorderLayout());
        tablePanel.setBorder(new EmptyBorder(12, 12, 12, 12));
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 30, 0, 30);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1; gbc.weighty = 1;
        add(tablePanel, gbc);

        columns = new String[]{"Pet Name", "Owner Name", "Service", "Appointment Date", "Status"};
        allData = new Object[][]{};
        model = new DefaultTableModel(null, columns) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(model) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row)) {
                    if (row == hoveredRow) {
                        c.setBackground(isDarkMode ? new Color(70, 80, 100) : new Color(230, 240, 255));
                    } else {
                        c.setBackground(isDarkMode ? new Color(60, 63, 65) : Color.WHITE);
                    }
                } else {
                    c.setBackground(isDarkMode ? new Color(90, 100, 120) : new Color(200, 220, 240));
                }
                c.setForeground(isDarkMode ? Color.WHITE : new Color(40, 40, 40));
                return c;
            }
        };
        table.setFont(new Font("SansSerif", Font.PLAIN, 18));
        table.setRowHeight(44);
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 20));
        table.getTableHeader().setBackground(new Color(255, 250, 245));
        table.getTableHeader().setForeground(new Color(40, 40, 40));
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setBackground(Color.WHITE);
        ((DefaultTableCellRenderer)table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.LEFT);

        table.getColumnModel().getColumn(4).setCellRenderer(new StatusCellRenderer());

        table.setToolTipText("Double-click a row to view details");


        table.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                if (row != hoveredRow) {
                    hoveredRow = row;
                    lastHoveredRow = row; // Lưu lại vị trí hover cuối cùng
                    table.repaint();
                }
            }
        });
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                hoveredRow = -1;
                table.repaint();
            }
        });

        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);
        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { filter(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { filter(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { filter(); }
            private void filter() {
                String text = searchField.getText();
                if (text.trim().length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        JPanel paginationPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        paginationPanel.setOpaque(false);
        // btnPrev đẹp
        btnPrev = createFancyButton("Prev", new Color(230, 240, 250), new Color(121, 162, 219), 14, new Color(60, 85, 170));
        btnNext = createFancyButton("Next", new Color(230, 240, 250), new Color(121, 162, 219), 14, new Color(60, 85, 170));
        lblPageInfo = new JLabel();
        btnPrev.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnNext.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnPrev.setPreferredSize(new Dimension(90, 34));
        btnNext.setPreferredSize(new Dimension(90, 34));
        btnPrev.addActionListener(e -> { if (currentPage > 1) { currentPage--; updateTablePage(); } });
        btnNext.addActionListener(e -> { if (currentPage < getTotalPages()) { currentPage++; updateTablePage(); } });
        paginationPanel.add(btnPrev);
        paginationPanel.add(lblPageInfo);
        paginationPanel.add(btnNext);
        tablePanel.add(paginationPanel, BorderLayout.SOUTH);

        updateTablePage();
    }

    private void updateCompletedButtonState() {
        if (showCompleted) {
            btnShowCompleted.setText("Đơn hoàn thành");
            btnShowCompleted.setBackground(new Color(0, 150, 136));
            btnShowCompleted.setForeground(Color.WHITE);
        } else if (showPaid) {
            btnShowCompleted.setText("Đơn đã thanh toán");
            btnShowCompleted.setBackground(new Color(46, 204, 113));
            btnShowCompleted.setForeground(Color.WHITE);
        } else if (showCanceled) {
            btnShowCompleted.setText("Đơn đã hủy");
            btnShowCompleted.setBackground(new Color(231, 76, 60));
            btnShowCompleted.setForeground(Color.WHITE);
        } else {
            btnShowCompleted.setText("Đơn đã xử lý ▼");
            btnShowCompleted.setBackground(new Color(220, 220, 220));
            btnShowCompleted.setForeground(new Color(60, 85, 170));
        }
    }

    private void updateFilter() {
        RowFilter<DefaultTableModel, Object> statusFilter = new RowFilter<DefaultTableModel, Object>() {
            @Override
            public boolean include(Entry<? extends DefaultTableModel, ? extends Object> entry) {
                String status = (String) entry.getValue(4);
                if (showCompleted) {
                    return status.equals("Hoàn thành");
                } else if (showPaid) {
                    return status.equals("Đã thanh toán");
                } else if (showCanceled) {
                    return status.equals("Đã hủy đơn");
                } else {
                    // Chỉ hiển thị các đơn đang xử lý
                    return !status.equals("Hoàn thành") &&
                            !status.equals("Đã thanh toán") &&
                            !status.equals("Đã hủy đơn");
                }
            }
        };

        String searchText = searchField.getText().toLowerCase();
        RowFilter<DefaultTableModel, Object> searchFilter = RowFilter.regexFilter("(?i)" + searchText);

        ArrayList<RowFilter<DefaultTableModel, Object>> filters = new ArrayList<>();
        filters.add(searchFilter);
        filters.add(statusFilter);

        sorter.setRowFilter(RowFilter.andFilter(filters));

        // Sau khi filter, kiểm tra nếu trang hiện tại không còn dữ liệu thì chuyển về trang 1
        if (currentPage > 1 && model.getRowCount() == 0) {
            currentPage = 1;
            updateTablePage();
        }
    }

    // Thêm phương thức này để cập nhật dữ liệu mới
    public void addNewAppointment(Object[] appointmentData) {
        // Thêm vào allData
        Object[][] newAllData = new Object[allData.length + 1][];
        System.arraycopy(allData, 0, newAllData, 0, allData.length);
        newAllData[allData.length] = appointmentData;
        allData = newAllData;

        // Nếu đơn mới đang ở trạng thái cần hiển thị, thêm vào model
        String status = (String) appointmentData[4];
        if (!showCompleted && !showPaid && !showCanceled &&
                (status.equals("Đang làm") || status.equals("Đang đợi"))) {
            model.addRow(appointmentData);
        }

        updateTablePage();
    }

    public void filterTable() { updateFilter(); }

    public void updateTablePage() {
        int oldHovered = hoveredRow; // Lưu lại giá trị hover hiện tại
        model.setRowCount(0);
        int start = (currentPage - 1) * rowsPerPage;
        int end = Math.min(start + rowsPerPage, allData.length);
        for (int i = start; i < end; i++) {
            model.addRow(allData[i]);
        }
        lblPageInfo.setText("Page " + currentPage + " / " + getTotalPages());
        btnPrev.setEnabled(currentPage > 1);
        btnNext.setEnabled(currentPage < getTotalPages());
        hoveredRow = oldHovered; // Khôi phục lại giá trị hover
        table.repaint();
    }

    public int getTotalPages() {
        return (int) Math.ceil((double) allData.length / rowsPerPage);
    }

    private void toggleDarkMode() {
        isDarkMode = !isDarkMode;
        Color bg = isDarkMode ? new Color(40, 44, 52) : new Color(200, 220, 240);
        Color fg = isDarkMode ? Color.WHITE : new Color(40, 40, 40);
        setBackground(bg);
        table.setBackground(isDarkMode ? new Color(60, 63, 65) : Color.WHITE);
        table.setForeground(fg);
        table.getTableHeader().setBackground(isDarkMode ? new Color(50, 54, 60) : new Color(255, 250, 245));
        table.getTableHeader().setForeground(fg);
        btnToggleDarkMode.setBackground(isDarkMode ? new Color(80, 80, 80) : new Color(220, 220, 220));
        btnToggleDarkMode.setForeground(fg);
        repaint();
    }


    static class StatusCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JLabel lbl = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            String status = value.toString();
            switch (status) {
                case "Hoàn thành": lbl.setForeground(new Color(34, 139, 34)); break;
                case "Đang làm":   lbl.setForeground(new Color(255, 140, 0)); break;
                case "Đang chờ nhận": lbl.setForeground(new Color(220, 20, 60)); break;
                default:          lbl.setForeground(Color.GRAY);
            }
            lbl.setFont(lbl.getFont().deriveFont(Font.BOLD));
            return lbl;
        }
    }

    public class CustomerComboItem {
        private final int id;
        private final String name;
        public CustomerComboItem(int id, String name) { this.id = id; this.name = name; }
        public int getId() { return id; }
        public String getName() { return name; }
        @Override public String toString() { return name; }
    }

    public class OrderDetailDialog extends JDialog {
        private JTextField txtPetName;
        private JComboBox<CustomerComboItem> cbOwnerName;
        private JComboBox<String> cbStaff;
        private JCheckBox cbSpa, cbGrooming, cbBathing, cbVaccination;
        private JLabel lblDate;
        private Consumer<OrderUpdateData> onSaveCallback;
        private Consumer<OrderUpdateData> onDeleteCallback;
        private String originalOwnerName;
        private CustomerComboItem originalCustomer;
        private JButton btnComplete, btnSave, btnDelete, btnPrint;

        public OrderDetailDialog(Object[] order) {
            setTitle("Order Details");
            setModal(true);
            setSize(650, 480);
            setLocationRelativeTo(null);
            setLayout(new BorderLayout(10, 10));
            getContentPane().setBackground(new Color(245, 250, 255));

            txtPetName = new JTextField(20);
            cbOwnerName = new JComboBox<>();
            cbStaff = new JComboBox<>(new String[]{"Nguyen Van A", "Tran Thi B", "Pham C"});
            cbSpa = new JCheckBox("Spa");
            cbGrooming = new JCheckBox("Grooming");
            cbBathing = new JCheckBox("Bathing");
            cbVaccination = new JCheckBox("Vaccination");
            lblDate = new JLabel();

            txtPetName.setText(order[0].toString());
            cbOwnerName.setSelectedItem(order[1].toString());
            lblDate.setText(order[3].toString());
            setServiceCheckBoxes(order[2].toString());

            JPanel infoPanel = createInfoPanel(order);
            add(infoPanel, BorderLayout.CENTER);

            JPanel buttonPanel = createButtonPanel(order);
            add(buttonPanel, BorderLayout.SOUTH);

            String status = order[4].toString();
            if (status.equals("Đã hủy đơn") || status.equals("Đã thanh toán")) {
                btnComplete.setEnabled(false);
                btnSave.setEnabled(false);
                btnDelete.setEnabled(false);
                btnPrint.setEnabled(false);

                txtPetName.setEnabled(false);
                txtPetName.setDisabledTextColor(new Color(19, 19, 19));
                txtPetName.setBackground(new Color(240,240,240));

                cbOwnerName.setEnabled(false);
                cbOwnerName.setBackground(new Color(240,240,240));
                cbOwnerName.setForeground(new Color(19, 19, 19));

                cbStaff.setEnabled(false);
                cbStaff.setBackground(new Color(240,240,240));
                cbStaff.setForeground(new Color(19, 19, 19));

                cbSpa.setEnabled(false);
                cbSpa.setBackground(new Color(240,240,240));
                cbSpa.setForeground(new Color(19, 19, 19));
                cbGrooming.setEnabled(false);
                cbGrooming.setBackground(new Color(240,240,240));
                cbGrooming.setForeground(new Color(19, 19, 19));
                cbBathing.setEnabled(false);
                cbBathing.setBackground(new Color(240,240,240));
                cbBathing.setForeground(new Color(19, 19, 19));
                cbVaccination.setEnabled(false);
                cbVaccination.setBackground(new Color(240,240,240));
                cbVaccination.setForeground(new Color(19, 19, 19));

                btnComplete.setForeground(new Color(80,80,80));
                btnComplete.setBackground(new Color(220,220,220));
                btnComplete.setOpaque(true);

                btnSave.setForeground(new Color(80,80,80));
                btnSave.setBackground(new Color(220,220,220));
                btnSave.setOpaque(true);

                btnDelete.setForeground(new Color(80,80,80));
                btnDelete.setBackground(new Color(220,220,220));
                btnDelete.setOpaque(true);

                btnPrint.setForeground(new Color(80,80,80));
                btnPrint.setBackground(new Color(220,220,220));
                btnPrint.setOpaque(true);
            } else if (status.equals("Hoàn thành")) {
                btnComplete.setEnabled(false);
                btnSave.setEnabled(false);
                btnDelete.setEnabled(false);
                btnPrint.setEnabled(true);
                btnPrint.setForeground(Color.WHITE);
                btnPrint.setBackground(new Color(52, 152, 219));
                btnPrint.setOpaque(true);

                txtPetName.setEnabled(false);
                txtPetName.setDisabledTextColor(new Color(19, 19, 19));
                txtPetName.setBackground(new Color(240,240,240));

                cbOwnerName.setEnabled(false);
                cbOwnerName.setBackground(new Color(240,240,240));
                cbOwnerName.setForeground(new Color(19, 19, 19));

                cbStaff.setEnabled(false);
                cbStaff.setBackground(new Color(240,240,240));
                cbStaff.setForeground(new Color(19, 19, 19));

                cbSpa.setEnabled(false);
                cbSpa.setBackground(new Color(240,240,240));
                cbSpa.setForeground(new Color(19, 19, 19));
                cbGrooming.setEnabled(false);
                cbGrooming.setBackground(new Color(240,240,240));
                cbGrooming.setForeground(new Color(19, 19, 19));
                cbBathing.setEnabled(false);
                cbBathing.setBackground(new Color(240,240,240));
                cbBathing.setForeground(new Color(19, 19, 19));
                cbVaccination.setEnabled(false);
                cbVaccination.setBackground(new Color(240,240,240));
                cbVaccination.setForeground(new Color(19, 19, 19));
            }
        }

        private JPanel createInfoPanel(Object[] order) {
            JPanel infoPanel = new JPanel(new GridBagLayout());
            infoPanel.setBackground(new Color(245, 250, 255));
            infoPanel.setBorder(new EmptyBorder(20, 20, 10, 20));

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(12, 10, 12, 10);
            gbc.anchor = GridBagConstraints.WEST;
            gbc.fill = GridBagConstraints.HORIZONTAL;

            Font fontLabel = new Font("Segoe UI", Font.BOLD, 13);
            Font fontField = new Font("Segoe UI", Font.PLAIN, 13);

            addLabelAndComponent(infoPanel, "Pet Name:", txtPetName, 0, fontLabel, fontField, gbc);
            addLabelAndComponent(infoPanel, "Owner Name:", cbOwnerName, 1, fontLabel, fontField, gbc);

            JPanel servicePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
            servicePanel.setBackground(new Color(245, 250, 255));
            servicePanel.add(cbSpa);
            servicePanel.add(cbGrooming);
            servicePanel.add(cbBathing);
            servicePanel.add(cbVaccination);
            addLabelAndComponent(infoPanel, "Services:", servicePanel, 2, fontLabel, fontField, gbc);

            addLabelAndComponent(infoPanel, "Appointment Date:", lblDate, 3, fontLabel, fontField, gbc);
            addLabelAndComponent(infoPanel, "Assign Staff:", cbStaff, 4, fontLabel, fontField, gbc);

            return infoPanel;
        }

        private void addLabelAndComponent(JPanel panel, String labelText, Component component,
                                          int gridy, Font labelFont, Font fieldFont, GridBagConstraints gbc) {
            gbc.gridx = 0;
            gbc.gridy = gridy;
            gbc.weightx = 0.3;
            JLabel label = new JLabel(labelText);
            label.setFont(labelFont);
            panel.add(label, gbc);

            if (component instanceof JTextField) {
                ((JTextField) component).setFont(fieldFont);
            } else if (component instanceof JComboBox) {
                ((JComboBox<?>) component).setFont(fieldFont);
            } else if (component instanceof JLabel) {
                ((JLabel) component).setFont(fieldFont);
            }

            gbc.gridx = 1;
            gbc.weightx = 0.7;
            panel.add(component, gbc);
        }

        private JPanel createButtonPanel(Object[] order) {
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
            buttonPanel.setBackground(new Color(245, 250, 255));

            btnComplete = createFancyButton("✓ Hoàn thành", new Color(0, 150, 136), new Color(0,180,170), 18, Color.WHITE);
            btnPrint = createFancyButton("\uD83D\uDCC4 In hóa đơn", new Color(52, 152, 219), new Color(80,180,255), 18, Color.WHITE);
            btnSave = createFancyButton("✔ Save", new Color(46, 204, 113), new Color(80,230,143), 18, Color.WHITE);
            btnDelete = createFancyButton("🗑 Delete", new Color(231, 76, 60), new Color(235,120,120), 18, Color.WHITE);

            String status = order[4].toString();
            System.out.println("DEBUG - Trạng thái đơn: " + status);

            // Chỉ vô hiệu hóa các nút khác nếu đã thanh toán hoặc hủy đơn
            if (status.equals("Đã thanh toán") || status.equals("Đã hủy đơn")) {
                System.out.println("DEBUG - Đơn đã thanh toán hoặc hủy");
                btnComplete.setEnabled(false);
                btnSave.setEnabled(false);
                btnDelete.setEnabled(false);
                disableButton(btnComplete);
                disableButton(btnSave);
                disableButton(btnDelete);

                // Cho phép in hóa đơn nếu đã thanh toán
                if (status.equals("Đã thanh toán")) {
                    System.out.println("DEBUG - Enable nút in cho đơn đã thanh toán");
                    btnPrint.setEnabled(true);
                    btnPrint.setBackground(new Color(52, 152, 219));
                    btnPrint.setForeground(Color.WHITE);
                    System.out.println("DEBUG - Trạng thái nút in: " + btnPrint.isEnabled());
                } else {
                    System.out.println("DEBUG - Disable nút in cho đơn đã hủy");
                    btnPrint.setEnabled(false);
                    disableButton(btnPrint);
                }
            } else if (status.equals("Hoàn thành")) {
                System.out.println("DEBUG - Đơn hoàn thành");
                btnPrint.setEnabled(true);
                btnPrint.setBackground(new Color(52, 152, 219));
                btnPrint.setForeground(Color.WHITE);
                System.out.println("DEBUG - Trạng thái nút in: " + btnPrint.isEnabled());

                btnComplete.setEnabled(false);
                btnSave.setEnabled(false);
                btnDelete.setEnabled(false);
                disableButton(btnComplete);
                disableButton(btnSave);
                disableButton(btnDelete);
            } else {
                System.out.println("DEBUG - Các trạng thái khác");
                btnPrint.setEnabled(false);
                disableButton(btnPrint);
            }

            // Log trạng thái cuối cùng của các nút
            System.out.println("\nDEBUG - Trạng thái cuối cùng các nút:");
            System.out.println("Complete: " + btnComplete.isEnabled());
            System.out.println("Print: " + btnPrint.isEnabled());
            System.out.println("Save: " + btnSave.isEnabled());
            System.out.println("Delete: " + btnDelete.isEnabled());

            btnComplete.addActionListener(e -> {
                System.out.println("DEBUG - Click nút Complete");
                handleComplete();
            });
            btnPrint.addActionListener(e -> {
                System.out.println("DEBUG - Click nút Print");
                handlePrint(order);
            });
            btnSave.addActionListener(e -> {
                System.out.println("DEBUG - Click nút Save");
                handleSave();
            });
            btnDelete.addActionListener(e -> {
                System.out.println("DEBUG - Click nút Delete");
                handleDelete();
            });

            buttonPanel.add(btnComplete);
            buttonPanel.add(btnPrint);
            buttonPanel.add(btnSave);
            buttonPanel.add(btnDelete);
            return buttonPanel;
        }

        private void disableButton(JButton btn) {
            btn.setForeground(new Color(80,80,80));
            btn.setBackground(new Color(220,220,220));
            btn.setOpaque(true);
        }

        private int getAppointmentId(Object[] order) {
            return order.length > 5 ? Integer.parseInt(order[5].toString()) : 0;
        }

        public class PrintData {
            private final String petName;
            private final String customerName;
            private final String[] services;
            private final String appointmentDate;
            private final int appointmentId;

            public PrintData(String petName, String customerName, String[] services,
                             String appointmentDate, int appointmentId) {
                this.petName = petName;
                this.customerName = customerName;
                this.services = services;
                this.appointmentDate = appointmentDate;
                this.appointmentId = appointmentId;
            }

            public String getPetName() { return petName; }
            public String getCustomerName() { return customerName; }
            public String[] getServices() { return services; }
            public String getAppointmentDate() { return appointmentDate; }
            public int getAppointmentId() { return appointmentId; }
        }
        private void handlePrint(Object[] order) {
            try {
                System.out.println("DEBUG - Bắt đầu in hóa đơn...");
                System.out.println("DEBUG - Trạng thái đơn: " + order[4].toString());
                System.out.println("DEBUG - AppointmentId: " + getAppointmentId(order));

                // Tạo đối tượng PrintData
                PrintData printData = new PrintData(
                        getPetName(),
                        getOwnerName(),
                        getServiceList().split(", "),
                        getAppointmentDate(),
                        getAppointmentId(order)
                );

                System.out.println("DEBUG - Thông tin in:");
                System.out.println("- Pet: " + printData.getPetName());
                System.out.println("- Owner: " + printData.getCustomerName());
                System.out.println("- Services: " + Arrays.toString(printData.getServices()));
                System.out.println("- Date: " + printData.getAppointmentDate());
                System.out.println("- AppointmentId: " + printData.getAppointmentId());

                // Tạo và gọi InvoicePrinter
                System.out.println("DEBUG - Tạo InvoicePrinter...");
                InvoicePrinter printer = new InvoicePrinter();

                System.out.println("DEBUG - Gọi hàm printInvoice...");
                boolean success = printer.printInvoice(printData);

                if (success) {
                    System.out.println("DEBUG - In hóa đơn thành công!");
                    if (order[4].toString().equals("Hoàn thành")) {
                        // Cập nhật trạng thái nếu là đơn hoàn thành
                        OrderUpdateData updateData = new OrderUpdateData(
                                getPetName(),
                                getOwnerName(),
                                getSelectedStaff(),
                                getServiceList(),
                                getAppointmentDate(),
                                "Đã thanh toán",
                                getSelectedCustomerId(),
                                isCustomerChanged()
                        );
                        onSaveCallback.accept(updateData);
                    }
                    dispose();
                } else {
                    System.out.println("DEBUG - In hóa đơn thất bại!");
                    throw new Exception("Không thể in hóa đơn");
                }
            } catch (Exception e) {
                System.err.println("DEBUG - Lỗi khi in hóa đơn:");
                e.printStackTrace();
                JOptionPane.showMessageDialog(this,
                        "Lỗi khi in hóa đơn: " + e.getMessage(),
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
            }
        }

        private void handleComplete() {
            if (onSaveCallback != null) {
                OrderUpdateData updateData = new OrderUpdateData(
                        getPetName(),
                        getOwnerName(),
                        getSelectedStaff(),
                        getServiceList(),
                        getAppointmentDate(),
                        "Hoàn thành",
                        getSelectedCustomerId(),
                        isCustomerChanged()
                );
                onSaveCallback.accept(updateData);
                dispose();
            }
        }

        private void handleSave() {
            if (txtPetName.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Vui lòng nhập tên thú cưng",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (cbOwnerName.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this,
                        "Vui lòng chọn chủ nhân",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!cbSpa.isSelected() && !cbGrooming.isSelected() &&
                    !cbBathing.isSelected() && !cbVaccination.isSelected()) {
                JOptionPane.showMessageDialog(this,
                        "Vui lòng chọn ít nhất một dịch vụ",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (cbStaff.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this,
                        "Vui lòng chọn nhân viên",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (onSaveCallback != null) {
                OrderUpdateData updateData = new OrderUpdateData(
                        getPetName(),
                        getOwnerName(),
                        getSelectedStaff(),
                        getServiceList(),
                        getAppointmentDate(),
                        "Đang làm",
                        getSelectedCustomerId(),
                        isCustomerChanged()
                );
                onSaveCallback.accept(updateData);
                dispose();
            }
        }

        private void handleDelete() {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Bạn có chắc chắn muốn xóa cuộc hẹn này?",
                    "Xác nhận xóa",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION && onDeleteCallback != null) {
                OrderUpdateData updateData = new OrderUpdateData(
                        getPetName(),
                        getOwnerName(),
                        getSelectedStaff(),
                        getServiceList(),
                        getAppointmentDate(),
                        "Đã hủy đơn"
                );
                onDeleteCallback.accept(updateData);
                dispose();
            }
        }

        public void setCustomerList(java.util.List<CustomerComboItem> customers, String currentOwnerName) {
            originalOwnerName = currentOwnerName;
            java.util.List<CustomerComboItem> sortedCustomers = new ArrayList<>();
            CustomerComboItem currentCustomer = null;
            for (CustomerComboItem customer : customers) {
                if (customer.getName().equals(currentOwnerName)) {
                    currentCustomer = customer;
                    sortedCustomers.add(0, customer);
                } else {
                    sortedCustomers.add(customer);
                }
            }
            DefaultComboBoxModel<CustomerComboItem> model = new DefaultComboBoxModel<>();
            for (CustomerComboItem customer : sortedCustomers) {
                model.addElement(customer);
            }
            cbOwnerName.setModel(model);
            if (currentCustomer != null) {
                cbOwnerName.setSelectedItem(currentCustomer);
                originalCustomer = currentCustomer;
            }
        }
        public void setStaffList(String[] staffNames) {
            cbStaff.setModel(new DefaultComboBoxModel<>(staffNames));
        }
        private void setServiceCheckBoxes(String services) {
            cbSpa.setSelected(services.contains("Spa"));
            cbGrooming.setSelected(services.contains("Grooming"));
            cbBathing.setSelected(services.contains("Bathing"));
            cbVaccination.setSelected(services.contains("Vaccination"));
        }
        private String getSelectedServices() {
            StringBuilder sb = new StringBuilder();
            if (cbSpa.isSelected()) sb.append("Spa, ");
            if (cbGrooming.isSelected()) sb.append("Grooming, ");
            if (cbBathing.isSelected()) sb.append("Bathing, ");
            if (cbVaccination.isSelected()) sb.append("Vaccination, ");
            return sb.length() > 0 ? sb.substring(0, sb.length() - 2) : "";
        }
        public String getPetName() { return txtPetName.getText().trim(); }
        public String getOwnerName() { return cbOwnerName.getSelectedItem().toString(); }
        public String getSelectedStaff() { return cbStaff.getSelectedItem().toString(); }
        public String getServiceList() { return getSelectedServices(); }
        public String getAppointmentDate() { return lblDate.getText(); }
        public void setOnSaveCallback(Consumer<OrderUpdateData> callback) { this.onSaveCallback = callback; }
        public void setOnDeleteCallback(Consumer<OrderUpdateData> callback) { this.onDeleteCallback = callback; }
        public boolean isCustomerChanged() {
            CustomerComboItem selected = (CustomerComboItem) cbOwnerName.getSelectedItem();
            return selected != null && !selected.getName().equals(originalOwnerName);
        }
        public int getSelectedCustomerId() {
            CustomerComboItem selected = (CustomerComboItem) cbOwnerName.getSelectedItem();
            return selected != null ? selected.getId() : 0;
        }
        public JComboBox<CustomerComboItem> getOwnerNameComboBox() { return cbOwnerName; }
    }

    public class OrderUpdateData {
        private String petName;
        private String ownerName;
        private String staffName;
        private String services;
        private String appointmentDate;
        private String status;
        private int customerId;
        private boolean customerChanged;

        public OrderUpdateData(String petName, String ownerName, String staffName,
                               String services, String appointmentDate, String status,
                               int customerId, boolean customerChanged) {
            this.petName = petName;
            this.ownerName = ownerName;
            this.staffName = staffName;
            this.services = services;
            this.appointmentDate = appointmentDate;
            this.status = status;
            this.customerId = customerId;
            this.customerChanged = customerChanged;
        }
        public OrderUpdateData(String petName, String ownerName, String staffName,
                               String services, String appointmentDate, String status) {
            this(petName, ownerName, staffName, services, appointmentDate, status, 0, false);
        }
        public boolean isCustomerChanged() { return customerChanged; }
        public String getPetName() { return petName; }
        public String getOwnerName() { return ownerName; }
        public String getStaffName() { return staffName; }
        public String getServices() { return services; }
        public String getAppointmentDate() { return appointmentDate; }
        public String getStatus() { return status; }
        public int getCustomerId() { return customerId; }
    }

    // ----------- GETTER & SETTER -----------

    public DefaultTableModel getTableModel() { return model; }
    public void setTableModel(DefaultTableModel model) {
        this.model = model;
        table.setModel(model);
        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);
    }
    public JTable getTable() { return table; }
    public void setTable(JTable table) { this.table = table; }
    public JTextField getSearchField() { return searchField; }
    public void setSearchField(JTextField searchField) { this.searchField = searchField; }
    public TableRowSorter<DefaultTableModel> getSorter() { return sorter; }
    public void setSorter(TableRowSorter<DefaultTableModel> sorter) {
        this.sorter = sorter;
        table.setRowSorter(sorter);
    }
    public int getRowsPerPage() { return rowsPerPage; }
    public void setRowsPerPage(int rowsPerPage) {
        this.rowsPerPage = rowsPerPage;
        currentPage = 1;
        updateTablePage();
    }
    public int getCurrentPage() { return currentPage; }
    public void setCurrentPage(int currentPage) {
        if (currentPage >= 1 && currentPage <= getTotalPages()) {
            this.currentPage = currentPage;
            updateTablePage();
        }
    }
    public JLabel getLblPageInfo() { return lblPageInfo; }
    public void setLblPageInfo(JLabel lblPageInfo) { this.lblPageInfo = lblPageInfo; }
    public JButton getBtnPrev() { return btnPrev; }
    public void setBtnPrev(JButton btnPrev) { this.btnPrev = btnPrev; }
    public JButton getBtnNext() { return btnNext; }
    public void setBtnNext(JButton btnNext) { this.btnNext = btnNext; }
    public Object[][] getAllData() { return allData; }
    public void setAllData(Object[][] allData) {
        this.allData = allData;
        currentPage = 1;
        updateTablePage();
    }
    public String[] getColumns() { return columns; }
    public void setColumns(String[] columns) { this.columns = columns; }
    public boolean isDarkMode() { return isDarkMode; }
    public void setDarkMode(boolean darkMode) {
        isDarkMode = darkMode;
        toggleDarkMode();
    }
    public JButton getBtnToggleDarkMode() { return btnToggleDarkMode; }
    public void setBtnToggleDarkMode(JButton btnToggleDarkMode) {
        this.btnToggleDarkMode = btnToggleDarkMode;
    }
}