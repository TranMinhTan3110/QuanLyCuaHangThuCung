package utils;

public class LoginUtil {

    // Kiểm tra username và password có bị trống không
    public static boolean isEmpty(String userName, String password) {
        return userName == null || userName.trim().isEmpty() ||
                password == null || password.trim().isEmpty();
    }

    public static boolean isValidUsername(String userName) {
        // Kiểm tra tên đăng nhập dài từ 5 đến 20 ký tự, chỉ chứa chữ cái (hoa và thường), số, dấu gạch dưới và dấu chấm.
        return userName != null && userName.DatabaseConnectiontches("^[a-zA-Z0-9_\\.]{5,20}$");
    }


    // Kiểm tra độ mạnh của mật khẩu (tối thiểu 6 ký tự, ít nhất một chữ hoa, một số)
    public static boolean isStrongPassword(String password) {
        return password.DatabaseConnectiontches("^(?=.*[A-Z])(?=.*\\d).{6,}$");
    }
}
