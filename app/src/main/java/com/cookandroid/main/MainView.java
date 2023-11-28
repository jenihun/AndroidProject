package com.cookandroid.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.firebase.FirebaseApp;

public class MainView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_view);

        Button PaperPlaneGameStartbtn = (Button) findViewById(R.id.startbtn1);
        Button BombGameStartntn = (Button) findViewById(R.id.startbtn2);
        Button whoWinGameStarbtn = (Button) findViewById(R.id.startbtn3);
        Button eggGameStartbtn = (Button) findViewById(R.id.startbtn4);
        ImageButton btnBack = findViewById(R.id.backbutton);


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainView.this, MainActivity.class);
                startActivity(intent);
            }
        });


        ImageView lala_gif_img = (ImageView)findViewById(R.id.lala_gif_img);
        Glide.with(this).load(R.drawable.background).into(lala_gif_img);

        eggGameStartbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainView.this, EggBreaker.class);
                startActivity(intent);
                finish();  // 현재 액티비티를 종료
            }
        });

        PaperPlaneGameStartbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainView.this, PlaneGame_Activity.class);
                startActivity(intent);
                finish();  // 현재 액티비티를 종료
            }
        });

        whoWinGameStarbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainView.this, whoWingame_Activity.class);
                startActivity(intent);
                finish();  // 현재 액티비티를 종료
            }
        });

        BombGameStartntn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainView.this, BombActivity.class);
                startActivity(intent);
                finish();  // 현재 액티비티를 종료
            }
        });

    }
}