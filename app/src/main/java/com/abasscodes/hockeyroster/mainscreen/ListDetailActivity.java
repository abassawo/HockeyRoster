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
import com.abasscodes.hockeyroster.base.ListDetailView;

import butterknife.BindView;

public abstract class ListDetailActivity<T extends BaseContract.Presenter>
        extends BaseMvpActivity<T> implements ListDetailView {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.roster_recycler_view)
    RecyclerView rosterRecyclerView;
    @BindView(R.id.roster_detail_recycler_viewpager)
    RecyclerView detailRecyclerViewPager;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    protected ContactAdapter contactListAdapter;
    protected ContactAdapter detailViewPagerAdapter;
    protected boolean searchIconVisible = true;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_main_roster_listdetail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);
        initializeViews();
    }

    protected void initializeViews() {
        rosterRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        rosterRecyclerView.setAdapter(contactListAdapter);
        detailRecyclerViewPager.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        detailRecyclerViewPager.setAdapter(detailViewPagerAdapter);
    }

    @Override
    public void bringListScreenToFront() {
        setActionBarAsUp(false);
        toolbar.setTitle(R.string.app_name);
        toolbar.setSubtitle("");
        collapsingToolbar.setTitle(getString(R.string.app_name));
        appbar.setExpanded(false);
        searchIconVisible = true;
        supportInvalidateOptionsMenu();
        detailRecyclerViewPager.setVisibility(View.INVISIBLE);
    }

    @Override
    public void bringDetailScreenToFront() {
        appbar.setExpanded(false);
        searchIconVisible = false;
        setActionBarAsUp(true);
        supportInvalidateOptionsMenu();
        detailRecyclerViewPager.setVisibility(View.VISIBLE);
        rosterRecyclerView.setVisibility(View.INVISIBLE);
    }
}
