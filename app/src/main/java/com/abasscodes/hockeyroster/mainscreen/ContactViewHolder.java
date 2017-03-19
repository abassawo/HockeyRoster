package com.abasscodes.hockeyroster.mainscreen;

import android.support.annotation.LayoutRes;
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

    public ContactViewHolder(ViewGroup parent, @LayoutRes int layoutRes, OnItemClickListener listener) {
        super(parent, layoutRes, listener);
    }

    public void bindContact(Contact contact) {
        this.contact = contact;
        contactNameTextView.setText(contact.getName());
        contactPositionTextField.setText(contact.getPosition());
        final String imageUrl = contact.getImageUrl();
        Picasso.with(itemView.getContext()).load(imageUrl).into(contactAvatar);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        listener.onContactClicked(contact);
    }
}