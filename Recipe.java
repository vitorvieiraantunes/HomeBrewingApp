package com.example.beerapp1;

import java.util.ArrayList;

public class Recipe {

    private String name;
    private String Style;
    private double oG;
    private double fG;
    private double preSG;
    private double IBU;
    private double ABV;
    private String Yeast;
    ArrayList<Malt> maltList;
    ArrayList<Hop> hopList;

    //Contructor
    public Recipe(String name, String Style, double oG, double fG, double preSG, double IBU, double ABV,  String Yeast,
                  ArrayList<Malt> maltList, ArrayList<Hop> hopList )
    {
        this.name=name;
        this.Style=Style;
        this.oG=oG;
        this.fG=fG;
        this.preSG=preSG;
        this.IBU=IBU;
        this.ABV=ABV;
        this.Yeast=Yeast;
        this.maltList = new ArrayList<Malt>();
        for(int maltIndex = 0; maltIndex < maltList.size(); maltIndex++)
        {
            Malt tempMalt = new Malt(maltList.get(maltIndex).getName(),
                    maltList.get(maltIndex).getKg(),
                    maltList.get(maltIndex).getLovibond());
            this.maltList.add(tempMalt);
        }
        //this.maltList = maltList;
        this.hopList = new ArrayList<Hop>();
        for(int hopIndex = 0; hopIndex < maltList.size(); hopIndex++)
        {
            Hop tempHop = new Hop(hopList.get(hopIndex).getName(),
                    hopList.get(hopIndex).getAlphaAcid(),
                    hopList.get(hopIndex).getGrams(),
                    hopList.get(hopIndex).getMinutes());
            this.hopList.add(tempHop);
        }
        //this.hopList = hopList;
    }


    public void setAll(String name, String Style,double oG, double fG,double preSG, double IBU,double ABV,
                       String Yeast, ArrayList<Malt> maltList, ArrayList<Hop> hopList)
    {
        this.name=name;
        this.Style=Style;
        this.oG=oG;
        this.fG=fG;
        this.preSG=preSG;
        this.IBU=IBU;
        this.ABV=ABV;
        this.Yeast=Yeast;
        this.maltList.clear();
        for(int maltIndex = 0; maltIndex < maltList.size(); maltIndex++)
        {
            Malt tempMalt = new Malt(maltList.get(maltIndex).getName(),
                    maltList.get(maltIndex).getKg(),
                    maltList.get(maltIndex).getLovibond());
            this.maltList.add(tempMalt);
        }
        //this.maltList = maltList;
        this.hopList.clear();
        for(int hopIndex = 0; hopIndex < maltList.size(); hopIndex++)
        {
            Hop tempHop = new Hop(hopList.get(hopIndex).getName(),
                    hopList.get(hopIndex).getAlphaAcid(),
                    hopList.get(hopIndex).getGrams(),
                    hopList.get(hopIndex).getMinutes());
            this.hopList.add(tempHop);
        }
        //this.hopList = hopList;
    }

    //getters
    public String getName()
    {
        return name;
    }
    public String getStyle()
    {
        return Style;
    }
    public double getoG()
    {
        return oG;
    }
    public double getfG()
    {
        return fG;
    }
    public double getPreSG()
    {
        return preSG;
    }
    public double getIBU()
    {
        return IBU;
    }
    public double getABV()
    {
        return ABV;
    }
    public String getYeast()
    {
        return Yeast;
    }
    public ArrayList<Malt> getMaltList(){
        return  maltList;
    }
    public ArrayList<Hop> getHopList(){
        return hopList;
    }

    //setters
    public void setName(String name)
    {
        this.name = name;
    }
    public void setStyle(String style)
    {
        Style = style;
    }
    public void setoG(double oG)
    {
        this.oG = oG;
    }
    public void setfG(double fG)
    {
        this.fG = fG;
    }
    public void setPreSG(double preSG)
    {
        this.preSG = preSG;
    }
    public void setIBU(double iBU)
    {
        IBU = iBU;
    }
    public void setABV(double aBV)
    {
        ABV = aBV;
    }
    public void setYeast(String yeast)
    {
        Yeast = yeast;
    }
}
