package com.example.binyamin.fakebook;

import org.apache.http.impl.client.DefaultHttpClient;


/**
 * Created by Zvi Lapp on 6/28/2015.
 */
public class SingletonHttpClient {

    private static DefaultHttpClient instance = null;

    /**
     * constractor
     */
    protected SingletonHttpClient() {
        // Exists only to defeat instantiation.
    }

    //return instance
    public static DefaultHttpClient getInstance() {
        return instance;
    }

    //set instance
    public static void setInstance(DefaultHttpClient h) {
        if(instance == null) {
            instance = h;
        }
    }
}