package com.abasscodes.hockeyroster;

import android.app.Application;

import timber.log.Timber;

public class RosterApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
    }
}
