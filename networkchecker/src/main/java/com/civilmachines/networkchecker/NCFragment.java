package com.civilmachines.networkchecker;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.RelativeLayout;

public class NCFragment extends Fragment {

    BroadcastReceiver breceiver;
    RelativeLayout NoNetBar;
    String KEY_BROADCAST_INTENTFILTER = "android.net.conn.CONNECTIVITY_CHANGE";

    public void setRelative(RelativeLayout param_bar) {
        NoNetBar = param_bar;
        setReceiver();
    }

    public void intent(final Class<? extends Activity>  activity, String Key, int data){
        Intent intent = new Intent(getActivity(), activity);
        if (Key!=null)
            intent.putExtra(Key, data);
        getActivity().startActivity(intent);
    }
    public void intent(final Class<? extends Activity>  activity, String Key, String data){
        Intent intent = new Intent(getActivity(), activity);
        if (Key!=null)
            intent.putExtra(Key, data);
        getActivity().startActivity(intent);
    }

    public void setReceiver(){
        if(NoNetBar != null) {
            NoNetBar.setVisibility(View.INVISIBLE);

            breceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    boolean isConnected = intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);
                    if (isConnected) {
                        NoNetBar.setVisibility(View.VISIBLE);

                    } else if (!isConnected) {
                        NoNetBar.setVisibility(View.INVISIBLE);

                    }

                }
            };
        }
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        if(breceiver != null) {
            getActivity().registerReceiver(breceiver, new IntentFilter(KEY_BROADCAST_INTENTFILTER));
        }
        super.onResume();
    }

    @Override
    public void onPause() {
        if(breceiver != null) {
            getActivity().unregisterReceiver(breceiver);
        }
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

