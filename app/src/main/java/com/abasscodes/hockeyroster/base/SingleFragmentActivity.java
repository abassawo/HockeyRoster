package com.abasscodes.hockeyroster.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.abasscodes.hockeyroster.R;

public abstract class SingleFragmentActivity<P extends BaseContract.Presenter> extends BaseMvpActivity<P> {

    protected abstract Fragment createFragment();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setActionBarTitle(R.string.app_name);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    protected void setActionBarTitle(@StringRes int titleResource) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(titleResource);
        }
    }

    protected void hostFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    @Override
    public void showSnackbar(@StringRes int messageResource, @StringRes final int actionText) {
        View snackbarSpace = findViewById(R.id.coord_layout);
        Snackbar.make(snackbarSpace, messageResource, Snackbar.LENGTH_SHORT)
                .setAction(actionText, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //todo
                    }
                })
                .show();
    }
}
