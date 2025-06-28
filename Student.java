public class Student {
    private int id;
    private String name;
    private String rollNo;
    private String department;
    private String email;
    private String phone;
    private double marks;
    
    // Default Constructor
    public Student() {}
    
    // Constructor without ID (for new students)
    public Student(String name, String rollNo, String department, String email, String phone, double marks) {
        this.name = name;
        this.rollNo = rollNo;
        this.department = department;
        this.email = email;
        this.phone = phone;
        this.marks = marks;
    }
    
    // Constructor with ID (for existing students)
    public Student(int id, String name, String rollNo, String department, String email, String phone, double marks) {
        this.id = id;
        this.name = name;
        this.rollNo = rollNo;
        this.department = department;
        this.email = email;
        this.phone = phone;
        this.marks = marks;
    }
    
    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getRollNo() { return rollNo; }
    public void setRollNo(String rollNo) { this.rollNo = rollNo; }
    
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
    public double getMarks() { return marks; }
    public void setMarks(double marks) { this.marks = marks; }
    
    @Override
    public String toString() {
        return String.format("%-5d %-20s %-15s %-15s %-25s %-12s %.2f", 
                           id, name, rollNo, department, email, phone, marks);
    }
}
