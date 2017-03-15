package com.abasscodes.hockeyroster.screens.mainscreen;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.abasscodes.hockeyroster.R;
import com.abasscodes.hockeyroster.base.SingleFragmentActivity;
import com.abasscodes.hockeyroster.screens.contactlist.RosterListFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends SingleFragmentActivity<MainScreenContract.Presenter> implements MainScreenContract.View{
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private Snackbar snackbar;

    @NonNull
    @Override
    public MainScreenContract.Presenter createPresenter() {
        return new MainScreenPresenter(this);
    }

    @Override
    protected Fragment createFragment() {
        return new RosterListFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        setActionBarTitle(R.string.app_name);
    }

    @Override
    public void showRosterScreen() {
        hostFragment(new RosterListFragment());
    }

    @Override
    public void dismissSnackBar() {

    }

    @Override
    public void checkInternetAccess() {
        ConnectivityManager connectivityMgr = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityMgr.getActiveNetworkInfo();
        boolean internetOn = networkInfo != null && networkInfo.isConnected();
        presenter.onInternetAccessCheckResult(internetOn);
    }

    @Override
    public void showConnectionErrorMessage() {
        View snackbarSpace = findViewById(R.id.coord_layout);
        snackbar = Snackbar.make(snackbarSpace, R.string.connection_error_msg, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.connect, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        presenter.onShowConnectionSettingsClicked();
                    }
                });
        snackbar.show();
    }

    @Override
    public void showConnectionSettings() {
        startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
    }
}
