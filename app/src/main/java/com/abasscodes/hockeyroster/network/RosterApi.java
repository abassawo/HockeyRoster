package com.abasscodes.hockeyroster.network;

import com.abasscodes.hockeyroster.model.ContactWrapper;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RosterApi {

    @GET("android_eval.json")
    Call<ContactWrapper> getRosterInformation();
}
