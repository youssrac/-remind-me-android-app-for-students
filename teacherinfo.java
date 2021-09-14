package com.example.pavilion.remind2;

/**
 * Created by PAVILION on 19/05/2018.
 */

public class teacherinfo {

        String name,sirname,phone,code,idd;
        double rate;
        public teacherinfo(){

        }

        public teacherinfo(String name, String sirname, String phone,String code,String idd, double rate) {
            this.name = name;
            this.sirname = sirname;
            this.phone = phone;
           this.code=code;
            this.idd=idd;
            this.rate=rate;
        }
    public String getIdd() {
        return idd;
    }

     public void setIdd(String idd) {
        this.idd = idd;
    }
        public double getRate() {
            return rate;
        }

        public void setRate(double rate) {
            this.rate = rate;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSirname() {
            return sirname;
        }

        public void setSirname(String sirname) {
            this.sirname = sirname;
        }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }


    }

