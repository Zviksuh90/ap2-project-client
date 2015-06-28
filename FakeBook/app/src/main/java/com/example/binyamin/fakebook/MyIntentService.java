package com.example.binyamin.fakebook;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;
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

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class MyIntentService extends IntentService {
    public static final String KEY_CHANNEL_ID = "channel_id";
    public static final String KEY_USER_ID = "user_id";
    public static final String KEY_TEXT = "text";
    public static final String KEY_DATE_TIME = "date_time";
    public static final String KEY_LONGTITUDE = "longtitude";
    public static final String KEY_LATITUDE = "latitude";
    public static final String KEY_ICON = "icon";
    public static final String KEY_NAME = "name";
    public static final String KEY_ID = "id";
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_GET_CHANNELS = "com.example.binyamin.alarmtest.action.GETCHANNELS";
    private static final String ACTION_GET_UPDATES = "com.example.binyamin.alarmtest.action.GETUPDATES";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "com.example.binyamin.alarmtest.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.example.binyamin.alarmtest.extra.PARAM2";

    private DatabaseHandler db;
    public static final String GET_UPDATES = "http://ap2-chat-server.appspot.com/" + "getUpdates";
    public static final String GET_CHANNELS =  "http://ap2-chat-server.appspot.com/" + "getChannels";

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionGetUpdates(Context context) {
        Intent intent = new Intent(context, MyIntentService.class);
        intent.setAction(ACTION_GET_UPDATES);
        //intent.putExtra(EXTRA_PARAM1, param1);
        //intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionGetChannels(Context context) {
        Intent intent = new Intent(context, MyIntentService.class);
        intent.setAction(ACTION_GET_CHANNELS);
        context.startService(intent);
    }

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_GET_UPDATES.equals(action)) {
                //final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                //final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionGetUpdates();
            } else if (ACTION_GET_CHANNELS.equals(action)) {
                handleActionGetChannels();
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionGetUpdates() {
        // TODO: Handle action Foo
        db = new DatabaseHandler(this);
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
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionGetChannels() {
        db = new DatabaseHandler(this);
        HttpClient httpClient = new DefaultHttpClient();
        HttpContext localContext = new BasicHttpContext();
        //handeling updates
        HttpGet httpGet = new HttpGet(GET_CHANNELS);
        String text = null;
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
                    Log.d("the channlel",data.getString(KEY_ICON)+ data.getString(KEY_NAME)+data.getString(KEY_ID));
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
