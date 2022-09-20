package com.example.flagquizgame;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

public class FirstFragment extends Fragment implements View.OnClickListener {

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onClick(View v) {

        EditText userInputNumQuestions = (EditText) v.findViewById(R.id.numOfQuestionsUI);

        // get number of questions that user would like quiz length to be
        Integer userInputNumQ = Integer.valueOf(userInputNumQuestions.getText().toString());

        //ensure that number entered by user is valid
        if (userInputNumQ >= 1 && userInputNumQ <= 254) {
            NavHostFragment.findNavController(FirstFragment.this)
                    .navigate(R.id.action_FirstFragment_to_SecondFragment);
        } else { //let user know to input a valid number of questions
            Toast toast2 = Toast.makeText(cContext.getContext(), "Please Enter a valid number between 1 and 254", Toast.LENGTH_SHORT);
            toast2.show();
        }

    }

}