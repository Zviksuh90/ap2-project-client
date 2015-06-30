package com.example.binyamin.fakebook;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;


public class MoviesAdvice extends ActionBarActivity {
    Button buttonSend;
    int port = 12345;
    String address  ="192.168.119.210";
    //String address="10.0.0.10";
    //String address="172.18.29.98";
    //String address="192.168.163.130";
    //String address="192.168.56.1";
    //String address="172.18.28.114";
   // String address = "192.168.239.140";
    private String message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_advice);
        buttonSend = (Button)findViewById(R.id.buttonSendMovies);
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                sendMoviesQuery();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_movies_advice, menu);
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

    private void sendMoviesQuery(){

        //getting message
        EditText chatText = (EditText) findViewById(R.id.moviesText);
        message = chatText.getText().toString();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Socket skt = null;
                DataInputStream in = null;
                DataOutputStream out = null;

                try {
                    skt = new Socket();
                    skt.connect(new InetSocketAddress(address, port));
                    in = new DataInputStream(skt.getInputStream());
                    out = new DataOutputStream(skt.getOutputStream());

                    out.writeBytes(message);
                    String response = in.readLine();
                    ((TextView) findViewById(R.id.movieResponse)).setText(response);

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                finally {
                    try {
                        in.close();
                    } catch (Exception e) {
                    }
                    try {
                        out.close();
                    } catch (Exception e) {
                    }
                    try {
                        skt.close();
                    } catch (Exception e) {
                    }
                }
            }
        });
        t.start();
        //chatArrayAdapter.add(new Message(side, chatText.getText().toString(),clientName));
        chatText.setText("");
    }
}
