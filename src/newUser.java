import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class newUser extends JFrame {
    private JTextField tfID;
    private JTextField tfEmail;
    private JTextField tfFN;
    private JTextField tfLN;
    private JTextField tfPN;
    private JComboBox<String> cbDP;
    private JButton saveButton;
    private JButton closeButton;
    private JPanel mainPanel;
    private JPasswordField passwordField1;
    private JPasswordField passwordField2;
    private JComboBox<String> cbRole;
    newUser(Employee employee){
        setContentPane(mainPanel);
        setTitle("Edit");
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setSize(750,300);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        int id= employee.getID();
        tfID.setText(Integer.toString(id));
        tfFN.setText(employee.getUserName());
        tfPN.setText(employee.getPhoneNumber());
        tfEmail.setText(employee.getEmail());
        passwordField1.setText(employee.getPassword());
        passwordField2.setText(employee.getPassword());
        globals.defaultMakeList(globals.departments,cbDP);
        cbRole.setEnabled(true);

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Department dp;
                if(cbDP.getSelectedIndex()==0)dp=null;
                else dp=globals.departments.get(cbDP.getSelectedIndex()-1);
                int role=cbRole.getSelectedIndex();
                String email=tfEmail.getText();
                String first=tfFN.getText();
                String last=tfLN.getText();
                String pn=tfPN.getText();
                String pw=passwordField1.getText();
                if(tfEmail.getText().trim().isEmpty()||tfFN.getText().trim().isEmpty()||tfLN.getText().trim().isEmpty()||tfPN.getText().trim().isEmpty()||pw.trim().isEmpty()){
                    JOptionPane.showMessageDialog(null, "Error: Please fill all information", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if(!Objects.equals(passwordField1.getText(), passwordField2.getText())){
                    JOptionPane.showMessageDialog(null, "Error: Passwords don't match", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                globals.employeesLL.remove(employee);
                for (Department department:globals.departments){
                    if (department== employee.getDepartment()){
                        department.removeFromEmployees(employee);
                        break;
                    }
                }
                if (role==0){
                    Employee editedEmployee =new Employee(id,dp,(first+" "+last).trim(),pw,email,pn);
                    editedEmployee.setAttendances(employee.getAttendances());
                    editedEmployee.setLeaves(employee.getLeaves());
                    globals.employeesLL.add(editedEmployee);
                    if(dp != null) dp.addToEmployees(editedEmployee);
                } else if (role==1) {
                    Head head=new Head(id,dp,first+" "+last,pw,email,pn);
                    head.setAttendances(employee.getAttendances());
                    head.setLeaves(employee.getLeaves());
                    globals.headsLL.add(head);
                    if(dp != null) dp.setHead(head);
                }
                else{
                    Admin admin1=new Admin(id,first+" "+last,pw,email,pn);
                    globals.adminsLL.add(admin1);
                }

                JOptionPane.showMessageDialog(mainPanel, "Info updated");
                dispose();
            }
        });
    }
    newUser(Head toBeEditedHead){
        setContentPane(mainPanel);
        setTitle("Edit");
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setSize(750,300);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        int id= toBeEditedHead.getID();
        tfID.setText(Integer.toString(id));
        tfFN.setText(toBeEditedHead.getUserName());
        tfPN.setText(toBeEditedHead.getPhoneNumber());
        tfEmail.setText(toBeEditedHead.getEmail());
        passwordField1.setText(toBeEditedHead.getPassword());
        passwordField2.setText(toBeEditedHead.getPassword());
        globals.defaultMakeList(globals.departments,cbDP);
        cbRole.setEnabled(true);

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Department dp;
                if(cbDP.getSelectedIndex()==0)dp=null;
                else dp=globals.departments.get(cbDP.getSelectedIndex()-1);
                int role=cbRole.getSelectedIndex();
                String email=tfEmail.getText();
                String first=tfFN.getText();
                String last=tfLN.getText();
                String pn=tfPN.getText();
                String pw=passwordField1.getText();
                if(tfEmail.getText().trim().isEmpty()||tfFN.getText().trim().isEmpty()||tfLN.getText().trim().isEmpty()||tfPN.getText().trim().isEmpty()||pw.trim().isEmpty()){
                    JOptionPane.showMessageDialog(null, "Error: Please fill all information", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if(!Objects.equals(passwordField1.getText(), passwordField2.getText())){
                    JOptionPane.showMessageDialog(null, "Error: Passwords don't match", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                globals.headsLL.remove(toBeEditedHead);
                for (Department department:globals.departments){
                    if (department== toBeEditedHead.getDepartment()){
                        department.setHead(null);
                        break;
                    }
                }
                if (role==0){
                    Employee editedEmployee =new Employee(id,dp,(first+" "+last).trim(),pw,email,pn);
                    editedEmployee.setAttendances(toBeEditedHead.getAttendances());
                    editedEmployee.setLeaves(toBeEditedHead.getLeaves());
                    globals.employeesLL.add(editedEmployee);

                    if(dp != null) dp.addToEmployees(editedEmployee);
                }
                else if (role==1) {
                    Head head=new Head(id,dp,first+" "+last,pw,email,pn);
                    head.setAttendances(toBeEditedHead.getAttendances());
                    head.setLeaves(toBeEditedHead.getLeaves());
                    globals.headsLL.add(head);
                    if(dp != null) dp.setHead(head);
                }
                else{
                    Admin admin1=new Admin(id,first+" "+last,pw,email,pn);
                    globals.adminsLL.add(admin1);
                }

                JOptionPane.showMessageDialog(mainPanel, "Info updated");
                dispose();
            }
        });
    }
    newUser(Admin toBeEditedAdmin){
        setContentPane(mainPanel);
        setTitle("Edit");
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setSize(750,300);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        int id= toBeEditedAdmin.getID();
        tfID.setText(Integer.toString(id));
        tfFN.setText(toBeEditedAdmin.getUserName());
        tfPN.setText(toBeEditedAdmin.getPhoneNumber());
        tfEmail.setText(toBeEditedAdmin.getEmail());
        passwordField1.setText(toBeEditedAdmin.getPassword());
        passwordField2.setText(toBeEditedAdmin.getPassword());
        globals.defaultMakeList(globals.departments,cbDP);
        cbRole.setEnabled(true);

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Department dp;
                if(cbDP.getSelectedIndex()==0)dp=null;
                else dp=globals.departments.get(cbDP.getSelectedIndex()-1);
                int role=cbRole.getSelectedIndex();
                String email=tfEmail.getText();
                String first=tfFN.getText();
                String last=tfLN.getText();
                String pn=tfPN.getText();
                String pw=passwordField1.getText();
                if(tfEmail.getText().trim().isEmpty()||tfFN.getText().trim().isEmpty()||tfLN.getText().trim().isEmpty()||tfPN.getText().trim().isEmpty()||pw.trim().isEmpty()){
                    JOptionPane.showMessageDialog(null, "Error: Please fill all information", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if(!Objects.equals(passwordField1.getText(), passwordField2.getText())){
                    JOptionPane.showMessageDialog(null, "Error: Passwords don't match", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                globals.adminsLL.remove(toBeEditedAdmin);
                if (role==0){
                    Employee editedEmployee =new Employee(id,dp,(first+" "+last).trim(),pw,email,pn);
                    globals.employeesLL.add(editedEmployee);
                    if(dp != null) dp.addToEmployees(editedEmployee);
                } else if (role==1) {
                    Head head=new Head(id,dp,first+" "+last,pw,email,pn);
                    globals.headsLL.add(head);
                    if(dp != null) dp.setHead(head);
                }
                else{
                    Admin admin1=new Admin(id,first+" "+last,pw,email,pn);
                    globals.adminsLL.add(admin1);
                }

                JOptionPane.showMessageDialog(mainPanel, "Info updated");
                dispose();
            }
        });
    }
    newUser(boolean admin){
        setContentPane(mainPanel);
        setTitle("Sign up");
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setSize(750,300);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        int id=globals.createNewID(globals.usersIDs);
        tfID.setText(Integer.toString(id));
        globals.defaultMakeList(globals.departments,cbDP);
        cbRole.setEnabled(admin);

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Department dp;
                if(cbDP.getSelectedIndex()==0)dp=null;
                else dp=globals.departments.get(cbDP.getSelectedIndex()-1);
                int role=cbRole.getSelectedIndex();
                String email=tfEmail.getText();
                String first=tfFN.getText();
                String last=tfLN.getText();
                String pn=tfPN.getText();
                String pw=passwordField1.getText();
                if(tfEmail.getText().trim().isEmpty()||tfFN.getText().trim().isEmpty()||tfLN.getText().trim().isEmpty()||tfPN.getText().trim().isEmpty()||pw.trim().isEmpty()){
                    JOptionPane.showMessageDialog(null, "Error: Please fill all information", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if(!Objects.equals(passwordField1.getText(), passwordField2.getText())){
                    JOptionPane.showMessageDialog(null, "Error: Passwords don't match", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (role==0){
                    Employee employee =new Employee(id,dp,first+" "+last,pw,email,pn);
                    if(!admin){
                        globals.pendingEmployeesLL.add(employee);
                        JOptionPane.showMessageDialog(mainPanel, "User pending approval by admin");
                    }
                    else {globals.employeesLL.add(employee);if(dp!=null)dp.addToEmployees(employee);}
                } else if (role==1) {
                    Head head=new Head(id,dp,first+" "+last,pw,email,pn);
                    globals.headsLL.add(head);
                    if(dp!=null)dp.setHead(head);
                }
                else{
                    Admin admin1=new Admin(id,first+" "+last,pw,email,pn);
                    globals.adminsLL.add(admin1);
                }
                if(admin)
                    JOptionPane.showMessageDialog(mainPanel, "Registered");

                dispose();
            }
        });
    }
}
