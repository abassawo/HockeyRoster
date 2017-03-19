package com.abasscodes.hockeyroster.screens.mainscreen;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.abasscodes.hockeyroster.base.BasePresenter;
import com.abasscodes.hockeyroster.model.Contact;
import com.abasscodes.hockeyroster.model.ContactWrapper;
import com.abasscodes.hockeyroster.network.RosterClient;
import com.abasscodes.hockeyroster.utils.TextFilterHelper;

import java.util.List;

class MainScreenPresenter extends BasePresenter<MainScreenContract.View> implements MainScreenContract.Presenter {
    @VisibleForTesting
    boolean detailMode = false;
    private List<Contact> contacts = null;
    private int currentDetailPage = 0;

    MainScreenPresenter(@NonNull MainScreenContract.View view) {
        super(view);
    }

    @Override
    protected void onViewBound() {
        super.onViewBound();
        if(detailMode) {
            if (contacts != null) {
                view.onContactsReady(contacts);
                showDetail(contacts.get(currentDetailPage));
            }
        }
        view.checkInternetAccess();
    }

    @Override
    public void onInternetAccessCheckResult(boolean internetOn) {
        if (internetOn) {
            RosterClient.getInstance(this).loadRosterList();
        } else {
            view.showMessage("Error retrofitting");
        }
    }

    @Override
    public void onShowConnectionSettingsClicked() {
        view.showConnectionSettings();
    }

    @Override
    public void onBackPressed() {
        if(detailMode){
            doBackwardsDetailNavigation();
        } else {
            //have view do super on backpressed.
        }
    }

    @Override
    public void onQueryChanged(String query) {
        if (query.length() > 0){
            final List<Contact> filteredContacts = TextFilterHelper.filter(contacts, query);
            view.showContactList(filteredContacts);
            if (detailMode){
                view.navigateBackToListScreen();
            }
        } else {
            view.showContactList(contacts);
        }
    }

    @Override
    public void onPageSwiped(int currentItem) {
        if(currentItem < contacts.size()) {
            view.setTitle(contacts.get(currentItem).getName());
        }
    }

    private void doBackwardsDetailNavigation() {
        if(detailMode){
            showList();
        } else {
            view.dismiss();
        }
    }

    private void showDetail(Contact contact){
        detailMode = true;
        currentDetailPage = contacts.indexOf(contact);
        view.showContact(currentDetailPage);
        view.setTitle(contact.getName());
        view.navigateBackToDetailScreen();
    }

    private void showList() {
        detailMode = false;
        view.showContactList(contacts);
        view.navigateBackToListScreen();
    }

    @Override
    public void onContactClicked(Contact contact) {
        showDetail(contact);
    }

    @Override
    public void onRosterLoaded(ContactWrapper roster) {
        this.contacts = roster.getContacts();
        view.onContactsReady(contacts);
        if(detailMode){
            view.showContactList(contacts);
        } else {
            showList();
        }
    }

    @Override
    public void onFailure(String errorMsg) {
        view.showMessage(errorMsg);
    }
}