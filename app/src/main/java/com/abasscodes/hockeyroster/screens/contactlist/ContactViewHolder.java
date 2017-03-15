package com.abasscodes.hockeyroster.screens.contactlist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.abasscodes.hockeyroster.R;
import com.abasscodes.hockeyroster.model.Contact;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

class ContactViewHolder extends RecyclerView.ViewHolder{
    @BindView(R.id.contact_name_textview)
    TextView contactNameTextView;
    @BindView(R.id.contact_team_position)
    TextView contactPositionTextField;
    @BindView(R.id.contact_avatar)
    ImageView contactAvatar;
    private Contact contact;

    ContactViewHolder(ViewGroup parent) {
        super(inflateView(parent));
        ButterKnife.bind(this, itemView);
    }

    private static View inflateView(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return inflater.inflate(R.layout.contact_item, parent, false);
    }

    void bindContact(Contact contact) {
        this.contact = contact;
        contactNameTextView.setText(contact.getName());
        contactPositionTextField.setText(contact.getPosition());
        Picasso.with(itemView.getContext()).load(contact.getImageUrl()).into(contactAvatar);
    }
}
