package com.cookandroid.main;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class whoWingame_Activity extends AppCompatActivity {

    private ImageView imageView1, imageView2;
    private Button startButton, backButton;
    private EditText resultEditText;
    private TextView vsTextView;
    private List<Pair<Integer, String>> imageTextList = new ArrayList<>();
    private List<String> outcomes = new ArrayList<>();
    private boolean gameStarted = false;
    private boolean firstCycle = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.whowin_game);

        ImageView lala_gif_img = (ImageView)findViewById(R.id.lala_gif_img);
        Glide.with(this).load(R.drawable.background).into(lala_gif_img);

        imageView1 = findViewById(R.id.imageView1);
        imageView2 = findViewById(R.id.imageView2);
        startButton = findViewById(R.id.startButton);
        backButton = findViewById(R.id.backButton);
        resultEditText = findViewById(R.id.resultEditText);
        vsTextView = findViewById(R.id.vsTextView);

        // Set scale type for the ImageViews
        imageView1.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView2.setScaleType(ImageView.ScaleType.CENTER_CROP);

        // 이미지와 텍스트를 쌍으로 리스트에 추가
        imageTextList.add(new Pair<>(R.drawable.bear, "곰돌이"));
        imageTextList.add(new Pair<>(R.drawable.alpaca, "알파카"));
        imageTextList.add(new Pair<>(R.drawable.owl, "부엉이"));
        imageTextList.add(new Pair<>(R.drawable.tiger, "호랑이"));
        imageTextList.add(new Pair<>(R.drawable.developer, "개발자"));

        // 결과 리스트에 가능한 결과 추가
        outcomes.add("벼락이 쳐서");
        outcomes.add("배탈이 나서");
        outcomes.add("갑자기 넘어져서");
        outcomes.add("레고를 밟아서");
        outcomes.add("카리스마 떄문에");

        // Set a click listener for the "Game Start" button
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initializeGame();
            }
        });

        // Set a click listener for the "Back" button
        backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(whoWingame_Activity.this, MainView.class);
                    startActivity(intent);
                    finish();  // 현재 액티비티를 종료
                }
            });


        // 처음에 "vs" 텍스트를 숨김
        vsTextView.setVisibility(View.GONE);
    }

    public void startGame(View view) {
        if (!gameStarted || imageTextList.size() < 2 || firstCycle) {
            // If the game hasn't started, there are less than 2 images, or it's the first cycle, initialize the game
            initializeGame();
            firstCycle = false;

            // "vs" TextView를 보이도록 설정
            vsTextView.setVisibility(View.VISIBLE);
        } else {
            // 이미지와 텍스트 리스트를 섞음
            Collections.shuffle(imageTextList);

            // 첫 번째 이미지와 텍스트 선택
            Pair<Integer, String> pair1 = imageTextList.get(0);
            int selectedImage1 = pair1.first;
            String text1 = pair1.second;

            // 이미지와 텍스트 리스트에서 첫 번째 이미지와 텍스트를 제외한 이미지와 텍스트 중에서 두 번째 이미지와 텍스트 선택
            Pair<Integer, String> pair2 = null;
            for (Pair<Integer, String> pair : imageTextList.subList(1, imageTextList.size())) {
                if (pair.first != selectedImage1) {
                    pair2 = pair;
                    break;
                }
            }

            if (pair2 != null) {
                int selectedImage2 = pair2.first;
                String text2 = pair2.second;

                // 두 개의 ImageView에 이미지 표시
                imageView1.setImageResource(selectedImage1);
                imageView2.setImageResource(selectedImage2);

                // "vs" TextView에 텍스트 표시
                vsTextView.setText(text1 + " vs " + text2);

                // 버튼과 에딧 텍스트의 가시성 설정
                startButton.setVisibility(View.GONE);
                resultEditText.setVisibility(View.VISIBLE);
                vsTextView.setVisibility(View.VISIBLE); // "vs" 텍스트 보이기
            }
        }
    }

    public void onImageClick(View view) {
        ImageView clickedImageView = (ImageView) view;
        Pair<Integer, String> clickedPair = null;
        Pair<Integer, String> otherPair = null;

        // Identify the clicked and other pairs
        if (clickedImageView.getId() == R.id.imageView1) {
            clickedPair = imageTextList.get(0);
            otherPair = imageTextList.get(1);
        } else if (clickedImageView.getId() == R.id.imageView2) {
            clickedPair = imageTextList.get(1);
            otherPair = imageTextList.get(0);
        }

        if (clickedPair != null && otherPair != null) {
            // Use clickedPair and otherPair to display the result
            checkWinLoss(clickedPair, otherPair);

            // "vs" TextView를 숨기도록 설정
            vsTextView.setVisibility(View.GONE);

            // Show the result in the text box
            showResultText();
        }
    }

    private void checkWinLoss(Pair<Integer, String> selectedPair, Pair<Integer, String> otherPair) {
        // 랜덤하게 결과 선택
        Random random = new Random();
        boolean isWinner = random.nextBoolean();

        // 결과 텍스트 설정
        String outcome = outcomes.get(random.nextInt(outcomes.size())); // Randomly select an outcome

        String resultText;
        if (isWinner) {
            resultText = outcome + " " + selectedPair.second + "가 이겼습니다!";
        } else {
            resultText = outcome + " " + selectedPair.second + "가 졌습니다!";
        }

        // 결과 텍스트 설정
        resultEditText.setText(resultText);
    }

    private void showResultText() {
        // "vs" TextView를 숨기도록 설정
        vsTextView.setVisibility(View.GONE);

        // 나머지 코드는 그대로 유지됩니다.
        resultEditText.setVisibility(View.VISIBLE);
        startButton.setVisibility(View.VISIBLE);
        gameStarted = false;
    }

    private void initializeGame() {
        // Shuffle the imageTextList
        Collections.shuffle(imageTextList);

        // Select the first pair of images and text
        Pair<Integer, String> pair1 = imageTextList.get(0);
        int selectedImage1 = pair1.first;
        String text1 = pair1.second;

        Pair<Integer, String> pair2 = imageTextList.get(1);
        int selectedImage2 = pair2.first;
        String text2 = pair2.second;

        // Set the images and text in the ImageViews and TextView
        imageView1.setImageResource(selectedImage1);
        imageView2.setImageResource(selectedImage2);
        vsTextView.setText(text1 + " vs " + text2);

        // Hide the result text, show the "vs" text, and set gameStarted flag
        resultEditText.setVisibility(View.GONE);
        startButton.setVisibility(View.GONE);
        vsTextView.setVisibility(View.VISIBLE);
        gameStarted = true;
    }

    // Add a method to handle back button click
    public void goBack(View view) {
        // Implement the logic to go back as needed
        // For example, you can use onBackPressed() to simulate the back button press
        onBackPressed();
    }
}