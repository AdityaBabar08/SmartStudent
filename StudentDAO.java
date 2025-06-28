import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentDAO {
    // Create - Add new student
    public boolean insertStudent(Student student) {
        String sql = "INSERT INTO students (name, roll_no, department, email, phone, marks) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, student.getName());
            pstmt.setString(2, student.getRollNo());
            pstmt.setString(3, student.getDepartment());
            pstmt.setString(4, student.getEmail());
            pstmt.setString(5, student.getPhone());
            pstmt.setDouble(6, student.getMarks());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error inserting student: " + e.getMessage());
            return false;
        }
    }
    
    // Read - Get all students
    public List<Student> selectAllStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students ORDER BY id";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                Student student = createStudentFromResultSet(rs);
                students.add(student);
            }
        } catch (SQLException e) {
            System.err.println("Error selecting all students: " + e.getMessage());
        }
        
        return students;
    }
    
    // Read - Get student by ID
    public Student selectStudentById(int id) {
        String sql = "SELECT * FROM students WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return createStudentFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error selecting student by ID: " + e.getMessage());
        }
        
        return null;
    }
    
    // Read - Get student by roll number
    public Student selectStudentByRollNo(String rollNo) {
        String sql = "SELECT * FROM students WHERE roll_no = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, rollNo);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return createStudentFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error selecting student by roll number: " + e.getMessage());
        }
        
        return null;
    }
    
    // Update - Update existing student
    public boolean updateStudent(Student student) {
        String sql = "UPDATE students SET name=?, roll_no=?, department=?, email=?, phone=?, marks=? WHERE id=?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, student.getName());
            pstmt.setString(2, student.getRollNo());
            pstmt.setString(3, student.getDepartment());
            pstmt.setString(4, student.getEmail());
            pstmt.setString(5, student.getPhone());
            pstmt.setDouble(6, student.getMarks());
            pstmt.setInt(7, student.getId());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating student: " + e.getMessage());
            return false;
        }
    }
    
    // Delete - Delete student by ID
    public boolean deleteStudent(int id) {
        String sql = "DELETE FROM students WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting student: " + e.getMessage());
            return false;
        }
    }
    
    // Search operations
    public List<Student> searchStudentsByName(String name) {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students WHERE name LIKE ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, "%" + name + "%");
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                students.add(createStudentFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error searching students by name: " + e.getMessage());
        }
        
        return students;
    }
    
    public List<Student> searchStudentsByDepartment(String department) {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students WHERE department = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, department);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                students.add(createStudentFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error searching students by department: " + e.getMessage());
        }
        
        return students;
    }
    
    public List<Student> searchStudentsByMarksRange(double minMarks, double maxMarks) {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students WHERE marks BETWEEN ? AND ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setDouble(1, minMarks);
            pstmt.setDouble(2, maxMarks);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                students.add(createStudentFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error searching students by marks range: " + e.getMessage());
        }
        
        return students;
    }
    
    // Statistics methods
    public int countTotalStudents() {
        String sql = "SELECT COUNT(*) FROM students";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Error counting total students: " + e.getMessage());
        }
        
        return 0;
    }
    
    public double getMaxMarks() {
        String sql = "SELECT MAX(marks) FROM students";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (SQLException e) {
            System.err.println("Error getting max marks: " + e.getMessage());
        }
        
        return 0.0;
    }
    
    public double getMinMarks() {
        String sql = "SELECT MIN(marks) FROM students";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (SQLException e) {
            System.err.println("Error getting min marks: " + e.getMessage());
        }
        
        return 0.0;
    }
    
    public Map<String, Integer> getDepartmentWiseStudentCount() {
        Map<String, Integer> departmentCount = new HashMap<>();
        String sql = "SELECT department, COUNT(*) FROM students GROUP BY department";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                departmentCount.put(rs.getString(1), rs.getInt(2));
            }
        } catch (SQLException e) {
            System.err.println("Error getting department-wise count: " + e.getMessage());
        }
        
        return departmentCount;
    }
    
    // Helper method to create Student object from ResultSet
    private Student createStudentFromResultSet(ResultSet rs) throws SQLException {
        return new Student(
            rs.getInt("id"),
            rs.getString("name"),
            rs.getString("roll_no"),
            rs.getString("department"),
            rs.getString("email"),
            rs.getString("phone"),
            rs.getDouble("marks")
        );
    }
}
