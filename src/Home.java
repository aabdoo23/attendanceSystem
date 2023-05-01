import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Objects;

public class Home extends JFrame {
    private JPanel mainPanel;
    private JTextField tfID;
    private JButton signUpButton;
    private JButton adminButton;
    private JButton signInButton;
    private JPasswordField pfPW;
    private JButton saveButton;

    Home() throws SQLException {
        new DB();
        setContentPane(mainPanel);
        setTitle("Attendance Report");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(420,180);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);

        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new newUser(false);
            }
        });
        adminButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AdminGUI();
            }
        });
        signInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Objects.equals(tfID.getText().trim(), "admin") && Objects.equals(pfPW.getText().trim(), "admin")){
                    new AdminGUI();
                    tfID.setText("");
                    pfPW.setText("");
                    return;
                }
                for (Admin admin :globals.adminsLL){
                    if (admin.getID() == Integer.parseInt(tfID.getText()) && Objects.equals(admin.getPassword(), pfPW.getText())){
                        new AdminGUI();
                        tfID.setText("");
                        pfPW.setText("");
                        return;
                    }
                }
                for (Employee employee : globals.pendingEmployeesLL) {
                    if (employee.getID() == Integer.parseInt(tfID.getText()) && Objects.equals(employee.getPassword(), pfPW.getText())) {
                        JOptionPane.showMessageDialog(mainPanel, "User still pending approval.");
                        tfID.setText("");
                        pfPW.setText("");
                        return;
                    }
                }
                for (Employee employee : globals.employeesLL) {
                    if (employee.getID() == Integer.parseInt(tfID.getText().trim()) && Objects.equals(employee.getPassword(), pfPW.getText())) {
                        new userMenu(employee);
                        tfID.setText("");
                        pfPW.setText("");
                        return;
                    }
                }

                for (Head head : globals.headsLL) {
                    if (head.getID() == Integer.parseInt(tfID.getText().trim()) && Objects.equals(head.getPassword(), pfPW.getText())) {
                        new userMenu(head);
                        tfID.setText("");
                        pfPW.setText("");
                        return;
                    }
                }

                JOptionPane.showMessageDialog(null, "Error: Credentials don't match", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    DB.setAllAttendances();
                    DB.setAllLeaves();
                    DB.setAllAdmins(globals.adminsLL);
                    DB.setAllHeads(globals.headsLL);
                    DB.setAllEmployees(globals.employeesLL);
                    DB.setAllPendingEmployees(globals.pendingEmployeesLL);
                    DB.setAllPendingLeaves(globals.pendingLeavesLL);
                    DB.setAllDepartments(globals.departments);
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
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
    }




}
