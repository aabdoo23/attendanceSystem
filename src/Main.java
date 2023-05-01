import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        new DB();
        globals.headsLL=DB.getAllHeads();
        globals.departments=DB.getAllDepartments();
        globals.employeesLL=DB.getAllEmployees();
        globals.adminsLL=DB.getAllAdmins();
        globals.pendingEmployeesLL=DB.getAllPendingEmployees();
        globals.pendingLeavesLL=DB.getAllPendingLeaves();
        for (Employee employee:globals.employeesLL){
            employee.setAttendances(DB.getEmployeeAttendances(employee));
            employee.setLeaves(DB.getEmployeeLeaves(employee));
        }
        for (Head employee:globals.headsLL){
            employee.setAttendances(DB.getHeadAttendances(employee));
            employee.setLeaves(DB.getHeadLeaves(employee));
        }
        new Home();

    }
}