import java.time.LocalDate;

public class Leave {
    private int type,userID,ID;
    private LocalDate start,end;
    private String reason;
    private boolean accepted;
    Leave(){}
    Leave(int id,int uID,int t,LocalDate start,LocalDate end,String details){
        this.ID=id;
        this.userID=uID;
        this.type=t;
        this.start=start;
        this.end=end;
        this.reason=details;
    }
    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }
    public void setID(int ID) {
        this.ID = ID;
    }
    public void setType(int type) {
        this.type = type;
    }
    public void setUserID(int userID) {
        this.userID = userID;
    }
    public void setEnd(LocalDate end) {
        this.end = end;
    }
    public void setStart(LocalDate start) {
        this.start = start;
    }
    public void setReason(String reason) {
        this.reason = reason;
    }

    public boolean isAccepted() {
        return accepted;
    }
    public int getID() {
        return ID;
    }
    public int getType() {
        return type;
    }
    public int getUserID() {
        return userID;
    }
    public LocalDate getEnd() {
        return end;
    }
    public LocalDate getStart() {
        return start;
    }
    public String getReason() {
        return reason;
    }

    @Override
    public String toString() {
        return "Leave" +
                "\n, type=" + type +
                "\n, userID=" + userID +
                "\n, ID=" + ID +
                "\n, start=" + start +
                "\n, end=" + end +
                "\n, reason=" + reason +
                "\n, accepted=" + (accepted?"YES":"NO") +
                '\n';
    }
}
