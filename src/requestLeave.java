import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class requestLeave extends JFrame{
    private JPanel mainPanel;
    private JTextField tfID;
    private JTextField tfUserID;
    private JComboBox<String> cbLeave;
    private JTextField tfDetails;
    private JTextField tfStartTime;
    private JTextField tfEndTime;
    private JButton applyForLeaveButton;
    int id=globals.createNewID(globals.leaveIDs);
    requestLeave(Employee employee){
        setContentPane(mainPanel);
        setTitle("Apply for Leave");
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setSize(500,500);
//        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        tfID.setText(Integer.toString(id));
        tfUserID.setText(Integer.toString(employee.getID()));

        applyForLeaveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LocalDate startDate = null;
                LocalDate endDate=null;
                try {
                     startDate= LocalDate.parse(tfStartTime.getText(), DateTimeFormatter.ISO_LOCAL_DATE);
                     endDate = LocalDate.parse(tfEndTime.getText(), DateTimeFormatter.ISO_LOCAL_DATE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(mainPanel, "Invalid Date format","Error",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if(tfDetails.getText().isEmpty()){
                    JOptionPane.showMessageDialog(mainPanel, "Empty details section","Error",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                Leave leave=new Leave(id, employee.getID(), cbLeave.getSelectedIndex(),startDate,endDate,tfDetails.getText());
                globals.pendingLeavesLL.add(leave);
                JOptionPane.showMessageDialog(mainPanel, "Leave requested, waiting for Admin approval");
                dispose();
            }
        });
    }
    requestLeave(Head head){
        setContentPane(mainPanel);
        setTitle("Apply for Leave");
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setSize(500,500);
//        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        tfID.setText(Integer.toString(id));
        tfUserID.setText(Integer.toString(head.getID()));

        applyForLeaveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LocalDate startDate = null;
                LocalDate endDate=null;
                try {
                    startDate= LocalDate.parse(tfStartTime.getText(), DateTimeFormatter.ISO_LOCAL_DATE);
                    endDate = LocalDate.parse(tfEndTime.getText(), DateTimeFormatter.ISO_LOCAL_DATE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(mainPanel, "Invalid Date format","Error",JOptionPane.ERROR_MESSAGE);
                }
                if(tfDetails.getText().isEmpty()){
                    JOptionPane.showMessageDialog(mainPanel, "Empty details section","Error",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                Leave leave=new Leave(id, head.getID(), cbLeave.getSelectedIndex(),startDate,endDate,tfDetails.getText());
                globals.pendingLeavesLL.add(leave);
                JOptionPane.showMessageDialog(mainPanel, "Leave requested, waiting for Admin approval");
                dispose();

            }
        });
    }

}
