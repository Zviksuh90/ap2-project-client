package com.example.binyamin.fakebook;

import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.CountDownTimer;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import com.example.binyamin.fakebook.dummy.SettingsActivity;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends ActionBarActivity implements OnMapReadyCallback,
        GestureDetector.OnGestureListener {
    private SensorManager mySensorManager;
    private GestureDetector gDetector;
    SwipeRefreshLayout swipeContainer;
    Button menuButton;
    Activity activity = null;

    //variable to detect shake
    private float xAccel;
    private float yAccel;
    private float zAccel;
    private float xPreviousAccel;
    private float yPreviousAccel;
    private float zPreviousAccel;
    private boolean firstUpdate = true;
    private final float shakeThreshold = 1.5f;
    private boolean shakeInitiated = false;

    //variable in order to get my lat and lng
    LocationManager lm;
    String provider;
    Location l;
    double lat;
    double lng;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //set up the menu for menu button
        menuButton = (Button)findViewById(R.id.menu);
        activity = this;
        this.registerForContextMenu(menuButton);
        //get my lat and lng
        lm=(LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
        Criteria c=new Criteria();
        provider=lm.getBestProvider(c, false);
        //now you have best provider
        //get location
        l=lm.getLastKnownLocation(provider);
        if(l!=null){
            //get latitude and longitude of the location
            lng=l.getLongitude();
            lat=l.getLatitude();
        } else {
            lat=32.0781;
            lng=34.8476;
        }
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        gDetector = new GestureDetector(this, this);

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new CountDownTimer(3000,3000) {
                    public void onTick(long millisUntilFinished) {}
                    public void onFinish() {
                        swipeContainer.setRefreshing(false);
                    }
                }.start();
            }
        });

        mySensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE); // (1)
        mySensorManager.registerListener(mySensorEventListener, mySensorManager
                        .getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL); // (2)

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
    public void onMapReady(GoogleMap map) {
        LatLng myCenter = new LatLng(lat, lng);
        map.addMarker(new MarkerOptions().position(new LatLng(lat, lng))
                .title("Me").snippet("online").icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        map.addMarker(new MarkerOptions().position(new LatLng(32.0753,34.8429)).title("Joe").snippet("online"));
        map.addMarker(new MarkerOptions().position(new LatLng(32.0767,34.8457)).title("Mimsy").snippet("offline"));
        map.addMarker(new MarkerOptions().position(new LatLng(32.0730,34.8440)).title("Sam").snippet("busy"));
        map.addMarker(new MarkerOptions().position(new LatLng(32.0710,34.8460)).title("Roger").snippet("chilling"));

        map.moveCamera(CameraUpdateFactory.newLatLng(myCenter));
        map.animateCamera(CameraUpdateFactory.zoomTo(12), 1000, null);
    }
    /** This will be invoked when an item in the listview is long pressed */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_menu, menu);
    }

    public void onClickButton(View v){
        activity.openContextMenu(menuButton);
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.cnt_mnu_settings:
                Intent settingsIntent = new Intent(MainActivity.this,SettingsActivity.class);
                MainActivity.this.startActivity(settingsIntent);
                break;
            case R.id.cnt_mnu_get_list:
                Intent listIntent = new Intent(MainActivity.this, ListActivity.class);
                MainActivity.this.startActivity(listIntent);
                break;
            case R.id.cnt_mnu_add_friend:
                Intent addFriendIntent = new Intent(MainActivity.this,AddChannelActivity.class);
                MainActivity.this.startActivity(addFriendIntent);
                break;
            default:
                return super.onContextItemSelected(item);
        }
        return true;
    }

    @Override
    public boolean onDown(MotionEvent arg0) {
        // TODO Auto-generated method stub
        return false;
    }
    @Override
    public boolean onFling(MotionEvent start, MotionEvent finish, float xVelocity, float yVelocity) {
        //swipe right
        if (start != null && finish != null && start.getRawX() > finish.getRawX()+250) {
            Intent listActivity = new Intent(MainActivity.this, ListActivity.class);
            MainActivity.this.startActivity(listActivity);
        }//swipe left
        else if (start != null && finish != null && start.getRawX() < finish.getRawX()-250){
            Intent listActivity = new Intent(MainActivity.this, ListActivity.class);
            MainActivity.this.startActivity(listActivity);
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

    private final SensorEventListener mySensorEventListener = new SensorEventListener() {

        public void onSensorChanged(SensorEvent se) {
            updateAccelParameters(se.values[0], se.values[1], se.values[2]);   // (1)
            if ((!shakeInitiated) && isAccelerationChanged()) {                                      // (2)
                shakeInitiated = true;
            } else if ((shakeInitiated) && isAccelerationChanged()) {                              // (3)
                executeShakeAction();
            } else if ((shakeInitiated) && (!isAccelerationChanged())) {                           // (4)
                shakeInitiated = false;
            }        }

        public void onAccuracyChanged(Sensor sensor, int accuracy) {
	    /* can be ignored in this example */
        }
        /* Store the acceleration values given by the sensor */
        private void updateAccelParameters(float xNewAccel, float yNewAccel,
                                           float zNewAccel) {
                /* we have to suppress the first change of acceleration, it results from first values being initialized with 0 */
            if (firstUpdate) {
                xPreviousAccel = xNewAccel;
                yPreviousAccel = yNewAccel;
                zPreviousAccel = zNewAccel;
                firstUpdate = false;
            } else {
                xPreviousAccel = xAccel;
                yPreviousAccel = yAccel;
                zPreviousAccel = zAccel;
            }
            xAccel = xNewAccel;
            yAccel = yNewAccel;
            zAccel = zNewAccel;
        }
        /* If the values of acceleration have changed on at least two axises, we are probably in a shake motion */
        private boolean isAccelerationChanged() {
            float deltaX = Math.abs(xPreviousAccel - xAccel);
            float deltaY = Math.abs(yPreviousAccel - yAccel);
            float deltaZ = Math.abs(zPreviousAccel - zAccel);
            return (deltaX > shakeThreshold && deltaY > shakeThreshold)
                    || (deltaX > shakeThreshold && deltaZ > shakeThreshold)
                    || (deltaY > shakeThreshold && deltaZ > shakeThreshold);
        }
        private void executeShakeAction() {
            swipeContainer.setRefreshing(true);
            new CountDownTimer(3000,3000) {
                public void onTick(long millisUntilFinished) {}
                public void onFinish() {
                    swipeContainer.setRefreshing(false);
                }
            }.start();
        }
    };
}
