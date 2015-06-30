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
        if (instance == null) {
            instance = new DefaultHttpClient();
        }
        return instance;
    }


}