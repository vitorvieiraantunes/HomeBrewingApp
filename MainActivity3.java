package com.example.beerapp1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity3 extends AppCompatActivity {

    double yeastCells = 0;
    ArrayList<Recipe> recipeList;
    int aIndex = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        loadData();
    }

    public void recipeTab() {
        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
    }

    public void FermentationTab() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void OnClick(View view){
        switch(view.getId()){
            case R.id.buttonCalculateCell:
                CalculateCells();
                break;
            case R.id.buttonCalculateYeast:
                CalculateCells();
                CalculateYeast();
                break;
            case R.id.Tab1:
                recipeTab();
                break;
            case R.id.Tab3:
                FermentationTab();;
                break;
            default:
                break;
        }
    }

    public  void CalculateCells(){
        EditText eTxtOG = findViewById(R.id.editTextNumberOG);
        EditText eTxtBatchSize = findViewById(R.id.editTextBatchSize);
        TextView txtViewCells = findViewById(R.id.textViewYeastCells);



        double og;
        double volume;
        og = Double.parseDouble(String.valueOf(eTxtOG.getText()));
        volume = Double.parseDouble(String.valueOf(eTxtBatchSize.getText()));
        if (og<2)
        {
            og = (182.4601 * Math.pow(og, 3)) -(775.6821 * Math.pow(og,2)) +(1262.7794 * og) -669.5622;
        }
        yeastCells = (0.75 * volume*1000 * og)/1000;
        txtViewCells.setText(String.valueOf((int)((yeastCells*10)+0.5)/10.0) + " Billion Cells");

    }

    public  void CalculateYeast(){
        EditText eTxtViability = findViewById(R.id.editTextViability);
        EditText eTxtViabilityDays = findViewById(R.id.editTextViabilityDays);
        TextView txtViewDryYeast = findViewById(R.id.textViewDryYeast);
        TextView txtViewLiquidYeast = findViewById(R.id.textViewLiquidYeast);
        TextView txtViewHarvestedYeast = findViewById(R.id.textViewHarvestedYeast);
        double dryYeast, liquidYeast, harvestedYeast, viability;
        if (eTxtViabilityDays.getText().equals(""))
        {
            viability =  Double.parseDouble(String.valueOf(eTxtViability.getText()));
            viability/=100;
            dryYeast = yeastCells/(20*viability);
            liquidYeast = yeastCells/(100*viability);
            harvestedYeast = yeastCells/(1.5*viability);
        }
        else
        {
            double days = Double.parseDouble(String.valueOf(eTxtViabilityDays.getText()));
            eTxtViability.setText("");
            dryYeast = yeastCells/(20*(90-(0.066667*days))/100);
            liquidYeast = yeastCells/(100*((97-(0.7*days))/100));
            harvestedYeast = yeastCells/(1.05*((92-(1.5*days))/100));
        }


        txtViewDryYeast.setText("Dry Yeast: " + String.valueOf((int)((dryYeast*10)+0.5)/10.0) + "g");
        txtViewLiquidYeast.setText("Liquid Yeast:" + String.valueOf((int)((liquidYeast*10)+0.5)/10.0) + " vials");
        txtViewHarvestedYeast.setText("Harvested Yeast:" +String.valueOf((int)((harvestedYeast*10)+0.5)/10.0) + "mL");




    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("recipe list", null);
        aIndex = sharedPreferences.getInt("array index", 0);
        Type type = new TypeToken<ArrayList<Recipe>>() {
        }.getType();
        recipeList = gson.fromJson(json, type);
        if (recipeList == null) {
            recipeList = new ArrayList<Recipe>();
        } else {
            if (aIndex > recipeList.size() - 1) aIndex = 0;
            EditText eTxtOG = findViewById(R.id.editTextNumberOG);
            eTxtOG.setText(String.valueOf(recipeList.get(aIndex).getoG()));


        }
    }
}