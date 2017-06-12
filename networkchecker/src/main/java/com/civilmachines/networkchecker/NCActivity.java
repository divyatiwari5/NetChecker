package com.civilmachines.networkchecker;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import java.util.ArrayList;


public class NCActivity extends AppCompatActivity{

    BroadcastReceiver breceiver;
    RelativeLayout NoNetBar;
    ArrayList<Button> buttons = null;
    String KEY_BROADCAST_INTENTFILTER = "android.net.conn.CONNECTIVITY_CHANGE";
    private Runnable netDisconnected;
    private Runnable netConnected;

    public void setRunnable(Runnable netConnected, Runnable netDisconnected){
        this.netConnected = netConnected;
        this.netDisconnected = netDisconnected;

    }

    public void setRelative(RelativeLayout param_bar, ArrayList<Button> param_buttons){
        buttons = param_buttons;
        NoNetBar = param_bar;
       // Toast.makeText(SampleActivity.this, "NoNetBar has been setup", Toast.LENGTH_SHORT).show();
        setReceiver();
    }

    public void pdOnbackpressed(final ProgressDialog pd){
        pd.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if(keyCode== KeyEvent.KEYCODE_BACK && !event.isCanceled()) {
                    if(pd.isShowing()) {
                        //your logic here for back button pressed event
                    }
                    return true;
                }
                return false;
            }
        });
    }

    public void setRelative(RelativeLayout param_bar){
        NoNetBar = param_bar;
        //Toast.makeText(SampleActivity.this, "NoNetBar has been setup", Toast.LENGTH_SHORT).show();
        setReceiver();
    }

    public void setReceiver(){
        if(NoNetBar != null) {
            NoNetBar.setVisibility(View.INVISIBLE);

            breceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    boolean isDisConnected = intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);

                    if (isDisConnected) {
                       //Toast.makeText(SampleActivity.this, "Internet DisConnected!", Toast.LENGTH_SHORT).show();
                        NoNetBar.setVisibility(View.VISIBLE);
                        if(buttons!=null){
                            for(int i=0; i<buttons.size(); i++){
                                buttons.get(i).setEnabled(false);
                            }
                        }
                        if(netDisconnected != null){
                            netDisconnected.run();
                        }
                    } else if (!isDisConnected) {
                        if(buttons!=null){
                            for(int i=0; i<buttons.size(); i++){
                                buttons.get(i).setEnabled(true);
                            }
                        }
                      //  Toast.makeText(SampleActivity.this, "Internet Connected!", Toast.LENGTH_SHORT).show();
                        NoNetBar.setVisibility(View.INVISIBLE);
                        if(netConnected != null)
                            netConnected.run();

                    }

                }
            };
          //  Toast.makeText(SampleActivity.this, "Broadcast Initialized", Toast.LENGTH_SHORT).show();
        }
    }

    public void intent(final Class<? extends Activity>  activity, String Key, int data){
        Intent intent = new Intent(this, activity);
        if (Key!=null)
            intent.putExtra(Key, data);
        startActivity(intent);
    }

    public void intent(final Class<? extends Activity>  activity, String Key, String data){
        Intent intent = new Intent(this, activity);
        if (Key!=null)
            intent.putExtra(Key, data);
        startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
       // Toast.makeText(SampleActivity.this, "App is Created", Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        if(breceiver != null) {
            registerReceiver(breceiver, new IntentFilter(KEY_BROADCAST_INTENTFILTER));
          //  Toast.makeText(SampleActivity.this, "Broadcast Registered", Toast.LENGTH_SHORT).show();
        }
        super.onResume();
//        Toast.makeText(SampleActivity.this, "App has Resumed", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        if(breceiver != null) {
            unregisterReceiver(breceiver);
         //   Toast.makeText(SampleActivity.this, "Broadcast UnRegistered", Toast.LENGTH_SHORT).show();
        }

        //Toast.makeText(SampleActivity.this, "App has Paused", Toast.LENGTH_SHORT).show();
        super.onPause();
    }

    @Override
    protected void onStop() {
        //Toast.makeText(SampleActivity.this, "App has stopped", Toast.LENGTH_SHORT).show();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
