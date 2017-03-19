package com.abasscodes.hockeyroster.screens.mainscreen;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.abasscodes.hockeyroster.base.BasePresenter;
import com.abasscodes.hockeyroster.model.Contact;
import com.abasscodes.hockeyroster.model.ContactWrapper;
import com.abasscodes.hockeyroster.network.RosterClient;
import com.abasscodes.hockeyroster.utils.PresenterConfiguration;
import com.abasscodes.hockeyroster.utils.TextFilterHelper;

import java.util.List;

import io.reactivex.disposables.Disposable;
import timber.log.Timber;

class MainScreenPresenter extends BasePresenter<MainScreenContract.View> implements MainScreenContract.Presenter {
    private boolean detailMode = false;
    private List<Contact> contacts = null;
    private int currentDetailPage = 0;
    private Disposable disposable;

    MainScreenPresenter(@NonNull MainScreenContract.View view,
                        PresenterConfiguration configuration) {
        super(view, configuration);
    }

    @Override
    protected void onViewBound() {
        super.onViewBound();
        Timber.d("Detail mode " + detailMode);

        if (contacts != null) {
            view.onContactsReady(contacts);
            showDetail(contacts.get(currentDetailPage));
        }

        view.checkInternetAccess();
    }

    @Override
    public void onInternetAccessCheckResult(boolean internetOn) {
        if (internetOn) {
            disposable = RosterClient.getInstance(this).loadRosterList(configuration);
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
        if (detailMode) {
            showList();
        } else {
            view.dismiss();
        }
    }

    @Override
    public void onQueryChanged(String query) {
        if (contacts == null) {
            return;
        }
        if (query.length() > 0) {
            final List<Contact> filteredContacts = TextFilterHelper.filter(contacts, query);
            view.showContactList(filteredContacts);
            if (detailMode) {
                view.navigateBackToListScreen();
            }
        } else {
            view.showContactList(contacts);
        }
    }

    @Override
    public void onPageSwiped(int currentItem) {
        if (currentItem < contacts.size()) {
            view.setTitle(contacts.get(currentItem).getName());
        }
    }

    private void showDetail(Contact contact) {
        detailMode = true;
        currentDetailPage = contacts.indexOf(contact);
        view.showContact(currentDetailPage);
        view.navigateBackToDetailScreen();
        view.setTitle(contact.getName());
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
        if (detailMode) {
            showDetail(contacts.get(currentDetailPage));
        } else {
            view.showContactList(contacts);
        }
    }

    @Override
    public void onFailure(String errorMsg) {
        if (view != null) {
            view.showMessage(errorMsg);
        }
    }

    @Override
    public void onViewDestroyed() {
        super.onViewDestroyed();
        disposable.dispose();
    }
}