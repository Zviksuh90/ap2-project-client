package com.example.binyamin.fakebook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.app.ListFragment;

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
        /*
        Channel ch1 = new Channel("icon","channel1","20022");
        Channel ch2 = new Channel("icon","channel2","20023");
        Channel ch3 = new Channel("icon","channel3","20024");
        channels.add(ch1);
        channels.add(ch2);
        channels.add(ch3);
        */
        ChannelArrayAdapter adapter = new ChannelArrayAdapter(getActivity(),
                R.layout.list_item, channels);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Intent chatIntent = new Intent(getActivity(), ChatActivity.class);
        getActivity().startActivity(chatIntent);
    }


}