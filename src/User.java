public class User {
    private int ID;
    private String userName;
    private String password;
    private String email;
    private String phoneNumber;

    public User() {}
    public User(int ID, String uN, String pW, String eM, String pN) {
        this.ID = ID;
        this.userName = uN;
        this.password = pW;
        this.email = eM;
        this.phoneNumber = pN;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getEmail() {
        return email;
    }
    public int getID() {
        return ID;
    }
    public String getPassword() {
        return password;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public String getUserName() {
        return userName;
    }

    @Override
    public String toString() {
        return "Person" +
                "\n, ID=" + ID +
                "\n, userName=" + userName +
                "\n, password=" + password +
                "\n, email=" + email +
                "\n, phoneNumber=" + phoneNumber +
                '\n';
    }
}