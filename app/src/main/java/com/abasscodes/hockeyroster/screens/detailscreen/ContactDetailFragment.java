package com.abasscodes.hockeyroster.screens.detailscreen;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.abasscodes.hockeyroster.R;
import com.abasscodes.hockeyroster.base.BaseFragment;
import com.abasscodes.hockeyroster.model.Contact;
import com.abasscodes.hockeyroster.utils.PresenterConfiguration;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by abass on 3/18/17.
 */

public class ContactDetailFragment extends BaseFragment<ContactDetailContract.Presenter> implements ContactDetailContract.View {
    private static final String CONTACT_EXTRA_KEY = "Extra_Key";
    @BindView(R.id.detail_contact_name_textview)
    TextView contactNameTextView;
    @BindView(R.id.detail_contact_team_position)
    TextView contactPositionTextField;
    @BindView(R.id.detail_contact_avatar)
    ImageView contactAvatar;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.contact_detail_fragment;
    }

    public static ContactDetailFragment newInstance(Contact contact) {
        Bundle args = new Bundle();
        args.putParcelable(CONTACT_EXTRA_KEY, contact);
        ContactDetailFragment fragment = new ContactDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        setActionBarDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void showConnectionSettings() {

    }

    @Override
    public void showMessage(String message) {

    }

    @NonNull
    @Override
    public ContactDetailContract.Presenter createPresenter(PresenterConfiguration configuration) {
        Contact contact = hasArguments() ? getArguments().getParcelable(CONTACT_EXTRA_KEY) : null;
        return new ContactDetailPresenter(this, configuration, contact);
    }

    @Override
    protected void handleAction(int actionText) {

    }

    @Override
    public void showContact(Contact contact) {
        contactNameTextView.setText(contact.getName());
        contactPositionTextField.setText(contact.getPosition());
        Picasso.with(contactAvatar.getContext()).load(contact.getImageUrl()).into(contactAvatar);
    }
}
