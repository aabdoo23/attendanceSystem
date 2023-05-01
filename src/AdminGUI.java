import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.Arrays;

public class AdminGUI extends JFrame {
    private JPanel mainPanel;
    private JTabbedPane tabbedPane1;
    private JPanel userPanel;
    private JPanel depoPanel;
    private JComboBox<String> cb;
    private JButton selectButton;
    private JList<String> usersList;
    private JList<String> departmentList;
    private JButton newUserButton;
    private JButton rejectButton;
    private JButton closeButton;
    private JButton refreshButton;
    private JButton approveButton;
    private JButton newDepartmentButton;
    private JList<String> attendanceReports;
    private JButton selectUserButton;
    private JButton dropButton;
    private JButton editButton;
    private JList<String> headsList;
    private JList<String> headsAttendanceList;
    private JButton editHeadButton;
    private JButton rejectHead;
    private JButton approveHead;
    private JButton selectHead;
    private JButton dropHeadAttendance;
    private JButton rejectHeadLeave;
    private JButton approveHeadLeave;
    private JList<String> headsLeavesList;
    private JButton printAttendanceReportButton;
    private JButton printAttendanceReportButton1;
    private JButton printLeavesReportButton1;
    Employee selectedEmployee = null;
    Head selectedHead = null;

    void updateUsersDisplay() {
        approveButton.setEnabled(usersList.getSelectedIndex() != -1 && usersList.getModel().getSize() > 0);
        rejectButton.setEnabled(usersList.getSelectedIndex() != -1 && usersList.getModel().getSize() > 0);
        editButton.setEnabled(usersList.getSelectedIndex() != -1 && usersList.getModel().getSize() > 0);
        selectUserButton.setEnabled(usersList.getSelectedIndex() != -1 && usersList.getModel().getSize() > 0);
        if (cb.getSelectedIndex() == 0) {
            globals.makeList(globals.pendingEmployeesLL, usersList);
        } else if (cb.getSelectedIndex() == 1) {
            globals.makeList(globals.employeesLL, usersList);
        }
        if (selectedEmployee != null)
            globals.makeList(selectedEmployee.getAttendances(), attendanceReports);

        globals.makeList(globals.pendingLeavesLL, headsLeavesList);

    }
    void updateHeadsDisplay() {
        if (headsList.getSelectedIndex() != -1 && headsList.getModel().getSize() > 0) {
            approveHead.setEnabled(true);
            rejectHead.setEnabled(true);
            editHeadButton.setEnabled(true);
            selectHead.setEnabled(true);
        }
        globals.makeList(globals.headsLL, headsList);

        if (selectedHead != null)
            globals.makeList(selectedHead.getAttendances(), headsAttendanceList);
        globals.makeList(globals.pendingLeavesLL, headsLeavesList);

    }
    void updateDepartments() {
        globals.makeList(globals.departments, departmentList);
    }

    void updateAll() {
        updateHeadsDisplay();
        updateDepartments();
        updateUsersDisplay();
    }

