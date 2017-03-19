package com.abasscodes.hockeyroster.screens.detailscreen;

import android.support.annotation.NonNull;

import com.abasscodes.hockeyroster.base.BasePresenter;
import com.abasscodes.hockeyroster.model.Contact;

/**
 * Created by abass on 3/18/17.
 */

class ContactDetailPresenter extends BasePresenter<ContactDetailContract.View> implements ContactDetailContract.Presenter{

    private final Contact contact;

    ContactDetailPresenter(@NonNull ContactDetailContract.View view, Contact contact) {
        super(view);
        this.contact = contact;
    }

    @Override
    public void onViewBound() {
        super.onViewBound();
        view.showContact(contact);
    }
}
