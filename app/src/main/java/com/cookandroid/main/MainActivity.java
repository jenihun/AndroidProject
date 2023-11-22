package com.cookandroid.main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.firebase.FirebaseApp;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_view);

        // Firebase SDK 초기화
        FirebaseApp.initializeApp(this);

        ImageView lala_gif_img = (ImageView)findViewById(R.id.lala_gif_img);
        Glide.with(this).load(R.drawable.background).into(lala_gif_img);

    }
}