package com.abasscodes.hockeyroster.utils;

import android.support.v4.view.ViewPager;

/**
 * Created by abass on 3/18/17.
 */

public abstract class PageChangedListener implements ViewPager.OnPageChangeListener {

    public void onPageChanged() {
        
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        onPageChanged();
    }

    @Override
    public void onPageSelected(int position) {
        onPageChanged();
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        onPageChanged();
    }
}
