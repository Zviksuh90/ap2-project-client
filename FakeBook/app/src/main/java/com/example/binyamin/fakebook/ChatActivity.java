package com.example.binyamin.fakebook;

import android.content.Intent;
import android.database.DataSetObserver;
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

import java.util.List;


public class ChatActivity extends ActionBarActivity {
    TextView t0;
    TextView t1;
    TextView t2;
    TextView t3;
    TextView t4;
    EditText inputText;



    private ChatArrayAdapter chatArrayAdapter;
    private ListView listView;
    private EditText chatText;
    private Button buttonSend;

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
        String channelID = "channel1";
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
        //TODO add message to send
        //chatArrayAdapter.add(new Message(side, chatText.getText().toString(),clientName));
        chatText.setText("");
        side = !side;
        return true;
    }
}
