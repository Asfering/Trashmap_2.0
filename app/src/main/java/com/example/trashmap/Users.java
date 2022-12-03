package com.example.trashmap;

public class Users {
    private String name;
    private String password;

    public Users (String Name, String Password){
        this.name = Name;
        this.password = Password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
