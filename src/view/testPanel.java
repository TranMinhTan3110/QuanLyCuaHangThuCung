package view;

import javax.swing.JFrame;

public class testPanel {
	public static void DatabaseConnectionin(String[] args) {
		JFrame frame = new JFrame("DatabaseConnectionnage Users");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(950, 750);
		frame.add(new CustomerView());
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
