package com.abasscodes.hockeyroster.base;

public class BaseContract {
    public interface View {
        void showConnectionSettings();

        void checkInternetAccess();

        void showMessage(String message);
    }

    public interface Presenter<T extends View> {
        void onInternetAccessCheckResult(boolean connected);

        void bindView(T view);

        void unbindView();

        void onViewCreated();

        void onViewDestroyed();
    }
}
