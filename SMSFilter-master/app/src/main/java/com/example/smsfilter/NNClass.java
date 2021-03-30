package com.example.smsfilter;
import java.util.ArrayList;

public class NNClass {

    private ArrayList<String> numeri ;
    private ArrayList<String> nomi ;

    public NNClass( ArrayList<String> numeri, ArrayList<String> nomi ) {

        this.numeri = numeri ;
        this.nomi = nomi ;
    }

    public ArrayList<String> getNumeri() { return this.numeri ; }
    public ArrayList<String> getNomi() { return this.nomi ;}


}

