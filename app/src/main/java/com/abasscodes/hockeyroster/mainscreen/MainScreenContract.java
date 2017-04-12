package com.abasscodes.hockeyroster.mainscreen;

import com.abasscodes.hockeyroster.base.BaseContract;
import com.abasscodes.hockeyroster.model.Contact;
import com.abasscodes.hockeyroster.utils.NetworkVerifier;

import java.util.List;

import static com.abasscodes.hockeyroster.base.BaseViewHolder.OnItemClickListener;
import static com.abasscodes.hockeyroster.network.RosterClient.OnClientResponseListener;
import static com.abasscodes.hockeyroster.utils.CustomSnapHelper.OnPageSwipeListener;

class MainScreenContract {
    interface View extends BaseContract.View {

        void onContactsReady(List<Contact> contacts);

        void showContact(int index);

        void showContactList(List<Contact> contacts);

        void hideKeyboard();

        void dismissScreen();

        NetworkVerifier getNetworkVerifier();
    }

    interface Presenter extends BaseContract.Presenter<View>, OnClientResponseListener,
            OnItemClickListener, OnPageSwipeListener {
        void onBackPressed();

        void onQueryChanged(String query);
    }
}
