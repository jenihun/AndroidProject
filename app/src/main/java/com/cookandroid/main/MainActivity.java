package com.cookandroid.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.firebase.FirebaseApp;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_view);

        Button eggGameStartbtn = (Button) findViewById(R.id.eggGamestartbtn);

        ImageView lala_gif_img = (ImageView)findViewById(R.id.lala_gif_img);
        Glide.with(this).load(R.drawable.background).into(lala_gif_img);

        eggGameStartbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EggBreaker.class);
                startActivity(intent);
                finish();  // 현재 액티비티를 종료
            }
        });

    }
}