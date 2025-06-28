import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/student_db";
    private static final String USERNAME = "root"; // Change as per your MySQL setup
    private static final String PASSWORD = "0803"; // Change as per your MySQL setup
    
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found", e);
        }
    }
    
    // Initialize database table
    // public static void initializeDatabase() {
    //     String createTableSQL = """
            // CREATE TABLE IF NOT EXISTS students (
            //     id INT AUTO_INCREMENT PRIMARY KEY,
            //     name VARCHAR(100) NOT NULL,
            //     roll_no VARCHAR(20) UNIQUE NOT NULL,
            //     department VARCHAR(50) NOT NULL,
            //     email VARCHAR(100),
            //     phone VARCHAR(15),
            //     marks DECIMAL(5,2)
            // )
    //     """;
        
    //     try (Connection conn = getConnection();
    //          Statement stmt = conn.createStatement()) {
    //         stmt.execute(createTableSQL);
    //         System.out.println("✅ Database initialized successfully!");
    //     } catch (SQLException e) {
    //         System.err.println("❌ Error initializing database: " + e.getMessage());
    //     }
    // }
}
