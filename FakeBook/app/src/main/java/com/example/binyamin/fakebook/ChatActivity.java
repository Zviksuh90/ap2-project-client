package com.example.binyamin.fakebook;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class ChatActivity extends ActionBarActivity {
    TextView t0;
    TextView t1;
    TextView t2;
    TextView t3;
    TextView t4;
    EditText inputText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        t0 = (TextView) findViewById(R.id.textView0);
        t1 = (TextView) findViewById(R.id.textView1);
        t2 = (TextView) findViewById(R.id.textView2);
        t3 = (TextView) findViewById(R.id.textView3);
        t4 = (TextView) findViewById(R.id.textView4);
        inputText = (EditText)findViewById(R.id.editText);
        t0.setText("");
        t1.setText("");
        t2.setText("");
        t3.setText("");
        t4.setText("");
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

    public void sendMessage(View view){

        String newMessage = new DatabaseHandler(this).getAllContacts().get(0).getPhoneNumber();
        t0.setText(t1.getText());
        t1.setText(t2.getText());
        t2.setText(t3.getText());
        t3.setText(t4.getText());
        t4.setText(newMessage);
    }
}
