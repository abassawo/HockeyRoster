package com.abasscodes.hockeyroster.screens.detailscreen;

import android.support.annotation.NonNull;

import com.abasscodes.hockeyroster.base.BasePresenter;
import com.abasscodes.hockeyroster.model.Contact;
import com.abasscodes.hockeyroster.utils.PresenterConfiguration;

class ContactDetailPresenter extends BasePresenter<ContactDetailContract.View> implements ContactDetailContract.Presenter{

    private final Contact contact;

    ContactDetailPresenter(@NonNull ContactDetailContract.View view,
                           PresenterConfiguration configuration, Contact contact) {
        super(view, configuration);
        this.contact = contact;
    }

    @Override
    public void onViewBound() {
        super.onViewBound();
        view.showContact(contact);
    }
}
