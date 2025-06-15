package view.UI;

import model.entity.Category;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicTextFieldUI;
import javax.swing.table.JTableHeader;
import javax.swing.text.JTextComponent;

public class Hover {
	public static void addHoverEffect(JButton button, Color hoverColor, Color defaultColor) {
		button.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				button.setBackground(hoverColor);
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {
				button.setBackground(defaultColor);
			}
		});
	}

	public static void addPlaceholder(JTextField textField, String placeholder) {
		textField.setText(placeholder);
		textField.setForeground(Color.GRAY);
		textField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (textField.getText().equals(placeholder)) {
					textField.setText("");
					textField.setForeground(Color.BLACK);
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (textField.getText().isEmpty()) {
					textField.setText(placeholder);
					textField.setForeground(Color.GRAY);
				}
			}
		});
	}

	public static void addHoverButtonEffect(JButton button, Color textHoverColor, float iconhover) {
		// Lưu lại icon gốc
		Icon orIcon = button.getIcon();
		Color orColor = button.getForeground();

		// Sự kiện khi trỏ chuột vào
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				button.setForeground(textHoverColor);
				if (orIcon instanceof ImageIcon) {
					Image img = ((ImageIcon) orIcon).getImage();
					int newWidth = (int) (img.getWidth(null) * iconhover);
					int newHeight = (int) (img.getHeight(null) * iconhover);
					Image scaledImg = img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
					button.setIcon(new ImageIcon(scaledImg));
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				button.setForeground(orColor);
				button.setIcon(orIcon);
			}
		});
	}

	public static void roundTextField(JTextField textField, int arcRadius, Color bgColor, Color borderColor) {
		textField.setBorder(null);
		textField.setBackground(bgColor);
		textField.setBorder(new Border() {
			@Override
			public Insets getBorderInsets(Component c) {
				return new Insets(5, 10, 5, 10); // Trả về một Insets hợp lệ
			}

			@Override
			public boolean isBorderOpaque() {
				return false;
			}

			@Override
			public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
				Graphics2D g2 = (Graphics2D) g;
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setColor(borderColor);
				g2.drawRoundRect(x + 1, y + 1, width - 3, height - 3, arcRadius, arcRadius);
			}
		});
	}

	public static void roundPanel(JPanel panel, int arcRadius, Color bgColor, Color borderColor) {
		panel.setOpaque(false); // Đảm bảo không vẽ nền mặc định
		panel.setBackground(bgColor);
		panel.setBorder(new Border() {
			@Override
			public Insets getBorderInsets(Component c) {
				return new Insets(4, 4, 4, 4);
			}

			@Override
			public boolean isBorderOpaque() {
				return false;
			}

			@Override
			public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
				Graphics2D g2 = (Graphics2D) g;
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

				// Vẽ nền
				g2.setColor(bgColor);
				g2.fillRoundRect(x + 1, y + 1, width - 3, height - 3, arcRadius, arcRadius);

				// Vẽ viền
				g2.setColor(borderColor);
				g2.drawRoundRect(x + 1, y + 1, width - 3, height - 3, arcRadius, arcRadius);
			}
		});
	}

	public static void customizeTableHeader(JTable table) {
		JTableHeader tableHeader = table.getTableHeader();
		tableHeader.setFont(new Font("Arial", Font.BOLD, 16));
		tableHeader.setBackground(new Color(135, 206, 250));
		tableHeader.setForeground(Color.WHITE);
	}

	public static void customizeTableHeader(JTable table, Font font, Color background, Color foreground) {
		JTableHeader tableHeader = table.getTableHeader();
		tableHeader.setFont(font);
		tableHeader.setBackground(background);
		tableHeader.setForeground(foreground);
	}

	public static void roundComboBox(JComboBox<Category> cateNameComboBox, int i, Color white, Color lightGray) {
	}
}