package com.abasscodes.hockeyroster;

import android.app.Application;

import com.abasscodes.hockeyroster.network.MockRosterApi;
import com.abasscodes.hockeyroster.network.RosterApi;
import com.abasscodes.hockeyroster.network.RosterClient;
import com.abasscodes.hockeyroster.utils.PresenterConfiguration;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class RosterApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
    }

    public PresenterConfiguration getPresenterConfiguration() {
        return getPresenterConfiguration(false);
    }

    public PresenterConfiguration getPresenterConfiguration(boolean mock) {
        RosterApi rosterApi = mock ? new MockRosterApi() : RosterClient.getApi();
        return new PresenterConfiguration(Schedulers.io(),
                                          AndroidSchedulers.mainThread(), rosterApi);
    }
}
