package com.abasscodes.hockeyroster.base;

public class BaseContract {
    public interface View {
        void showConnectionSettings();

        void showMessage(String message);
    }

    public interface Presenter<T extends View> {
        void bindView(T view);

        void unbindView();

        void onViewCreated();

        void onViewDestroyed();
    }
}
