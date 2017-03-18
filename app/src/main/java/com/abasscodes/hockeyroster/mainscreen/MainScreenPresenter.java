package com.abasscodes.hockeyroster.mainscreen;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;

import com.abasscodes.hockeyroster.base.BasePresenter;
import com.abasscodes.hockeyroster.model.Contact;
import com.abasscodes.hockeyroster.model.ContactWrapper;
import com.abasscodes.hockeyroster.network.RosterClient;

import java.util.List;

class MainScreenPresenter extends BasePresenter<MainScreenContract.View> implements MainScreenContract.Presenter {
    private boolean detailMode = false;
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
        }
    }

    @Override
    public void onSearchEntered(String query) {

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

    private void showDetail(int position){
        detailMode = true;
        currentDetailPage = position;
        view.showContact(position);
        view.setTitle(contacts.get(position).getName());
    }

    private void showList() {
        detailMode = false;
        view.showContactListPage();
    }

    @Override
    public void onItemClicked(int position) {
        showDetail(position);
    }

    @Override
    public void onRosterLoaded(ContactWrapper roster) {
        this.contacts = roster.getContacts();
        view.onContactsReady(roster.getContacts());
        if(detailMode){
            view.showContactListPage();
        } else {
            showList();
        }
    }

    @Override
    public void onFailure(String errorMsg) {
//        view.showMessage(errorMsg);
    }
}