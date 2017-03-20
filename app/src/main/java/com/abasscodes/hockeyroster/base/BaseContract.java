package com.abasscodes.hockeyroster.base;

import android.support.annotation.StringRes;

public class BaseContract {
    public interface View {
        void setActionBarTitle(String name);

        void checkInternetAccess();

        void showToast(@StringRes int message);

        void showNetworkSnackbarPrompt();
    }

    public interface Presenter<T extends View> {
        void onInternetAccessCheckResult(boolean connected);

        void bindView(T view);

        void unbindView();

        void onViewCreated();

        void onViewDestroyed();
    }
}
