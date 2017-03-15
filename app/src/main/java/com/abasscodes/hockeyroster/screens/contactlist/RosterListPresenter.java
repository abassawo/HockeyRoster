package com.abasscodes.hockeyroster.screens.contactlist;

import android.support.annotation.NonNull;

import com.abasscodes.hockeyroster.base.BasePresenter;
import com.abasscodes.hockeyroster.model.ContactWrapper;
import com.abasscodes.hockeyroster.network.RosterClient;

import timber.log.Timber;

class RosterListPresenter extends BasePresenter<RosterListContract.View> implements RosterListContract.Presenter {

    RosterListPresenter(@NonNull RosterListContract.View view) {
        super(view);
        RosterClient client = RosterClient.getInstance(this);
        client.loadRosterList();
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();
    }

    @Override
    public void onRosterLoaded(ContactWrapper contactWrapper) {
        Timber.d(" Size " + contactWrapper.getContacts().size());
        view.showContacts(contactWrapper.getContacts());
    }

    @Override
    public void onFailure(String errorMsg) {
        Timber.d("Failure retrofitting " + errorMsg);
        view.showError(errorMsg);
    }
}
