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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qz_session);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FirebaseStorage storage = FirebaseStorage.getInstance(); //"gs://flagquiz-d8db1.appspot.com"
        StorageReference gsReference = storage.getReferenceFromUrl("gs://flagquiz-d8db1.appspot.com/250px/ad.png");

        ImageView imageView = findViewById(R.id.flag);


        GlideApp.with(this /* context */)
                .load(gsReference)
                .into(imageView);




    }
}