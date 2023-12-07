package com.cookandroid.main;

public interface GameManagerListener {
    void onGameFinished(boolean isGameover);
    void onEventMessageReceived(String eventMessage);
}
