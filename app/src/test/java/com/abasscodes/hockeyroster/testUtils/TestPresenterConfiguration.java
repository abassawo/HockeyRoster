package com.abasscodes.hockeyroster.testUtils;

import android.support.annotation.NonNull;

import com.abasscodes.hockeyroster.network.RosterApi;
import com.abasscodes.hockeyroster.utils.PresenterConfiguration;

import org.mockito.Mockito;

import io.reactivex.schedulers.TestScheduler;

public class TestPresenterConfiguration extends PresenterConfiguration {
    private TestPresenterConfiguration(@NonNull RosterApi restApi) {
        super(new TestScheduler(), new TestScheduler(), restApi);
    }

    public static TestPresenterConfiguration createTestConfiguration() {
        RosterApi mockApi = Mockito.mock(RosterApi.class);
        return new TestPresenterConfiguration(mockApi);
    }

    @NonNull
    @Override
    public TestScheduler getIoScheduler() {
        return (TestScheduler) super.getIoScheduler();
    }

    @NonNull
    @Override
    public TestScheduler getUiScheduler() {
        return (TestScheduler) super.getUiScheduler();
    }

    /**
     * Helper method for triggering pending Rx events
     */
    public void triggerRxSchedulers() {
        getIoScheduler().triggerActions();
        getUiScheduler().triggerActions();
    }
}
