package com.thesis.anti.ragging;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String PREF_USER_FIRST_TIME = "user_first_time";
    boolean isUserFirstTime;
    private SharedPreferences pref;
    String flag_fragment = "";

    NotificationManager manager;
    Notification myNotication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        isUserFirstTime = Boolean.valueOf(Utils.readSharedSetting(MainActivity.this, PREF_USER_FIRST_TIME, "true"));

        Intent introIntent = new Intent(MainActivity.this, PagerActivity.class);
        introIntent.putExtra(PREF_USER_FIRST_TIME, isUserFirstTime);

        if (isUserFirstTime) {

            startActivity(introIntent);

        }

        setContentView(R.layout.main);
        pref = getPreferences(0);
        initFragment();
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        Intent intent = new Intent(this, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 1, intent, 0);


        Drawable drawable= ContextCompat.getDrawable(this,R.drawable.s1);

        Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();

        Notification.Builder builder = new Notification.Builder(MainActivity.this);

        builder.setAutoCancel(false);
        builder.setTicker("this is ticker text");
        builder.setContentTitle("Stop Ragging");
        builder.setContentText("Make a Ragging free Campus");
        /*if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setLargeIcon(bitmap);
            builder.setSmallIcon(R.drawable.s1);
        } else {
            builder.setSmallIcon(R.drawable.s2);
        }*/
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),
                R.drawable.s1));
        builder.setSmallIcon(R.drawable.s1);
        builder.setContentIntent(pendingIntent);
        builder.setOngoing(true);
       // builder.setSubText("This is subtext...");   //API level 16
        builder.setNumber(100);
        builder.build();

        myNotication = builder.getNotification();
        manager.notify(11, myNotication);
    }

    private void initFragment(){
        Fragment fragment;
        if(pref.getBoolean(Constants.IS_LOGGED_IN,false)){
            fragment = new ProfileFragment();
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragment_frame,fragment,"TAG")
                    .commit();
            flag_fragment = "1";
        }else {
            fragment = new LoginFragment();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_frame,fragment);
            ft.commit();
        }

    }

    boolean flag = false;

    boolean flag2 = false;

    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {

       // ProfileFragment fragment = new ProfileFragment();
        ProfileFragment myFragment = (ProfileFragment) getFragmentManager().findFragmentByTag("TAG");
        if (myFragment != null && myFragment.isVisible()) {
            // add your code here
               if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
                   Log.d("Test", "Long press!");
                   Toast.makeText(getApplicationContext(),myFragment.getTag(),Toast.LENGTH_LONG).show();
                   flag = false;
                   flag2 = true;
                   myFragment.trouble();
                   return true;
               }
        }else{
           Toast.makeText(getApplicationContext(),"Long long test" + flag_fragment ,Toast.LENGTH_LONG).show();
       }
        return super.onKeyLongPress(keyCode, event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            event.startTracking();
            if (flag2 == true) {
                flag = false;
            } else {
                flag = true;
                flag2 = false;
            }

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {

            event.startTracking();
            if (flag) {
                Log.d("Test", "Short");
            }
            flag = true;
            flag2 = false;
            return true;
        }

        return super.onKeyUp(keyCode, event);
    }
}
