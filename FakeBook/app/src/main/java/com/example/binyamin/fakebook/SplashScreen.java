package com.example.binyamin.fakebook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class SplashScreen extends Activity {
    public static final String GET_CHANNELS =  "http://ap2-chat-server.appspot.com/" + "getChannels";
    public static final String GET_UPDATES = "http://ap2-chat-server.appspot.com/" + "getUpdates";

    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 5000;
    private final int SECOND = 1000;
    TextView splashText;
    DatabaseHandler db;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.splash_screen);
        //starting chat service
        //Intent intent = new Intent(this, ChatService.class);
        //startService(intent);



        splashText = (TextView)findViewById(R.id.splashText);
        db = new DatabaseHandler(this);
        db.deleteAllChannels();
        //db.addMessage(new Message("Jim","hello","id","time","lat","long"));
        final Handler h = new Handler();
        final int delay = 10000; //milliseconds

        h.postDelayed(new Runnable(){
            public void run(){
                Toast.makeText(getApplicationContext(), "handler running",
                        Toast.LENGTH_SHORT).show();
                //db.addChannel(new Channel("name", "id", "hello"));
                getUpdates();
                h.postDelayed(this, delay);
            }
        }, delay);
        new CountDownTimer(SPLASH_DISPLAY_LENGTH, SECOND) {
            public void onTick(long millisUntilFinished) {
                int second = (int) millisUntilFinished/SECOND;
                switch (second) {
                    case 4:
                        splashText.setText(R.string.uploading_names);
                        break;
                    case 3:
                        splashText.setText(R.string.uploading_places);
                        break;
                    case 2:
                        splashText.setText(R.string.uploading_images);
                        break;
                    case 1:
                        splashText.setText(R.string.uploading_data);
                        break;
                }
            }
            public void onFinish() {
                Intent loginIntent = new Intent(SplashScreen.this,LoginActivity.class);
                SplashScreen.this.startActivity(loginIntent);
                SplashScreen.this.finish();
            }
        }.start();
    }

    public void getUpdates(){
        Toast.makeText(getApplicationContext(), "in getUpdates",
                Toast.LENGTH_LONG).show();
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
                    Toast.makeText(getApplicationContext(), "service added" + data.getString(DatabaseHandler.KEY_ICON) + data.getString(DatabaseHandler.KEY_NAME),
                            Toast.LENGTH_LONG).show();
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


