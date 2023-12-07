package com.cookandroid.main;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.util.Random;

public class BombActivity extends AppCompatActivity {

    private ImageView bombImageView;
    private ImageView youImageView;
    private TextView winTextView;
    private TextView countdownTextView;
    private Button startButton;
    private Button restartButton;
    private Button gobackButton;

    private boolean isExploded = false;
    private int countdownSeconds;

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bomb_game);

        ImageView lala_gif_img = findViewById(R.id.lala_gif_img);
        Glide.with(this).load(R.drawable.background).into(lala_gif_img);

        bombImageView = findViewById(R.id.bombImageView);
        youImageView = findViewById(R.id.youImageView);
        winTextView = findViewById(R.id.winTextView);
        countdownTextView = findViewById(R.id.countdownTextView);
        startButton = findViewById(R.id.startButton);
        gobackButton = findViewById(R.id.gobackButton);
        restartButton = findViewById(R.id.restartButton);

        startButton.setOnClickListener(view -> startGame());
        restartButton.setOnClickListener(view -> resetGame());

        gobackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BombActivity.this, MainView.class);
                startActivity(intent);
                finish();
            }
        });



    }

    private void startGame() {
        // Hide the "Start" button
        startButton.setVisibility(Button.INVISIBLE);

        // Hide the "Go Back" and "Restart" buttons
        gobackButton.setVisibility(Button.INVISIBLE);
        restartButton.setVisibility(Button.INVISIBLE);

        // Start rotating the bomb immediately
        rotateBomb();

        // Set a random countdown time (2 to 10 seconds)
        countdownSeconds = new Random().nextInt(9) + 2;
        updateCountdown();

        // Start updating countdown and check for explosion
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                updateCountdown();

                if (countdownSeconds <= 0 && !isExploded) {
                    // Show "you.png" image
                    youImageView.setVisibility(ImageView.VISIBLE);

                    // Show "Start Game" button
                    startButton.setVisibility(Button.INVISIBLE);

                    explodeBomb();
                } else if (isExploded) {
                    // Show "Go Back" and "Restart" buttons after explosion
                    gobackButton.setVisibility(Button.VISIBLE);
                    restartButton.setVisibility(Button.VISIBLE);
                } else {
                    handler.postDelayed(this, 1000);
                }
            }
        };
        handler.post(runnable);
    }

    private void rotateBomb() {
        // Start rotating the bomb immediately
        Animation rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate_animation);
        bombImageView.startAnimation(rotateAnimation);
    }

    private void explodeBomb() {
        isExploded = true;

        // Hide bomb image immediately
        bombImageView.setVisibility(ImageView.INVISIBLE);

        // Show "you.png" image
        youImageView.setVisibility(ImageView.VISIBLE);

        // Show "바로 너!!" text
        winTextView.setText("");
        winTextView.setVisibility(TextView.VISIBLE);

        // Start explosion animation
        Animation explodeAnimation = AnimationUtils.loadAnimation(this, R.anim.explode_animation);
        bombImageView.startAnimation(explodeAnimation);

        explodeAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // Animation start, if needed
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // Animation end, reset bomb after explosion
                resetBomb();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // Animation repeat, if needed
            }
        });
    }

    private void resetBomb() {
        isExploded = false;
        bombImageView.clearAnimation();
        bombImageView.setVisibility(ImageView.INVISIBLE);

        // Hide "바로 너!!"
        winTextView.setVisibility(TextView.INVISIBLE);

        // Show the "Go Back" and "Restart" buttons
        gobackButton.setVisibility(Button.VISIBLE);
        restartButton.setVisibility(Button.VISIBLE);
    }

    private void updateCountdown() {
        countdownSeconds--;

        // Update the countdown text
        countdownTextView.setText(String.valueOf(countdownSeconds));

        if (countdownSeconds <= 0) {
            // Hide the countdownTextView when countdown is 0
            countdownTextView.setVisibility(View.INVISIBLE);
        } else {
            // Show the countdownTextView when countdown is not 0
            countdownTextView.setVisibility(View.VISIBLE);
        }
    }

    private void resetGame() {
        // Stop any ongoing processes related to the game
        handler.removeCallbacksAndMessages(null);

        // Hide the "Restart" button after it's clicked
        restartButton.setVisibility(Button.INVISIBLE);
        gobackButton.setVisibility(Button.INVISIBLE);

        // Hide the "you.png" image when restarting the game
        youImageView.setVisibility(ImageView.INVISIBLE);

        // Restart the bomb rotating game
        startGame();
    }
}