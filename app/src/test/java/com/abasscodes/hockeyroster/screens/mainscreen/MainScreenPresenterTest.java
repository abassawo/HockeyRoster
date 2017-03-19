package com.abasscodes.hockeyroster.screens.mainscreen;

import com.abasscodes.hockeyroster.base.BasePresenter;
import com.abasscodes.hockeyroster.model.Contact;
import com.abasscodes.hockeyroster.model.ContactWrapper;
import com.abasscodes.hockeyroster.testUtils.BasePresenterTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
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

    @Before
    public void setup() {
        presenter = new MainScreenPresenter(mockView, testConfiguration);
        presenter.onViewCreated();
        presenter.onViewBound();
        ContactWrapper contactWrapper = new ContactWrapper(mockContacts);
        presenter.onRosterLoaded(contactWrapper);
    }


    @Test
    public void onViewBoundShouldCauseViewToCheckInternetAccess() {
        verify(mockView).checkInternetAccess();
    }

    @Test
    public void onBackPressedShouldCauseViewToGoToListWhenInDetailMode() {
        presenter.onBackPressed();
        verify(mockView).showContactList(any());
        verify(mockView).navigateBackToListScreen();
    }

    @Test
    public void onViewBoundShouldCauseViewToResumeDetailPageIfDetailModeIsTrue() {
        when(mockContacts.get(anyInt())).thenReturn(mockContact);
        presenter.onContactClicked(mockContact);
        presenter.onViewBound();
        verify(mockView, times(2)).showContact(anyInt());
    }

    @Test
    public void onPageSwipedShouldCauseViewToChangeTitleTextWhenInBounds() {
        when(mockContacts.size()).thenReturn(14);
        when(mockContacts.get(anyInt())).thenReturn(mockContact);
        when(mockContact.getName()).thenReturn("Tester");

        presenter.onViewBound();
        presenter.onPageSwiped(0);

        verify(mockView).setTitle(anyString());
    }

    @Test
    public void onShowConnectionSettingsClickedShouldCauseViewToShowConnectionSettings() {
        presenter.onShowConnectionSettingsClicked();
        verify(mockView).showConnectionSettings();
    }

    @Test
    public void onInternetAccessCheckResultNegativeShouldCauseViewToShowMessage() {
        presenter.onInternetAccessCheckResult(false);
        verify(mockView).showMessage(anyString());
    }

    @Test
    public void onInternetAccessCheckResultPositiveShouldTriggerApiCall() {
        presenter.onInternetAccessCheckResult(true);
        testConfiguration.triggerRxSchedulers();
        verify(mockView).onContactsReady(mockContacts);
    }
}
