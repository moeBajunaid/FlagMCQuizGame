/**
 * need to recall how activities work within the context of android.
 */
package com.example.flagquizgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initializeQuiz startQuiz = new initializeQuiz(cContext.getContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("where does this print to??");
    }


}