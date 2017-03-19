package com.abasscodes.hockeyroster.mainscreen;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SnapHelper;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.abasscodes.hockeyroster.R;
import com.abasscodes.hockeyroster.base.BaseMvpActivity;
import com.abasscodes.hockeyroster.model.Contact;
import com.abasscodes.hockeyroster.utils.CustomSnapHelper;
import com.abasscodes.hockeyroster.utils.PresenterConfiguration;

import java.util.List;

import butterknife.BindView;

import static android.support.v7.widget.SearchView.OnQueryTextListener;

public class MainActivity extends BaseMvpActivity<MainScreenContract.Presenter> implements MainScreenContract.View {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.roster_recycler_view)
    RecyclerView rosterRecyclerView;
    @BindView(R.id.roster_detail_viewpager)
    RecyclerView detailViewPager;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.image_backdrop)
    ImageView imageBackdrop;
    private ContactAdapter contactListAdapter;
    private ContactAdapter detailViewPagerAdapter;
    private boolean searchIconVisible = true;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_main;
    }

    @NonNull
    @Override
    public MainScreenContract.Presenter createPresenter(PresenterConfiguration configuration) {
        presenter = (MainScreenContract.Presenter) getLastCustomNonConfigurationInstance();
        presenter = presenter == null ? new MainScreenPresenter(this, configuration) : presenter;
        contactListAdapter = new ContactAdapter(presenter);
        detailViewPagerAdapter = new ContactAdapter(R.layout.contact_detail_viewpage_item, presenter);
        presenter.bindView(this);
        return presenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);
        initializeViews();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        detailViewPager.setVisibility(View.VISIBLE);
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return presenter;
    }

    private void initializeViews() {
        rosterRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        rosterRecyclerView.setAdapter(contactListAdapter);
        detailViewPager.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        detailViewPager.setAdapter(detailViewPagerAdapter);
        SnapHelper snapHelper = new CustomSnapHelper(presenter);
        snapHelper.attachToRecyclerView(detailViewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem searchItem = menu.findItem(R.id.menu_item_search);
        searchItem.setVisible(searchIconVisible);
        final SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                presenter.onQueryChanged(newText);
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                presenter.onBackPressed();
                break;
        }
        return true;
    }

    @Override
    public void showContactList(List<Contact> contacts) {
        rosterRecyclerView.setVisibility(View.VISIBLE);
        contactListAdapter.setData(contacts);
    }
    @Override
    public void onContactsReady(List<Contact> contacts) {
        contactListAdapter.setData(contacts);
        detailViewPagerAdapter.setData(contacts);
    }

    @Override
    public void showContact(int index) {
        navigateBackToDetailScreen();
        detailViewPager.scrollToPosition(index);
    }

    @Override
    public void navigateBackToListScreen() {
        setActionBarAsUp(false);
        toolbar.setTitle(R.string.app_name);
        toolbar.setSubtitle("");
        collapsingToolbar.setTitle(getString(R.string.app_name));
        appbar.setExpanded(false);
        searchIconVisible = true;
        supportInvalidateOptionsMenu();
        detailViewPager.setVisibility(View.INVISIBLE);
    }


    @Override
    public void navigateBackToDetailScreen() {
        appbar.setExpanded(false);
        searchIconVisible = false;
        setActionBarAsUp(true);
        supportInvalidateOptionsMenu();
        detailViewPager.setVisibility(View.VISIBLE);
        rosterRecyclerView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void dismissScreen() {
        moveTaskToBack(true);
    }

    @Override
    public void setTitle(String name) {
        collapsingToolbar.setTitle(name);
        toolbar.setSubtitle(name);
    }

    @Override
    public void onBackPressed() {
        presenter.onBackPressed();
    }

    @Override
    public void checkInternetAccess() {
        ConnectivityManager connectivityMgr = (ConnectivityManager) getSystemService(
                CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityMgr.getActiveNetworkInfo();
        boolean internetOn = networkInfo != null && networkInfo.isConnected();
        presenter.onInternetAccessCheckResult(internetOn);
    }


    @Override
    public void showConnectionSettings() {
        startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
    }
}