    AdminGUI() {
        setContentPane(mainPanel);
        setTitle("Admin panel");
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setSize(800, 600);
        setExtendedState(MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setVisible(true);
        updateAll();
        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateAll();
                editButton.setEnabled(cb.getSelectedIndex() == 1);
                approveButton.setEnabled(cb.getSelectedIndex() == 0);
                selectUserButton.setEnabled(cb.getSelectedIndex() == 1);
            }
        });
        newUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new newUser(true);
                updateAll();
            }
        });
        approveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Employee employee = globals.pendingEmployeesLL.get(usersList.getSelectedIndex());
                globals.pendingEmployeesLL.remove(employee);
                globals.employeesLL.add(employee);
                for (Department department : globals.departments) {
                    if (department == employee.getDepartment() && department != null) {
                        department.addToEmployees(employee);
                        return;
                    }
                }
                JOptionPane.showMessageDialog(mainPanel, "User Approved");

                updateAll();
            }
        });
        rejectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedInd = cb.getSelectedIndex();
                if (selectedInd == 0) {
                    if (usersList.getSelectedIndex() != -1 && usersList.getModel().getSize() > 0) {
                        Employee employee = globals.pendingEmployeesLL.get(usersList.getSelectedIndex());
                        globals.pendingEmployeesLL.remove(employee);
                    }
                } else if (selectedInd == 1) {
                    if (usersList.getSelectedIndex() != -1 && usersList.getModel().getSize() > 0) {

                        Employee employee = globals.employeesLL.get(usersList.getSelectedIndex());
                        globals.employeesLL.remove(employee);
                        for (Department department : globals.departments) {
                            if (department == employee.getDepartment() && employee.getDepartment() != null) {
                                department.removeFromEmployees(employee);
                                return;
                            }
                        }
                    }
                } else {
                    if (usersList.getSelectedIndex() != -1 && usersList.getModel().getSize() > 0) {
                        Admin admin = globals.adminsLL.get(usersList.getSelectedIndex());
                        globals.adminsLL.remove(admin);

                    }
                }
                JOptionPane.showMessageDialog(mainPanel, "User Rejected/Removed");
            }
        });
        rejectHead.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                if (headsList.getSelectedIndex() != -1 && headsList.getModel().getSize() > 0) {
                    Head head = globals.headsLL.get(headsList.getSelectedIndex());
                    globals.headsLL.remove(head);
                    for (Department department : globals.departments) {
                        if (department == head.getDepartment() && head.getDepartment() != null) {
                            department.setHead(null);
                            return;
                        }
                    }
                }

                JOptionPane.showMessageDialog(mainPanel, "Head Rejected/Removed");

            }
        });
        newDepartmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new newDepartment();
            }
        });
        selectUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (usersList.getSelectedIndex() != -1 && usersList.getModel().getSize() > 0)
                    selectedEmployee = globals.employeesLL.get(usersList.getSelectedIndex());
                updateAll();
            }
        });
        selectHead.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (headsList.getSelectedIndex() != -1 && headsList.getModel().getSize() > 0)
                    selectedHead = globals.headsLL.get(headsList.getSelectedIndex());
                updateAll();
            }
        });
        dropButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedEmployee != null)
                    selectedEmployee.removeFromAttendances(selectedEmployee.getAttendances().get(attendanceReports.getSelectedIndex()));
                updateAll();
                JOptionPane.showMessageDialog(mainPanel, "Attendance record dropped.");

            }
        });
        dropHeadAttendance.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedHead != null)
                    selectedHead.removeFromAttendances(selectedHead.getAttendances().get(headsAttendanceList.getSelectedIndex()));
                updateAll();
                JOptionPane.showMessageDialog(mainPanel, "Attendance record dropped.");
            }
        });
        rejectHeadLeave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Head thisHead = null;
                Employee thisEmployee = null;

                globals.pendingLeavesLL.get(headsLeavesList.getSelectedIndex()).setAccepted(false);
                for (Head head : globals.headsLL) {
                    if (head.getID() == globals.pendingLeavesLL.get(headsLeavesList.getSelectedIndex()).getUserID()) {
                        thisHead = head;
                        break;
                    }
                }
                if (thisHead == null) {
                    for (Employee head : globals.employeesLL) {
                        if (head.getID() == globals.pendingLeavesLL.get(headsLeavesList.getSelectedIndex()).getUserID()) {
                            thisEmployee = head;
                            break;
                        }
                    }
                }
                if (thisHead != null)
                    thisHead.addToLeaves(globals.pendingLeavesLL.get(headsLeavesList.getSelectedIndex()));
                else if (thisEmployee != null)
                    thisEmployee.addToLeaves(globals.pendingLeavesLL.get(headsLeavesList.getSelectedIndex()));
                globals.pendingLeavesLL.remove(headsLeavesList.getSelectedIndex());

                JOptionPane.showMessageDialog(mainPanel, "Leave rejected.");

            }
        });
        approveHeadLeave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    globals.pendingLeavesLL.get(headsLeavesList.getSelectedIndex()).setAccepted(true);
                    for (Employee employee:globals.employeesLL){
                        if(employee.getID()==globals.pendingLeavesLL.get(headsLeavesList.getSelectedIndex()).getUserID()){
                            employee.addToLeaves(globals.pendingLeavesLL.get(headsLeavesList.getSelectedIndex()));
                        }
                    }
                    for (Head head : globals.headsLL) {
                        if (head.getID() == globals.pendingLeavesLL.get(headsLeavesList.getSelectedIndex()).getUserID()) {
                            head.addToLeaves(globals.pendingLeavesLL.get(headsLeavesList.getSelectedIndex()));
                        }
                    }
                    globals.pendingLeavesLL.remove(headsLeavesList.getSelectedIndex());


                updateAll();
                JOptionPane.showMessageDialog(mainPanel, "Leave accepted.");


            }
        });
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (usersList.getSelectedIndex() != -1 && usersList.getModel().getSize() > 0) {
                    Employee employee = globals.employeesLL.get(usersList.getSelectedIndex());
                    new newUser(employee);
                    updateAll();
                } else
                    JOptionPane.showMessageDialog(mainPanel, "Please select a user.", "ERROR", JOptionPane.ERROR_MESSAGE);

            }
        });
        editHeadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (headsList.getSelectedIndex() != -1 && headsList.getModel().getSize() > 0) {
                    Head head = globals.headsLL.get(headsList.getSelectedIndex());
                    new newUser(head);
                    updateAll();
                } else
                    JOptionPane.showMessageDialog(mainPanel, "Please select a user.", "ERROR", JOptionPane.ERROR_MESSAGE);

            }
        });
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateAll();
            }
        });
        printAttendanceReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedHead != null) {
                    PrinterJob job = PrinterJob.getPrinterJob();
                    // Set the printable object
                    PrintLinkedList printable = new PrintLinkedList(selectedHead,globals.makeList(selectedHead.getAttendances()));
                    job.setPrintable(printable);

                    // Show the print dialog and print the document
                    if (job.printDialog()) {
                        try {
                            job.print();
                            JOptionPane.showMessageDialog(mainPanel, "Print successful.");
                            return;
                        } catch (PrinterException x) {
                            JOptionPane.showMessageDialog(mainPanel, Arrays.toString(x.getStackTrace()), "ERROR", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }
                }
                JOptionPane.showMessageDialog(mainPanel, "Please select a user.", "ERROR", JOptionPane.ERROR_MESSAGE);

            }
        });
        printAttendanceReportButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedEmployee != null) {
                    PrinterJob job = PrinterJob.getPrinterJob();
                    // Set the printable object
                    PrintLinkedList printable = new PrintLinkedList(selectedEmployee,globals.makeList(selectedEmployee.getAttendances()));
                    job.setPrintable(printable);

                    // Show the print dialog and print the document
                    if (job.printDialog()) {
                        try {
                            job.print();
                            JOptionPane.showMessageDialog(mainPanel, "Print successful.");
                            return;
                        } catch (PrinterException x) {
                            JOptionPane.showMessageDialog(mainPanel, Arrays.toString(x.getStackTrace()), "ERROR", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }
                }
                JOptionPane.showMessageDialog(mainPanel, "Please select a user.", "ERROR", JOptionPane.ERROR_MESSAGE);

            }
        });
        printLeavesReportButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedHead != null) {
                    PrinterJob job = PrinterJob.getPrinterJob();
                    PrintLinkedList printable = new PrintLinkedList(selectedHead,globals.makeList(selectedHead.getLeaves()));
                    job.setPrintable(printable);

                    if (job.printDialog()) {
                        try {
                            job.print();
                            JOptionPane.showMessageDialog(mainPanel, "Print successful.");
                            return;
                        } catch (PrinterException x) {
                            JOptionPane.showMessageDialog(mainPanel, Arrays.toString(x.getStackTrace()), "ERROR", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }
                }
                JOptionPane.showMessageDialog(mainPanel, "Please select a user.", "ERROR", JOptionPane.ERROR_MESSAGE);

            }
        });
    }
}

