package com.cookandroid.main;

import android.util.Log;

import java.util.List;
import java.util.Random;

public class NaturalDisaster {

    private Random random = new Random();

    private String eventMessage;

    public String randomEvent(List<PaperPlane> planes) {
        Log.d("PaperPlane", "비행기 리스트 크기: " + planes.size());
        if (planes.isEmpty()) {
            Log.d("PaperPlane", "비행기 생성 안됬음.");
            return "비행기 생성 안됨";
        }

        int randomPlaneIndex = random.nextInt(planes.size());
        PaperPlane randomPlane = planes.get(randomPlaneIndex);

        int randomNumber = random.nextInt(5); // 0부터 4까지의 난수를 생성

        switch (randomNumber) {
            case 0:
                birdShot(randomPlane);
                return randomPlaneIndex + "번 비행기는 새의 공격을 받았습니다.";
            case 1:
                heavyRain(randomPlane);
                return randomPlaneIndex + "번 비행기는 운이 없게도 폭우를 피하지 못했습니다.";
            case 2:
                thunder(randomPlane);
                return randomPlaneIndex + "번 비행기는 운이 없게도 낙뢰를 맞았습니다.";
            case 3:
                rockAttack(randomPlane);
                return randomPlaneIndex + "번 비행기는 날아오는 돌을 피하지 못했습니다.";
            default:
                return "아무일도 일어나지 않았습니다.";
        }
    }

    public void birdShot(PaperPlane plane){plane.decreaseDurability(10f);}

    public void heavyRain(PaperPlane plane){
        plane.decreaseDurability(10f);
    }

    public void thunder(PaperPlane plane){
        plane.decreaseDurability(10f);
    }

    public void rockAttack(PaperPlane plane){
        plane.decreaseDurability(10f);
    }
}
