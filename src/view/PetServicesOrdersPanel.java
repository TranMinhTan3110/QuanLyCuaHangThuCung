package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

public class PetServicesOrdersPanel extends JPanel {
    private DefaultTableModel model;
    private JTable table;
    private JTextField searchField;
    private TableRowSorter<DefaultTableModel> sorter;
    private int hoveredRow = -1;

    // Pagination
    private int rowsPerPage = 4;
    private int currentPage = 1;
    private JLabel lblPageInfo;
    private JButton btnPrev, btnNext;
    private Object[][] allData;
    private String[] columns;

    // Dark mode
    private boolean isDarkMode = false;
    private JButton btnToggleDarkMode;

    public PetServicesOrdersPanel() {
        setBackground(new Color(200, 220, 240));
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Title
        JLabel title = new JLabel("Pet Services Orders");
        title.setFont(new Font("SansSerif", Font.BOLD, 36));
        title.setForeground(new Color(40, 40, 40));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.insets = new Insets(30, 38, 10, 0);
        gbc.anchor = GridBagConstraints.WEST;
        add(title, gbc);

        // Search panel with icon inside
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setBackground(new Color(245, 245, 250));
        searchPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        searchPanel.setPreferredSize(new Dimension(260, 40));

        JLabel searchIcon = new JLabel("\uD83D\uDD0D"); // Unicode magnifier
        searchIcon.setBorder(new EmptyBorder(0, 8, 0, 0));
        searchPanel.add(searchIcon, BorderLayout.WEST);

        searchField = new JTextField();
        searchField.setFont(new Font("SansSerif", Font.PLAIN, 18));
        searchField.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        searchField.setBackground(new Color(245, 245, 250));
        searchField.setForeground(new Color(60, 60, 60));
        searchField.setToolTipText("Type to search orders...");
        searchPanel.add(searchField, BorderLayout.CENTER);

        gbc.gridx = 1; gbc.gridy = 0; gbc.gridwidth = 1;
        gbc.insets = new Insets(30, 0, 10, 38);
        gbc.anchor = GridBagConstraints.NORTHEAST;
        add(searchPanel, gbc);

        // Dark mode toggle button
        btnToggleDarkMode = new JButton("Dark Mode");
        btnToggleDarkMode.setFont(new Font("SansSerif", Font.PLAIN, 14));
        btnToggleDarkMode.setFocusPainted(false);
        btnToggleDarkMode.setBackground(new Color(220, 220, 220));
        btnToggleDarkMode.addActionListener(e -> toggleDarkMode());
        gbc.gridx = 1; gbc.gridy = 0; gbc.gridwidth = 1;
        gbc.insets = new Insets(75, 0, 0, 38);
        gbc.anchor = GridBagConstraints.NORTHEAST;
        add(btnToggleDarkMode, gbc);

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

        // Table model with status (no Actions column)
        columns = new String[]{"Pet Name", "Owner Name", "Service", "Appointment Date", "Status"};
        allData = new Object[][]{
                {"Bella",   "Alice Johnson",   "Spa",        "2024-05-10", "Completed"},
                {"Max",     "David Smith",     "Grooming",   "2024-05-08", "Pending"},
                {"Luna",    "Emma Williams",   "Bathing",    "2024-05-07", "Completed"},
                {"Charlie", "Michael Brown",   "Vaccination","2024-05-05", "Pending"},
                {"Lucy",    "Sarah Miller",    "Bathing",    "2024-05-03", "Completed"},
                {"Rocky",   "James Wilson",    "Grooming",   "2024-05-02", "Cancelled"},
                {"Milo",    "Olivia Lee",      "Spa",        "2024-05-01", "Completed"},
                {"Coco",    "Sophia Kim",      "Vaccination","2024-04-29", "Pending"},
                {"Buddy",   "Liam Chen",       "Bathing",    "2024-04-28", "Completed"},
                {"Daisy",   "Noah Park",       "Grooming",   "2024-04-27", "Cancelled"},
        };
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

        // Status renderer
        table.getColumnModel().getColumn(4).setCellRenderer(new StatusCellRenderer());

        // Tooltip for accessibility
        table.setToolTipText("Double-click a row to view details");

        // Hover effect
        table.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                if (row != hoveredRow) {
                    hoveredRow = row;
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
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && table.getSelectedRow() != -1) {
                    int row = table.convertRowIndexToModel(table.getSelectedRow());
                    Object[] order = new Object[columns.length];
                    for (int i = 0; i < columns.length; i++) {
                        order[i] = model.getValueAt(row, i);
                    }
                    new OrderDetailDialog(order).setVisible(true);
                }
            }
        });

        // Search/filter
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

        // Pagination controls
        JPanel paginationPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        paginationPanel.setOpaque(false);
        btnPrev = new JButton("Prev");
        btnNext = new JButton("Next");
        lblPageInfo = new JLabel();
        btnPrev.setFont(new Font("SansSerif", Font.PLAIN, 14));
        btnNext.setFont(new Font("SansSerif", Font.PLAIN, 14));
        btnPrev.addActionListener(e -> { if (currentPage > 1) { currentPage--; updateTablePage(); } });
        btnNext.addActionListener(e -> { if (currentPage < getTotalPages()) { currentPage++; updateTablePage(); } });
        paginationPanel.add(btnPrev);
        paginationPanel.add(lblPageInfo);
        paginationPanel.add(btnNext);
        tablePanel.add(paginationPanel, BorderLayout.SOUTH);

        // Load first page
        updateTablePage();
    }

    private void updateTablePage() {
        model.setRowCount(0);
        int start = (currentPage - 1) * rowsPerPage;
        int end = Math.min(start + rowsPerPage, allData.length);
        for (int i = start; i < end; i++) {
            model.addRow(allData[i]);
        }
        lblPageInfo.setText("Page " + currentPage + " / " + getTotalPages());
        btnPrev.setEnabled(currentPage > 1);
        btnNext.setEnabled(currentPage < getTotalPages());
    }

    private int getTotalPages() {
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

    // Status cell renderer with color
    static class StatusCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JLabel lbl = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            String status = value.toString();
            switch (status) {
                case "Completed": lbl.setForeground(new Color(34, 139, 34)); break;
                case "Pending":   lbl.setForeground(new Color(255, 140, 0)); break;
                case "Cancelled": lbl.setForeground(new Color(220, 20, 60)); break;
                default:          lbl.setForeground(Color.GRAY);
            }
            lbl.setFont(lbl.getFont().deriveFont(Font.BOLD));
            return lbl;
        }
    }

    // Popup dialog for order details
    class OrderDetailDialog extends JDialog {
        public OrderDetailDialog(Object[] order) {
            setTitle("Order Details");
            setModal(true);
            setSize(400, 350);
            setLocationRelativeTo(PetServicesOrdersPanel.this);
            setLayout(new BorderLayout(10, 10));

            JPanel infoPanel = new JPanel(new GridLayout(0, 1, 5, 5));
            infoPanel.setBorder(new EmptyBorder(20, 20, 10, 20));
            for (int i = 0; i < columns.length; i++) {
                infoPanel.add(new JLabel(columns[i] + ": " + order[i]));
            }
            add(infoPanel, BorderLayout.CENTER);

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            JButton btnEdit = new JButton("Edit");
            JButton btnDelete = new JButton("Delete");
            btnEdit.addActionListener(e -> JOptionPane.showMessageDialog(this, "Edit order (implement logic)"));
            btnDelete.addActionListener(e -> JOptionPane.showMessageDialog(this, "Delete order (implement logic)"));
            buttonPanel.add(btnEdit);
            buttonPanel.add(btnDelete);
            add(buttonPanel, BorderLayout.SOUTH);
        }
    }

    // For testing
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame f = new JFrame("Pet Services Orders");
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setSize(900, 700);
            f.add(new PetServicesOrdersPanel());
            f.setLocationRelativeTo(null);
            f.setVisible(true);
        });
    }
}