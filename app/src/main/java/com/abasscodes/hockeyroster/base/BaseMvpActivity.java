package com.abasscodes.hockeyroster.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.abasscodes.hockeyroster.RosterApplication;
import com.abasscodes.hockeyroster.utils.PresenterConfiguration;

import butterknife.ButterKnife;

public abstract class BaseMvpActivity<T extends BaseContract.Presenter> extends AppCompatActivity implements BaseContract.View {

    protected T presenter;

    @NonNull
    public abstract T createPresenter(PresenterConfiguration configuration);

    protected abstract  @LayoutRes int getLayoutRes();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutRes());
        ButterKnife.bind(this);
        presenter = createPresenter(getRosterApplication().getPresenterConfiguration());
        onViewCreated(savedInstanceState);
        presenter.onViewCreated();
    }

    private RosterApplication getRosterApplication() {
        return (RosterApplication) getApplication();
    }

    protected void setActionBarTitle(@StringRes int titleResource) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(titleResource);
        }
    }

    protected void setActionBarTitle(String title) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
    }

    protected void setActionBarAsUp(boolean upNavigation){
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(upNavigation);
        }
    }

    @Override
    public void showMessage(String message) {

    }

    protected void onViewCreated(Bundle savedInstanceState) {

    }

    @Override
    @CallSuper
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        presenter.bindView(this);
    }

    @Override
    @CallSuper
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        presenter.bindView(this);
    }

    @Override
    @CallSuper
    public void onStart() {
        super.onStart();
        presenter.bindView(this);
    }

    @Override
    @CallSuper
    public void onStop() {
        super.onStop();
        presenter.unbindView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onViewDestroyed();
    }
}
