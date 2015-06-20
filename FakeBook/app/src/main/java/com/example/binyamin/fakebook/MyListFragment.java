package com.example.binyamin.fakebook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.app.ListFragment;
import android.widget.TextView;

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
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Intent chatIntent = new Intent(getActivity(), ChatActivity.class);
        String name = ((TextView) v.findViewById(R.id.title)).getText().toString();
        chatIntent.putExtra("ChannelKey",name);
        getActivity().startActivity(chatIntent);
    }


}