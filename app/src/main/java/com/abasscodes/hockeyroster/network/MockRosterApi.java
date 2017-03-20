package com.abasscodes.hockeyroster.network;

import com.abasscodes.hockeyroster.model.ContactWrapper;

import java.util.ArrayList;

import io.reactivex.Observable;

public class MockRosterApi implements RosterApi {

    @Override
    public Observable<ContactWrapper> getRosterInformation() {
        return Observable.just(new ContactWrapper(new ArrayList<>()));
    }
}
