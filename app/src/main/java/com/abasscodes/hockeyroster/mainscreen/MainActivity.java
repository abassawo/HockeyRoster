package com.abasscodes.hockeyroster.mainscreen;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SnapHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.abasscodes.hockeyroster.R;
import com.abasscodes.hockeyroster.model.Contact;
import com.abasscodes.hockeyroster.utils.CustomSnapHelper;
import com.abasscodes.hockeyroster.utils.PresenterConfiguration;

import java.util.List;

import static android.support.v7.widget.SearchView.OnQueryTextListener;

public class MainActivity extends ListDetailActivity<MainScreenContract.Presenter> implements MainScreenContract.View {

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
    protected void initializeViews() {
        super.initializeViews();
        SnapHelper snapHelper = new CustomSnapHelper(presenter);
        snapHelper.attachToRecyclerView(detailRecyclerViewPager);
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
    public void onContactsReady(List<Contact> contacts) {
        contactListAdapter.setData(contacts);
        detailViewPagerAdapter.setData(contacts);
    }


    @Override
    public void showContactList(List<Contact> contacts) {
        bringListScreenToFront();
        rosterRecyclerView.setVisibility(View.VISIBLE);
        contactListAdapter.setData(contacts);
    }

    @Override
    public void showContact(int index) {
        bringDetailScreenToFront();
        detailRecyclerViewPager.scrollToPosition(index);
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