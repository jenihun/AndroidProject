package com.cookandroid.main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    int count = 0;

    private Random random = new Random();

    int Randomnum = random.nextInt(50);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button eggBreakButton = (Button) findViewById(R.id.eggBreakButton);

        eggBreakButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                if(count == Randomnum) {
                    Toast.makeText(MainActivity.this, "축하합니다! 당첨되셨습니다." + "행운의 숫자는 " + Randomnum, Toast.LENGTH_SHORT).show();
                    Randomnum = random.nextInt(50);
                    count = 0;
                }
            }
        });

    }
}