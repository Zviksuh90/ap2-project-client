package com.example.binyamin.fakebook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.widget.TextView;

import org.apache.http.impl.client.DefaultHttpClient;

public class SplashScreen extends Activity {
    public static final String GET_CHANNELS = "http://ap2-chat-server.appspot.com/" + "getChannels";
    public static final String GET_UPDATES = "http://ap2-chat-server.appspot.com/" + "getUpdates";

    /**
     * Duration of wait *
     */
    private final int SPLASH_DISPLAY_LENGTH = 5000;
    private final int SECOND = 1000;
    TextView splashText;
    DatabaseHandler db;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.splash_screen);
        SingletonHttpClient.setInstance(new DefaultHttpClient());
        splashText = (TextView) findViewById(R.id.splashText);
        db = new DatabaseHandler(this);
        db.deleteAllChannels();
        db.deleteAllMessages();
        db.addMessage(new Message("soccer", "hello", "Jim", "time", "lat", "long"));
        db.addMessage(new Message("soccer", "goodbye", "sam", "time", "lat", "long"));
        db.addMessage(new Message("soccer","hello","yossi","time","lat","long"));
        db.addChannel(new Channel("icon","soccer","soccerId"));
        db.addChannel(new Channel("icon2","baseball","baseballId"));


        final Handler h = new Handler();
        final int delay = 10000; //milliseconds
        h.postDelayed(new Runnable() {
            public void run() {
                //db.addChannel(new Channel("name", "id", "hello"));
                MyIntentService.startActionGetUpdates(getApplicationContext());
                MyIntentService.startActionGetChannels(getApplicationContext());
                h.postDelayed(this, delay);
            }
        }, delay);

        new CountDownTimer(SPLASH_DISPLAY_LENGTH, SECOND) {
            public void onTick(long millisUntilFinished) {
                int second = (int) millisUntilFinished / SECOND;
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
                Intent loginIntent = new Intent(SplashScreen.this, LoginActivity.class);
                SplashScreen.this.startActivity(loginIntent);
                SplashScreen.this.finish();
            }
        }.start();
    }
/*
    private class ServerFeedsChannel extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            Toast.makeText(getApplicationContext(), "in doInBackgroud",
                    Toast.LENGTH_LONG).show();
            HttpClient httpClient = new DefaultHttpClient();
            HttpContext localContext = new BasicHttpContext();
            HttpGet httpGet = new HttpGet(params[0]);
            String text = null;
            try {
                HttpResponse response = httpClient.execute(httpGet, localContext);
                HttpEntity entity = response.getEntity();
                text = getASCIIContentFromEntity(entity);
            } catch (Exception e) {}
            return text;
        }
        protected void onPostExecute(String result) {
            JSONObject obj = null;
            try {
                obj = new JSONObject(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JSONArray messages = null;
            try {
                messages = obj.getJSONArray("channels");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < messages.length(); i++) {
                JSONObject data = null;
                try {
                    data = messages.getJSONObject(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    db.addChannel(new Channel(data.getString(DatabaseHandler.KEY_ICON),
                            data.getString(DatabaseHandler.KEY_NAME),
                            data.getString(DatabaseHandler.KEY_ID)));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        /*
        @Override
        protected String doInBackground(String... params) {
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
                    Log.e("myApp", "exception", e);
                }
            } catch (Exception e) {
                Log.e("myApp", "exception", e);
            }
            return "";
        }
    /*
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
                Log.e("myApp","exception",e);
            }
        } catch (Exception e) {
            Log.e("myApp","exception",e);
        }
    }
    *
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
    }*/
}


