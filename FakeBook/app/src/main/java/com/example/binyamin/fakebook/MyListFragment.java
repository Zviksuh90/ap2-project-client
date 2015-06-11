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
    List<RowItem> rowItems;


    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        rowItems = new ArrayList<RowItem>();





        CustomArrayAdapter adapter = new CustomArrayAdapter(getActivity(),
                R.layout.list_item, rowItems);
        setListAdapter(adapter);

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Intent chatIntent = new Intent(getActivity(), ChatActivity.class);
        getActivity().startActivity(chatIntent);
    }


}