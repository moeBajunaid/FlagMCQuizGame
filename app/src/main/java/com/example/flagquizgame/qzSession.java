package com.example.flagquizgame;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class qzSession extends AppCompatActivity {
    private FirebaseStorage storage;
    private PopulateAndProcess eachQ;
    private ImageView currentFlag;
    private StorageReference flagImageUrl;
    private int answered;
    private String imageUrl;
    private int numOfQuestions;
    private int correctAns;
    private Boolean choseAnswer = false; //to be used to check for loading up next question after user answers
    //private int numOfClicks = 0; //to be used to avoid action taking place for multiple button clicks on the same question
    private Boolean alreadyChoseAnswer = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qz_session);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent userIntent = getIntent();
        numOfQuestions = userIntent.getIntExtra("numOfQuestions",0);
        storage = FirebaseStorage.getInstance(); //"gs://flagquiz-d8db1.appspot.com"
        eachQ = new PopulateAndProcess(storage);
        //StorageReference gsReference = storage.getReferenceFromUrl("gs://flagquiz-d8db1.appspot.com/250px/ad.png");
        imageUrl = "gs://flagquiz-d8db1.appspot.com/250px/";
        currentFlag = findViewById(R.id.flag);
        answered = 0; //to be used to keep track of how many questions answered
        populatePage();

    }

    public void populatePage(){ //method to be used to populate each image / answers to choose from
        //while (answered < numOfQuestions) {
         //  if(!choseAnswer) {
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
           //}
        //}
    }

    public void showScorePage(){
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

    public void loadImage(){
        GlideApp.with(this /* context */)
                .load(flagImageUrl)
                .into(currentFlag);

       // choseAnswer = true;
    }

    public void resetImageUrl(){
        imageUrl = "gs://flagquiz-d8db1.appspot.com/250px/";
    }

    //getters
    public PopulateAndProcess getEachQ() {
        return eachQ;
    }


    public ImageView getCurrentFlag() {
        return currentFlag;
    }


    public void checkSelectedAnswer(View view){

        if(!alreadyChoseAnswer) { //ensure that if user attempts to answer same question again, it is not taken into account

            boolean verified;
            int viewId = view.getId(); //used to capture current button's id
            TextView userChoice = (TextView) findViewById(viewId); // EditText (EditText)
            String choiceSelected = userChoice.getText().toString();
            verified = eachQ.verifyAnswer(choiceSelected);



            if (verified) { //display toast message indicating correct answer
                //view.getBackground().setColorFilter(Color.parseColor("#99cc00"), PorterDuff.Mode.DARKEN);
                view.setBackgroundColor(0xFF99cc00);
                //view.invalidate();
                Toast toast = Toast.makeText(cContext.getContext(), "Correct!", Toast.LENGTH_SHORT);
//                Toast message1 = Toast.makeText(cContext.getContext(), "Correct!", Toast.LENGTH_SHORT);
//                ImageView correct = new ImageView(cContext.getContext());
//                correct.setImageResource(R.drawable.correct);
//                toast.setView(correct);
                //message1.setGravity(Gravity.TOP | Gravity.START, 0, 0);
                toast.show();
//                message1.show();
                System.out.println("CORRECT");
                correctAns++;
                //TextView tv = (TextView) toast.getView().findViewById(android.)

            } else { //display toast message indicating incorrect answer
                view.setBackgroundColor(0xFFb00020);
//                view.getBackground().setColorFilter(Color.parseColor("#b00020"));
                //view.invalidate();
                Toast toast2 = Toast.makeText(cContext.getContext(), "Incorrect! :(", Toast.LENGTH_SHORT);
//                Toast message = Toast.makeText(cContext.getContext(), "Incorrect :(", Toast.LENGTH_SHORT);
//                ImageView Incorrect = new ImageView(cContext.getContext());
//                Incorrect.setImageResource(R.drawable.incorrect);
//                toast2.setView(Incorrect);
                //message.setGravity(Gravity.TOP | Gravity.START, 0, 0);
                toast2.show();
//                message.show();
                System.out.println("INCORRECT");
            }

            alreadyChoseAnswer = true;
            //choseAnswer = false;
            answered++;


        new CountDownTimer(3000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            public void onFinish(){
                view.setBackgroundColor(0xFF6200ee);
                populatePage(); //load up / queue next question if end is not reached yet
                alreadyChoseAnswer = false;
            }

        }.start();

        }
    }


  /*Notes / ideas:
    - wrote down simple algo to select answer and 3 other countries from list, also method for verifying if answer is correct.
    - next step should be to have something here that makes url for image to use and will be passed to firebase
    - also think of how I'll take care of populating already used places,
    - at higher level, how and where I'll keep track of user's progress / session for each question, as well as clearing the key index variable and 3 otherindexes after each question.
    - link to correct / wrong page or fragement from here? May be a better idea to use toast + add in checkmark for correct answer, or wrong check mark if answer is incorrect.


    DO NEXT:
    -use toast + image to verify if answer selected by user is correct. (also need to have onclick lister for each of the 4 buttons that calls checkanswer method -> depending if true or false, use either correct or incorrect png
    -figure out how I'll get it to refresh page to get next question after toast message disappears
    -have it where user can select how many questions to do... will have to initially hardcode for testing (also make sure later to handle error messages relating to this)
    -think about adding more color / animations to app?


    FIGURE OUT WHY IM GETTING no auth token for request Warning for Firebase??



    TO DO (March 15 2021):
    - add outline to flag pictures to avoid it blending in with white background (or any other color background?) DONE (Added border to imageView... may still need to figure out how to make image fill out imageView completely
    - add in more colours to app?
    - make buttons a bit bigger to accomadate answers
    - make question a bit more generic (not all flags used are country flags!) (DONE: made question use location instead of country)
    - add in way for user to select how many questions they want to answer (WIP: looking to do this with first fragment)
    - add in a page at end to show user their stats (overall score, number of correct questions, incorrect, etc), also button to allow user to relaunch or try again
    - fix up homescreen, add in animations or pics?
    - maybe add in a leaderboard feature to keep track of attempts
    - potentially add timer to top to show how long they've taken (could maybe have a separate mode where it would also countdown time before user has before moving on to next question


    May 30 2021:
    - added in a border around the flag picture
    - current idea is to use launch first fragment after mainActivity launch button is clicked.
      - will prompt user to enter a number for how many questions to complete, between 1 and 254
      - will use setError in EditText to ensure user does this
      - start button will be linked to inflate and start actual quiz with number of questions given by the user (will need to pass along the number to here in numOfQuestions variable)
    - Idea: have the last page display total score, then give user choice of either viewing leaderboard, or linking back to the main page.
    - Idea: add in a sound for correct / incorrect questions
    - see prev notes for more things of what I want to implement


    May 31 2021:
    - idea: use second fragment as the page to display the user's score, and also allow it to link back to either the main menu, or a type of leaderboard that keeps track of user's score
    - add in a correct / wrong sound?
    - found out that you cannot pass intent from an activity to a fragment... will need to figure out how I wanna proceed (may scrap using fragments all together)
    - currently have something in place to hopefully avoid user inputting an invalid num of questions to populate quiz with
    - Idea: Check if I can pass on the intent to qzSession as I had it from before, but then attach / launch the fragment from it. Afterwards, pass back user answer from fragment to qzSession. 



    June 19 2021:
    New idea:
    have a fragment with the first part (launch button, and potentially options button? adding a picture later maybe too)

    on launch button click, swap fragments

    have that fragment load up the quiz, and pass on the number provided by the user

    Have a last fragment that shows user their score, and allows them to either launch another quiz, or view their "score"

    added in a menu folder, but does not do what I expected. provides functionality for top bar menu


    July 1 2021:
    - started process of changing main menu to a fragment...
      - need to let pic show up for main menu via code not the xml file. see https://stackoverflow.com/questions/39294103/viewing-an-image-on-a-android-fragment/39294375 or https://stackoverflow.com/questions/45377401/display-image-in-fragment
     added in the pic for main menu


     September 22 2021:
     - scrapped idea of making it a fragment to keep app simple.
     - fixed more or less having the flag image fit dimensions of imageView
     - Interface allows user to enter how many questions they would like to answer
     - left to do:
       - have a score tracker (variables already there, will prob just be an edit text box that display user score to them) DONE
       - implement allowing user to start another session from the end of their current attempt DONE
       ACTUALLY LEFT TO DO
       -code cleanup / removing unused files
       - make toast notification stay at bottom with gravity?
       - think of a way to show the correct answer when wrong answer is selected (changing color of button with actual answer to green?
    */

    //setters
    public void setEachQ(PopulateAndProcess eachQ) {
        this.eachQ = eachQ;
    }


    public void setCurrentFlag(ImageView currentFlag) {
        this.currentFlag = currentFlag;
    }
}