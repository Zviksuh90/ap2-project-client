package com.example.binyamin.fakebook;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.app.ListFragment;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MyListFragment extends ListFragment {
    private String[] titles;
    private String[] descriptions;
    private Integer[] images;
    List<Channel> channels;
    DatabaseHandler db;


    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        db = new DatabaseHandler(getActivity());
        channels = new ArrayList<Channel>();
        channels = db.getAllChannels();
        ChannelArrayAdapter adapter = new ChannelArrayAdapter(getActivity(),
                R.layout.list_item, channels);
        setListAdapter(adapter);
        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String nameToToast = ((TextView) view.findViewById(R.id.title)).getText().toString();
                new ServerFeeds().execute(nameToToast);
                Toast.makeText(getActivity(), "Joined "+ nameToToast + " Channel", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Intent chatIntent = new Intent(getActivity(), ChatActivity.class);
        String name = ((TextView) v.findViewById(R.id.title)).getText().toString();
        chatIntent.putExtra("ChannelKey",name);
        getActivity().startActivity(chatIntent);
    }

    private class ServerFeeds extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost("http://ap2-chat-server.appspot.com/joinChannel");
            try {
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
                nameValuePairs.add(new BasicNameValuePair("id",
                        params[0]));
                post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = client.execute(post);
                BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                String line = "";
                while ((line = rd.readLine()) != null) {
                    Log.d("the httpost join channels", line);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return "";
        }
    }

}