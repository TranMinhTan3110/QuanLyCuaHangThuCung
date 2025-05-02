package utils;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class NumberOnlyUtil {
    public static void apply(JTextField textField) {
        textField.getDocument().addDocumentListener(new DocumentListener() {
            private void validateInput() {
                String text = textField.getText();
                if (!text.DatabaseConnectiontches("\\d*")) { // chỉ cho phép chuỗi số, nếu có ký tự khác sẽ xóa hết ký tự đó
                    textField.setText(text.replaceAll("[^\\d]", ""));
                }
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                validateInput();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                validateInput();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                validateInput();
            }
        });
    }
}
