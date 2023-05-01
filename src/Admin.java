public class Admin extends User {
    Admin() {
        super();
    }
    Admin(int ID, String uN, String pW, String eM, String pN) {
        super(ID,uN, pW, eM, pN);
    }

    @Override
    public String toString() {
        return "Admin" +
                "\n, ID=" + getID() +
                "\n, userName=" + getUserName() +
                "\n, password=" + getPassword() +
                "\n, email=" + getEmail() +
                "\n, phoneNumber=" + getPhoneNumber() +
                '\n';
    }
}