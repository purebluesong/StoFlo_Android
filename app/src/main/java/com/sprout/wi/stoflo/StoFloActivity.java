package com.sprout.wi.stoflo;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import com.avos.avoscloud.AVUser;

/**
 * Created by purebluesong on 2016/6/21.
 */
public class StoFloActivity extends Activity {

    private EditText mUsernameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_stoflo);

        mUsernameView = (EditText) findViewById(R.id.status_username);
        mUsernameView.setText(AVUser.getCurrentUser().getUsername());
    }
}
