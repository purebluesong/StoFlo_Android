package com.sprout.wi.stoflo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import com.avos.avoscloud.AVUser;

/**
 * Created by purebluesong on 2016/6/21.
 */
public class StoFloActivity extends Activity {

    private EditText mUsernameView;
    private Button mCreateNewGameButton;
    private Button mContinueLastGameButton;
    private ListView mGameListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stoflo);

        initView();

    }

    private void initView() {
        mUsernameView = (EditText) findViewById(R.id.status_username);
        mUsernameView.setText(AVUser.getCurrentUser().getUsername());
        mCreateNewGameButton = (Button) findViewById(R.id.create_new_game);
        mContinueLastGameButton = (Button) findViewById(R.id.continue_last_button);
        mGameListView = (ListView) findViewById(R.id.GAME_LIST_VIEW);


    }

    private void jumpToCreatePage(){

    }
}
