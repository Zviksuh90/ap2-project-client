package com.example.binyamin.fakebook;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;


public class ListActivity extends ActionBarActivity implements GestureDetector.OnGestureListener {
    private GestureDetector gDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            Intent mainActivity = new Intent(ListActivity.this,MainActivity.class);
            ListActivity.this.startActivity(mainActivity);
            ListActivity.this.finish();
        } else {
            setContentView(R.layout.activity_list);
            gDetector = new GestureDetector(this, this);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_friend_list, menu);
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

    @Override
    public boolean onDown(MotionEvent arg0) {
        // TODO Auto-generated method stub
        return false;
    }
    @Override
    public boolean onFling(MotionEvent start, MotionEvent finish, float xVelocity, float yVelocity) {
        //swipe right
        if (start.getRawX() > finish.getRawX()+250) {
            Intent mainActivity = new Intent(ListActivity.this, MainActivity.class);
            ListActivity.this.startActivity(mainActivity);
        }//swipe left
        else if (start.getRawX() < finish.getRawX()-250){
            Intent mainActivity = new Intent(ListActivity.this, MainActivity.class);
            ListActivity.this.startActivity(mainActivity);
        }
        return true;
    }
    @Override
    public void onLongPress(MotionEvent arg0) {
    }
    @Override
    public boolean onScroll(MotionEvent arg0, MotionEvent arg1, float arg2, float arg3) {
        return false;
    }
    @Override
    public void onShowPress(MotionEvent arg0) {
    }
    @Override
    public boolean onSingleTapUp(MotionEvent arg0) {
        return false;
    }
    @Override
    public boolean onTouchEvent(MotionEvent me) {
        return gDetector.onTouchEvent(me);
    }

}
