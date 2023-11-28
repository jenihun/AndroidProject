package com.cookandroid.main;
import android.content.Context;
import android.util.Log;
import android.os.Handler;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PlaneGameManager {

    private GameManagerListener listener;

    private Handler handler = new Handler();
    private final int NATURAL_DISASTER_INTERVAL = 5000; // 5초

    private int selectPeoplenum;
    private List<PaperPlane> paperPlanes;
    private List<PaperPlane> defeatedPlanes; // 패배한 비행기들을 저장하는 리스트

    private NaturalDisaster naturalDisaster;

    private int crashedCount = 0;

    // PlaneGameManager 클래스 수정
    private Runnable naturalDisasterEventRunnable = new Runnable() {
        @Override
        public void run() {
            // 자연재해 이벤트 발생
            String eventMessage = naturalDisaster.randomEvent(paperPlanes); // 이벤트 메시지 가져오기
            Log.d("PlaneGameManager", "자연재해 이벤트 발생");

            // updateGame() 호출
            if (!updateGame()) {
                Log.d("PlaneGameManager", "다음 이벤트 예약");
                handler.postDelayed(this, NATURAL_DISASTER_INTERVAL);
            } else {
                Log.d("PlaneGameManager", "게임 종료");
            }
        }

    };

    public PlaneGameManager(List<PaperPlane> paperPlanes, int selectPeoplenum, NaturalDisaster naturalDisaster) {
        this.paperPlanes = paperPlanes;
        this.selectPeoplenum = selectPeoplenum;
        this.defeatedPlanes = new ArrayList<>();
        this.naturalDisaster = naturalDisaster;
    }


    public synchronized boolean updateGame() {
        Log.d("PlaneGameManager", "Game updated");

        // 게임이 이미 종료된 경우 추가 처리를 하지 않음
        if (crashedCount >= selectPeoplenum) {
            Log.d("PlaneGameManager", "게임 이미 종료");
            return true;
        }
        Iterator<PaperPlane> iterator = paperPlanes.iterator();
        while (iterator.hasNext()) {
            PaperPlane plane = iterator.next();
            if (plane.getDurability() <= 0) {
                Log.d("PlaneGameManager", "Plane " + plane.getId() + " has crashed!");
                crashedCount++;

                // 패배한 비행기 리스트에 추가
                defeatedPlanes.add(plane);

                // 종이비행기가 추락한 경우 추가적인 처리를 할 수 있음
                plane.stopDurabilityDecreaseScheduler();
                iterator.remove(); // 리스트에서 제거
            }
        }

        Log.d("DefeatedPlanes", "Defeated Planes:");
        for (PaperPlane defeatedPlane : defeatedPlanes) {
            Log.d("DefeatedPlanes", defeatedPlane.toString());
        }

        Log.d("PlaneGameManager", "Crashed Count: " + crashedCount);
        Log.d("PlaneGameManager", "Select People Num: " + selectPeoplenum);

        if (crashedCount >= selectPeoplenum) {
            Log.d("PlaneGameManager", "게임 종료");

            // 핸들러에 대기 중인 모든 콜백 및 메시지 제거
            handler.removeCallbacksAndMessages(null);

            // 현재 실행 중인 콜백 제거
            handler.removeCallbacks(naturalDisasterEventRunnable);

            return true;
        }

        // 게임이 종료되지 않았다면 다음 이벤트 예약
        handler.postDelayed(naturalDisasterEventRunnable, NATURAL_DISASTER_INTERVAL);
        return false;
    }

    // 패배한 비행기들의 정보를 반환
    public List<PaperPlane> getDefeatedPlanes() {
        return defeatedPlanes;
    }
}