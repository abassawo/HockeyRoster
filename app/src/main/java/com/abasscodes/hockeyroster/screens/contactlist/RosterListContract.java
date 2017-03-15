package com.abasscodes.hockeyroster.screens.contactlist;

import com.abasscodes.hockeyroster.base.BaseContract;
import com.abasscodes.hockeyroster.model.Contact;
import com.abasscodes.hockeyroster.network.RosterClient;

import java.util.List;

class RosterListContract {
    interface View extends BaseContract.View {
        void showContacts(List<Contact> contacts);

        void showError(String errorMsg);
    }

    interface Presenter extends BaseContract.Presenter<View>, RosterClient.OnClientResponseListener {
    }
}
