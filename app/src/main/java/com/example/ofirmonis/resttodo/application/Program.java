package com.example.ofirmonis.resttodo.application;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by programing on 23/02/2016.
 */
public class Program extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
