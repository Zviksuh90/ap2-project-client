package com.example.binyamin.fakebook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONArray;
import org.json.JSONObject;

public class ChatService extends IntentService {
    private DatabaseHandler db;
    public static final String GET_UPDATES = "http://ap2-chat-server.appspot.com/" + "getUpdates";
    public static final String GET_CHANNELS =  "http://ap2-chat-server.appspot.com/" + "getChannels";
    public ChatService() {
        super("ChatService");
        db = new DatabaseHandler(this);
    }

    // will be called asynchronously by Android
    @Override
    protected void onHandleIntent(Intent intent) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpContext localContext = new BasicHttpContext();
        //handeling updates
        HttpGet httpGet = new HttpGet(GET_UPDATES);
        String text = null;
        try {
            HttpResponse response = httpClient.execute(httpGet, localContext);
            HttpEntity entity = response.getEntity();
            text = getASCIIContentFromEntity(entity);
            String result = text;
            try {
                JSONObject obj = new JSONObject(result);
                JSONArray messages = obj.getJSONArray("messages");
                for (int i = 0; i < messages.length(); i++) {
                    JSONObject data = messages.getJSONObject(i);
                    db.addMessage(new Message(data.getString(DatabaseHandler.KEY_CHANNEL_ID),
                                                data.getString(DatabaseHandler.KEY_USER_ID),
                                                data.getString(DatabaseHandler.KEY_TEXT),
                                                data.getString(DatabaseHandler.KEY_DATE_TIME),
                                                data.getString(DatabaseHandler.KEY_LONGTITUDE),
                                                data.getString(DatabaseHandler.KEY_LATITUDE)));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        //handeling channels
        httpGet = new HttpGet(GET_CHANNELS);
        text = null;
        try {
            HttpResponse response = httpClient.execute(httpGet, localContext);
            HttpEntity entity = response.getEntity();
            text = getASCIIContentFromEntity(entity);
            String result = text;
            try {
                JSONObject obj = new JSONObject(result);
                JSONArray messages = obj.getJSONArray("channels");
                for (int i = 0; i < messages.length(); i++) {
                    JSONObject data = messages.getJSONObject(i);
                    db.addChannel(new Channel(data.getString(DatabaseHandler.KEY_ICON),
                            data.getString(DatabaseHandler.KEY_NAME),
                            data.getString(DatabaseHandler.KEY_ID)));
                }


            } catch (Exception e) {
                e.printStackTrace();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected String getASCIIContentFromEntity(HttpEntity entity)
            throws IllegalStateException, IOException {
        InputStream in = entity.getContent();
        StringBuffer out = new StringBuffer();
        int n = 1;
        while (n > 0) {
            byte[] b = new byte[4096];
            n = in.read(b);
            if (n > 0)
                out.append(new String(b, 0, n));
        }
        return out.toString();
    }
}