package com.abasscodes.hockeyroster.mainscreen;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.abasscodes.hockeyroster.PageChangedListener;
import com.abasscodes.hockeyroster.R;
import com.abasscodes.hockeyroster.ViewPagerAdapter;
import com.abasscodes.hockeyroster.base.BaseMvpActivity;
import com.abasscodes.hockeyroster.mainscreen.showcontactsrv.ContactAdapter;
import com.abasscodes.hockeyroster.model.Contact;

import java.util.List;

import butterknife.BindView;

import static android.support.v7.widget.SearchView.OnQueryTextListener;

public class MainActivity extends BaseMvpActivity<MainScreenContract.Presenter> implements MainScreenContract.View {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.roster_recycler_view)
    RecyclerView rosterRecyclerView;
    @BindView(R.id.roster_detail_viewpager)
    ViewPager detailViewPager;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.image_backdrop)
    ImageView imageBackdrop;
    private ContactAdapter contactListAdapter;
    private ViewPagerAdapter detailViewPagerAdapter;
    private boolean searchIconVisible = true;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_main;
    }

    @NonNull
    @Override
    public MainScreenContract.Presenter createPresenter() {
        presenter = (MainScreenContract.Presenter) getLastCustomNonConfigurationInstance();
        presenter = presenter == null ? new MainScreenPresenter(this) : presenter;
        contactListAdapter = new ContactAdapter(presenter);
        detailViewPagerAdapter = new ViewPagerAdapter(this);
        presenter.bindView(this);
        return presenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);
        contactListAdapter = new ContactAdapter(presenter);
        detailViewPagerAdapter = new ViewPagerAdapter(this);
        initializeViews();
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return presenter;
    }

    private void initializeViews() {
        rosterRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        rosterRecyclerView.setAdapter(contactListAdapter);
        detailViewPager.setAdapter(detailViewPagerAdapter);
        detailViewPager.addOnPageChangeListener(new PageChangedListener() {
            @Override
            public void onPageChanged() {
                super.onPageChanged();
                presenter.onPageSwiped(detailViewPager.getCurrentItem());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem searchItem = menu.findItem(R.id.menu_item_search);
        searchItem.setVisible(searchIconVisible);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnSearchClickListener(v -> presenter.onSearchEntered(String.valueOf(
                searchView.getQuery())));

        searchView.setOnQueryTextListener(new OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                presenter.onSearchEntered(query);
                searchView.onActionViewCollapsed();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
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
    public void showContactListPage() {
        setActionBarAsUp(false);
        toolbar.setTitle(R.string.app_name);
        toolbar.setSubtitle("");
        collapsingToolbar.setTitle(getString(R.string.app_name));
        appbar.setExpanded(false);
        searchIconVisible = true;
        supportInvalidateOptionsMenu();
        detailViewPager.setVisibility(View.GONE);
        rosterRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onContactsReady(List<Contact> contacts) {
        contactListAdapter.setData(contacts);
        detailViewPagerAdapter.setData(contacts);
    }

    @Override
    public void showContact(int index) {
        appbar.setExpanded(false);
        searchIconVisible = false;
        setActionBarAsUp(true);
        supportInvalidateOptionsMenu();
        rosterRecyclerView.setVisibility(View.GONE);
        detailViewPager.setCurrentItem(index);
        detailViewPager.setVisibility(View.VISIBLE);
    }

    @Override
    public void dismiss() {
        super.onBackPressed();
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