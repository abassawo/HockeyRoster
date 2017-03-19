package com.abasscodes.hockeyroster.rv_views;

import android.view.ViewGroup;

import com.abasscodes.hockeyroster.R;
import com.abasscodes.hockeyroster.model.Contact;

public class ContactPageViewHolder extends ContactViewHolder {
    public ContactPageViewHolder(ViewGroup parent,
                                 OnItemClickListener listener) {
        super(parent, R.layout.contact_detail_viewpage_item, listener);
    }

    @Override
    public void bindContact(Contact contact) {
        super.bindContact(contact);
        itemView.setOnClickListener(null);
    }
}
