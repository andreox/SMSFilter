package com.example.smsfilter;

import org.junit.Test;

import static org.junit.Assert.*;

//Test di Unità dei Metodi Terminali dell'app.

public class ExampleUnitTest {
    @Test
    public void UsersGetCorrect() {

        String nome = "Alessio" ;
        String email = "alessio@gmail.com" ;
        String pwd = "password" ;

        User u = new User(nome,email,pwd) ;

        assertEquals(nome, u.getName()) ;
        assertEquals(email, u.getEmail()) ;
        assertEquals(pwd, u.getPwd()) ;

    }

    @Test
    public void UsersCheckCorrectEmail() {

        User u = new User("Alessio" , "alessio@gmail.com", "password") ;
        assertTrue(u.checkEmailIsValid()) ;

    }

    @Test
    public void UsersCheckIncorrectEmail() {

        User u = new User("Alessio", "wrong_email", "pswd") ;

        assertFalse(u.checkEmailIsValid()); ;

    }

    @Test
    public void RegisterCheckCorrectName() {

        RegisterActivity ra = new RegisterActivity() ;

        assertTrue(ra.checkName("Alessio")) ;

    }

    @Test
    public void RegisterCheckUncorrectName() {

        RegisterActivity ra = new RegisterActivity() ;
        assertFalse(ra.checkName("Sen6")) ;

    }

    @Test
    public void RegisterCheckCorrectPassword() {

        RegisterActivity ra = new RegisterActivity() ;
        assertTrue(ra.checkPassword("123456")) ;
    }

    @Test
    public void RegisterCheckUncorrectPassword() {

        RegisterActivity ra = new RegisterActivity() ;
        assertFalse(ra.checkPassword("1234")) ;

    }
}