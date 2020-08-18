package com.example.beerapp1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {

    //Class Variables
    String name = "";//recipe name
    String Style = "";
    double oG =0;
    double fG =0;
    double preSG=0;//pre boil SG
    double IBU=0;
    double ABV=0;
    String malt= "";
    double kg=0;
    double Lovibond =0;//Color
    String hop = "";
    double alphaAcid=0;
    double grams=0;
    double minutes=0;
    String Yeast= "";
    ArrayList<Recipe> recipeList;// list of recipes
    int aIndex = 0; // Current recipe array index position
    ArrayList<Malt> tempMaltList; // list to hold the malts of the recipe
    ArrayList<Hop> tempHopList; // list to hold the hops of the recipe

    //initializes activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        //initializes the malt and hop list
        tempMaltList = new ArrayList<Malt>();
        tempHopList = new ArrayList<Hop>();
        //Load data from saved recipes
        loadData();
        //If the recipe list is not empty, load the current recipe to the GUI
        if(recipeList.size()>0) {putData();}



    }

    /*Method Name: OnClick
     *Purpose: ActionListener for all buttons
     *Accepts: View
     *Returns: Void
     */
    public void OnClick(View view) {
        //switch statement to select correct action base on the button Id
        switch (view.getId()) {
            case R.id.Tab1://goes to MainActivity Activity
                fermentationTab();
                break;
            case R.id.Tab3://goes to MainActivity3 Activity
                yeastTab();
                break;
            case R.id.buttonRecSave:// Add recipe to the list and save the list in device storage
                saveBtn();
                break;
            case R.id.buttonRecClearGrain://Remove all items from malt list
                removeMalts();
                break;
            case R.id.buttonRecClearHop:// remove all items from hop list
                removeHops();
                break;
            case R.id.buttonRecAddGrain://// add item the malt list
                addMaltBtn();
                break;
            case R.id.buttonRecAddHop:// add item to the hop list
                addHopBtn();
                break;
            case R.id.buttonNew:// Clear all fields in the  GUI
                newRec();
                break;
            case R.id.buttonDelete://Remove recipe from the list
                deleteRec();
                break;
            case R.id.buttonNext:// Load the next recipe on the list on to the GUI
                nextRec();
                break;
            case R.id.buttonPrev:// Load the previous recipe on the list on to the GUI
                prevRec();
                break;
            default:
                break;
        }
    }

    /*Method Name: fermentationTab
     *Purpose: goes to MainActivity Activity
     *Accepts:
     *Returns: Void
     */
    public void fermentationTab() {
        Intent intent = new Intent(this, MainActivity.class);
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

    /*Method Name: putData
     *Purpose: Load data from the current recipe to all fields of the GUI
     *Accepts:
     *Returns: Void
     */
    public void putData(){
        //find text fields
        EditText eTxtname= findViewById(R.id.edTxtRecName);
        EditText eTxtStyle= findViewById(R.id.edTxtRecStyle);
        EditText eTxtoG= findViewById(R.id.edTxtNumbRecOG);
        EditText eTxtfG= findViewById(R.id.edTxtNumbRecFG);
        EditText eTxtpreSG= findViewById(R.id.edTxtNumbRecPBSG);
        EditText eTxtIBU= findViewById(R.id.edTxtNumbRecIBU);
        EditText eTxtABV= findViewById(R.id.edTxtNumbRecABV);
        EditText eTxtYeast= findViewById(R.id.edTxtRecYeast);

        //load values on to the text fields
        eTxtname.setText(recipeList.get(aIndex).getName());
        eTxtStyle.setText(recipeList.get(aIndex).getStyle());
        eTxtoG.setText(String.valueOf(recipeList.get(aIndex).getoG()));
        eTxtfG.setText(String.valueOf(recipeList.get(aIndex).getfG()));
        eTxtpreSG.setText(String.valueOf(recipeList.get(aIndex).getPreSG()));
        eTxtIBU.setText(String.valueOf(recipeList.get(aIndex).getIBU()));
        eTxtABV.setText(String.valueOf(recipeList.get(aIndex).getABV()));
        eTxtYeast.setText(String.valueOf(recipeList.get(aIndex).getYeast()));
        //Remove all items from the GUI tables
        removeHops();
        removeMalts();
        //loops the recipes's malt list
        for(int maltIndex = 0; maltIndex < recipeList.get(aIndex).getMaltList().size(); maltIndex++)
        {
            //creates malt object using Malt List from the  recipe
            Malt tempMalt = new Malt(recipeList.get(aIndex).getMaltList().get(maltIndex).getName(),
                                     recipeList.get(aIndex).getMaltList().get(maltIndex).getKg(),
                                     recipeList.get(aIndex).getMaltList().get(maltIndex).getLovibond());
            //add malt object to the list
            tempMaltList.add(tempMalt);
        }
        //loops the recipe's hop list
        for(int hopIndex = 0; hopIndex < recipeList.get(aIndex).getHopList().size(); hopIndex++)
        {
            //creates malt object using Malt List from the  recipe
            Hop tempHop = new Hop(recipeList.get(aIndex).getHopList().get(hopIndex).getName(),
                                  recipeList.get(aIndex).getHopList().get(hopIndex).getAlphaAcid(),
                                  recipeList.get(aIndex).getHopList().get(hopIndex).getGrams(),
                                  recipeList.get(aIndex).getHopList().get(hopIndex).getMinutes());
            //add malt object to the list
            tempHopList.add(tempHop);
        }
        // loops the malt list and add items to the table on the GUI
        for (int maltIndex = 0; maltIndex < tempMaltList.size(); maltIndex++)
        {
            malt = tempMaltList.get(maltIndex).getName();
            kg = tempMaltList.get(maltIndex).getKg();
            Lovibond = tempMaltList.get(maltIndex).getLovibond();
            addMalt();
        }
        // loops the hop list and add items to the table on the GUI
        for(int hopIndex = 0; hopIndex < tempHopList.size(); hopIndex++)
        {
            hop = tempHopList.get(hopIndex).getName();
            alphaAcid = tempHopList.get(hopIndex).getAlphaAcid();
            grams = tempHopList.get(hopIndex).getGrams();
            minutes = tempHopList.get(hopIndex).getMinutes();
            addHop();
        }
    }

    /*Method Name: saveData
     *Purpose: Save the current list of recipes and the current index to internal storage
     *Accepts:
     *Returns: Void
     */
    private void saveData(){
        //Itinitalizes shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //Initializes Gson object
        Gson gson = new Gson();
        //Saves the Array List in a String object usint GsontoJson
        String json = gson.toJson(recipeList);
        //Saves the recepy in a String value pair
        editor.putString("recipe list", json);
        editor.putInt("array index", aIndex);
        editor.apply();

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
        //if nothing is save, creates a new list
        if (recipeList == null) recipeList = new ArrayList<Recipe>();
        //if Index is out of bounds
        if(aIndex> recipeList.size()-1) aIndex=0;



    }

    /*Method Name: turnTextToNumber
     *Purpose: Extracts String number from Text field and parse it a double
     *Accepts: EditText
     *Returns: double
     */
    public double turnTextToNumber(EditText editText){
        //if there no number in the text field returns 0
        if (String.valueOf(editText.getText()).equals(""))
        {
            return 0;
        }
        else
        {
            return Double.parseDouble(String.valueOf(editText.getText()));
        }

    }

    /*Method Name: saveBtn
     *Purpose: Save the current recipe to the list and calls the SaveData method
     *Accepts:
     *Returns: Void
     */
    public void saveBtn () {

        //find fields
        EditText eTxtname= findViewById(R.id.edTxtRecName);
        EditText eTxtStyle= findViewById(R.id.edTxtRecStyle);
        EditText eTxtoG= findViewById(R.id.edTxtNumbRecOG);
        EditText eTxtfG= findViewById(R.id.edTxtNumbRecFG);
        EditText eTxtpreSG= findViewById(R.id.edTxtNumbRecPBSG);
        EditText eTxtIBU= findViewById(R.id.edTxtNumbRecIBU);
        EditText eTxtABV= findViewById(R.id.edTxtNumbRecABV);
        EditText eTxtMalt= findViewById(R.id.edTxtRecgrain);
        EditText eTxtkg= findViewById(R.id.edTxtNumbRecGrainKg);
        EditText eTxtLovibond= findViewById(R.id.edTxtNumbRecGrainColor);
        EditText eTxtHop= findViewById(R.id.edTxtRecHop);
        EditText eTxtalphaAcid= findViewById(R.id.edTxtNumbRecHopAA);
        EditText eTxtgrams= findViewById(R.id.edTxtNumbRecHopG);
        EditText eTxtminutes= findViewById(R.id.edTxtNumbRecHopMin);
        EditText eTxtYeast= findViewById(R.id.edTxtRecYeast);

        //try-catch in case of bad input
        try {
            //get values from user
            name=String.valueOf(eTxtname.getText());
            Style=String.valueOf(eTxtStyle.getText());
            oG = turnTextToNumber(eTxtoG);
            fG = turnTextToNumber(eTxtfG);
            preSG=turnTextToNumber(eTxtpreSG);
            IBU=turnTextToNumber(eTxtIBU);
            ABV=turnTextToNumber(eTxtABV);
            malt=String.valueOf(eTxtMalt.getText());
            kg=turnTextToNumber(eTxtkg);
            Lovibond =turnTextToNumber(eTxtLovibond);
            hop =String.valueOf(eTxtHop.getText());
            alphaAcid=turnTextToNumber(eTxtalphaAcid);
            grams=turnTextToNumber(eTxtgrams);
            minutes=turnTextToNumber(eTxtminutes);
            Yeast = String.valueOf(eTxtYeast.getText());
            boolean alreadyExists = false;// boolean to check if recipe is already on the list
            int index = 0;// index value in case recipe is already on the list

            //only saves recipes with a name
            if (!name.equals("")) {
                //checks if recipe already exists
                for (int i = 0; i < recipeList.size(); i++) {
                    if (recipeList.get(i).getName().equals(name)) {
                        alreadyExists = true;
                        index = i;
                    }

                }
                if (alreadyExists) {
                    //updates recipe
                    recipeList.get(index).setAll(name, Style, oG, fG, preSG, IBU, ABV, Yeast, tempMaltList, tempHopList);
                } else {
                    //Creates new recipe and add it to the array
                    Recipe newRecipe = new Recipe(name, Style, oG, fG, preSG, IBU, ABV, Yeast, tempMaltList, tempHopList);
                    recipeList.add(newRecipe);
                }
                //saves the array in the device's storage
                saveData();
            }

        }
        catch (Exception e)//show error to the screen
        {
            Tools.exceptionToast(getApplicationContext(), e.getMessage());
        }


    }

    /*Method Name: removeMalts
     *Purpose: Remove all item from the malt list and table
     *Accepts:
     *Returns: Void
     */
    public void removeMalts(){
        //find table
        TableLayout tMalt;
        tMalt = (TableLayout)findViewById(R.id.tableMalt);
        // find how many rows there are
        int childCount = tMalt.getChildCount();

        // Remove all rows except the first one( the header)
        if (childCount > 1) {
            tMalt.removeViews(1, childCount - 1);
        }
        //remove items from the array
        tempMaltList.clear();

    }

    /*Method Name: removeHops
     *Purpose: Remove all item from the hop list and table
     *Accepts:
     *Returns: Void
     */
    public void removeHops(){
        //find table
        TableLayout tHop;
        tHop = (TableLayout)findViewById(R.id.tableHop);

        // find how many rows there are
        int childCount = tHop.getChildCount();

        // Remove all rows except the first one( the header)
        if (childCount > 1) {
            tHop.removeViews(1, childCount - 1);
        }
        //remove items from the array
        tempHopList.clear();

    }

    /*Method Name: addMaltBtn
     *Purpose: add malt Object to the array and to the table
     *Accepts:
     *Returns: Void
     */
    public void addMaltBtn (){
        //find fields
        EditText eTxtMalt= findViewById(R.id.edTxtRecgrain);
        EditText eTxtkg= findViewById(R.id.edTxtNumbRecGrainKg);
        EditText eTxtLovibond= findViewById(R.id.edTxtNumbRecGrainColor);
        //get values from user
        malt=String.valueOf(eTxtMalt.getText());
        kg=turnTextToNumber(eTxtkg);
        Lovibond =turnTextToNumber(eTxtLovibond);
        // only adds malt if it has a name
        if (!malt.equals(""))
        {
            //creates malt Object and add it to the list
            Malt newMalt = new Malt(malt,kg,Lovibond);
            tempMaltList.add(newMalt);
        }
        //add values of the object to the table
        addMalt();

    }

    /*Method Name: addMalt
     *Purpose: add malt Object to the table
     *Accepts:
     *Returns: Void
     */
    public void addMalt(){
        //Method Variables
        TextView tv1, tv2, tv3;
        TableLayout tMalt;
        TableRow tr;
        //Gets table by Id
        tMalt = (TableLayout)findViewById(R.id.tableMalt);
        tMalt.setColumnStretchable(0,true);
        tMalt.setColumnStretchable(1,true);
        tMalt.setColumnStretchable(2,true);
        //Creates table row
        tr = new TableRow(this);
        tv1 = new TextView(this);
        tv1.setText(malt);
        tv1.setGravity(Gravity.CENTER);
        tv1.setBackgroundColor(Color.WHITE);
        tv2 = new TextView(this);
        tv2.setText(String.valueOf(kg));
        tv2.setGravity(Gravity.CENTER);
        tv2.setBackgroundColor(Color.WHITE);
        tv3 = new TextView(this);
        tv3.setText(String.valueOf(Lovibond));
        tv3.setGravity(Gravity.CENTER);
        tv3.setBackgroundColor(Color.WHITE);
        //Add views to row
        tr.addView(tv1);
        tr.addView(tv2);
        tr.addView(tv3);
        //Add row to table
        tMalt.addView(tr);
    }

    /*Method Name: addHopBtn
     *Purpose: add hop Object to the array and to the table
     *Accepts:
     *Returns: Void
     */
    public void addHopBtn (){
        //find fields
        EditText eTxtHop= findViewById(R.id.edTxtRecHop);
        EditText eTxtAlphaAcid= findViewById(R.id.edTxtNumbRecHopAA);
        EditText eTxtGrams= findViewById(R.id.edTxtNumbRecHopG);
        EditText eTxtMinutes= findViewById(R.id.edTxtNumbRecHopMin);
        //get values from user
        hop =String.valueOf(eTxtHop.getText());
        alphaAcid=turnTextToNumber(eTxtAlphaAcid);
        grams=turnTextToNumber(eTxtGrams);
        minutes=turnTextToNumber(eTxtMinutes);
        // only adds hop if it has a name
        if (!hop.equals(""))
        {
            //creates hop Object and add it to the list
            Hop newHop = new Hop(hop,alphaAcid,grams, minutes);
            tempHopList.add(newHop);
        }
        //add values of the object to the table
        addHop();
    }

    /*Method Name: addHop
     *Purpose: add hop Object to the table
     *Accepts:
     *Returns: Void
     */
    public void addHop(){
        //Method Variables
        TextView tv1, tv2, tv3, tv4;
        TableLayout tHop;
        TableRow tr;
        //Gets table by Id
        tHop = (TableLayout)findViewById(R.id.tableHop);
        tHop.setColumnStretchable(0,true);
        tHop.setColumnStretchable(1,true);
        tHop.setColumnStretchable(2,true);
        tHop.setColumnStretchable(3,true);
        //Creates table row
        tr = new TableRow(this);
        tv1 = new TextView(this);
        tv1.setText(hop);
        tv1.setGravity(Gravity.CENTER);
        tv1.setBackgroundColor(Color.WHITE);
        tv2 = new TextView(this);
        tv2.setText(String.valueOf(alphaAcid));
        tv2.setGravity(Gravity.CENTER);
        tv2.setBackgroundColor(Color.WHITE);
        tv3 = new TextView(this);
        tv3.setText(String.valueOf(grams));
        tv3.setGravity(Gravity.CENTER);
        tv3.setBackgroundColor(Color.WHITE);
        tv4 = new TextView(this);
        tv4.setText(String.valueOf(minutes));
        tv4.setGravity(Gravity.CENTER);
        tv4.setBackgroundColor(Color.WHITE);
        //Add views to row
        tr.addView(tv1);
        tr.addView(tv2);
        tr.addView(tv3);
        tr.addView(tv4);
        //Add row to table
        tHop.addView(tr);
    }

    /*Method Name: nextRec
     *Purpose: Load the next recipe on the list on to the GUI
     *Accepts:
     *Returns: Void
     */
    public void nextRec() {

        //if the array is not empty
        if(recipeList.size()>0)
        {
            //if the index is less than the last item, increase the index, else, turn it back to 0
            if (aIndex < recipeList.size()-1) aIndex++;
            else aIndex = 0;
            //load data to the GUI
            putData();
            //Save all the data to the storage
            saveData();
        }
    }

    /*Method Name: prevRec
     *Purpose: Load the previous recipe on the list on to the GUI
     *Accepts:
     *Returns: Void
     */
    public void prevRec (){
        //if the index is less than the last item, increase the index, else, turn it back to 0
        if(recipeList.size()>1)
        {
            //if the index is more than the first item decrease  the index, else, set it to the last item
            if (aIndex > 0) aIndex--;
            else aIndex = recipeList.size()-1;
            //load data to the GUI
            putData();
            //save all the data to the storage
            saveData();
        }

    }

    /*Method Name: newRec
     *Purpose: Clear all fields in the  GUI
     *Accepts:
     *Returns: Void
     */
    public void newRec (){
        //if the array has at least one item, set index to one past the last item of the array
        if(recipeList.size()>0) aIndex = recipeList.size();
        removeHops();
        removeMalts();
        EditText eTxtname= findViewById(R.id.edTxtRecName);
        eTxtname.getText().clear();
        EditText eTxtStyle= findViewById(R.id.edTxtRecStyle);
        eTxtStyle.getText().clear();
        EditText eTxtoG= findViewById(R.id.edTxtNumbRecOG);
        eTxtoG.getText().clear();
        EditText eTxtfG= findViewById(R.id.edTxtNumbRecFG);
        eTxtfG.getText().clear();
        EditText eTxtpreSG= findViewById(R.id.edTxtNumbRecPBSG);
        eTxtpreSG.getText().clear();
        EditText eTxtIBU= findViewById(R.id.edTxtNumbRecIBU);
        eTxtIBU.getText().clear();
        EditText eTxtABV= findViewById(R.id.edTxtNumbRecABV);
        eTxtABV.getText().clear();
        EditText eTxtYeast= findViewById(R.id.edTxtRecYeast);
        eTxtYeast.getText().clear();

    }

    /*Method Name: deleteRec
     *Purpose: Remove recipe from the list
     *Accepts:
     *Returns: Void
     */
    public void deleteRec (){

        EditText eTxtname= findViewById(R.id.edTxtRecName);
        name=String.valueOf(eTxtname.getText());
        if (!name.equals("")) {
            boolean alreadyExists = false;
            int index = 0;
            if (recipeList.size()>0)
            {
                for (int i = 0; i < recipeList.size(); i++) {
                    if (recipeList.get(i).getName().equals(name)) {
                        alreadyExists = true;
                        index = i;
                    }

                }
                if (alreadyExists) {
                    recipeList.remove(index);
                    saveData();
                    aIndex = recipeList.size();
                }
            }
        }

        removeHops();
        removeMalts();
        eTxtname.getText().clear();
        EditText eTxtStyle= findViewById(R.id.edTxtRecStyle);
        eTxtStyle.getText().clear();
        EditText eTxtoG= findViewById(R.id.edTxtNumbRecOG);
        eTxtoG.getText().clear();
        EditText eTxtfG= findViewById(R.id.edTxtNumbRecFG);
        eTxtfG.getText().clear();
        EditText eTxtpreSG= findViewById(R.id.edTxtNumbRecPBSG);
        eTxtpreSG.getText().clear();
        EditText eTxtIBU= findViewById(R.id.edTxtNumbRecIBU);
        eTxtIBU.getText().clear();
        EditText eTxtABV= findViewById(R.id.edTxtNumbRecABV);
        eTxtABV.getText().clear();
        EditText eTxtYeast= findViewById(R.id.edTxtRecYeast);
        eTxtYeast.getText().clear();

    }

}