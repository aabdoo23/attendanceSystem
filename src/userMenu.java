import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;

public class userMenu extends JFrame {
    private JPanel mainPanel;
    private JTextField tfID;
    private JTextField tfEmail;
    private JTextField tfName;
    private JTextField tfPN;
    private JTextField tfDP;
    private JTextField tfRole;
    private JTextField tfStatus;
    private JButton timeOutButton;
    private JButton refreshButton;
    private JList<String> attendanceList;
    private JButton timeInButton;
    private JButton requestALeaveButton;
    private JList<String> leavesList;
    boolean status = false;

    Employee employee;
    Head head1;

    void updateDisplay() {
        tfID.setText(Integer.toString(employee.getID()));
        tfEmail.setText(employee.getEmail());
        tfName.setText(employee.getUserName());
        tfPN.setText(employee.getPhoneNumber());
        if (employee.getDepartment() != null)
            tfDP.setText(employee.getDepartment().getName());
        else
            tfDP.setText("Not assigned yet");
        tfRole.setText("Employee");
        if (!status)
            tfStatus.setText("Not logged in");
        else
            tfStatus.setText("Logged in");
        globals.makeList(employee.getAttendances(), attendanceList);
        globals.makeList(employee.getLeaves(), leavesList);
    }
    void updateHeadDisplay() {
        tfID.setText(Integer.toString(head1.getID()));
        tfEmail.setText(head1.getEmail());
        tfName.setText(head1.getUserName());
        tfPN.setText(head1.getPhoneNumber());
        if (head1.getDepartment() != null)
            tfDP.setText(head1.getDepartment().getName());
        else
            tfDP.setText("Not assigned yet");
        tfRole.setText("Head");
        if (!status)
            tfStatus.setText("Not logged in");
        else
            tfStatus.setText("Logged in");
        globals.makeList(head1.getAttendances(), attendanceList);
        globals.makeList(head1.getLeaves(), leavesList);
    }

    userMenu(Employee employee1) {
        status=false;
        if(employee1.getAttendances().size()>0){
            status=employee1.getAttendances().getLast().isAttendanceStatus();
        }
        setContentPane(mainPanel);
        setTitle("User Menu");
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setSize(650, 500);
        setLocationRelativeTo(null);
        setVisible(true);
        employee = employee1;
        updateDisplay();
        timeInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (status) {
                    JOptionPane.showMessageDialog(mainPanel, "You're already logged in");
                } else {
                    int id = globals.createNewID(globals.attendanceIDs);
                    LocalDateTime dateTime = LocalDateTime.now();
                    Attendance attendance = new Attendance(id, employee.getID(), true, dateTime);
                    employee.addToAttendances(attendance);
                    status = true;
                    updateDisplay();
                }
            }
        });
        timeOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!status) {
                    JOptionPane.showMessageDialog(mainPanel, "You're already logged out");
                } else {
                    int id = globals.createNewID(globals.attendanceIDs);
                    LocalDateTime dateTime = LocalDateTime.now();
                    Attendance attendance = new Attendance(id, employee.getID(), false, dateTime);
                    employee.addToAttendances(attendance);
                    status = false;
                    updateDisplay();
                }
            }
        });
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateDisplay();
            }
        });
        requestALeaveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new requestLeave(employee);
            }
        });
    }

    userMenu(Head head) {
        status=false;
        if(head.getAttendances().size()>0){
            status=head.getAttendances().getLast().isAttendanceStatus();
        }
        setContentPane(mainPanel);
        setTitle("Head Menu");
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setSize(650, 500);
        setLocationRelativeTo(null);
        setVisible(true);
        head1 = head;
        updateHeadDisplay();
        timeInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (status) {
                    JOptionPane.showMessageDialog(mainPanel, "You're already logged in");
                } else {
                    int id = globals.createNewID(globals.attendanceIDs);
                    LocalDateTime dateTime = LocalDateTime.now();
                    Attendance attendance = new Attendance(id, head.getID(), true, dateTime);
                    head.addToAttendances(attendance);
                    status = true;
                    updateHeadDisplay();
                }
            }
        });
        timeOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!status) {
                    JOptionPane.showMessageDialog(mainPanel, "You're already logged out");
                } else {
                    int id = globals.createNewID(globals.attendanceIDs);
                    LocalDateTime dateTime = LocalDateTime.now();
                    Attendance attendance = new Attendance(id, head.getID(), false, dateTime);
                    head.addToAttendances(attendance);
                    status = false;
                    updateHeadDisplay();
                }
            }
        });
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateHeadDisplay();
            }
        });
        requestALeaveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new requestLeave(head);
                updateHeadDisplay();
            }
        });
    }
}
