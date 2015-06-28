package com.example.binyamin.fakebook;

import android.app.Application;

/**
 * Created by binyamin on 28-Jun-15.
 */
public class MyApplication extends Application {

    private String serverLink;

    public String getServerLink() {
        return serverLink;
    }

    public void setServerLink(String serverLink) {
        this.serverLink = serverLink;
    }
}