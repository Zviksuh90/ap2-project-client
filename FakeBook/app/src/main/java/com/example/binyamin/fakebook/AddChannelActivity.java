package com.example.binyamin.fakebook;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class AddChannelActivity extends ActionBarActivity {
    DatabaseHandler db;
    Button buttonAdd;
    String icon = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_channel);

        buttonAdd = (Button) findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                addChannel();
                AddChannelActivity.this.finish();

            }
        });
    }

    public void addPicture(View view){
        Bitmap bitmap = null;
        switch (view.getId()) {
            case R.id.smile:
                bitmap = BitmapFactory.decodeResource(getResources(),  R.drawable.emoji);
                break;
            case R.id.kiss:
                bitmap = BitmapFactory.decodeResource(getResources(),  R.drawable.emoji3);
                break;
            case R.id.tongue:
                bitmap = BitmapFactory.decodeResource(getResources(),  R.drawable.emoji2);
                break;
        }
        icon = MyApplication.encodeTobase64(bitmap);
        /*
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bao);
        byte [] ba = bao.toByteArray();
        icon = Base64.encodeToString(ba,Base64.DEFAULT);
        */
    }

    private void addChannel() {
        EditText editTextChannelName = (EditText)findViewById(R.id.channelName);
        String channelName = editTextChannelName.getText().toString();
        EditText editTextChannelId = (EditText)findViewById(R.id.channelId);
        String channelId = editTextChannelId.getText().toString();
        //String channelToAdd = "http://ap2-chat-server.appspot.com/Add_Channel?name=" + channelName + "&icon=" + channelId;

        new ServerFeeds().execute(channelName,icon,channelId);
        db = new DatabaseHandler(this);
        db.addChannel(new Channel(icon,channelName,channelId,true));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_friend, menu);
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

    private class ServerFeeds extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            DefaultHttpClient client = SingletonHttpClient.getInstance();
            HttpPost post = new HttpPost(MyApplication.getServerLink()+"/addChannel");
            try {
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
                nameValuePairs.add(new BasicNameValuePair("name",
                        params[0]));
                nameValuePairs.add(new BasicNameValuePair("icon",
                        params[1]));
                nameValuePairs.add(new BasicNameValuePair("id",
                        params[2]));
                post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = client.execute(post);
                BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                String line = "";
                while ((line = rd.readLine()) != null) {
                    Log.d("httpost addChannel", line);

                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return "";
        }

        protected void onPostExecute(String result) {

        }
    }

}
