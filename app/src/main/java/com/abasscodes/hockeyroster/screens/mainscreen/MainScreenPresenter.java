package com.abasscodes.hockeyroster.screens.mainscreen;

import android.support.annotation.NonNull;

import com.abasscodes.hockeyroster.base.BasePresenter;

public class MainScreenPresenter extends BasePresenter<MainScreenContract.View> implements MainScreenContract.Presenter {

    MainScreenPresenter(@NonNull MainScreenContract.View view) {
        super(view);
    }

    @Override
    protected void onViewBound() {
        super.onViewBound();
        view.checkInternetAccess();
    }

    @Override
    public void onInternetAccessCheckResult(boolean internetOn) {
        if (internetOn) {
            view.showRosterScreen();
        } else {
            view.showConnectionErrorMessage();
        }
    }

    @Override
    public void onShowConnectionSettingsClicked() {
        view.showConnectionSettings();
    }
}
