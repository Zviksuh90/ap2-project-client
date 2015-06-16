package com.example.binyamin.fakebook;

import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import org.apache.http.impl.client.DefaultHttpClient;

//the result for the auth token request is returned to your application
//via the Account Manager Callback you specified when making the request.
//check the returned bundle if an Intent is stored against the AccountManager.KEY_INTENT key.
//if there is an Intent then start the activity using that intent to ask for user permission
//otherwise you can retrieve the auth token from the bundle.
public class OnTokenAcquired implements AccountManagerCallback<Bundle> {

    private static final int USER_PERMISSION = 989;
    private static final String APP_ID = "ap2-chat-server";
    private DefaultHttpClient httpclient;
    Activity activity;

    public OnTokenAcquired(DefaultHttpClient httpclient, Activity activity)
    {
        this.httpclient = httpclient;
        this.activity = activity;
    }

    public void run(AccountManagerFuture<Bundle> result) {

        Bundle bundle;

        try {
            bundle = (Bundle) result.getResult();
            if (bundle.containsKey(AccountManager.KEY_INTENT)) {
                Intent intent = bundle.getParcelable(AccountManager.KEY_INTENT);
                intent.setFlags(intent.getFlags() & ~Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivityForResult(intent, USER_PERMISSION);
            } else {
                setAuthToken(bundle);
                //added code
                Intent loginIntent = new Intent(activity, MainActivity.class);
                activity.startActivity(loginIntent);
                activity.finish();
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    //using the auth token and ask for a auth cookie
    protected void setAuthToken(Bundle bundle) {
        String authToken = bundle.getString(AccountManager.KEY_AUTHTOKEN);

        new GetCookie(httpclient, APP_ID, activity.getBaseContext()).execute(authToken);
    }
};