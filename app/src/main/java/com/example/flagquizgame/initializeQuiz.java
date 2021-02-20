/**
 * file to do initial set up needed for the quiz.
 * may get more refined later on as needed.
 */
package com.example.flagquizgame;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import android.content.res.AssetManager;

public class initializeQuiz {
    private List<String> countries; //stores country names
    private List<String> cCodes; //stores country 2 digit ISO 3166 codes (exception for countries relating to uk which have "gb-" prefixed at start)
    private HashMap<String, String> cNameToCode; //to be used to store country names and iso codes as key pair values
    private HashMap<String, String> codeToCname; //to be used to store country names and iso codes as key pair values the opposite way
    private HashMap<Integer, String> indexForComplete; //may be potentially used to keep track of flags already asked in a quiz session? leave for later
    private Integer totalFlags = 0; //use to index and keep track of number elements stored
    //may end up moving some of variables above to be parameters of constructor below in order to have data stored in mainActivity or elsewhere that might be more appropriate
    //was used to working in c, forgot that java has setters and getters for that!

    public initializeQuiz(Context context){
        System.out.println("initializing quiz...");
        countries = new ArrayList<String>();
        cCodes = new ArrayList<String>();
        cNameToCode = new HashMap<String, String>();
        codeToCname = new HashMap<String,String>();

        try {
            AssetManager assetManager = context.getAssets();
            Scanner cInput = new Scanner(assetManager.open("countries.txt"));
            Scanner codeInput = new Scanner(assetManager.open("country codes.txt"));

            while (cInput.hasNextLine()) { //since lists are same length, checking that only one of them still has content for now. can be improved later on.
                String country = cInput.nextLine();
                String code = codeInput.nextLine();
                countries.add(country);
                cCodes.add(code);
                cNameToCode.put(country,code);
                codeToCname.put(code,country);
                totalFlags++;
            }
            cInput.close();
            codeInput.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if(!countries.isEmpty()) {
            for (int i = 0; i < countries.size(); i++) {
                System.out.println(countries.get(i) + " " + cCodes.get(i));
            }
        }else{
            System.out.println("list currently empty.");
        }
    }

    //getters
    public List<String> getCountries() {
        return countries;
    }

    public List<String> getcCodes() {
        return cCodes;
    }

    public HashMap<String, String> getcNameToCode() {
        return cNameToCode;
    }

    public HashMap<Integer, String> getIndexForComplete() {
        return indexForComplete;
    }

    public HashMap<String, String> getCodeToCname() {
        return codeToCname;
    }

    public Integer getTotalFlags() {
        return totalFlags;
    }



    //setters

    public void setCountries(List<String> countries) {
        this.countries = countries;
    }

    public void setcNameToCode(HashMap<String, String> cNameToCode) {
        this.cNameToCode = cNameToCode;
    }

    public void setcCodes(List<String> cCodes) {
        this.cCodes = cCodes;
    }

    public void setIndexForComplete(HashMap<Integer, String> indexForComplete) {
        this.indexForComplete = indexForComplete;
    }

    public void setTotalFlags(Integer totalFlags) {
        this.totalFlags = totalFlags;
    }

    public void setCodeToCname(HashMap<String, String> codeToCname) {
        this.codeToCname = codeToCname;
    }
}
