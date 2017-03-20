package com.abasscodes.hockeyroster.mainscreen;

import com.abasscodes.hockeyroster.base.BaseContract;
import com.abasscodes.hockeyroster.base.BaseViewHolder;
import com.abasscodes.hockeyroster.model.Contact;
import com.abasscodes.hockeyroster.network.RosterClient;
import com.abasscodes.hockeyroster.utils.CustomSnapHelper;

import java.util.List;

class MainScreenContract {
    interface View extends BaseContract.View {

        void onContactsReady(List<Contact> contacts);

        void showContact(int index);

        void showContactList(List<Contact> contacts);

        void dismissScreen();

        void setTitle(String name);

    }

    interface Presenter extends BaseContract.Presenter<View>, RosterClient.OnClientResponseListener,
            BaseViewHolder.OnItemClickListener, CustomSnapHelper.OnPageSwipeListener{
        void onInternetAccessCheckResult(boolean internetOn);

        void onShowConnectionSettingsClicked();

        void onBackPressed();

        void onQueryChanged(String query);
    }
}
