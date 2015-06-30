package com.example.binyamin.fakebook;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.widget.TextView;

import org.apache.http.impl.client.DefaultHttpClient;

public class SplashScreen extends Activity {

    /**
     * Duration of wait *
     */
    private final int SPLASH_DISPLAY_LENGTH = 5000;
    private final int SECOND = 1000;
    TextView splashText;
    DatabaseHandler db;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.splash_screen);

        splashText = (TextView) findViewById(R.id.splashText);
        db = new DatabaseHandler(this);
        db.deleteAllChannels();
        db.deleteAllMessages();
        db.getReadableDatabase();
        //db.addMessage(new Message("soccer", "hello", "Jim", "time", "lat", "long"));
        db.addMessage(new Message("soccer", "goodbye", "sam", "time", "lat", "long"));
        db.addMessage(new Message("soccer","hello","yossi","time","lat","long"));
        db.addChannel(new Channel("icon","soccer","soccerId", false));
        //db.addChannel(new Channel("icon2","baseball","baseballId"));
        String movieBit = MyApplication.encodeTobase64(BitmapFactory.decodeResource(getResources(), R.drawable.moviemoji));
        db.addChannel(new Channel(movieBit,"Movies","MovieId", true));

        final Handler h = new Handler();
        final int delay = 10000; //milliseconds
        h.postDelayed(new Runnable() {
            public void run() {
                //db.addChannel(new Channel("name", "id", "hello"));
                MyIntentService.startActionGetUpdates(getApplicationContext());
                MyIntentService.startActionGetChannels(getApplicationContext());
                h.postDelayed(this, delay);
            }
        }, delay);
        
        new CountDownTimer(SPLASH_DISPLAY_LENGTH, SECOND) {
            public void onTick(long millisUntilFinished) {
                int second = (int) millisUntilFinished / SECOND;
                switch (second) {
                    case 4:
                        splashText.setText(R.string.uploading_names);
                        break;
                    case 3:
                        splashText.setText(R.string.uploading_places);
                        break;
                    case 2:
                        splashText.setText(R.string.uploading_images);
                        break;
                    case 1:
                        splashText.setText(R.string.uploading_data);
                        break;
                }
            }

            public void onFinish() {
                Intent loginIntent = new Intent(SplashScreen.this, LoginActivity.class);
                SplashScreen.this.startActivity(loginIntent);
                SplashScreen.this.finish();
            }
        }.start();
    }
}


