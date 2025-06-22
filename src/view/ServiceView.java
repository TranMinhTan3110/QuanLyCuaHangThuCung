package view;

import controller.BookingController;
import controller.CheckDonController;
import respository.dao.AppointmentDAO;
import respository.dao.DaoInterface;
import service.AppointmentService;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class ServiceView extends JPanel {
    private JButton btnDatLich, btnXemDon;
    private CardLayout cardLayout;
    private JPanel centerPanel;

    // Màu dựa đúng style booking
    private static final Color HEADER_BG = new Color(194, 216, 240);    // Xanh nhạt
    private static final Color MAIN_BG = new Color(194, 216, 240);      // Kem
    private static final Color BUTTON_BG = Color.WHITE;
    private static final Color BUTTON_BORDER = new Color(210, 210, 210);

    public ServiceView() {
        setLayout(new CardLayout());
        setBackground(HEADER_BG);

        // Header: title style booking
        JPanel panelHeader = new JPanel(new BorderLayout());
        panelHeader.setBackground(HEADER_BG);
        panelHeader.setBorder(new EmptyBorder(28, 32, 16, 0));
        JLabel titleLabel = new JLabel("Pet Care Services");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 32));
        titleLabel.setForeground(new Color(41, 54, 64));
        panelHeader.add(titleLabel, BorderLayout.WEST);

        // Main: nền kem, chứa nút
        JPanel panelMain = new JPanel(new GridBagLayout());
        panelMain.setBackground(MAIN_BG);
        panelMain.setBorder(new EmptyBorder(36, 48, 48, 48));

        // Nút đặt lịch - tăng kích thước gấp đôi (360x440)
        btnDatLich = createStyledButton("Đặt lịch", "Đặt lịch dịch vụ", "/view/Icon/booking.png", new Dimension(360, 440));
        // Nút xem đơn - tăng kích thước gấp đôi (360x440)
        btnXemDon = createStyledButton("Xem đơn", "Kiểm tra đơn đã đặt", "/view/Icon/box.png", new Dimension(360, 440));

        // Layout nút ngang, căn trái, spacing lớn
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0; gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(0, 0, 0, 60);
        gbc.gridx = 0;
        panelMain.add(btnDatLich, gbc);
        gbc.gridx = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        panelMain.add(btnXemDon, gbc);

        // Tổng panel hiển thị
        JPanel rootPanel = new JPanel(new BorderLayout());
        rootPanel.setBackground(MAIN_BG);
        rootPanel.add(panelHeader, BorderLayout.NORTH);
        rootPanel.add(panelMain, BorderLayout.CENTER);

        // CardLayout cho các panel chức năng
        cardLayout = new CardLayout();
        centerPanel = new JPanel(cardLayout);
        centerPanel.setBackground(MAIN_BG);

        centerPanel.add(rootPanel, "MainButtons");
        centerPanel.add(createBookingPanel(), "Booking");
        centerPanel.add(createCheckPanel(), "Check");

        add(centerPanel, "Main");

        // Sự kiện chuyển panel
        btnDatLich.addActionListener(e -> showPanel("Booking"));
        btnXemDon.addActionListener(e -> showPanel("Check"));

        showMainButtons();
    }

    private JPanel createBookingPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(HEADER_BG);

        BookingView bookingPanel = new BookingView();
        DaoInterface bookingDAO = new AppointmentDAO();
        AppointmentService appointmentService = new AppointmentService(bookingDAO);
        new BookingController(bookingPanel, appointmentService);
        panel.add(bookingPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setBackground(HEADER_BG);
        JButton backButton = new JButton("Quay lại");
        backButton.setFont(new Font("SansSerif", Font.PLAIN, 16));
        backButton.setBackground(new Color(240, 240, 235));
        backButton.setBorder(BorderFactory.createLineBorder(BUTTON_BORDER, 1, true));
        backButton.addActionListener(e -> showMainButtons());
        buttonPanel.add(backButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createCheckPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(HEADER_BG);

        CheckDonView checkPanel = new CheckDonView();
        DaoInterface checkDAO = new AppointmentDAO();
        AppointmentService appointmentService = new AppointmentService(checkDAO);
        new CheckDonController(checkPanel, appointmentService);
        panel.add(checkPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setBackground(HEADER_BG);
        JButton backButton = new JButton("Quay lại");
        backButton.setFont(new Font("SansSerif", Font.PLAIN, 16));
        backButton.setBackground(new Color(240, 240, 235));
        backButton.setBorder(BorderFactory.createLineBorder(BUTTON_BORDER, 1, true));
        backButton.addActionListener(e -> showMainButtons());
        buttonPanel.add(backButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JButton createStyledButton(String text, String subText, String iconPath, Dimension size) {
        JButton button = new JButton();
        button.setLayout(new BorderLayout());
        button.setPreferredSize(size);
        button.setBackground(BUTTON_BG);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BUTTON_BORDER, 1, true),
                BorderFactory.createEmptyBorder(16, 16, 16, 16)
        ));
        button.setFocusPainted(false);

        // Icon
        if (iconPath != null) {
            try {
                ImageIcon icon = new ImageIcon(getClass().getResource(iconPath));
                // Scale icon lớn hơn cho nút to (gấp đôi)
                Image img = icon.getImage().getScaledInstance(140, 140, Image.SCALE_SMOOTH);
                JLabel iconLabel = new JLabel(new ImageIcon(img));
                iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
                button.add(iconLabel, BorderLayout.CENTER);
            } catch (Exception e) {
                System.out.println("Could not load icon: " + iconPath);
            }
        }

        // Main text
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("SansSerif", Font.BOLD, 36));
        label.setForeground(new Color(44, 44, 60));
        label.setBorder(BorderFactory.createEmptyBorder(24, 0, 6, 0));
        button.add(label, BorderLayout.SOUTH);

        // Subtext
        if (subText != null && !subText.isEmpty()) {
            JLabel subLabel = new JLabel(subText, SwingConstants.CENTER);
            subLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
            subLabel.setForeground(new Color(80, 80, 110));
            subLabel.setBorder(BorderFactory.createEmptyBorder(12, 0, 0, 0));
            button.add(subLabel, BorderLayout.NORTH);
        }

        addHoverEffect(button);
        return button;
    }

    private void addHoverEffect(JButton button) {
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(245, 245, 240));
                button.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(140, 180, 225), 2, true),
                        BorderFactory.createEmptyBorder(16, 16, 16, 16)
                ));
                button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(BUTTON_BG);
                button.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BUTTON_BORDER, 1, true),
                        BorderFactory.createEmptyBorder(16, 16, 16, 16)
                ));
                button.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
    }

    public void addBookingListener(ActionListener listener) {
        btnDatLich.addActionListener(listener);
    }
    public void addViewOrdersListener(ActionListener listener) {
        btnXemDon.addActionListener(listener);
    }
    public void showPanel(String panelName) {
        cardLayout.show(centerPanel, panelName);
    }
    public void showMainButtons() {
        cardLayout.show(centerPanel, "MainButtons");
    }
}