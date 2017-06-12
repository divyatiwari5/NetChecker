package com.civilmachines.netchecker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.civilmachines.networkchecker.NCActivity;

public class MainActivity extends AppCompatActivity {


    TextView textView1, textView2;
    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView1 = (TextView) findViewById(R.id.conn);
        textView2 = (TextView) findViewById(R.id.notconn);
        NCActivity ncActivity = new NCActivity();
        ncActivity.setRelative(relativeLayout);



    }
}
