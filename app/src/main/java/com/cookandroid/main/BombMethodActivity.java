package com.cookandroid.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.ImageView;
import com.bumptech.glide.Glide;
public class BombMethodActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bombmethod);

        ImageView lala_gif_img = findViewById(R.id.lala_gif_img);
        Glide.with(this).load(R.drawable.background).into(lala_gif_img);

        Button gamebtn = findViewById(R.id.startbtn);
        gamebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BombMethodActivity.this, BombActivity.class);
                startActivity(intent);
            }
        });

    }


}





