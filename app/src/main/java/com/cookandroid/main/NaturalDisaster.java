package com.cookandroid.main;

import java.util.List;
import java.util.Random;

public class NaturalDisaster {

    private Random random = new Random();

    public void randomEvent(List<PaperPlane> planes) {
        if (planes.isEmpty()) {
            System.out.println("비행기가 생성되지 않았습니다.");
            return;
        }

        int randomPlaneIndex = random.nextInt(planes.size());
        PaperPlane randomPlane = planes.get(randomPlaneIndex);

        int randomNumber = random.nextInt(5); // 0부터 4까지의 난수를 생성

        switch (randomNumber) {
            case 0:
                birdShot(randomPlane);
                System.out.println(randomPlaneIndex+"번 비행기는 새의 공격을 받았습니다.");
                break;
            case 1:
                heavyRain(randomPlane);
                System.out.println(randomPlaneIndex+"번 비행기는 운이 없게도 폭우를 피하지 못했습니다.");
                break;
            case 2:
                thunder(randomPlane);
                System.out.println(randomPlaneIndex+"번 비행기는 운이 없게도 낙뢰를 맞았습니다.");
                break;
            case 3:
                rockAttack(randomPlane);
                System.out.println(randomPlaneIndex+"번 비행기는 날아오는 돌을 피하지 못했습니다.");
                break;
            default:
                System.out.println("아무일도 일어나지 않았습니다.");
                break;
        }
    }

    public void birdShot(PaperPlane plane){
        plane.decreaseDurability(10f);
    }

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
