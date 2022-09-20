/**
 * main activity that user interacts with when after they launch the app.
 */
package com.example.flagquizgame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.fragment.NavHostFragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class MainActivity extends AppCompatActivity {

    public Button start; // button used to allow user to start the quiz.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        start = (Button)findViewById(R.id.button_first);
        start.setOnClickListener(new View.OnClickListener(){ // onclick listener for when user clicks on start button
            public void onClick(View v){
                loadQuiz(v);
            }
        });

        System.out.println("mainActivity onCreate method called..");
    }


    public void loadQuiz (View view){ //loads up quiz after user selects start button.


        EditText userInputNumQuestions = (EditText) findViewById(R.id.numOfQuestionsUI);

        if(!userInputNumQuestions.getText().toString().isEmpty()) { //double check that user has entered a value, and did not leave field empty
            Integer userInputNumQ = Integer.valueOf(userInputNumQuestions.getText().toString()); // converting number entered by user from string to integer


            if (userInputNumQ >= 1 && userInputNumQ <= 254) { //confirm that user entered a valid number of questions to do and start the quiz if they did.
                Intent beginQuiz = new Intent(this, qzSession.class);
                beginQuiz.putExtra("numOfQuestions", userInputNumQ);
                startActivity(beginQuiz);
            } else { //otherwise let user know to input a valid number of questions
                Toast toast2 = Toast.makeText(cContext.getContext(), "Please Enter a valid number between 1 and 254", Toast.LENGTH_SHORT);
                toast2.show();
            }
        }

    }






}




