package models;

public abstract class User {
    private String name;
    private String email;
    private String phonenumber;
    private String password;

    public User(String name, String email, String phonenumber, String password) {
        this.name = name;
        this.email = email;
        this.phonenumber = phonenumber;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setphonenumber(String phoneNumber) {
        this.phonenumber = phoneNumber;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
