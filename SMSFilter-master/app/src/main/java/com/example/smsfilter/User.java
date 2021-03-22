package com.example.smsfilter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User {

    public String name ;
    public String email ;
    public String pwd ;
    private static final String regex = "^(.+)@(.+)$";

    User ( String name, String email, String pwd ) {

        this.name = name ;
        this.email = email ;
        this.pwd = pwd ;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public boolean checkEmailIsValid() {

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        if ( matcher.matches() ) return true ;

        return false ;
    }
}
