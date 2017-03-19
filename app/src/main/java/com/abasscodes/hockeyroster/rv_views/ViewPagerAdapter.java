package com.abasscodes.hockeyroster.rv_views;

import android.view.ViewGroup;

import com.abasscodes.hockeyroster.R;
import com.abasscodes.hockeyroster.base.BaseViewHolder;
import com.abasscodes.hockeyroster.ContactAdapter;

public class ViewPagerAdapter extends ContactAdapter {

    public ViewPagerAdapter(BaseViewHolder.OnItemClickListener listener) {
        super(R.layout.contact_detail_viewpage_item, listener);
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ContactPageViewHolder(parent, listener);
    }
}