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

public class PlaneGame_Activity extends AppCompatActivity implements GameManagerListener{

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

    private TextView eventTextView;

    private LinearLayout naturalDisasterLayout;

    private androidx.gridlayout.widget.GridLayout planegridlayout;

    private boolean isGameover;

    @Override
    public void onEventMessageReceived(String eventMessage) {
        // 이벤트 메시지를 받아와서 처리 (예: 텍스트뷰에 표시)
        eventTextView.setText(eventMessage);

        // 이벤트 메시지에 따라 이미지뷰 추가
        addNaturalDisasterImageView(eventMessage);
    }

    private void addNaturalDisasterImageView(String eventMessage) {
        LinearLayout layout = findViewById(R.id.naturalDisasterLayout);

        // 이전에 추가된 모든 자식 뷰(이미지뷰)를 제거
        layout.removeAllViews();

        // 이벤트 메시지에 따라 자연재해 이미지를 선택
        int disasterImageResource = 0;
        if (eventMessage.contains("새의 공격")) {
            disasterImageResource = R.drawable.bird_attack;
        } else if (eventMessage.contains("폭우")) {
            disasterImageResource = R.drawable.heavy_rain;
        } else if (eventMessage.contains("낙뢰")) {
            disasterImageResource = R.drawable.thunder;
        } else if (eventMessage.contains("날아오는 돌")) {
            disasterImageResource = R.drawable.rock_attack;
        } else if (eventMessage.contains("강한 기류")) {
            disasterImageResource = R.drawable.strong_wind;
        }

        // 자연재해 이미지뷰 생성 및 추가
        if (disasterImageResource != 0) {
            // 새로운 LinearLayout 생성
            LinearLayout newLayout = new LinearLayout(this);
            newLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            newLayout.setOrientation(LinearLayout.VERTICAL);
            newLayout.setGravity(Gravity.CENTER);

            // ImageView 생성
            ImageView disasterImageView = new ImageView(this);
            disasterImageView.setLayoutParams(new LinearLayout.LayoutParams(
                    512, // 너비
                    512 // 높이
            ));
            disasterImageView.setImageResource(disasterImageResource);
            disasterImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            disasterImageView.setPadding(8, 8, 8, 8);

            // 새로운 LinearLayout에 ImageView 추가
            newLayout.addView(disasterImageView);

            // 기존의 layout에 새로운 LinearLayout 추가
            layout.addView(newLayout);
        }
    }

    NaturalDisaster naturalDisaster = new NaturalDisaster();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paperplane_layout);

        planegridlayout = (androidx.gridlayout.widget.GridLayout) findViewById(R.id.planegridlayout);

        eventTextView = (TextView) findViewById(R.id.eventTextView);

        naturalDisasterLayout = (LinearLayout) findViewById(R.id.naturalDisasterLayout);

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
                    startGame(numberOfPlanes, numberOfPeople);
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
    private void startGame(int numberOfPlanes, int numberOfPeople) {
        List<PaperPlane> planes = PaperPlane.createPaperPlanes(numberOfPlanes);
        gameManager = new PlaneGameManager(planes, numberOfPeople, naturalDisaster);
        showGameMainScreen();
        gameManager.setGameManagerListener(this);
        gameManager.updateGame();
        addImageViewsWithNumbersToGridLayout(numberOfPlanes);
    }

    // 비행기 뷰 생성
    private View createPlaneView(int planeId) {
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
        textView.setText(String.valueOf(planeId));
        textView.setTextSize(18);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.BLACK);

        // PaperPlane 객체 가져오기
        PaperPlane plane = getPaperPlaneById(planeId);

        // 탈락 여부 확인
        if (gameManager != null && plane != null && gameManager.isDefeated(plane)) {
            // 해당 비행기가 탈락한 경우 해당 뷰를 숨김
            linearLayout.setVisibility(View.INVISIBLE);
        }

        // LinearLayout에 ImageView 및 TextView 추가
        linearLayout.addView(imageView);
        linearLayout.addView(textView);

        return linearLayout;
    }

    private void addImageViewsWithNumbersToGridLayout(int numberOfPlanes) {
        GridLayout gridLayout = findViewById(R.id.planegridlayout);
        gridLayout.setVisibility(View.VISIBLE);
        gridLayout.setColumnCount(4);
        gridLayout.setRowCount(2);
        gridLayout.setAlignmentMode(GridLayout.ALIGN_MARGINS); // 추가: 가로 중앙 정렬

        for (int i = 0; i < numberOfPlanes; i++) {
            // 새로운 비행기 뷰 생성 및 추가
            View planeView = createPlaneView(i + 1);
            gridLayout.addView(planeView);
        }
    }

    // 비행기 ID로 PaperPlane 객체 가져오기
    private PaperPlane getPaperPlaneById(int planeId) {
        if (gameManager != null) {
            for (PaperPlane plane : gameManager.getPaperPlanes()) {
                if (plane.getId() == planeId) {
                    return plane;
                }
            }
        }
        return null;
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

    // main화면을 보이게 하는 메서드
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
        StringBuilder defeatInfo = new StringBuilder("패배! 추락한 비행기들:\n");

        // 각 패배한 비행기의 정보를 문자열에 추가
        for (PaperPlane defeatedPlane : defeatedPlanes) {
            defeatInfo.append(", 아이디: ").append(defeatedPlane.getId())
                    .append(", 비행시간: ").append(defeatedPlane.getSurvivalTime())
                    .append("\n");
        }

        // AlertDialog.Builder를 사용하여 다이얼로그 생성
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("추락한 비행기들") // 다이얼로그 제목
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

    public void onGameFinished(boolean isGameover) {
        if (isGameover) {
            showDefeatDialog();
            eventTextView.setVisibility(View.INVISIBLE);
            naturalDisasterLayout.setVisibility(View.INVISIBLE);
            planegridlayout.setVisibility(View.INVISIBLE);

        }
    }
}