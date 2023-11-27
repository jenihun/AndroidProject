package com.cookandroid.main;
import java.util.ArrayList;
import java.util.List;

public class PlaneGameManager {

    private int selectPeoplenum;
    private List<PaperPlane> paperPlanes;
    private List<PaperPlane> defeatedPlanes; // 패배한 비행기들을 저장하는 리스트

    private NaturalDisaster naturalDisaster;

    public PlaneGameManager(List<PaperPlane> paperPlanes, int selectPeoplenum) {
        this.paperPlanes = paperPlanes;
        this.selectPeoplenum = selectPeoplenum;
        this.defeatedPlanes = new ArrayList<>();
    }

    public boolean updateGame() {
        int crashedCount = 0;

        // 종이비행기 리스트를 순회하면서 내구도가 0이하인 비행기를 찾음
        for (PaperPlane plane : paperPlanes) {
            if (plane.getDurability() <= 0) {
                crashedCount++;
                defeatedPlanes.add(plane); // 패배한 비행기 리스트에 추가

                // 종이비행기가 추락한 경우 추가적인 처리를 할 수 있음
                // 예를 들어, 각 비행기의 상태를 로그로 출력하거나 다른 동작을 수행할 수 있음
            }
        }

        // 자연재해 이벤트 발생
        naturalDisaster.randomEvent(paperPlanes);

        // 추락한 비행기 수가 벌칙 인원 수보다 많으면 게임 종료
        if (crashedCount >= selectPeoplenum) {
            return true;
        }
        else{
            return false;
        }
    }

    // 패배한 비행기들의 정보를 반환
    public List<PaperPlane> getDefeatedPlanes() {
        return defeatedPlanes;
    }
}