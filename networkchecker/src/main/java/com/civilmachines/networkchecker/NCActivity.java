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
import android.widget.Toast;

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

    /**
     * This is used to set Realtive layout and buttons that will be affected from net status change
     * @param param_bar is RelativeLayout referring to the layout that will be shown when net is off
     * @param param_buttons are buttons that will only be enabled if net is working
     */
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

   /* public boolean isOnline() {
        ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if(netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()){
            Toast.makeText(getApplicationContext(), "No Internet connection!", Toast.LENGTH_LONG).show();
            NoNetBar.setVisibility(View.INVISIBLE);
            NoNetBar2.setVisibility(View.VISIBLE);
            return false;
        }
        NoNetBar.setVisibility(View.VISIBLE);
        NoNetBar2.setVisibility(View.INVISIBLE);
        Toast.makeText(getApplicationContext(), "Connected", Toast.LENGTH_LONG).show();
        return true;
    }
    */
  public void setReceiver(){
        if(NoNetBar != null) {
           // NoNetBar.setVisibility(View.INVISIBLE);

            breceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    boolean isDisConnected = intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);

                    if (isDisConnected) {
                       Toast.makeText(NCActivity.this, "Internet DisConnected!", Toast.LENGTH_SHORT).show();
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
                       Toast.makeText(NCActivity.this, "Internet Connected!", Toast.LENGTH_SHORT).show();
                        NoNetBar.setVisibility(View.VISIBLE);
                        if(netConnected != null)
                            netConnected.run();

                    }

                }
            };
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
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        if(breceiver != null) {
            // Register Broadcast Receiver
            registerReceiver(breceiver, new IntentFilter(KEY_BROADCAST_INTENTFILTER));
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        if(breceiver != null) {
            // Unregister the Broadcast Receiver
            unregisterReceiver(breceiver);
        }
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
