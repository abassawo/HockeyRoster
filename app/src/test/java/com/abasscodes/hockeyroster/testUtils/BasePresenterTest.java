package com.abasscodes.hockeyroster.testUtils;

import com.abasscodes.hockeyroster.base.BasePresenter;
import com.abasscodes.hockeyroster.network.MockRosterApi;
import com.abasscodes.hockeyroster.network.RosterApi;

import org.junit.Before;
import org.junit.Rule;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

public class BasePresenterTest<P extends BasePresenter> {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    protected P presenter;
    protected RosterApi mockRestApi;

    @Before
    public void baseSetup() {
        mockRestApi = new MockRosterApi();
    }
}

