import java.time.LocalDateTime;

public class Attendance {
    private int ID,userID;
    private boolean attendanceStatus;
    private LocalDateTime dateTime;
    Attendance(){}
    Attendance(int id,int userId,boolean status,LocalDateTime dateTime1){
        this.ID=id;
        this.userID=userId;
        this.attendanceStatus=status;
        this.dateTime=dateTime1;
    }
    public void setID(int ID) {
        this.ID = ID;
    }
    public void setUserID(int userID) {
        this.userID = userID;
    }
    public void setAttendanceStatus(boolean attendanceStatus) {
        this.attendanceStatus = attendanceStatus;
    }
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
    public int getID() {
        return ID;
    }
    public int getUserID() {
        return userID;
    }
    public boolean isAttendanceStatus() {
        return attendanceStatus;
    }
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    @Override
    public String toString() {
        return "Attendance" +
                "\n, ID=" + ID +
                "\n, userID=" + userID +
                "\n, attendanceStatus=" + attendanceStatus +
                "\n, dateTime=" + dateTime +
                '\n';
    }

    public String customToString() {
        return "\nID= " + ID ;
    }
}

