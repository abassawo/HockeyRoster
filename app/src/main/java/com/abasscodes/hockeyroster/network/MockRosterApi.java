package com.abasscodes.hockeyroster.network;

import com.abasscodes.hockeyroster.model.Contact;
import com.abasscodes.hockeyroster.model.ContactWrapper;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.Call;

public class MockRosterApi implements RosterApi {

    @Override
    public Observable<ContactWrapper> getRosterInformation() {
        return Observable.just(new ContactWrapper(new ArrayList<Contact>()));
    }
}
