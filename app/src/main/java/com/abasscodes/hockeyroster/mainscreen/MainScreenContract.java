package com.abasscodes.hockeyroster.mainscreen;

import com.abasscodes.hockeyroster.base.BaseContract;
import com.abasscodes.hockeyroster.base.BaseViewHolder;
import com.abasscodes.hockeyroster.model.Contact;
import com.abasscodes.hockeyroster.network.RosterClient;

import java.util.List;

class MainScreenContract {
    interface View extends BaseContract.View {

        void onContactsReady(List<Contact> contacts);

        void showContact(int pageForContact);

        void checkInternetAccess();

        void showContactListPage();

        void dismiss();

        void setTitle(String name);

    }

    interface Presenter extends BaseContract.Presenter<View>, RosterClient.OnClientResponseListener, BaseViewHolder.OnItemClickListener{
        void onInternetAccessCheckResult(boolean internetOn);

        void onShowConnectionSettingsClicked();

        void onBackPressed();

        void onSearchEntered(String query);

        void onPageSwiped(int currentItem);
    }
}
