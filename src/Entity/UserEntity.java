package Entity;

public class UserEntity {
    private int userID;
    private  String userName;
    private  String userEmail;
    private  String userPassword;
    private  String userFullName;
    private int isAdmin;

    public UserEntity(int userID, String userName, String userEmail, String userPassword, String userFullName, int isAdmin) {
        this.userID = userID;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userFullName = userFullName;
        this.isAdmin = isAdmin;
    }

    public UserEntity() {
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public int getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(int isAdmin) {
        this.isAdmin = isAdmin;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "userID=" + userID +
                ", userName='" + userName + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userFullName='" + userFullName + '\'' +
                ", isAdmin=" + isAdmin +
                '}';
    }
}
