package com.abasscodes.hockeyroster.screens.contactlist;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.abasscodes.hockeyroster.model.Contact;

import java.util.ArrayList;
import java.util.List;

class ContactAdapter extends RecyclerView.Adapter<ContactViewHolder> {
    private List<Contact> contacts = new ArrayList<>();
    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ContactViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, int position) {
        holder.bindContact(contacts.get(position));
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
