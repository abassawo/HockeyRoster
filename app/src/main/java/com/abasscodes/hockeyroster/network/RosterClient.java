package com.abasscodes.hockeyroster.network;

import com.abasscodes.hockeyroster.model.ContactWrapper;
import com.abasscodes.hockeyroster.utils.PresenterConfiguration;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import io.reactivex.ObservableTransformer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

public class RosterClient {
    private static final String BASE_URL = "https://hockeyserver.herokuapp.com";
    private static final long CONNECTION_TIMEOUT = 30;
    private static RosterClient clientInstance;
    private final OnClientResponseListener listener;
    private static RosterApi rosterApi;

    public static RosterApi getApi() {
        return rosterApi;
    }

    public interface OnClientResponseListener {
        void onRosterLoaded(ContactWrapper roster);

        void onRosterLoadFailure(String errorMsg);
    }

    public static RosterClient getInstance(OnClientResponseListener listener) {
        if (clientInstance == null) {
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
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        rosterApi = retrofit.create(RosterApi.class);
    }

    private <R> ObservableTransformer<R, R> subscribeOnIoObserveOnUi(PresenterConfiguration configuration) {
        Scheduler ioScheduler = configuration.getIoScheduler();
        Scheduler uiScheduler = configuration.getUiScheduler();
        return observable -> observable.subscribeOn(ioScheduler).observeOn(uiScheduler);
    }

    public Disposable loadRosterList(PresenterConfiguration configuration) {
        return rosterApi.getRosterInformation()
                .compose(subscribeOnIoObserveOnUi(configuration))
                .subscribe(listener::onRosterLoaded,
                           throwable -> listener.onRosterLoadFailure(throwable.getMessage()));
    }
}
