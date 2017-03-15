package com.abasscodes.hockeyroster.screens.mainscreen;

import com.abasscodes.hockeyroster.base.BaseContract;

class MainScreenContract {
    interface View extends BaseContract.View {
        void checkInternetAccess();

        void showRosterScreen();

        void showConnectionErrorMessage();

        void showConnectionSettings();
    }

    interface Presenter extends BaseContract.Presenter<View> {
        void onInternetAccessCheckResult(boolean internetOn);

        void onShowConnectionSettingsClicked();
    }
}
