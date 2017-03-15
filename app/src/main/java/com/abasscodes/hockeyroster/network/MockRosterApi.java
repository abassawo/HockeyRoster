package com.abasscodes.hockeyroster.network;

import com.abasscodes.hockeyroster.model.ContactWrapper;

import retrofit2.Call;

public class MockRosterApi implements RosterApi {

    @Override
    public Call<ContactWrapper> getRosterInformation() {
        return null;
    }
}
