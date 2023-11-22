package com.cookandroid.main;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;

public class FMS extends FirebaseMessagingService {
    @Override
    public void onNewToken(@NonNull String token){
        super.onNewToken(token);
        Log.d("FCM Token", token);
    }
}
