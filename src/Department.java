import java.util.Arrays;
import java.util.LinkedList;

public class Department {
    private String name;
    private int ID;
    private Head head =null;
    LinkedList<Employee>employees=new LinkedList<>();
    Department(){}
    Department(int id, String name, Head employee){
        this.ID=id;
        this.head = employee;
        this.name=name;
    }
    public String getName() {
        return name;
    }
    public void setEmployees(LinkedList<Employee> employees) {
        this.employees = employees;
    }
    public LinkedList<Employee> getEmployees() {
        return employees;
    }
    public void addToEmployees(Employee Employee){
        employees.add(Employee);
    }
    public void removeFromEmployees(Employee Employee){
        employees.remove(Employee);
    }

    public void setHead(Head head) {
        this.head = head;
    }
    public Employee getHead() {
        return head;
    }
    public int getID() {
        return ID;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setID(int ID) {
        this.ID = ID;
    }

    @Override
    public String toString() {
        String hun="null";
        String[]emp = new String[0];
        if(employees!=null&&employees.size()>0){
            emp=new String[employees.size()];
            int i=0;
            for (Employee employee:employees){emp[i]=employee.getUserName();i++;}
        }
        if(head !=null)hun= head.getUserName();
        return "Department" +
                "\n, name=" + name +
                "\n, ID=" + ID +
                "\n, Head=" + hun +
                "\n, employees=" + Arrays.toString(emp) +
                '\n';
    }

}
