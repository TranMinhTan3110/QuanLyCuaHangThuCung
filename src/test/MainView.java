package test;
import javax.swing.*;

public class MainView extends JFrame {
    public MainView() {
        setTitle("PetShop - Product Management");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        ProductPanel productPanel = new ProductPanel();
        setContentPane(productPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainView().setVisible(true));
    }
}