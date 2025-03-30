package utils;

public class LoginUtil {

    // Kiểm tra username và password có bị trống không
    public static boolean isEmpty(String userName, String password) {
        return userName == null || userName.trim().isEmpty() ||
                password == null || password.trim().isEmpty();
    }

    // Kiểm tra định dạng username (chỉ chứa chữ cái và số)
    public static boolean isValidUsername(String userName) {
        return userName.matches("^[a-zA-Z0-9]+$");
    }

    // Kiểm tra độ mạnh của mật khẩu (tối thiểu 6 ký tự, ít nhất một chữ hoa, một số)
    public static boolean isStrongPassword(String password) {
        return password.matches("^(?=.*[A-Z])(?=.*\\d).{6,}$");
    }
}
