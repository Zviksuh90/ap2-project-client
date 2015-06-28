package com.example.binyamin.fakebook;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class AddChannelActivity extends ActionBarActivity {

    Button buttonAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_channel);

        buttonAdd = (Button) findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                addChannel();
            }
        });
    }

    private void addChannel() {
        EditText editTextChannelName = (EditText)findViewById(R.id.channelName);
        String channelName = editTextChannelName.getText().toString();
        EditText editTextChannelId = (EditText)findViewById(R.id.channelId);
        String channelId = editTextChannelId.getText().toString();
        //String channelToAdd = "http://ap2-chat-server.appspot.com/Add_Channel?name=" + channelName + "&icon=" + channelId;
        new ServerFeeds().execute(channelName,channelId,channelName);

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
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost("http://ap2-chat-server.appspot.com/addChannel");
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
                    Log.d("the return from the httpost channel", line);

                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return "";
        }

            /*
            HttpClient httpClient = new DefaultHttpClient();
            HttpContext localContext = new BasicHttpContext();
            List<NameValuePair> paramsList = new ArrayList<NameValuePair>(2);
            paramsList.add(new BasicNameValuePair("name", params[0]));
            paramsList.add(new BasicNameValuePair("icon", params[1]));
            HttpPost httpPost = new HttpPost(params[0]);
            try {
                httpClient.execute(httpPost, localContext);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "";

        }
*/
        protected void onPostExecute(String result) {

        }
    }

}
