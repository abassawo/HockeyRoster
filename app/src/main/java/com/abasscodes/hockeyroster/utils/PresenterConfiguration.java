package com.abasscodes.hockeyroster.utils;

import android.support.annotation.NonNull;

import com.abasscodes.hockeyroster.network.RosterApi;

import io.reactivex.Scheduler;

public class PresenterConfiguration {
    @NonNull
    private final Scheduler ioScheduler;
    @NonNull
    private final Scheduler uiScheduler;
    @NonNull
    private final RosterApi restApi;

    public PresenterConfiguration(@NonNull Scheduler ioScheduler,
                                  @NonNull Scheduler uiScheduler,
                                  @NonNull RosterApi restApi) {
        this.ioScheduler = ioScheduler;
        this.uiScheduler = uiScheduler;
        this.restApi = restApi;
    }

    @NonNull
    public Scheduler getIoScheduler() {
        return ioScheduler;
    }

    @NonNull
    public Scheduler getUiScheduler() {
        return uiScheduler;
    }

    @NonNull
    public RosterApi getRestApi() {
        return restApi;
    }
}