package com.cookandroid.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class PlaneGame_Activity extends AppCompatActivity{

    private PlaneGameManager gameManager;

    private TextView planeNumTitle;
    private Spinner numberOfPlanesSpinner;
    private Button nextButton;

    private TextView peopleNumTitle;
    private Spinner numberOfPeopleSpinner;
    private Button startButton;

    private Button resultButton;
    private Button mainButton;
    private int numberOfPlanes;

    private boolean isGameover;

    NaturalDisaster naturalDisaster = new NaturalDisaster();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paperplane_layout);

        ImageView planegamebackground = (ImageView)findViewById(R.id.planebackground);
        Glide.with(this).load(R.drawable.planegame_background).into(planegamebackground);

        planeNumTitle = findViewById(R.id.planeNumTitle);
        numberOfPlanesSpinner = findViewById(R.id.numberOfPlanesSpinner);
        nextButton = findViewById(R.id.nextButton);

        peopleNumTitle = findViewById(R.id.peopleNumTitle);
        numberOfPeopleSpinner = findViewById(R.id.numberOfPeopleSpinner);
        startButton = findViewById(R.id.startButton);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 스피너에서 선택한 비행기의 개수 가져오기
                String selectedNumberOfPlanes = numberOfPlanesSpinner.getSelectedItem().toString();

                // 선택한 비행기의 개수가 비어있는지 확인
                if (!selectedNumberOfPlanes.isEmpty()) {
                    // 사용자가 비행기의 개수를 선택한 경우
                    int numberOfPlanes = Integer.parseInt(selectedNumberOfPlanes);
                    // 다음 단계로 넘어가는 로직 수행
                    showNextViews(numberOfPlanes);
                } else {
                    // 사용자가 비행기의 개수를 선택하지 않은 경우
                    Toast.makeText(PlaneGame_Activity.this, "비행기의 개수를 선택하세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 스피너에서 선택한 벌칙 받을 인원 가져오기
                String selectedNumberOfPeople = numberOfPeopleSpinner.getSelectedItem().toString();

                // 선택한 벌칙 받을 인원이 비어있는지 확인
                if (!selectedNumberOfPeople.isEmpty()) {
                    // 사용자가 벌칙 받을 인원을 선택한 경우
                    int numberOfPeople = Integer.parseInt(selectedNumberOfPeople);
                    // 게임 시작 메서드 호출
                    isGameover = startGame(numberOfPlanes, numberOfPeople);
                } else {
                    // 사용자가 벌칙 받을 인원을 선택하지 않은 경우
                    Toast.makeText(PlaneGame_Activity.this, "벌칙 받을 인원을 선택하세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        resultButton = (Button) findViewById(R.id.resultButton);

        resultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDefeatDialog();
                if (isGameover) {
                    Log.d("PlaneGameManager", "isGameover is true");
                    showDefeatDialog();
                }
            }
        });

        mainButton = (Button) findViewById(R.id.mainButton);

        mainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlaneGame_Activity.this, MainView.class);
                startActivity(intent);
                finish();  // 현재 액티비티를 종료
            }
        });
    }

    // 게임 시작 메서드
    private boolean startGame(int numberOfPlanes, int numberOfPeople) {
        List<PaperPlane> planes = PaperPlane.createPaperPlanes(numberOfPlanes);
        gameManager = new PlaneGameManager(planes, numberOfPeople, naturalDisaster);
        showGameMainScreen();
        boolean isGameOver = gameManager.updateGame(); // 한 번 호출
        Log.d("PlaneGameManager", "isGameOverValue: " + isGameOver);
        addImageViewsWithNumbersToGridLayout(numberOfPlanes);
        return isGameOver;
    }

    private void addImageViewsWithNumbersToGridLayout(int numberOfPlanes) {
        GridLayout gridLayout = findViewById(R.id.planegridlayout);
        gridLayout.setVisibility(View.VISIBLE);
        gridLayout.setColumnCount(4);
        gridLayout.setRowCount(2);
        gridLayout.setAlignmentMode(GridLayout.ALIGN_MARGINS); // 추가: 가로 중앙 정렬

        for (int i = 0; i < numberOfPlanes; i++) {
            // 각 그리드 레이아웃 요소를 담을 LinearLayout 생성
            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setGravity(Gravity.CENTER);

            // ImageView 생성
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(
                    200, // 너비
                    200 // 높이
            ));
            imageView.setImageResource(R.drawable.main_plane);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);

            // TextView 생성 (번호)
            TextView textView = new TextView(this);
            textView.setText(String.valueOf(i + 1));
            textView.setTextSize(18);
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(Color.BLACK);

            // LinearLayout에 ImageView 및 TextView 추가
            linearLayout.addView(imageView);
            linearLayout.addView(textView);

            // 새로운 LinearLayout을 GridLayout에 추가
            gridLayout.addView(linearLayout);
        }
    }


    // showNextViews 메서드에 numberOfPlanes 매개변수 추가
    private void showNextViews(int numberOfPlanes) {
        // 다음 버튼을 누를 때 숨겨진 뷰들을 보이게 하고, 보이던 뷰는 숨기게 함
        peopleNumTitle.setVisibility(View.VISIBLE);
        numberOfPeopleSpinner.setVisibility(View.VISIBLE);
        startButton.setVisibility(View.VISIBLE);

        // 숨겨진 뷰들
        planeNumTitle.setVisibility(View.GONE);
        numberOfPlanesSpinner.setVisibility(View.GONE);
        nextButton.setVisibility(View.GONE);

        // startButton 클릭 시에 numberOfPlanes를 사용할 수 있도록 전역 변수에 저장
        this.numberOfPlanes = numberOfPlanes;
    }

    private void showGameMainScreen(){
        mainButton.setVisibility(View.VISIBLE);
        resultButton.setVisibility(View.VISIBLE);

        peopleNumTitle.setVisibility(View.GONE);
        numberOfPeopleSpinner.setVisibility(View.GONE);
        startButton.setVisibility(View.GONE);
    }

    private void showDefeatDialog() {
        List<PaperPlane> defeatedPlanes = gameManager.getDefeatedPlanes();

        // 패배 정보 텍스트 생성
        StringBuilder defeatInfo = new StringBuilder("패배! 생존하지 못한 비행기들:\n");

        // 각 패배한 비행기의 정보를 문자열에 추가
        for (PaperPlane defeatedPlane : defeatedPlanes) {
            defeatInfo.append("내구도: ").append(defeatedPlane.getDurability())
                    .append(", 아이디: ").append(defeatedPlane.getId())
                    .append("\n");
        }

        // AlertDialog.Builder를 사용하여 다이얼로그 생성
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("패배자들...") // 다이얼로그 제목
                .setMessage(defeatInfo.toString()) // 패배 정보 메시지
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // OK 버튼을 클릭하면 다이얼로그를 닫을 수 있습니다.
                        dialog.dismiss();
                    }
                });
        // 다이얼로그를 표시
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}