package com.example.beerapp1;

public class Hop {

    String name;
    double alphaAcid;
    double grams;
    double minutes;

    public Hop(String name, double alphaAcid, double grams,double minutes){
        this.name = name;
        this.alphaAcid = alphaAcid;
        this.grams = grams;
        this.minutes = minutes;
    }

    //getters
    public String getName()
    {
        return name;
    }
    public double getAlphaAcid()
    {
        return alphaAcid;
    }
    public double getGrams()
    {
        return grams;
    }
    public double getMinutes()
    {
        return minutes;
    }
}
