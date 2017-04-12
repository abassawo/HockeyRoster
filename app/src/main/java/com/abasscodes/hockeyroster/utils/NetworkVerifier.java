package com.abasscodes.hockeyroster.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class NetworkVerifier {

    private final Context context;

    public NetworkVerifier(Context context){
        this.context = context;
    }

    public boolean checkInternetAccess() {
        ConnectivityManager connectivityMgr = (ConnectivityManager) context.getSystemService(
                CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityMgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}
