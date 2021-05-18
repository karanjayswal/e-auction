package com.example.e_auctionfinal.Authentication;

public class User {
    String mail,userName,password;

    public User(String mail, String userName, String password) {

        this.mail = mail;
        this.userName = userName;
        this.password = password;

    }
    public User()
    {}



    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}

