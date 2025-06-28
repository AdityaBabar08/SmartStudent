import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class UI {
    private AdminService adminService;
    private Scanner scanner;
    
    public UI() {
        this.adminService = new AdminService();
        this.scanner = new Scanner(System.in);
    }
    
    public void start() {
        System.out.println("🎓 Welcome to Smart Student Management System");
        
        if (!showLoginScreen()) {
            System.out.println(" Access denied. Exiting...");
            return;
        }
        
        showMainMenu();
    }
    
    private boolean showLoginScreen() {
        System.out.println("\n========== ADMIN LOGIN ==========");
        System.out.print("Username: ");
        String username = scanner.nextLine();
        
        System.out.print("Password: ");
        String password = scanner.nextLine();
        
        if (adminService.authenticateAdmin(username, password)) {
            System.out.println(" Login successful!");
            return true;
        } else {
            System.out.println(" Invalid credentials!");
            return false;
        }
    }
    
    private void showMainMenu() {
        while (true) {
            printMainMenu();
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1: addStudentMenu(); break;
                case 2: viewAllStudentsMenu(); break;
                case 3: updateStudentMenu(); break;
                case 4: deleteStudentMenu(); break;
                case 5: searchMenu(); break;
                case 6: showStatisticsMenu(); break;
                case 7: 
                    System.out.println(" Thank you for using Smart Student Management System!");
                    return;
                default:
                    System.out.println(" Invalid choice! Please try again.");
            }
        }
    }
    
    private void printMainMenu() {
        System.out.println("\n========== MAIN MENU ==========");
        System.out.println("1. Add Student");
        System.out.println("2. View All Students");
        System.out.println("3. Update Student");
        System.out.println("4. Delete Student");
        System.out.println("5. Search Students");
        System.out.println("6. Statistics");
        System.out.println("7. Exit");
        System.out.println("================================");
    }
    
    private void addStudentMenu() {
        System.out.println("\n========== ADD STUDENT ==========");
        
        System.out.print("Name: ");
        String name = scanner.nextLine();
        
        System.out.print("Roll Number: ");
        String rollNo = scanner.nextLine();
        
        System.out.print("Department: ");
        String department = scanner.nextLine();
        
        System.out.print("Email: ");
        String email = scanner.nextLine();
        
        System.out.print("Phone: ");
        String phone = scanner.nextLine();
        
        double marks = getDoubleInput("Marks (0-100): ");
        
        Student student = new Student(name, rollNo, department, email, phone, marks);
        
        if (adminService.addStudent(student)) {
            System.out.println(" Student added successfully!");
        } else {
            System.out.println(" Failed to add student!");
        }
    }
    
    private void viewAllStudentsMenu() {
        System.out.println("\n========== ALL STUDENTS ==========");
        List<Student> students = adminService.getAllStudents();
        
        if (students.isEmpty()) {
            System.out.println(" No students found!");
            return;
        }
        
        printStudentTable(students);
        System.out.println("Total Students: " + students.size());
    }
    
    private void updateStudentMenu() {
        System.out.println("\n========== UPDATE STUDENT ==========");
        System.out.print("Enter Roll Number to update: ");
        String rollNo = scanner.nextLine();
        
        Student student = adminService.getStudentByRollNo(rollNo);
        if (student == null) {
            System.out.println(" Student not found!");
            return;
        }
        
        System.out.println("Current details:");
        printStudentTableHeader();
        System.out.println(student);
        
        System.out.println("\nEnter new details (press Enter to keep current value):");
        
        System.out.print("Name (" + student.getName() + "): ");
        String name = scanner.nextLine();
        if (!name.trim().isEmpty()) student.setName(name);
        
        System.out.print("Department (" + student.getDepartment() + "): ");
        String department = scanner.nextLine();
        if (!department.trim().isEmpty()) student.setDepartment(department);
        
        System.out.print("Email (" + student.getEmail() + "): ");
        String email = scanner.nextLine();
        if (!email.trim().isEmpty()) student.setEmail(email);
        
        System.out.print("Phone (" + student.getPhone() + "): ");
        String phone = scanner.nextLine();
        if (!phone.trim().isEmpty()) student.setPhone(phone);
        
        System.out.print("Marks (" + student.getMarks() + "): ");
        String marksStr = scanner.nextLine();
        if (!marksStr.trim().isEmpty()) {
            try {
                student.setMarks(Double.parseDouble(marksStr));
            } catch (NumberFormatException e) {
                System.out.println(" Invalid marks format!");
                return;
            }
        }
        
        if (adminService.updateStudent(student)) {
            System.out.println("✅ Student updated successfully!");
        } else {
            System.out.println(" Failed to update student!");
        }
    }
    
    private void deleteStudentMenu() {
        System.out.println("\n========== DELETE STUDENT ==========");
        System.out.print("Enter Roll Number to delete: ");
        String rollNo = scanner.nextLine();
        
        Student student = adminService.getStudentByRollNo(rollNo);
        if (student == null) {
            System.out.println(" Student not found!");
            return;
        }
        
        System.out.println("Student to delete:");
        printStudentTableHeader();
        System.out.println(student);
        
        System.out.print("Are you sure you want to delete this student? (yes/no): ");
        String confirm = scanner.nextLine();
        
        if ("yes".equalsIgnoreCase(confirm)) {
            if (adminService.deleteStudent(student.getId())) {
                System.out.println(" Student deleted successfully!");
            } else {
                System.out.println(" Failed to delete student!");
            }
        } else {
            System.out.println(" Delete operation cancelled.");
        }
    }
    
    private void searchMenu() {
        System.out.println("\n========== SEARCH MENU ==========");
        System.out.println("1. Search by Roll Number");
        System.out.println("2. Search by Name");
        System.out.println("3. Search by Department");
        System.out.println("4. Search by Marks Range");
        System.out.println("5. Back to Main Menu");
        
        int choice = getIntInput("Enter your choice: ");
        
        switch (choice) {
            case 1: searchByRollNumberMenu(); break;
            case 2: searchByNameMenu(); break;
            case 3: searchByDepartmentMenu(); break;
            case 4: searchByMarksRangeMenu(); break;
            case 5: return;
            default: System.out.println(" Invalid choice!");
        }
    }
    
    private void searchByRollNumberMenu() {
        System.out.print("Enter Roll Number: ");
        String rollNo = scanner.nextLine();
        
        Student student = adminService.getStudentByRollNo(rollNo);
        if (student != null) {
            printStudentTableHeader();
            System.out.println(student);
        } else {
            System.out.println(" No student found with roll number: " + rollNo);
        }
    }
    
    private void searchByNameMenu() {
        System.out.print("Enter Name (partial match allowed): ");
        String name = scanner.nextLine();
        
        List<Student> students = adminService.searchStudentsByName(name);
        if (!students.isEmpty()) {
            printStudentTable(students);
        } else {
            System.out.println(" No students found with name containing: " + name);
        }
    }
    
    private void searchByDepartmentMenu() {
        System.out.print("Enter Department: ");
        String department = scanner.nextLine();
        
        List<Student> students = adminService.searchStudentsByDepartment(department);
        if (!students.isEmpty()) {
            printStudentTable(students);
        } else {
            System.out.println(" No students found in department: " + department);
        }
    }
    
    private void searchByMarksRangeMenu() {
        double minMarks = getDoubleInput("Enter minimum marks: ");
        double maxMarks = getDoubleInput("Enter maximum marks: ");
        
        List<Student> students = adminService.searchStudentsByMarksRange(minMarks, maxMarks);
        if (!students.isEmpty()) {
            printStudentTable(students);
        } else {
            System.out.println(" No students found with marks between " + minMarks + " and " + maxMarks);
        }
    }
    
    private void showStatisticsMenu() {
        System.out.println("\n========== STATISTICS ==========");
        
        AdminService.StudentStatistics stats = adminService.getStudentStatistics();
        
        System.out.println(" Total Students: " + stats.getTotalStudents());
        
        if (stats.getTotalStudents() > 0) {
            System.out.println(" Highest Marks: " + stats.getHighestMarks());
            System.out.println(" Lowest Marks: " + stats.getLowestMarks());
            
            System.out.println("\n Department-wise Student Count:");
            Map<String, Integer> deptCount = stats.getDepartmentWiseCount();
            for (Map.Entry<String, Integer> entry : deptCount.entrySet()) {
                System.out.println("   " + entry.getKey() + ": " + entry.getValue() + " students");
            }
        }
        
        System.out.println("================================");
    }
    
    // Helper methods
    private void printStudentTable(List<Student> students) {
        printStudentTableHeader();
        for (Student student : students) {
            System.out.println(student);
        }
        System.out.println("================================================================================================");
    }
    
    private void printStudentTableHeader() {

        // float mea = 
        System.out.println("================================================================================================");
        System.out.printf("%-5s %-20s %-15s %-15s %-25s %-12s %s%n", 
                         "ID", "Name", "Roll No", "Department", "Email", "Phone", "Marks");
        System.out.println("================================================================================================");
    }
    
    private int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println(" Please enter a valid number!");
            }
        }
    }
    
    private double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println(" Please enter a valid number!");
            }
        }
    }
}
