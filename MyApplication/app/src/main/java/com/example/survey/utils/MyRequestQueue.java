package com.example.survey.utils;

import android.content.Context;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;

public class MyRequestQueue {

    private static com.example.survey.utils.MyRequestQueue instance;
    private  static Context context;

    private MyRequestQueue(){}

    public static synchronized com.example.survey.utils.MyRequestQueue getInstance(Context context){
        if(instance == null){
            instance = new com.example.survey.utils.MyRequestQueue();
            com.example.survey.utils.MyRequestQueue.context=context;
        }
        return instance;
    }

    public RequestQueue addToRequestQueue() {

        RequestQueue queue = Volley.newRequestQueue(context);
        return queue;

    }

    public RequestQueue getNetworkRequestQueue(){
        Network network=new BasicNetwork(new HurlStack());
        Cache cache = new DiskBasedCache(context.getCacheDir(), 1024 * 1024); // 1MB cap
        RequestQueue requestQueue=new RequestQueue(cache,network);
        return requestQueue;
    }




}
