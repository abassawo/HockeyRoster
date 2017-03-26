package com.abasscodes.hockeyroster.mainscreen.adapter;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.abasscodes.hockeyroster.R;
import com.abasscodes.hockeyroster.base.BaseViewHolder;
import com.abasscodes.hockeyroster.model.Contact;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactViewHolder> {
    private final int layoutRes;
    private final BaseViewHolder.OnItemClickListener listener;
    private final List<Contact> contacts = new ArrayList<>();

    public ContactAdapter(BaseViewHolder.OnItemClickListener listener) {
        this(R.layout.contact_row_item, listener);
    }

    public ContactAdapter(@LayoutRes int layoutRes, BaseViewHolder.OnItemClickListener listener) {
        this.layoutRes = layoutRes;
        this.listener = listener;
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ContactViewHolder(parent, layoutRes, listener);
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, int position) {
        Contact contact = contacts.get(position);
        holder.bindContact(contact);
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public void setData(List<Contact> data) {
        contacts.clear();
        contacts.addAll(data);
        notifyDataSetChanged();
    }
}
