package com.civilmachines.netchecker;

import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.civilmachines.networkchecker.NCActivity;

public class MainActivity extends NCActivity {


    TextView textView1;
    RelativeLayout relativeLayout, relativeLayout2;
    private Runnable netconn, netdisconn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        relativeLayout = (RelativeLayout) findViewById(R.id.rl);
        relativeLayout2 = (RelativeLayout) findViewById(R.id.rl2);
        textView1 = (TextView) findViewById(R.id.conn);
        setRelative(relativeLayout);



    }
}
