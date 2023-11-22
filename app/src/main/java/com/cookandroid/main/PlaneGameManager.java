package com.cookandroid.main;

import java.util.List;

public class PlaneGameManager {

    // 벌칙에 해당하는 사람 수
    private int selectPeoplenum;
    // 추락한 비행기 수를 저장하는 변수
    private int crashedPlanes = 0;
    
    // 종이비행기 객체를 저장할 리스트
    private List<PaperPlane> paperPlanes;

    public PlaneGameManager(List<PaperPlane> paperPlanes, int selectPeoplenum) {
        this.paperPlanes = paperPlanes;
        this.selectPeoplenum = selectPeoplenum;
    }

    // 게임 업데이트 메서드
    public void updateGame() {

        for (PaperPlane plane : paperPlanes) {
            // 내구도가 0이 되면 벌칙을 받는 인원 추가
            if (plane.getDurability() == 0) {
                selectPeoplenum++;
                crashedPlanes++;
            }

            // 벌칙 인원 수 만큼의 비행기가 추락하면 나머지는 생존하여 승리
            if (crashedPlanes >= selectPeoplenum) {
                declareVictory(plane);
                return; // 승리 조건 충족 시 더 이상 게임 업데이트를 진행하지 않고 종료
            }
//            // 생존시간이 30초가 넘으면 승리 조건
//            if ((System.currentTimeMillis() - plane.getSurvivalTime()) > 30000) {
//                declareVictory(plane);
//            }
        }
    }

    // 승리 조건 선언 메서드
    private void declareVictory(PaperPlane winningPlane) {
        System.out.println("Victory! The plane survived the longest: " +
                "Durability: " + winningPlane.getDurability() +
                ", Distance: " + winningPlane.getDistance() +
                ", Survival Time: " + (System.currentTimeMillis() - winningPlane.getSurvivalTime()) + " milliseconds");

        // 게임 종료 시 해당 비행기의 ID 반환
        int winningPlaneId = winningPlane.getId();
        System.out.println("Winning Plane ID: " + winningPlaneId);
        // 게임 종료 처리를 추가할 수 있습니다.
    }

//    public static void main(String[] args) {
//        // 게임 시작
//        List<PaperPlane> planes = PaperPlane.createPaperPlanes(5);
//        PlaneGameManager gameManager = new PlaneGameManager(planes);
//
//        // 게임 루프
//        while (true) {
//            gameManager.updateGame();
//        }
//    }
}
