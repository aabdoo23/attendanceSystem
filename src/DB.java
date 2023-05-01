import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;

public class DB {
    private static Connection conn;
    private static PreparedStatement pstmt;
    private static ResultSet rs;
    private static Statement stmt;
    String url = "jdbc:mysql://localhost:3306/attendance";
    String username = "root";
    String pass = "";

    public DB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, username, pass);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e);
        }
    }
    public static void truncateAdmins() throws SQLException {
        stmt = conn.createStatement();
        stmt.execute("SET FOREIGN_KEY_CHECKS=0");
        stmt.executeUpdate("TRUNCATE TABLE admins");
    }
    public static LinkedList<Admin> getAllAdmins() throws SQLException {
        stmt = conn.createStatement();
        rs = stmt.executeQuery("SELECT * FROM admins");
        LinkedList<Admin> admins = new LinkedList<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            String userName = rs.getString("username");
            String password = rs.getString("password");
            String email = rs.getString("email");
            String phoneNumber = rs.getString("phone_number");
            Admin admin = new Admin(id, userName, password, email, phoneNumber);
            admins.add(admin);
        }
        return admins;
    }
    public static void setAllAdmins(LinkedList<Admin> admins) throws SQLException {
        truncateAdmins();
        pstmt = conn.prepareStatement("INSERT INTO admins (id, username, password, email, phone_number) VALUES (?, ?, ?, ?, ?)");
        for (Admin admin : admins) {
            pstmt.setInt(1, admin.getID());
            pstmt.setString(2, admin.getUserName());
            pstmt.setString(3, admin.getPassword());
            pstmt.setString(4, admin.getEmail());
            pstmt.setString(5, admin.getPhoneNumber());
            pstmt.executeUpdate();
        }
    }

    public static void truncateAttendances() throws SQLException {
        stmt = conn.createStatement();
        stmt.execute("SET FOREIGN_KEY_CHECKS=0");
        stmt.executeUpdate("TRUNCATE TABLE attendances");
    }
    public static LinkedList<Attendance> getEmployeeAttendances(Employee employee) throws SQLException {
        LinkedList<Attendance> attendances = new LinkedList<>();
        String query = ("SELECT * FROM attendances WHERE userID = "+Integer.toString(employee.getID()));
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Attendance attendance = new Attendance();
                    attendance.setID(rs.getInt("ID"));
                    attendance.setUserID( rs.getInt("userID"));
                    attendance.setAttendanceStatus( rs.getBoolean("attendanceStatus"));
                    attendance.setDateTime(rs.getObject("dateTime", LocalDateTime.class));
                    attendances.add(attendance);
                }
            }
        }
        return attendances;
    }
    public static LinkedList<Attendance> getHeadAttendances(Head employee) throws SQLException {
        LinkedList<Attendance> attendances = new LinkedList<>();
        String query = ("SELECT * FROM attendances WHERE userID = "+Integer.toString(employee.getID()));
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Attendance attendance = new Attendance();
                    attendance.setID(rs.getInt("ID"));
                    attendance.setUserID( rs.getInt("userID"));
                    attendance.setAttendanceStatus( rs.getBoolean("attendanceStatus"));
                    attendance.setDateTime(rs.getObject("dateTime", LocalDateTime.class));
                    attendances.add(attendance);
                }
            }
        }
        return attendances;
    }
    public static void setAllAttendances() throws SQLException {
        truncateAttendances();
        LinkedList<Attendance>attendances=new LinkedList<>();
        for (Employee employee:globals.employeesLL){
            attendances.addAll(employee.getAttendances());
        }
        for (Head employee:globals.headsLL){
            attendances.addAll(employee.getAttendances());
        }
        String query = "INSERT INTO attendances(ID, userID, attendanceStatus, dateTime) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            for (Attendance attendance : attendances) {
                stmt.setInt(1, attendance.getID());
                stmt.setInt(2, attendance.getUserID());
                stmt.setBoolean(3, attendance.isAttendanceStatus());
                stmt.setObject(4, attendance.getDateTime());
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
    }

    public static void truncateDepartments() throws SQLException {
        stmt = conn.createStatement();
        stmt.execute("SET FOREIGN_KEY_CHECKS=0");
        stmt.executeUpdate("TRUNCATE TABLE departments");
    }
    public static LinkedList<Department> getAllDepartments() throws SQLException {
        stmt = conn.createStatement();
        rs = stmt.executeQuery("SELECT * FROM departments");
        LinkedList<Department> departments = new LinkedList<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            Head head = getHead(rs.getInt("head_id"));
            Department department = new Department(id,name, head);
            department.setEmployees(getDepartmentEmployees(department));
            departments.add(department);
        }
        return departments;
    }
    public static void setAllDepartments(LinkedList<Department> departments) throws SQLException {
        truncateDepartments();
        pstmt = conn.prepareStatement("INSERT INTO departments (id, name, head_id) VALUES (?, ?, ?)");
        for (Department department : departments) {
            pstmt.setInt(1, department.getID());
            pstmt.setString(2, department.getName());
            if(department.getHead()!=null)
                pstmt.setInt(3, department.getHead().getID());
            else
                pstmt.setInt(3, 0);

            pstmt.executeUpdate();
        }
    }

    public static void truncateEmployees() throws SQLException {
        stmt = conn.createStatement();
        stmt.execute("SET FOREIGN_KEY_CHECKS=0");
        stmt.executeUpdate("TRUNCATE TABLE employees");
    }
    public static LinkedList<Employee> getAllEmployees() throws SQLException {
        stmt = conn.createStatement();
        rs = stmt.executeQuery("SELECT * FROM employees");
        LinkedList<Employee> employees = new LinkedList<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            String userName = rs.getString("username");
            String password = rs.getString("password");
            String email = rs.getString("email");
            String phoneNumber = rs.getString("phone_number");
            Employee employee = new Employee(id, userName, password, email, phoneNumber);
            employees.add(employee);
        }
        return employees;
    }
    public static void setAllEmployees(LinkedList<Employee> employees) throws SQLException {
        truncateEmployees();

        pstmt = conn.prepareStatement("INSERT INTO employees (id, username, password, email, phone_number) VALUES (?, ?, ?, ?, ? )");
        for (Employee employee : employees) {
            if (employee.getDepartment()!=null){
                pstmt = conn.prepareStatement("INSERT INTO employees (id, username, password, email, phone_number,department_id) VALUES (?, ?, ?, ?, ?, ?)");
                pstmt.setInt(6,employee.getDepartment().getID());
            }
            else {
                pstmt = conn.prepareStatement("INSERT INTO employees (id, username, password, email, phone_number) VALUES (?, ?, ?, ?, ?)");
            }

            pstmt.setInt(1, employee.getID());
            pstmt.setString(2, employee.getUserName());
            pstmt.setString(3, employee.getPassword());
            pstmt.setString(4, employee.getEmail());
            pstmt.setString(5, employee.getPhoneNumber());
            pstmt.executeUpdate();
        }
    }

    public static void truncateHeads() throws SQLException {
        stmt = conn.createStatement();
        stmt.execute("SET FOREIGN_KEY_CHECKS=0");
        stmt.executeUpdate("TRUNCATE TABLE heads");
    }
    public static LinkedList<Head> getAllHeads() throws SQLException {
        stmt = conn.createStatement();
        rs = stmt.executeQuery("SELECT * FROM heads");
        LinkedList<Head> heads = new LinkedList<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            String userName = rs.getString("username");
            String password = rs.getString("password");
            String email = rs.getString("email");
            String phoneNumber = rs.getString("phone_number");
            int departmentID = rs.getInt("department_id");
            Department department = getDepartment(departmentID);
            Head head = new Head(id, department, userName, password, email, phoneNumber);
            heads.add(head);
        }
        return heads;
    }
    public static void setAllHeads(LinkedList<Head> heads) throws SQLException {
        truncateHeads();
        pstmt = conn.prepareStatement("INSERT INTO heads (id, username, password, email, phone_number) VALUES (?, ?, ?, ?, ?)");
        for (Head head : heads) {
            if (head.getDepartment()!=null){
                pstmt = conn.prepareStatement("INSERT INTO heads (id, username, password, email, phone_number,department_id) VALUES (?, ?, ?, ?, ?, ?)");
                pstmt.setInt(6,head.getDepartment().getID());
            }
            else {
                pstmt = conn.prepareStatement("INSERT INTO heads (id, username, password, email, phone_number) VALUES (?, ?, ?, ?, ?)");
            }
            pstmt.setInt(1, head.getID());
            pstmt.setString(2, head.getUserName());
            pstmt.setString(3, head.getPassword());
            pstmt.setString(4, head.getEmail());
            pstmt.setString(5, head.getPhoneNumber());
            pstmt.executeUpdate();
        }
    }

    public static void truncatePendingEmployees() throws SQLException {
        stmt = conn.createStatement();
        stmt.execute("SET FOREIGN_KEY_CHECKS=0");
        stmt.executeUpdate("TRUNCATE TABLE pending_employees");
    }
    public static LinkedList<Employee> getAllPendingEmployees() throws SQLException {
        LinkedList<Employee> employees = new LinkedList<>();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM pending_employees");
        while (rs.next()) {
            int id = rs.getInt("id");
            String username = rs.getString("username");
            String password = rs.getString("password");
            String email = rs.getString("email");
            String phoneNumber = rs.getString("phone_number");
            int departmentID = rs.getInt("department_id");
            Department department = getDepartment(departmentID);
            employees.add(new Employee(id, department, username, password, email, phoneNumber));
        }
        return employees;
    }
    public static void setAllPendingEmployees(LinkedList<Employee> employees) throws SQLException {
        truncatePendingEmployees();
        String query = "INSERT INTO pending_employees (id, username, password, email, phone_number) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            for (Employee employee : employees) {
                if (employee.getDepartment()!=null){
                    pstmt = conn.prepareStatement("INSERT INTO pending_employees (id, username, password, email, phone_number,department_id) VALUES (?, ?, ?, ?, ?, ?)");
                    pstmt.setInt(6,employee.getDepartment().getID());
                }
                else {
                    pstmt = conn.prepareStatement("INSERT INTO pending_employees (id, username, password, email, phone_number) VALUES (?, ?, ?, ?, ?)");
                }
                statement.setInt(1, employee.getID());
                statement.setString(2, employee.getUserName());
                statement.setString(3, employee.getPassword());
                statement.setString(4, employee.getEmail());
                statement.setString(5, employee.getPhoneNumber());
                statement.executeUpdate();
            }
        }
    }

    public static void truncatePendingLeaves() throws SQLException {
        stmt = conn.createStatement();
        stmt.execute("SET FOREIGN_KEY_CHECKS=0");
        stmt.executeUpdate("TRUNCATE TABLE pending_leaves");
    }
    public static LinkedList<Leave> getAllPendingLeaves() throws SQLException {
        LinkedList<Leave> leaves = new LinkedList<>();
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM pending_leaves");
            while (rs.next()) {
                int id = rs.getInt("id");
                int type = rs.getInt("type");
                int userID = rs.getInt("user_id");
                LocalDate start = rs.getDate("start").toLocalDate();
                LocalDate end = rs.getDate("end").toLocalDate();
                String reason = rs.getString("reason");
                Leave leave=new Leave(id, userID,type , start, end, reason);
                leave.setAccepted(false);
                leaves.add(leave);
            }
        }
        return leaves;
    }
    public static void setAllPendingLeaves(LinkedList<Leave> leaves) throws SQLException {
        truncatePendingLeaves();
        PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO pending_leaves (id, type, user_id, start, end, reason, accepted) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?) ");
        for (Leave leave : leaves) {
            stmt.setInt(1, leave.getID());
            stmt.setInt(2, leave.getType());
            stmt.setInt(3, leave.getUserID());
            stmt.setDate(4, Date.valueOf(leave.getStart()));
            stmt.setDate(5, Date.valueOf(leave.getEnd()));
            stmt.setString(6, leave.getReason());
            stmt.setBoolean(7, leave.isAccepted());
            stmt.executeUpdate();
        }
    }

    public static void truncateLeaves() throws SQLException {
        stmt = conn.createStatement();
        stmt.execute("SET FOREIGN_KEY_CHECKS=0");
        stmt.executeUpdate("TRUNCATE TABLE leaves");
    }
    public static LinkedList<Leave> getEmployeeLeaves(Employee employee) throws SQLException {
        LinkedList<Leave> leaves = new LinkedList<>();
        String query = ("SELECT * FROM leaves WHERE user_id = "+Integer.toString(employee.getID()));
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    int type = rs.getInt("type");
                    int userID = rs.getInt("user_id");
                    LocalDate start = rs.getDate("start").toLocalDate();
                    LocalDate end = rs.getDate("end").toLocalDate();
                    String reason = rs.getString("reason");
                    Leave leave=new Leave(id, userID,type , start, end, reason);
                    leave.setAccepted(false);
                    leaves.add(leave);
                }
            }
        }
        return leaves;
    }
    public static LinkedList<Leave> getHeadLeaves(Head employee) throws SQLException {
        LinkedList<Leave> leaves = new LinkedList<>();
        String query = ("SELECT * FROM leaves WHERE user_id = "+Integer.toString(employee.getID()));
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    int type = rs.getInt("type");
                    int userID = rs.getInt("user_id");
                    LocalDate start = rs.getDate("start").toLocalDate();
                    LocalDate end = rs.getDate("end").toLocalDate();
                    String reason = rs.getString("reason");
                    Leave leave=new Leave(id, userID,type , start, end, reason);
                    leave.setAccepted(false);
                    leaves.add(leave);
                }
            }
        }
        return leaves;
    }
    public static void setAllLeaves() throws SQLException {
        truncateLeaves();
        LinkedList<Leave>leaves=new LinkedList<>();

        for (Employee employee:globals.employeesLL){
            leaves.addAll(employee.getLeaves());
        }
        for (Head employee:globals.headsLL){
            leaves.addAll(employee.getLeaves());
        }
        String query = "INSERT INTO leaves (id, type, user_id, start, end, reason, accepted) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?) ";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            for (Leave leave : leaves) {
                stmt.setInt(1, leave.getID());
                stmt.setInt(2, leave.getType());
                stmt.setInt(3, leave.getUserID());
                stmt.setDate(4, Date.valueOf(leave.getStart()));
                stmt.setDate(5, Date.valueOf(leave.getEnd()));
                stmt.setString(6, leave.getReason());
                stmt.setBoolean(7, leave.isAccepted());
                stmt.executeUpdate();
            }
            stmt.executeBatch();
        }
    }

    

    private static LinkedList<Employee> getDepartmentEmployees(Department department) throws SQLException {
        LinkedList<Employee> employees = getAllEmployees(),depEmp=new LinkedList<>();
        for (Employee employee : employees) {
            if (employee.getDepartment() == department) {
                depEmp.add(employee);
            }
        }
        return depEmp;
    }
    private static Department getDepartment(int departmentID) throws SQLException {
        globals.departments=getAllDepartments();
        for (Department department:globals.departments){
            if(department.getID()==departmentID)return department;
        }
        return null;
    }
    private static Head getHead(int headId) {
        for (Head head:globals.headsLL){
            if (head.getID()==headId){
                return head;
            }
        }
        return null;
    }

}
