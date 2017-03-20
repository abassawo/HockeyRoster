package com.abasscodes.hockeyroster.mainscreen;

import com.abasscodes.hockeyroster.model.Contact;
import com.abasscodes.hockeyroster.model.ContactWrapper;
import com.abasscodes.hockeyroster.testUtils.BasePresenterTest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.Iterator;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by abass on 3/18/17.
 */

public class MainScreenPresenterTest extends BasePresenterTest<MainScreenPresenter> {
    @Mock
    MainScreenContract.View mockView;
    @Mock
    List<Contact> mockContacts;
    @Mock
    Contact mockContact;
    @Mock
    Iterator<Contact> mockIterator;

    @Before
    public void setup() {
        presenter = new MainScreenPresenter(mockView, testConfiguration);
        presenter.onViewCreated();
        when(mockContacts.size()).thenReturn(14);
        ContactWrapper contactWrapper = new ContactWrapper(mockContacts);
        presenter.onRosterLoaded(contactWrapper);
    }


    @Test
    public void onViewBoundShouldCauseViewToCheckInternetAccess() {
        presenter.onViewBound();
        verify(mockView).checkInternetAccess();
    }

    @Test
    public void onBackPressedShouldCauseViewToGoToListWhenInDetailMode() {
        presenter.onBackPressed();
        verify(mockView).showContactList(any());
    }

    @Test
    public void onRosterLoadedWhenInDetailModeShouldCauseViewToReloadDetailPage() {
        when(mockContact.getName()).thenReturn("Tester");
        when(mockContacts.get(anyInt())).thenReturn(mockContact);

        presenter.onContactClicked(mockContact);
        presenter.onRosterLoaded(new ContactWrapper(mockContacts));

        verify(mockView, times(2)).showContact(anyInt());
        verify(mockView, times(2)).setActionBarTitle(anyString());
    }

    @Test
    public void onContactClickedShouldCauseViewToHideKeyboardAndShowContact() {
        presenter.onContactClicked(mockContact);

        verify(mockView).hideKeyboard();
        verify(mockView).showContact(anyInt());
    }

    @Test
    public void onQueryChangedShouldDoNothingWhenDataIsNull() {
        presenter.onRosterLoaded(new ContactWrapper(null));
        presenter.onQueryChanged("Test");
        verify(mockView, times(1)).showContactList(anyList());

    }

    @Test
    public void onQueryChangedToZeroLengthStringShouldCauseViewToShowOriginalListAgain() {
        presenter.onViewBound();
        String EMPTY_TEXT = "";

        presenter.onQueryChanged(EMPTY_TEXT);

        verify(mockView, times(2)).showContactList(mockContacts);
    }

    @Test
    public void onQueryChangedToValidTextShouldCauseViewToShowSearchedSubList() {
        when(mockIterator.next()).thenReturn(mockContact);
        when(mockContacts.iterator()).thenReturn(mockIterator);

        String VALID_TEXT = "test";
        presenter.onQueryChanged(VALID_TEXT);

        verify(mockView).showContactList(argThat(argument -> argument.size() < mockContacts.size()));
    }

    @Test
    public void onBackPressedWhenInDetailModeShouldCauseViewToReturnToListScreen() {
        presenter.onContactClicked(mockContact);
        presenter.onBackPressed();
        verify(mockView, times(2)).showContactList(anyList());
    }

    @Test
    public void onViewBoundShouldCauseViewToResumeListPageIfDetailModeIsTrue() {
        when(mockContacts.get(anyInt())).thenReturn(mockContact);
        presenter.onContactClicked(mockContact);
        verify(mockView).showContact(anyInt());
    }

    @Test
    public void onPageSwipedShouldCauseViewToChangeTitleTextWhenInBounds() {
        when(mockContacts.size()).thenReturn(14);
        when(mockContacts.get(anyInt())).thenReturn(mockContact);
        when(mockContact.getName()).thenReturn("Tester");

        presenter.onViewBound();
        presenter.onPageSwiped(0);

        verify(mockView).setActionBarTitle(anyString());
    }

    @Test
    public void onInternetAccessCheckResultNegativeShouldCauseViewToShowNetworkSnackbar() {
        presenter.onInternetAccessCheckResult(false);
        verify(mockView).showNetworkSnackbarPrompt();
    }

    @Test
    public void onInternetAccessCheckResultPositiveShouldTriggerApiCall() {
        presenter.onInternetAccessCheckResult(true);
        testConfiguration.triggerRxSchedulers();
        verify(mockView).onContactsReady(mockContacts);
    }

    @Test
    public void onRosterLoadingFailureShouldCauseViewToShowErrorMessage() {
        presenter.onRosterLoadFailure(anyString());
        verify(mockView).showToast(anyInt());
    }
}
