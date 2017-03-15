package com.abasscodes.hockeyroster.base;

import android.support.annotation.NonNull;

public abstract class BasePresenter<T extends BaseContract.View> implements BaseContract.Presenter<T> {

    protected T view;
    private boolean isViewBound = false;

    public BasePresenter(@NonNull T view) {
        this.view = view;
    }

    @Override
    public void bindView(T view) {
        this.view = view;

        if (!isViewBound) {
            onViewBound();
            isViewBound = true;
        }
    }

    public void onViewCreated() {

    }

    protected void onViewBound() {

    }

    @Override
    public void unbindView() {
        this.view = null;

        if (isViewBound) {
            onViewUnbound();
            isViewBound = false;
        }
    }

    protected void onViewUnbound() {

    }

    @Override
    public void onViewDestroyed() {

    }
}
