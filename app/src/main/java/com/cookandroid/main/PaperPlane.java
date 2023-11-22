package com.cookandroid.main;

import java.util.ArrayList;
import java.util.List;

public class PaperPlane {

    // 비행기의 구성요소
    private float durability = 100f;
    private float distance = 0f;
    private long survivalTime;
    private static int nextId = 1;
    private int id;

    public PaperPlane(){
        this.id = nextId++;
        this.survivalTime = System.currentTimeMillis();
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
    public void decreaseDurability(float amount){
        durability -= amount;
        if(durability < 0){
            durability = 0;
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
