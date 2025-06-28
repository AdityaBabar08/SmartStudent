import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AdminService {
    private StudentDAO studentDAO;
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin123";
    
    public AdminService() {
        this.studentDAO = new StudentDAO();
    }
    
    // Authentication
    public boolean authenticateAdmin(String username, String password) {
        return ADMIN_USERNAME.equals(username) && ADMIN_PASSWORD.equals(password);
    }
    
    // CRUD Operations with business logic
    public boolean addStudent(Student student) {
        // Business rule: Check if roll number already exists
        if (studentDAO.selectStudentByRollNo(student.getRollNo()) != null) {
            System.err.println("❌ Roll number already exists!");
            return false;
        }
        
        // Business rule: Validate marks range
        if (student.getMarks() < 0 || student.getMarks() > 100) {
            System.err.println("❌ Marks should be between 0 and 100!");
            return false;
        }
        
        return studentDAO.insertStudent(student);
    }
    
    public List<Student> getAllStudents() {
        return studentDAO.selectAllStudents();
    }
    
    public Student getStudentByRollNo(String rollNo) {
        return studentDAO.selectStudentByRollNo(rollNo);
    }
    
    public Student getStudentById(int id) {
        return studentDAO.selectStudentById(id);
    }
    
    public boolean updateStudent(Student student) {
        // Business rule: Check if student exists
        if (studentDAO.selectStudentById(student.getId()) == null) {
            System.err.println("❌ Student not found!");
            return false;
        }
        
        // Business rule: Validate marks range
        if (student.getMarks() < 0 || student.getMarks() > 100) {
            System.err.println("❌ Marks should be between 0 and 100!");
            return false;
        }
        
        return studentDAO.updateStudent(student);
    }
    
    public boolean deleteStudent(int studentId) {
        // Business rule: Check if student exists before deletion
        if (studentDAO.selectStudentById(studentId) == null) {
            System.err.println("❌ Student not found!");
            return false;
        }
        
        return studentDAO.deleteStudent(studentId);
    }
    
    // Search operations
    public List<Student> searchStudentsByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            System.err.println("❌ Name cannot be empty!");
            return new ArrayList<>();
        }
        return studentDAO.searchStudentsByName(name);
    }
    
    public List<Student> searchStudentsByDepartment(String department) {
        if (department == null || department.trim().isEmpty()) {
            System.err.println("❌ Department cannot be empty!");
            return new ArrayList<>();
        }
        return studentDAO.searchStudentsByDepartment(department);
    }
    
    public List<Student> searchStudentsByMarksRange(double minMarks, double maxMarks) {
        if (minMarks > maxMarks) {
            System.err.println("❌ Minimum marks cannot be greater than maximum marks!");
            return new ArrayList<>();
        }
        return studentDAO.searchStudentsByMarksRange(minMarks, maxMarks);
    }
    
    // Statistics operations
    public StudentStatistics getStudentStatistics() {
        return new StudentStatistics(
            studentDAO.countTotalStudents(),
            studentDAO.getMaxMarks(),
            studentDAO.getMinMarks(),
            studentDAO.getDepartmentWiseStudentCount()
        );
    }
    
    // Inner class for statistics
    public static class StudentStatistics {
        private int totalStudents;
        private double highestMarks;
        private double lowestMarks;
        private Map<String, Integer> departmentWiseCount;
        
        public StudentStatistics(int totalStudents, double highestMarks, double lowestMarks, 
                               Map<String, Integer> departmentWiseCount) {
            this.totalStudents = totalStudents;
            this.highestMarks = highestMarks;
            this.lowestMarks = lowestMarks;
            this.departmentWiseCount = departmentWiseCount;
        }
        
        // Getters
        public int getTotalStudents() { return totalStudents; }
        public double getHighestMarks() { return highestMarks; }
        public double getLowestMarks() { return lowestMarks; }
        public Map<String, Integer> getDepartmentWiseCount() { return departmentWiseCount; }
    }
}
