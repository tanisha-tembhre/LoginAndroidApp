package com.example.dell.myapplication;

/**
 * Created by DELL on 3/29/2018.
 */

public class UserProfile {

    public String a,b,c,d;

    //empty constructor for function overloading
    public UserProfile(){

    }

    //constructor
    public UserProfile(String a1, String b1, String c1, String d1){
        this.a=a1;
        this.b=b1;
        this.c=c1;
        this.d=d1;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }
}
