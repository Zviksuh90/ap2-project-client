package com.example.binyamin.fakebook;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.app.ListFragment;

import java.util.ArrayList;
import java.util.List;

public class MyListFragment extends ListFragment {
    public static final String[] titles = new String[] { "Jim",
            "John", "Sam", "Lenny", "Yossi","Smith","Jordan","Manny","Kevin","Lian","Samantha",
            "Joan","Roxan","Howard","Ying Yang","To","Ho","Yo","Lo","Po"};

    public static final String[] descriptions = new String[] {
            "online","busy","can't talk","at work","chilling","online",
            "available","sweet","available","available","available","available",
            "offline","offline","available","offline","available","offline",
            "offline","available",
    };

    public static final Integer[] images = { R.drawable.emoji,
            R.drawable.emoji2, R.drawable.emoji3, R.drawable.emoji2,R.drawable.emoji,
            R.drawable.emoji3,R.drawable.emoji,R.drawable.emoji2,R.drawable.emoji,R.drawable.emoji,
            R.drawable.emoji3,R.drawable.emoji,R.drawable.emoji3,R.drawable.emoji2,R.drawable.emoji3,
            R.drawable.emoji2,R.drawable.emoji3,R.drawable.emoji2,R.drawable.emoji,R.drawable.emoji,};

    List<RowItem> rowItems;


    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        rowItems = new ArrayList<RowItem>();
        for (int i = 0; i < titles.length; i++) {
            RowItem item = new RowItem(images[i], titles[i], descriptions[i]);
            rowItems.add(item);
        }
        CustomArrayAdapter adapter = new CustomArrayAdapter(getActivity(),
                R.layout.list_item, rowItems);
        setListAdapter(adapter);

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Intent chatIntent = new Intent(getActivity(),ChatActivity.class);
        getActivity().startActivity(chatIntent);
    }
}