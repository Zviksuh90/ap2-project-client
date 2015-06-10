package com.example.binyamin.fakebook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

public class SplashScreen extends Activity {

    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 5000;
    private final int SECOND = 1000;
    TextView splashText;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.splash_screen);
        splashText = (TextView)findViewById(R.id.splashText);
        new CountDownTimer(SPLASH_DISPLAY_LENGTH, SECOND) {
            public void onTick(long millisUntilFinished) {
                int second = (int) millisUntilFinished/SECOND;
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
                Intent mainIntent = new Intent(SplashScreen.this,MainActivity.class);
                SplashScreen.this.startActivity(mainIntent);
                SplashScreen.this.finish();
            }
        }.start();
    }

}