package com.example.survey.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;

public class CheckInternetConnection {

    public static boolean  isConnected(Context c){
        ConnectivityManager connectivityManager=(ConnectivityManager)c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        boolean isConnected= networkInfo!=null && networkInfo.isConnectedOrConnecting();
        return isConnected;
    }

}
