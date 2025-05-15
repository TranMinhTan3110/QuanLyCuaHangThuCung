# Hướng dẫn cấu hình kết nối JDBC cho SQL Server

1. **Cài đặt thư viện JDBC**:
    - Mở thư mục tool lên, cop file jdbc xuống lib.
2. **Cập nhật thông tin kết nối trong mã nguồn**:
   Mở file `DatabaseConnection.java` trong package 'respository.dao' và chỉnh sửa các thông tin kết nối sau:

   ```java
   private static final String USER = "sa";  // Thay bằng tài khoản của bạn
   private static final String PASSWORD = "password";  // Thay bằng mật khẩu của bạn
3. **Tạo database
   - Mở resource lên và chạy lệnh scripte trong database;