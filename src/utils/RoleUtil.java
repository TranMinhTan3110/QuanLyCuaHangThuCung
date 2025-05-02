package utils;

import model.entity.Role;
import java.util.Arrays;

public class RoleUtil {

    /**
     * Chuyển đổi một chuỗi role lấy từ database (NVARCHAR) thành enum Role.
     * Ví dụ: "ADMIN", "admin" hoặc " admin " sẽ được chuyển thành Role.Admin.
     *
     * @param roleStr chuỗi role từ database
     * @return Role tương ứng
     * @throws IllegalArgumentException nếu chuỗi không hợp lệ
     */
    public static Role parseRole(String roleStr) {
        if (roleStr == null) {
            throw new IllegalArgumentException("Role string is null");
        }
        String trimmed = roleStr.trim();
        if (trimmed.isEmpty()) {
            throw new IllegalArgumentException("Role string is empty");
        }
        // Chuyển "ADMIN" => "admin"
        String formatted = trimmed.toLowerCase();
        // Lúc này, enum Role phải định nghĩa: admin, employee
        try {
            return Role.valueOf(formatted);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid role string: '" + roleStr + "'. Expected one of: " + Arrays.toString(Role.values()));
        }
    }

    /**
     * Định dạng lại enum Role thành chuỗi viết thường hoàn toàn để lưu vào database (nếu cần).
     * Ví dụ: Role.Admin -> "admin"
     *
     * @param role giá trị Role
     * @return chuỗi viết thường
     */
    public static String formatRole(Role role) {
        if (role == null) {
            throw new IllegalArgumentException("Role is null");
        }
        return role.name().toLowerCase();
    }
}