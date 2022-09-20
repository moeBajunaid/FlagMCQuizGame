package com.example.flagquizgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Game_Over extends AppCompatActivity {

    public TextView result;
    public Button playAgain;
    @Override
    protected void onCreate(Bundle savedInstanceState) { //display game over screen with user score and let them play again if they want.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game__over);
        result = (TextView)findViewById(R.id.displayUserScore);
        playAgain = (Button) findViewById(R.id.playAgainButton);
        Bundle bundle = getIntent().getExtras();
        String endScore = bundle.getString("Result");
        result.setText(endScore);

        playAgain.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                restartQuiz();
            }
        });
    }

    public void restartQuiz(){ //method that takes user back to main menu if they would like to play again
        Intent mainMenu = new Intent(this,MainActivity.class);
        startActivity(mainMenu);
    }
}