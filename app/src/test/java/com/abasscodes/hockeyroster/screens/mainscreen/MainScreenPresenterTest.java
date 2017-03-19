package com.abasscodes.hockeyroster.screens.mainscreen;

import com.abasscodes.hockeyroster.base.BasePresenter;
import com.abasscodes.hockeyroster.model.Contact;
import com.abasscodes.hockeyroster.model.ContactWrapper;
import com.abasscodes.hockeyroster.testUtils.BasePresenterTest;

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
import static org.mockito.Mockito.mock;
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
    Call<ContactWrapper> mockCall;

    @Before
    public void setup() {
        presenter = new MainScreenPresenter(mockView);
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
    public void onViewBoundShouldCauseViewToResumeDetailPageIfDetailModeIsTrue() {
        when(mockContacts.get(anyInt())).thenReturn(mockContact);
        presenter.detailMode = true;
        presenter.onViewBound();
        verify(mockView).showContact(anyInt());
    }

    @Test
    public void onInternetAccessCheckResultNegativeShouldCauseViewToShowMessage() {
        presenter.onInternetAccessCheckResult(false);
        verify(mockView).showMessage(anyString());
    }

    @Test
    public void onInternetAccessCheckResultPositiveShouldTriggerApiCall() {
        when(mockRestApi.getRosterInformation()).
                thenReturn(mockCall);
        ArgumentCaptor<Callback<ContactWrapper>> captor = ArgumentCaptor.forClass(Callback.class);
        mockCall.enqueue(captor.capture());
        captor.getValue().onResponse(mockCall, any());
        presenter.onInternetAccessCheckResult(true);
        verify(mockView).onContactsReady(mockContacts);
    }
}
