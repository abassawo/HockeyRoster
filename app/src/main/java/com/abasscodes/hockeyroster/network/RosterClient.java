package com.abasscodes.hockeyroster.network;

import com.abasscodes.hockeyroster.model.ContactWrapper;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

public class RosterClient {
    private static final String BASE_URL = "https://jc-xerxes.gpshopper.com/";
    private static final long CONNECTION_TIMEOUT = 30;
    private static RosterClient clientInstance;
    private final OnClientResponseListener listener;
    private RosterApi rosterApi;

    public static RosterClient getInstance(OnClientResponseListener listener) {
        if(clientInstance == null){
            clientInstance = new RosterClient(listener);
        }
        return clientInstance;
    }

    private RosterClient(OnClientResponseListener listener) {
        this.listener = listener;
            Gson gson = new GsonBuilder()
                    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                    .setPrettyPrinting()
                    .create();
            GsonConverterFactory gsonConverter = GsonConverterFactory.create(gson);
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                    .addInterceptor(new HttpLoggingInterceptor(message -> Timber.v(message)).setLevel(
                            HttpLoggingInterceptor.Level.BODY))
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(gsonConverter)
                    .build();
            rosterApi = retrofit.create(RosterApi.class);
    }

    public void loadRosterList() {
        Call<ContactWrapper> call = rosterApi.getRosterInformation();
        call.enqueue(new Callback<ContactWrapper>() {
            @Override
            public void onResponse(Call<ContactWrapper> call, Response<ContactWrapper> response) {
                Timber.d(response.body().toString());
                listener.onRosterLoaded(response.body());
            }

            @Override
            public void onFailure(Call<ContactWrapper> call, Throwable t) {
                Timber.d("Error " + t);
                listener.onFailure(t.toString());
            }
        });
    }

    public interface OnClientResponseListener {
        void onRosterLoaded(ContactWrapper roster);
        void onFailure(String errorMsg);
    }
}
