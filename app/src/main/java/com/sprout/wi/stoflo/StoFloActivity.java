package com.sprout.wi.stoflo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by purebluesong on 2016/6/21.
 */
public class StoFloActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stoflo);
        TextView tv = (TextView) findViewById(R.id.test_text);
        tv.setText("login succeed");

    }
}
