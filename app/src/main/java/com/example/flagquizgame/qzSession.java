package com.example.flagquizgame;

import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ImageView;

public class qzSession extends AppCompatActivity {
    private FirebaseStorage storage;
    private PopulateAndProcess eachQ;
    private ImageView currentFlag;
    private StorageReference flagImageUrl;
    private int answered;
    private String imageUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qz_session);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        storage = FirebaseStorage.getInstance(); //"gs://flagquiz-d8db1.appspot.com"
        eachQ = new PopulateAndProcess(storage);
        //StorageReference gsReference = storage.getReferenceFromUrl("gs://flagquiz-d8db1.appspot.com/250px/ad.png");
        imageUrl = "gs://flagquiz-d8db1.appspot.com/250px/";
        currentFlag = findViewById(R.id.flag);
        answered = 0; //to be used to keep track of how many questions answered


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





    /*Notes / ideas:
    - wrote down simple algo to select answer and 3 other countries from list, also method for verifying if answer is correct.
    - next step should be to have something here that makes url for image to use and will be passed to firebase
    - also think of how I'll take care of populating already used places,
    - at higher level, how and where I'll keep track of user's progress / session for each question, as well as clearing the key index variable and 3 otherindexes after each question.
    - link to correct / wrong page or fragement from here? May be a better idea to use toast + add in checkmark for correct answer, or wrong check mark if answer is incorrect.
    */

    //setters
    public void setEachQ(PopulateAndProcess eachQ) {
        this.eachQ = eachQ;
    }


    public void setCurrentFlag(ImageView currentFlag) {
        this.currentFlag = currentFlag;
    }
}