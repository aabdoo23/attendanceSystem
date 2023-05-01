import java.util.Arrays;
import java.util.LinkedList;

public class Employee extends User {
    private LinkedList<Leave>leaves=new LinkedList<>();
    private Department department=null;
    private LinkedList<Attendance>attendances=new LinkedList<>();
    Employee(){}
    Employee(int ID, String uN, String pW, String eM, String pN){
        super(ID, uN, pW, eM, pN);
        for (Department department1:globals.departments){
            for (Employee employee:department1.getEmployees()){
                if (employee.getID()==ID){
                    this.department=department1;
                    break;
                }
            }
        }
    }
    Employee(int ID, Department department, String uN, String pW, String eM, String pN){
        super(ID, uN, pW, eM, pN);
        this.department=department;
    }
    public void setDepartment(Department department) {
        this.department = department;
    }
    public Department getDepartment() {
        return department;
    }
    public void setAttendances(LinkedList<Attendance> attendances) {
        this.attendances = attendances;
    }
    public void setLeaves(LinkedList<Leave> leaves) {
        this.leaves = leaves;
    }

    public void addToAttendances(Attendance attendance){
        attendances.add(attendance);
    }
    public void addToLeaves(Leave leave){
        leaves.add(leave);
    }
    public void removeFromAttendances(Attendance attendance){
        attendances.remove(attendance);
    }
    public void removeFromLeaves(Leave leave){
        leaves.remove(leave);
    }
    public LinkedList<Attendance> getAttendances() {
        return attendances;
    }
    public LinkedList<Leave> getLeaves() {
        return leaves;
    }
    String[] att(){
        String []at=new String[attendances.size()];
        int i=0;
        for (Attendance attendance:attendances){
            at[i]=attendance.customToString();
            i++;
        }
        return at;
    }
    @Override
    public String toString() {
        return super.toString()+", Employee" +
                "\n, leaves=" + leaves +
                "\n, department=" + (department!=null?department.getName():"") +
                "\n, attendances=" + Arrays.toString(att()) +
                '\n';
    }
    public String customToString() {
        return "ID=" + super.getID() +
                "\n, userName=" + super.getUserName() +
                "\n, email=" + super.getEmail()+
                "\n, phoneNumber=" + super.getPhoneNumber() +
                "\n, department=" + (department!=null?department.getName():"") +
                '\n';
    }
}
