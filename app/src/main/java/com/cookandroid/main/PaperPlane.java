package com.cookandroid.main;

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
    // 초당 내구도 감소량
    private static final float DURABILITY_DECREASE_PER_SECOND = 1f;
    // 내구도를 감소시키는 ScheduledExecutorService
    private ScheduledExecutorService durabilityDecreaseScheduler;

    public PaperPlane() {
        this.id = nextId++;
        this.survivalTime = System.currentTimeMillis();
        startDurabilityDecreaseScheduler();
    }

    // 내구도를 감소시키는 스케줄러 시작
    private void startDurabilityDecreaseScheduler() {
        durabilityDecreaseScheduler = Executors.newSingleThreadScheduledExecutor();
        durabilityDecreaseScheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                decreaseDurability(DURABILITY_DECREASE_PER_SECOND);
            }
        }, 0, 1, TimeUnit.SECONDS); // 초당 1번씩 실행
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
    public void decreaseDurability(float amount) {
        durability -= amount;
        if (durability < 0f) {
            durability = 0f;
        }
    }


    // 입력받은 수만 큼 비행기를 생성하는 메서드
    public static List<PaperPlane> createPaperPlanes(int numberOfPlanes) {
        List<PaperPlane> planes = new ArrayList<>();
        for (int i = 0; i < numberOfPlanes; i++) {
            PaperPlane plane = new PaperPlane();
            planes.add(plane);
        }
        return planes;
    }
}
