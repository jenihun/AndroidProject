package com.cookandroid.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class EggBreaker extends AppCompatActivity {

    MediaPlayer mediaPlayer;

    int count = 0;
    private Random random = new Random();
    private TextView title;
    int Randomnum = random.nextInt(50);
    private final int[] images = {R.drawable.egg_image_button, R.drawable.egg_image_button_mid, R.drawable.broken_egg_result};

    // 다시하기 버튼
    private Button replayButton;
    private Button mainButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eggbreaker_layout);
        
        //음악 재생
        mediaPlayer = MediaPlayer.create(this, R.raw.test);
        mediaPlayer.setLooping(true); //무한재생
        mediaPlayer.start();

        // title 초기화
        title = findViewById(R.id.title);

        final ImageButton eggBreakButton = findViewById(R.id.eggBreakButton);
        replayButton = findViewById(R.id.replayButton);
        mainButton = findViewById(R.id.mainButton);

        // 다시하기 버튼 클릭 리스너 설정
        replayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restartGame();
            }
        });
        mainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EggBreaker.this, MainActivity.class);
                startActivity(intent);
                finish();  // 현재 액티비티를 종료
            }
        });


        eggBreakButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                if (count == Randomnum / 2) {
                    eggBreakButton.setImageResource(images[1]);
                    // 텍스트도 변경
                    String newText = "어라?";
                    title.setText(newText);
                    Toast.makeText(EggBreaker.this, "어라..알의 상태가?", Toast.LENGTH_SHORT).show();
                }
                if (count == Randomnum) {
                    String newText = "풉 ㅋㅋ";
                    title.setText(newText);
                    eggBreakButton.setImageResource(images[2]);
                    Toast.makeText(EggBreaker.this, "축하합니다! 당첨되셨습니다." + "행운의 숫자는 " + Randomnum, Toast.LENGTH_SHORT).show();
                    // 게임이 끝났을 때 다시하기 버튼 표시
                    showReplayButton();
                }
            }
        });
    }

//    // DB에서 FCM 토큰 호출
//    String registrationToken = "YOUR_REGISTRATION_TOKEN";
//
//    // 게임 결과 생성(메세지 내용)
//    Message message = Message.builder()
//            .putData("score", "850")
//            .putData("time", "2:45")
//            .setToken(registrationToken)
//            .build();
//
//    String response = FirebaseMessaging.getInstance().send(message);


    // 다시하기, 메인 버튼을 표시하는 메서드
    private void showReplayButton() {
        replayButton.setVisibility(View.VISIBLE);
        mainButton.setVisibility(View.VISIBLE);
    }

    // 게임을 초기화하고 UI를 설정하는 메서드
    private void restartGame() {
        // 게임 초기화 로직 추가
        // 예: 이미지, 텍스트 초기화 및 다시하기 버튼 숨김
        ImageButton eggBreakButton = findViewById(R.id.eggBreakButton);
        eggBreakButton.setImageResource(images[0]);

        title.setText("부셔보던가");

        // 다시하기 버튼 숨김
        replayButton.setVisibility(View.GONE);

        // 게임 카운트 초기화
        count = 0;
        // 새로운 랜덤 숫자 생성
        Randomnum = random.nextInt(50);
    }
}