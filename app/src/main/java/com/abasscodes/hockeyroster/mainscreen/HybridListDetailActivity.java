package com.abasscodes.hockeyroster.mainscreen;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.abasscodes.hockeyroster.R;
import com.abasscodes.hockeyroster.base.BaseContract;
import com.abasscodes.hockeyroster.base.BaseMvpActivity;
import com.abasscodes.hockeyroster.base.HybridListDetailView;

import butterknife.BindView;

import static android.support.v7.widget.LinearLayoutManager.HORIZONTAL;

public abstract class HybridListDetailActivity<T extends BaseContract.Presenter>
        extends BaseMvpActivity<T> implements HybridListDetailView {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.roster_recycler_view)
    RecyclerView rosterRecyclerView;
    @BindView(R.id.roster_detail_recycler_viewpager)
    RecyclerView detailRecyclerViewPager;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    protected ContactAdapter contactListAdapter;
    protected ContactAdapter detailViewPagerAdapter;
    protected boolean searchIconVisible = true;
    protected boolean listVisible;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_main_roster_listdetail;
    }

    @Override
    protected void onViewCreated(Bundle savedInstanceState) {
        super.onViewCreated(savedInstanceState);
        setSupportActionBar(toolbar);
        initializeViews();
    }

    protected void initializeViews() {
        rosterRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        rosterRecyclerView.setAdapter(contactListAdapter);
        detailRecyclerViewPager.setLayoutManager(new LinearLayoutManager(this,
                                                                         HORIZONTAL,
                                                                         false));
        detailRecyclerViewPager.setAdapter(detailViewPagerAdapter);
    }

    private void resetActionBar() {
        setActionBarAsUp(false);
        toolbar.setTitle(R.string.app_name);
        appbar.setExpanded(false);
    }

    private void setSearchIconVisible(boolean visible) {
        searchIconVisible = visible;
        supportInvalidateOptionsMenu();
    }

    @Override
    public void bringListScreenToFront() {
        listVisible = true;
        resetActionBar();
        setSearchIconVisible(true);
        toolbar.setSubtitle("");
        detailRecyclerViewPager.setVisibility(View.INVISIBLE);
    }

    @Override
    public void bringDetailScreenToFront() {
        listVisible = false;
        setSearchIconVisible(false);
        appbar.setExpanded(false);
        setActionBarAsUp(true);
        detailRecyclerViewPager.setVisibility(View.VISIBLE);
        rosterRecyclerView.setVisibility(View.INVISIBLE);
    }
}
