package com.abasscodes.hockeyroster.base;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.abasscodes.hockeyroster.RosterApplication;
import com.abasscodes.hockeyroster.utils.PresenterConfiguration;

import butterknife.ButterKnife;

public abstract class BaseMvpActivity<T extends BaseContract.Presenter> extends AppCompatActivity implements BaseContract.View {

    protected T presenter;

    @NonNull
    public abstract T createPresenter(PresenterConfiguration configuration);

    protected abstract
    @LayoutRes
    int getLayoutRes();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutRes());
        ButterKnife.bind(this);
        presenter = createPresenter(getRosterApplication().getPresenterConfiguration());
        onViewCreated(savedInstanceState);
        presenter.onViewCreated();
    }

    protected void onViewCreated(Bundle savedInstanceState) {
        //For subclass implementation
    }

    @Override
    public void showToast(@StringRes int message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void checkInternetAccess() {
        ConnectivityManager connectivityMgr = (ConnectivityManager) getSystemService(
                CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityMgr.getActiveNetworkInfo();
        boolean internetOn = networkInfo != null && networkInfo.isConnected();
        presenter.onInternetAccessCheckResult(internetOn);
    }

    protected void showConnectionSettings() {
        startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        //Used to persist presenter data throughout configuration changes.
        return presenter;
    }

    private RosterApplication getRosterApplication() {
        return (RosterApplication) getApplication();
    }

    protected void setActionBarAsUp(boolean upNavigation) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(upNavigation);
        }
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
