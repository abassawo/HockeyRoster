package com.abasscodes.hockeyroster.mainscreen;

import android.support.annotation.NonNull;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SnapHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.abasscodes.hockeyroster.R;
import com.abasscodes.hockeyroster.model.Contact;
import com.abasscodes.hockeyroster.utils.CustomSnapHelper;
import com.abasscodes.hockeyroster.utils.PresenterConfiguration;

import java.util.List;

import static android.support.v7.widget.SearchView.OnQueryTextListener;

public class MainActivity extends HybridListDetailActivity<MainScreenContract.Presenter> implements MainScreenContract.View {

    @NonNull
    @Override
    public MainScreenContract.Presenter createPresenter(PresenterConfiguration configuration) {
        presenter = (MainScreenContract.Presenter) getLastCustomNonConfigurationInstance();
        presenter = presenter == null ? new MainScreenPresenter(this, configuration) : presenter;
        contactListAdapter = new ContactAdapter(presenter);
        detailViewPagerAdapter = new ContactAdapter(R.layout.contact_detail_viewpage_item,
                                                    presenter);
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
        if (!listVisible) {
            // Avoid unnecessary ActionBar toggles if listVisible
            bringListScreenToFront();
        }
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
    public void setActionBarTitle(String name) {
        toolbar.setSubtitle(name);
    }

    @Override
    public void onBackPressed() {
        presenter.onBackPressed();
    }

    @Override
    public void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
