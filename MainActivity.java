/*
 		 Title: MainActivity.java
	    Author: Vitor Antunes
	      Date: Jul 21, 2020 2:51:06 PM
   Description: Main class to Create a GUI to calculate Beer Fermentation Variables
 */

package com.example.beerapp1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //Class Variables
    ArrayList<Recipe> recipeList;// list of recipes
    int aIndex = 0; // Current array index position

    //initializes activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //load data from saved recipes
        loadData();
        //load beer styles on the table
        tableLoad("British ales","1.5 - 2.0");
        tableLoad("Porter, Stout","1.7 - 2.3");
        tableLoad("Belgian ales","1.9 - 2.4");
        tableLoad("American ales","2.2 - 2.7");
        tableLoad("European lagers","2.2 - 2.7");
        tableLoad("Belgian Lambic","2.4 - 2.8");
        tableLoad("American wheat","2.7 - 3.3");
        tableLoad("German wheat","3.3 - 4.5");
    }

    /*Method Name: OnClick
     *Purpose: ActionListener for all buttons
     *Accepts: View
     *Returns: Void
     */
    public void OnClick(View view)
    {
        //switch statement to select correct action base on the button Id
        switch(view.getId()){
            case R.id.Tab1://goes to MainActivity2 Activity
                recipeTab();
                break;
            case R.id.Tab3://goes to MainActivity3 Activity
                yeastTab();;
                break;
            case R.id.buttonCalculate:// calculates the ABV and Attenuation
                calc();
                break;
            case R.id.buttonCalculateCarb:// Calculates the primming sugar and CO2 pressure
                calcCarb();
                break;
            default:
                break;
        }
    }
    /*Method Name: recipeTab
     *Purpose: goes to MainActivity2 Activity
     *Accepts:
     *Returns: Void
     */
    public void recipeTab() {
        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
    }
    /*Method Name: yeastTab
     *Purpose: goes to MainActivity3 Activity
     *Accepts:
     *Returns: Void
     */
    public void yeastTab() {
        Intent intent = new Intent(this, MainActivity3.class);
        startActivity(intent);
    }

    /*Method Name: calcCarb
     *Purpose: calculates the primming sugar and CO2 pressure
     *Accepts:
     *Returns: Void
     */
    public void calcCarb () {
        //Method Variables
        double cBeer;//CO2 already disolved in the beer
        double pHead = 0; //only useful for beer fermented under pressure. Leaving at 0 for current version of the program
        double tBeer ;// temperature of the beer
        double finalCBeer; //Target Carbonation
        double tableSugar; // grams of sugar to be added
        double vBeer; // total batch volume

        //get Text Containers
        EditText eTxtTemp = findViewById(R.id.editTextNumberFermTemp);
        EditText eTxtCO2 = findViewById(R.id.editTextNumberCO2);
        EditText eTxtVolume = findViewById(R.id.editTextBatchSize);
        TextView txtVCarbo = findViewById(R.id.textViewCarbo);

        //Get user input values
        tBeer = Double.parseDouble(String.valueOf(eTxtTemp.getText()));
        finalCBeer = Double.parseDouble(String.valueOf(eTxtCO2.getText()));
        vBeer = Double.parseDouble(String.valueOf(eTxtVolume.getText()));

        //find the current CO2 in the Beer
        cBeer = (pHead+1.013)*(Math.pow(2.71828182845904 , (-10.73797+(2617.25/(tBeer+273.15)))))*10; // in g/l (CO2/Beer)
        cBeer /= 2;// in volumes of CO2.  1g/l = 0.5 volumes
        //Calculates the primming sugar
        tableSugar = (vBeer*(finalCBeer-cBeer))*4 ; // * by 4 instead of 2 because all is in volumes of CO2 not g/l
        //Calculates the necessary CO2 pressure
        pHead =  ((finalCBeer*2)/((Math.pow(2.71828182845904 , (-10.73797+(2617.25/(tBeer+273.15)))))*10)) - 1.013;
        pHead *= 14.5038;
        //Display Results
        txtVCarbo.setText("Sugar: " + ((int)((tableSugar * 10) + .5) / 10.0) +"g (" + ((int)(((tableSugar/vBeer) * 10) + .5) / 10.0) +"g/l) or " + (int)(pHead + .5) + " Psi");

    }
    /*Method Name: calc
     *Purpose: calculates the ABV and Attenuation
     *Accepts:
     *Returns: Void
     */
    public void calc () {
        //get Text Containers
        EditText eTxtOG = findViewById(R.id.editTextNumberOG);
        EditText eTxtFG = findViewById(R.id.editTextNumberFG);
        TextView txtVABV = findViewById(R.id.textViewABV);
        TextView txtViewAtten = findViewById(R.id.textViewAtten);

        //Method Variables
        double og;//Original Gravity
        double fg;//Final Gravity
        double result;//resulting ABV
        double brixOg;
        og = Double.parseDouble(String.valueOf(eTxtOG.getText()));
        fg = Double.parseDouble(String.valueOf(eTxtFG.getText()));

        if (og>=2)//if og is in Brix
        {
            brixOg = og;//Save the brix value
            //Convert Brix value to SG
            og = 1.000019 + (0.003865613*og) + (0.00001296425*og) + (0.00000005701128*og);
            og = (int)((og*1000)+0.5)/1000.0;
            //display converted value
            eTxtOG.setText(Double.toString(og));

        }
        //if already in SG, convert value to Brix and save it
        else brixOg = (182.4601 * Math.pow(og, 3)) -(775.6821 * Math.pow(og,2)) +(1262.7794 * og) -669.5622;;

        if (fg>=2)//if fg is in Brix
        {
            //Convert Brix value to SG with alcohol correction
            fg =  1.0000 - 0.00085683*brixOg + 0.0034941*fg;
            fg = (int)((fg*1000)+0.5)/1000.0;
            //display converted value
            eTxtFG.setText(Double.toString(fg));

        }
        //calculates the ABV
        result = (og-fg)*131.25;
        //Round the value
        result = (int)((result*10)+0.5)/10.0;
        //Calculates the attenuation
        double attenuation = ((og-1) - (fg-1))/(og-1);
        //Round the value
        attenuation = (int)((attenuation*1000)+0.5)/10.0;
        //Display results
        txtVABV.setText("ABV: " + result  + "%");
        txtViewAtten.setText("Attenuation: " + attenuation + "%");
    }
    /*Method Name: loadData
     *Purpose: load data from saved recipes
     *Accepts:
     *Returns: Void
     */
    private void loadData(){
        //Uses SharedPreferences to get a string value pair, and uses it a json String to load a Array list of Recipe Objects
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("recipe list",null);
        aIndex = sharedPreferences.getInt("array index", 0);
        Type type = new TypeToken<ArrayList<Recipe>>() {}.getType();
        recipeList = gson.fromJson(json, type);
        if (recipeList == null)// if the list doesn exist
        {
            recipeList = new ArrayList<Recipe>();
        }
        else
        {
            if(aIndex> recipeList.size()-1) aIndex=0;//if out of bounds, reset the index
            //Load values from the recipe to the Text fields
            EditText eTxtOG = findViewById(R.id.editTextNumberOG);
            EditText eTxtFG = findViewById(R.id.editTextNumberFG);
            eTxtOG.setText(String.valueOf(recipeList.get(aIndex).getoG()));
            eTxtFG.setText(String.valueOf(recipeList.get(aIndex).getfG()));
        }
    }
    /*Method Name: tableLoad
     *Purpose: loads a pair of Strings to the Table of Styles
     *Accepts: String, String
     *Returns: Void
     */
    public void  tableLoad(String style, String volumes){
        //Method Variables
        TextView tv1, tv2;
        TableLayout tStyles;
        TableRow tr;
        //Gets table by Id
        tStyles = (TableLayout)findViewById(R.id.tableStyles);
        tStyles.setColumnStretchable(0,true);
        tStyles.setColumnStretchable(1,true);
        //Creates table row
        tr = new TableRow(this);
        tv1 = new TextView(this);
        tv1.setText(style);
        tv1.setGravity(Gravity.CENTER);
        tv1.setBackgroundColor(Color.WHITE);
        tv2 = new TextView(this);
        tv2.setText(String.valueOf(volumes));
        tv2.setGravity(Gravity.CENTER);
        tv2.setBackgroundColor(Color.WHITE);
        tr.addView(tv1);
        tr.addView(tv2);
        //Add row to table
        tStyles.addView(tr);
    }
}