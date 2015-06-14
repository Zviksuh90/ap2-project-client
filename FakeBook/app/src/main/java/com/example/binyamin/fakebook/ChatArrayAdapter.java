package com.example.binyamin.fakebook;

/**
 * Created by binyamin on 14-Jun-15.
 */
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ChatArrayAdapter extends ArrayAdapter {

    private static final String USER = "my_id";
    private TextView chatText;
    private List<Message> MessageList = new ArrayList();
    private LinearLayout singleMessageContainer;

    public void add(Message object) {
        MessageList.add(object);
        super.add(object);
    }

    public ChatArrayAdapter(Context context, int textViewResourceId, List<Message> messages) {
        super(context, textViewResourceId);
        MessageList = messages;
    }

    public int getCount() {
        return this.MessageList.size();
    }

    public Message getItem(int index) {
        return this.MessageList.get(index);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.activity_chat_singlemessage, parent, false);
        }
        singleMessageContainer = (LinearLayout) row.findViewById(R.id.singleMessageContainer);
        Message MessageObj = getItem(position);
        chatText = (TextView) row.findViewById(R.id.singleMessage);
        chatText.setText(MessageObj.getText() + "\n" + MessageObj.getUser());
        //check if message is from this client or other
        chatText.setBackgroundResource((USER == MessageObj.getUser()) ? R.drawable.bubble_a : R.drawable.bubble_b);
        singleMessageContainer.setGravity((USER == MessageObj.getUser()) ? Gravity.LEFT : Gravity.RIGHT);
        return row;
    }

    public Bitmap decodeToBitmap(byte[] decodedByte) {
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }

}