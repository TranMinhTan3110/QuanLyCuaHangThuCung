package utils;

public class inputUtil {

    public static boolean isValidPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            return false;
        }
        // Xóa khoảng trắng nếu có
        phoneNumber = phoneNumber.trim();
        // Regex: bắt đầu bằng 0, theo sau là 9-10 chữ số
        return phoneNumber.matches("0[0-9]{9}");
    }
    //kiểm tra id là số dương
    public static boolean isValidID(String id){
        if( id.isEmpty()){
            return false;
        }
        try{
            return Integer.parseInt(id) > 0;
        }
        catch(NumberFormatException e){
            return false;
        }
    }
    //kiểm tra username
    public static boolean isValidUserName(String userName) {
        // Kiểm tra tên đăng nhập không phải null, không rỗng và không chỉ có khoảng trắng
        if (userName == null || userName.trim().isEmpty()) {
            return false;
        }
        // Kiểm tra tên đăng nhập có chứa ký tự đặc biệt hoặc khoảng trắng hay không
        if (!userName.matches("[a-zA-Z0-9_]+")) {
            return false;
        }
        // Kiểm tra độ dài của tên đăng nhập (ví dụ từ 3 đến 20 ký tự)
        if (userName.length() < 3 || userName.length() > 20) {
            return false;
        }
        return true;
    }

    //password
    public static boolean isValidPassword(String password) {
        // Kiểm tra không phải null và không rỗng
        if (password == null || password.trim().isEmpty()) {
            return false;
        }

        // Kiểm tra độ dài mật khẩu (ít nhất 8 ký tự)
        if (password.length() < 8) {
            return false;
        }

        // Kiểm tra có chứa ít nhất một chữ cái in hoa
        if (!password.matches(".*[A-Z].*")) {
            return false;
        }

        // Kiểm tra có chứa ít nhất một chữ cái in thường
        if (!password.matches(".*[a-z].*")) {
            return false;
        }

        // Kiểm tra có chứa ít nhất một chữ số
        if (!password.matches(".*[0-9].*")) {
            return false;
        }

        // Kiểm tra có chứa ít nhất một ký tự đặc biệt
        if (!password.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) {
            return false;
        }

        return true;
    }
    //đia chỉ
    public  static boolean isValidAddress(String address) {
        // Kiểm tra không phải null và không rỗng
        if (address == null || address.trim().isEmpty()) {
            return false;
        }

        // Kiểm tra địa chỉ không chỉ chứa các khoảng trắng
        if (address.trim().isEmpty()) {
            return false;
        }

        if (!address.matches(".*[\\p{L}].*")) {
            return false;
        }


        return true;
    }
    public static boolean isValidProductName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        return name.matches("^[\\p{L} ]+$");
    }
    public static boolean isValidName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        return name.matches("^[\\p{L} ]+$");
    }


}
