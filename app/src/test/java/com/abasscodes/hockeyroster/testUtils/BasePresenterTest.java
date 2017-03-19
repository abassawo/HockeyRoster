package com.abasscodes.hockeyroster.testUtils;

import com.abasscodes.hockeyroster.base.BasePresenter;
import com.abasscodes.hockeyroster.network.MockRosterApi;
import com.abasscodes.hockeyroster.network.RosterApi;

import org.junit.Before;
import org.junit.Rule;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import io.reactivex.schedulers.TestScheduler;

public class BasePresenterTest<P extends BasePresenter> {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    protected P presenter;
    protected RosterApi mockRestApi;
    protected TestPresenterConfiguration testConfiguration;

    @Before
    public void baseSetup() {
        testConfiguration = TestPresenterConfiguration.createTestConfiguration();
        mockRestApi = new MockRosterApi();
    }
}

