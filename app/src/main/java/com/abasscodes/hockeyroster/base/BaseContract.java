package com.abasscodes.hockeyroster.base;

import android.support.annotation.StringRes;

public class BaseContract {
    public interface View {
        void setActionBarTitle(String name);

        void showToast(@StringRes int message);

        void showNetworkSnackbarPrompt();
    }

    public interface Presenter<T extends View> {
        void bindView(T view);

        void unbindView();

        void onViewCreated();

        void onViewDestroyed();
    }
}
