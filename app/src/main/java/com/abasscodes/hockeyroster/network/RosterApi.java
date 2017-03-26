package com.abasscodes.hockeyroster.network;

import com.abasscodes.hockeyroster.model.ContactWrapper;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface RosterApi {
    @GET("api/v1/roster")
    Observable<ContactWrapper> getRosterInformation();
}
