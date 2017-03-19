package com.abasscodes.hockeyroster.base;

import android.support.annotation.NonNull;

import com.abasscodes.hockeyroster.network.RosterApi;
import com.abasscodes.hockeyroster.utils.PresenterConfiguration;

import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;

public abstract class BasePresenter<T extends BaseContract.View> implements BaseContract.Presenter<T> {

    @NonNull
    protected final Scheduler ioScheduler;
    @NonNull
    protected final Scheduler uiScheduler;
    @NonNull
    protected final RosterApi restApi;
    protected final PresenterConfiguration configuration;
    protected T view;
    private boolean isViewBound = false;
    protected Disposable disposable;

    public BasePresenter(@NonNull T view, PresenterConfiguration configuration) {
        this.view = view;
        this.configuration = configuration;
        this.ioScheduler = configuration.getIoScheduler();
        this.uiScheduler = configuration.getUiScheduler();
        this.restApi = configuration.getRestApi();
    }

    @Override
    public void bindView(T view) {
        this.view = view;

        onViewBound();
        isViewBound = true;
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
        disposable.dispose();
    }
}
