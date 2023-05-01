import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class newDepartment extends JFrame{
    private JPanel mainPanel;
    private JTextField tfID;
    private JTextField tfName;
    private JComboBox<String> cbHead;
    private JButton addButton;
    private JButton cancelButton;

    public newDepartment() {
        setContentPane(mainPanel);
        setTitle("New Department");
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setSize(320,250);
//        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        int id=globals.createNewID(globals.departmentsIDs);
        tfID.setText(Integer.toString(id));
        globals.defaultMakeList(globals.headsLL,cbHead);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Head head;
                if(cbHead.getSelectedIndex()==0){
                    head=null;
                }
                else {
                    head=globals.headsLL.get(cbHead.getSelectedIndex()-1);
                }
                String name=tfName.getText();
                Department department=new Department(id,name,head);
                globals.departments.add(department);
                JOptionPane.showMessageDialog(mainPanel, "Department registered");
                dispose();
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
}
