package com.abasscodes.hockeyroster.screens.mainscreen;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.abasscodes.hockeyroster.R;
import com.abasscodes.hockeyroster.base.BaseViewHolder;
import com.abasscodes.hockeyroster.model.Contact;
import com.squareup.picasso.Picasso;

import butterknife.BindView;

import static android.view.View.OnClickListener;

public class ContactViewHolder extends BaseViewHolder implements OnClickListener {
    @BindView(R.id.contact_name_textview)
    TextView contactNameTextView;
    @BindView(R.id.contact_team_position)
    TextView contactPositionTextField;
    @BindView(R.id.contact_avatar)
    ImageView contactAvatar;
    private Contact contact;

    ContactViewHolder(ViewGroup parent, OnItemClickListener listener) {
        super(parent, R.layout.contact_row_item, listener);
        itemView.setOnClickListener(this);
    }

    void bindContact(Contact contact) {
        this.contact = contact;
        contactNameTextView.setText(contact.getName());
        contactPositionTextField.setText(contact.getPosition());
        Picasso.with(itemView.getContext()).load(contact.getImageUrl()).into(contactAvatar);
    }

    @Override
    public void onClick(View v) {
        listener.onContactClicked(contact);
    }
}