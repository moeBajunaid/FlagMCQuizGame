package com.example.flagquizgame;

import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class PopulateAndProcess {
    private FirebaseStorage firebaseStorageStorage;
    private initializeQuiz startQuiz;

    private int answerIndex;
    private List<Integer> other3Indexes;


    private Random generator;

    private List<String> countries;
    private HashMap<Integer, String> alreadyUsedIndex;

    PopulateAndProcess(FirebaseStorage imgStorage){
        firebaseStorageStorage = imgStorage; //may be able to leave step of concatenating url of image location to class above and just have index of it set from here
        startQuiz = new initializeQuiz(cContext.getContext());
        generator = new Random();
        countries = startQuiz.getCountries();
        alreadyUsedIndex = startQuiz.getIndexForComplete();
    }

    public void populateAnswerIndex(){
        //local variables to be used while getting index of country to use
        String countryName; //may not end up using it
        int countryIndex;
        int randomIndex = 0;
        boolean answerIndexUnselected = true;


        while(answerIndexUnselected){
            randomIndex = generator.nextInt(countries.size());
            if(alreadyUsedIndex.isEmpty() || !alreadyUsedIndex.containsKey(randomIndex)){
                answerIndexUnselected = false;
            }
        }

        answerIndex = randomIndex;
    }


    public void populateOther3Indexes(){ //have it where this method is called after populateAnswerIndex
        int randomIndex = 0;
        //List<String> countries = startQuiz.getCountries();
        other3Indexes = new ArrayList<Integer>();

        while (other3Indexes.size() != 3){
            randomIndex = generator.nextInt(countries.size());
            if(randomIndex != answerIndex){
                other3Indexes.add(randomIndex);
            }
        }

    }


    public boolean verifyAnswer(String countryName){ //verifies if answer is correct or not
        return countries.indexOf(countryName) == answerIndex;
    }


    public int[] randomizeChoicePlacementInButtons(){
        int[] buttonsOrder = new int[4];
        int completed = 0;
        int currentIndex = 0;
        int num;
        HashMap<Integer,Integer> used = new HashMap<Integer, Integer>();

        while(completed != 4){
            num = generator.nextInt(4) + 1;
            if(!used.containsKey(num)){
                buttonsOrder[currentIndex] = num;
                used.put(num,0);
                completed++;
                currentIndex++;
            }
        }

        return buttonsOrder;
    }

   //getters
    public int getAnswerIndex() {
        return answerIndex;
    }

    public List<Integer> getOther3Indexes() {
        return other3Indexes;
    }


    public List<String> getCountries() {
        return countries;
    }

    public String getCountryName(int index){
        return countries.get(index);
    }


    public HashMap<Integer, String> getAlreadyUsedIndex() {
        return alreadyUsedIndex;
    }

    public initializeQuiz getStartQuiz() {
        return startQuiz;
    }

    //setters
    public void setOther3Indexes(List<Integer> other3Indexes) {
        this.other3Indexes = other3Indexes;
    }


    public void setCountries(List<String> countries) {
        this.countries = countries;
    }


    public void setAlreadyUsedIndex(HashMap<Integer, String> alreadyUsedIndex) {
        this.alreadyUsedIndex = alreadyUsedIndex;
    }

    public void setAnswerIndex(int answerIndex) {
        this.answerIndex = answerIndex;
    }
}
