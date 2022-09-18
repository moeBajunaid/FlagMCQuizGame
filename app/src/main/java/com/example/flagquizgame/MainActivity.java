/**
 * need to recall how activities work within the context of android.
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

    // public Integer userInputNumQ;
    public Button start;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        start = (Button)findViewById(R.id.button_first);
        start.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                loadQuiz(v);
            }
        });

        /*
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container_view, FirstFragment.class, null)
                    .commit();

        }
        */




        System.out.println("where does this print to??");
    }


    public void loadQuiz (View view){ //loads up quiz after user selects start buttton. Currently immediately starts quiz, but may later change this to allow user to select number of questions
         //BELOW DOES NOT WORK ... CANNOT PASS ON INTENT FROM ACTIVITY TO A FRAGMENT....

        EditText userInputNumQuestions = (EditText) findViewById(R.id.numOfQuestionsUI);

        if(!userInputNumQuestions.getText().toString().isEmpty()) { //double check that user has entered a value, and did not leave field empty
            Integer userInputNumQ = Integer.valueOf(userInputNumQuestions.getText().toString()); //should be converting number from string to integer


            if (userInputNumQ >= 1 && userInputNumQ <= 254) {
                Intent beginQuiz = new Intent(this, qzSession.class);
                beginQuiz.putExtra("numOfQuestions", userInputNumQ);
                startActivity(beginQuiz);
            } else { //add in toast notification here to let user know to input a valid number of questions
                Toast toast2 = Toast.makeText(cContext.getContext(), "Please Enter a valid number between 1 and 254", Toast.LENGTH_SHORT);
                toast2.show();
            }
        }
        /*

        //FROM previous implementation attempting to use fragments: NavHostFragment.findNavController(FirstFragment.this)
        //                    .navigate(R.id.action_FirstFragment_to_SecondFragment);



        Intent enterQuizLength = new Intent(this,FirstFragment.class);
        startActivity(enterQuizLength);

         will be moving this over somewhere else, most likely make it launch from first fragment

        */
    }






}




