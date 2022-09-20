package com.example.flagquizgame;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class qzSession extends AppCompatActivity {
    private FirebaseStorage storage;
    private PopulateAndProcess eachQ;
    private ImageView currentFlag;
    private StorageReference flagImageUrl;
    private int answered; //to be used to keep track of how many questions answered
    private String imageUrl; //url to be used to retrieve country flag image corresponding to answer of current question
    private int numOfQuestions;
    private int correctAns;
    private Boolean alreadyChoseAnswer = false; //ensure that if user attempts to answer same question again, it is not counted as answering another question

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qz_session);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent userIntent = getIntent();
        numOfQuestions = userIntent.getIntExtra("numOfQuestions",0);
        storage = FirebaseStorage.getInstance();
        eachQ = new PopulateAndProcess(storage);

        imageUrl = "gs://flagquiz-d8db1.appspot.com/250px/";
        currentFlag = findViewById(R.id.flag);
        answered = 0;
        populatePage();

    }

    public void populatePage(){ //method to be used to populate each image / answers to choose from
        if(answered < numOfQuestions) {
            eachQ.populateAnswerIndex();
            eachQ.populateOther3Indexes();
            randomizeButtonForChoicePlacement();
            buildImgUrl();
            loadImage();
            resetImageUrl();
            eachQ.getAlreadyUsedIndex().put(eachQ.getAnswerIndex(), eachQ.getCountryName(eachQ.getAnswerIndex())); //make pretty later
        }else{
            showScorePage();
        }

    }

    public void showScorePage(){ //method that displays to user their score at the end
        Intent endQuiz = new Intent(this,Game_Over.class);
        String endScore = correctAns + "/" + numOfQuestions;
        Bundle bundle = new Bundle();
        bundle.putString("Result",endScore);
        endQuiz.putExtras(bundle);
        startActivity(endQuiz);
    }

    public void randomizeButtonForChoicePlacement(){ //method to be used to avoid having answer always in the same place / button selected
        int [] placementOrder;
        placementOrder = eachQ.randomizeChoicePlacementInButtons();
        TextView button;
        for(int i = 0; i < placementOrder.length; i++){
            switch (placementOrder[i]){
                case 1:
                    button = (TextView) findViewById(R.id.choice1);
                    break;
                case 2:
                    button = (TextView) findViewById(R.id.choice2);
                    break;
                case 3:
                    button = (TextView) findViewById(R.id.choice3);
                    break;
                case 4:
                    button = (TextView) findViewById(R.id.choice4);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + placementOrder[i]);
            }

            if(i == 0) {
                int index = eachQ.getAnswerIndex();
                button.setText(eachQ.getCountryName(index));
            }else{
                int otherIndex = eachQ.getOther3Indexes().get(i-1);
                button.setText(eachQ.getCountryName(otherIndex));
            }
        }

    }

    public void buildImgUrl(){
        int countryIndex = eachQ.getAnswerIndex();
        String countryName = eachQ.getCountryName(countryIndex);
        String countryCode = eachQ.getStartQuiz().getcNameToCode().get(countryName);
        imageUrl = imageUrl + countryCode + ".png";
        flagImageUrl = storage.getReferenceFromUrl(imageUrl);
    }

    public void loadImage(){ //used to load flag image for each question
        GlideApp.with(this /* context */)
                .load(flagImageUrl)
                .into(currentFlag);
    }

    public void resetImageUrl(){
        imageUrl = "gs://flagquiz-d8db1.appspot.com/250px/";
    }


    public void checkSelectedAnswer(View view){

        if(!alreadyChoseAnswer) { //ensure that if user attempts to answer same question again, it is not taken into account

            boolean verified;
            int viewId = view.getId(); //used to capture current button's id
            TextView userChoice = (TextView) findViewById(viewId); //get which button user selected
            String choiceSelected = userChoice.getText().toString();
            verified = eachQ.verifyAnswer(choiceSelected);



            if (verified) { //display toast message indicating correct answer
                view.setBackgroundColor(0xFF99cc00); //set button color to green to indicate that user got correct answer
                Toast toast = Toast.makeText(cContext.getContext(), "Correct!", Toast.LENGTH_SHORT); //display message that user is correct
                toast.show();
                System.out.println("CORRECT");
                correctAns++;
            } else { //display toast message indicating incorrect answer
                view.setBackgroundColor(0xFFb00020); //set button color picked by user to red to indicate answer collected is wrong.
                Toast toast2 = Toast.makeText(cContext.getContext(), "Incorrect! :(", Toast.LENGTH_SHORT);
                toast2.show();
                System.out.println("INCORRECT");
            }

            alreadyChoseAnswer = true;
            answered++;


        new CountDownTimer(3000,1000) { //timer used for time to wait between each question
            @Override
            public void onTick(long millisUntilFinished) {

            }

            public void onFinish(){ //after timer is done, reset color of button chosen by user to default, and load next question
                view.setBackgroundColor(0xFF6200ee);
                populatePage(); //load up / queue next question if end is not reached yet
                alreadyChoseAnswer = false;
            }

        }.start();

        }
    }

    //getters
    public PopulateAndProcess getEachQ() {
        return eachQ;
    }


    public ImageView getCurrentFlag() {
        return currentFlag;
    }


    //setters
    public void setEachQ(PopulateAndProcess eachQ) {
        this.eachQ = eachQ;
    }


    public void setCurrentFlag(ImageView currentFlag) {
        this.currentFlag = currentFlag;
    }
}