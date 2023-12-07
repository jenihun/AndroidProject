package com.cookandroid.main;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;




public class PaperPlane {

    // 비행기의 구성요소
    private float durability = 100f;
    private float distance = 0f;
    private long survivalTime;
    private static int nextId = 1;
    private int id;

    private boolean isDefeated = false;

    public boolean isDefeated() {
        return isDefeated;
    }

    // 비행기가 추락했을 때 호출되는 메서드
    public void onCrash() {
        isDefeated = true;
        // 추가적인 처리가 필요하다면 여기에 작성
    }

    // 초당 내구도 감소량
    private static final float DURABILITY_DECREASE_PER_SECOND = 2f;
    // 내구도를 감소시키는 ScheduledExecutorService
    private ScheduledExecutorService durabilityDecreaseScheduler;

    private boolean durabilityDecreaseSchedulerStopped = false;

    public PaperPlane() {
        this.id = nextId++;
        this.survivalTime = System.currentTimeMillis();
        startDurabilityDecreaseScheduler();
        Log.d("PaperPlane", "비행기 생성됨"+id);
    }

    // 내구도를 감소시키는 스케줄러 시작
    private void startDurabilityDecreaseScheduler() {
        Log.d("PaperPlane", "Durability decrease scheduler started for plane " + id);
        durabilityDecreaseScheduler = Executors.newSingleThreadScheduledExecutor();
        durabilityDecreaseScheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                decreaseDurability(DURABILITY_DECREASE_PER_SECOND);
            }
        }, 0, 1, TimeUnit.SECONDS); // 초당 1번씩 실행
    }

    public void stopDurabilityDecreaseScheduler() {
        Log.d("PaperPlane", "Durability decrease scheduler stopped for plane " + id);
        if (!durabilityDecreaseSchedulerStopped && durabilityDecreaseScheduler != null) {
            durabilityDecreaseSchedulerStopped = true;
            durabilityDecreaseScheduler.shutdown(); // shutdown()으로 변경
            try {
                if (!durabilityDecreaseScheduler.awaitTermination(1, TimeUnit.SECONDS)) {
                    durabilityDecreaseScheduler.shutdownNow();
                }
            } catch (InterruptedException e) {
                durabilityDecreaseScheduler.shutdownNow();
            }
        }
    }


    //아이디 값 리셋
    public static void resetNextId() {
        nextId = 1;
    }

    // 내구도 getter
    public float getDurability() {
        return durability;
    }

    // 거리 getter
    public float getDistance(){
        return distance;
    }

    // 생존시간 getter
    public long getSurvivalTime(){
        return survivalTime;
    }

    // ID getter
    public int getId(){
        return id;
    }

    // 내구도 감소 메서드
    public synchronized void decreaseDurability(float amount) {
        if (durabilityDecreaseSchedulerStopped) {
            // 이미 종료된 스레드인 경우 추가 작업을 수행하지 않음
            return;
        }
        Log.d("PaperPlane", "Decreasing durability by: " + amount + "현재 내구도: "+durability + "현재 아이디: "+id);
        durability -= amount;
        if (durability <= 0f) {
            durability = 0f;
            stopDurabilityDecreaseScheduler();
        }
    }


    // 입력받은 수만 큼 비행기를 생성하는 메서드
    public static List<PaperPlane> createPaperPlanes(int numberOfPlanes) {
        List<PaperPlane> paperPlanes = new ArrayList<>();
        for (int i = 0; i < numberOfPlanes; i++) {
            PaperPlane plane = new PaperPlane();
            paperPlanes.add(plane);
        }
        return paperPlanes;
    }

    @Override
    public String toString() {
        return "PaperPlane{id=" + id + ", durability=" + durability + "}";
    }
}
