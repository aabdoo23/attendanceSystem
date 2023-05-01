import javax.swing.*;
import java.util.LinkedList;
import java.util.Random;

public class globals {
    public static LinkedList<Leave> pendingLeavesLL =new LinkedList<>();
    public static LinkedList<Department> departments=new LinkedList<>();
    public static LinkedList<Employee> pendingEmployeesLL=new LinkedList<>();
    public static LinkedList<Employee> employeesLL=new LinkedList<>();
    public static LinkedList<Head> headsLL=new LinkedList<>();
    public static LinkedList<Admin> adminsLL=new LinkedList<>();
    public static boolean[] usersIDs = new boolean[200];
    public static boolean[] departmentsIDs = new boolean[200];
    public static boolean[] attendanceIDs = new boolean[2000];
    public static boolean[] leaveIDs = new boolean[2000];



    public static int createNewID(boolean[] v) {
        Random random = new Random();
        int x = random.nextInt(v.length-1);
        while (v[x]) {
            x = random.nextInt(v.length-1);
        }
        v[x] = true;
        return x;
    }
    public static void makeList(LinkedList linkedList, JList<String> list) {
        String[] strings = new String[linkedList.size()];
        int i = 0;
        for (Object ob : linkedList) {
            strings[i] = ob.toString();
            i++;
        }
        list.setListData(strings);
    }
    public static String[] makeList(LinkedList linkedList) {
        String[] strings = new String[linkedList.size()];
        int i = 0;
        for (Object ob : linkedList) {
            strings[i] = ob.toString();
            i++;
        }
        return strings;
    }

    public static void makeList(String[] strings, JList<String> list) {
        list.setListData(strings);
    }
    public static void makeList(LinkedList linkedList, JComboBox<String> list) {

        String[] strings = new String[linkedList.size()];
        int i = 0;
        for (Object ob : linkedList) {
            strings[i] = ob.toString();
            i++;
        }
        list.setModel(new DefaultComboBoxModel<>(strings));
    }
    public static void defaultMakeList(LinkedList linkedList, JComboBox<String> list) {

        String[] strings = new String[linkedList.size()+1];
        strings[0]="-";
        int i = 1;
        for (Object ob : linkedList) {
            strings[i] = ob.toString();
            i++;
        }
        list.setModel(new DefaultComboBoxModel<>(strings));
    }
}
