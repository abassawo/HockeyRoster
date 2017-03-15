package com.abasscodes.hockeyroster.base;

import android.support.annotation.StringRes;

public class BaseContract {
    public interface View {
        void showSnackbar(@StringRes int errorRes, @StringRes int actionTextRes);
        void dismissSnackBar();
    }

    public interface Presenter<T extends View> {
        void bindView(T view);

        void unbindView();

        void onViewCreated();

        void onViewDestroyed();
    }
}
