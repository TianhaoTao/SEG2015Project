package com.example.tianhao.seg2105project.Login;

public class User {
    private String username;
    private String email;
    private String password;
    private String passwordComfirm;
    private String userType;

    public User() {
    }

    public User(String username, String email, String password, String passwordComfirm, String userType) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.passwordComfirm=passwordComfirm;
        this.userType = userType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordComfirm() {return passwordComfirm;}

    public void setpasswordComfirm(String passwordComfirm) {this.passwordComfirm=passwordComfirm;}

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
