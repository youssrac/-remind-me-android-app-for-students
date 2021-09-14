package com.example.pavilion.remind2;

/**
 * Created by PAVILION on 17/05/2018.
 */
public class studentinfo {
    String phone;
    String fname;
    String pass;
    String n;
   double rating;
    String group;
    public studentinfo(){

    }
    public studentinfo(String n, String phone, String fname, String pass,String group, double rating){
        this.fname=fname;
        this.phone=phone;
        this.pass=pass;
        this.n=n;
        this.group=group;
        this.rating=rating;

    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void setN(String n) {
        this.n = n;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getGroup() {
        return group;
    }

    public String getPhone() {
        return phone;
    }

    public String getFname() {
        return fname;
    }

    public double getRating() {
        return rating;
    }

    public String getPass() {
        return pass;
    }

    public String getN() {
        return n;
    }
}
