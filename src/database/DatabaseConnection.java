package database;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String USER = "sa";
    private static final String PASSWORD = "tanbeo@321";
    private static final String SERVER = "Tan\\MINHTAN";
    private static final String DATABASE = "QuanLyCuaHangThuCung";
    private static final int PORT = 1433;

    public static Connection getConnection() throws SQLException {
        SQLServerDataSource ds = new SQLServerDataSource();
        ds.setUser(USER);
        ds.setPassword(PASSWORD);
        ds.setServerName(SERVER);
        ds.setPortNumber(PORT);
        ds.setDatabaseName(DATABASE);
        //ds.setEncrypt(false); // Không dùng SSL
        ds.setTrustServerCertificate(true); // Bỏ qua kiểm tra chứng chỉ

        return ds.getConnection();
    }

    public static void main(String[] args) {
        try (Connection conn = getConnection()) {
            System.out.println("✅ Kết nối thành công!");
            System.out.println("Database: " + conn.getCatalog());
        } catch (SQLException e) {
            System.err.println("❌ Lỗi kết nối: " + e.getMessage());
        }
    }
}

