package com.example.beerapp1;

public class Malt {

    String name;
    double kg;
    double lovibond;

    public Malt(String name, double kg, double lovibond){
        this.name = name;
        this.kg = kg;
        this.lovibond = lovibond;
    }

    //getters
    public String getName()
    {
        return name;
    }
    public double getKg()
    {
        return kg;
    }
    public double getLovibond()
    {
        return lovibond;
    }
}
