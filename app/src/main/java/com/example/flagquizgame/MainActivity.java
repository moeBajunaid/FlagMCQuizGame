/**
 * need to recall how activities work within the context of android.
 */
package com.example.flagquizgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class MainActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);




        System.out.println("where does this print to??");
    }


    public void loadQuiz (View view){ //loads up quiz after user selects start buttton. Currently immediately starts quiz, but may later change this to allow user to select number of questions
        Intent beginQuiz = new Intent(this,qzSession.class);
        startActivity(beginQuiz);

    }






}