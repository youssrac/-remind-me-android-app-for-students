package com.example.pavilion.remind2;

/**
 * Created by PAVILION on 17/05/2018.
 */

public class Notifications extends notificationID {
    String phone,email,name,password,group,surname,code;
    public Notifications(){}
    public Notifications(String id,String email,String code,String name,String sirname,String group,String password){
        this.phone=id;
        this.email=email;
        this.name=name;
        this.surname=sirname;
        this.password=password;
        this.group=group;
        this.code=code;

    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public void setId(String id) {
        this.phone = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFullname(String fullname) {
        this.name = fullname;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getFullname() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getSirname() {
        return surname;
    }

    public void setSirname(String sirname) {
        this.surname = sirname;
    }
}
