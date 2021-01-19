/**
 * file to do initial set up needed for the quiz.
 * may get more refined later on as needed.
 */
package com.example.flagquizgame;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import android.content.res.AssetManager;

public class initializeQuiz {
    private List<String> countries;

    public initializeQuiz(Context context){
        System.out.println("initializing quiz...");
        countries = new ArrayList<String>();

        try {
            AssetManager assetManager = context.getAssets();
            Scanner input = new Scanner(assetManager.open("countries.txt"));

            while (input.hasNextLine()) {
                String country = input.nextLine();
                countries.add(country);
            }
            input.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if(!countries.isEmpty()) {
            for (int i = 0; i < countries.size(); i++) {
                System.out.println(countries.get(i));
            }
        }else{
            System.out.println("list currently empty.");
        }
    }
}
