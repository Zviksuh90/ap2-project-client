package com.example.binyamin.fakebook;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;


public class ChatActivity extends ActionBarActivity {

    private ChatArrayAdapter chatArrayAdapter;
    private ListView listView;
    private EditText chatText;
    private Button buttonSend;
    private String channelId;

    Intent intent;
    private boolean side = false;
    DatabaseHandler db;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        setContentView(R.layout.activity_chat);

        //TODO set up datebase
        db = new DatabaseHandler(this);
        //db.onOpen();
        //create new messages
        /*
        Message m1 = new Message("channel1","hello","jimmy","1234","12367","12389");
        Message m2 = new Message("channel1","bye","sam","34","356","123345");
        Message m3 = new Message("channel1","why","josh","5523","389","167");
        db.addMessage(m1);
        db.addMessage(m2);
        db.addMessage(m3);
        */
        buttonSend = (Button) findViewById(R.id.buttonSend);
        listView = (ListView) findViewById(R.id.listView1);
        //TODO set up the get messages
        channelId = "channel1";
        Intent intent = getIntent();
        /*
        if (null != intent) {
            channelID = intent.getStringExtra("CHANNEL");
        }
        */
        List<Message> messagesList = db.getAllMessages();
        chatArrayAdapter = new ChatArrayAdapter(getApplicationContext(), R.layout.activity_chat_singlemessage,messagesList);
        listView.setAdapter(chatArrayAdapter);
        chatText = (EditText) findViewById(R.id.chatText);
        chatText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    return sendChatMessage();
                }
                return false;
            }
        });

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                sendChatMessage();
            }
        });

        listView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        listView.setAdapter(chatArrayAdapter);

        //to scroll the list view to bottom on data change
        chatArrayAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                listView.setSelection(chatArrayAdapter.getCount() - 1);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_chat, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean sendChatMessage(){
        String clientName = "sammy";

        //getting date
        Calendar c = Calendar.getInstance();
        System.out.println("Current time =&gt; "+c.getTime());
        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        String formattedDate = df.format(c.getTime());
        Toast.makeText(this, formattedDate, Toast.LENGTH_SHORT).show();
        //getting message
        EditText chatText = (EditText) findViewById(R.id.chatText);
        String message = chatText.getText().toString();

        String messageToSend = "http://ap2-chat-server.appspot.com/Save_Message?date=" + formattedDate +
                "&user=" + clientName + "&chan=" + channelId + "&text=" + message + "&latitude=" + "&longtitude=";
        http://ap2-chat-server.appspot.com/Save_Message?date=14.06.2015.14.23&user=binny&chan=fox&text=nooooice&latitude=10&longtitude=20

        new ServerFeeds().execute("http://10.0.2.2:13081/");

        //chatArrayAdapter.add(new Message(side, chatText.getText().toString(),clientName));
        chatText.setText("");
        return true;
    }

    private class ServerFeeds extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            HttpClient httpClient = new DefaultHttpClient();
            HttpContext localContext = new BasicHttpContext();
            HttpGet httpGet = new HttpGet(params[0]);
            String text = null;
            try {
                HttpResponse response = httpClient.execute(httpGet, localContext);
                HttpEntity entity = response.getEntity();
                text = getASCIIContentFromEntity(entity);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return text;
        }

        protected void onPostExecute(String result) {
            try {
                JSONObject obj = new JSONObject(result);
                String version = obj.getString("version");
                JSONArray feeds = obj.getJSONArray("feeds");
                for (int i = 0; i < feeds.length(); i++) {
                    JSONObject data = feeds.getJSONObject(i);
                    int a = 1;
                    if (a == 1)
                    {

                    }
                }
                SharedPreferences sharedPref = getPreferences(MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("version5", version);
                editor.commit();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
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
