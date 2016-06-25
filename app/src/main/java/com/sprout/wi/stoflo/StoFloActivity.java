package com.sprout.wi.stoflo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
    private Button mLogoutButton;
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
        mLogoutButton = (Button) findViewById(R.id.logout_button);
        mGameListView = (ListView) findViewById(R.id.GAME_LIST_VIEW);

        mCreateNewGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpToCreatePage();
            }
        });
        mContinueLastGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpToGamePage(0,0);
            }
        });
        mLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
    }

    private void logout() {
        AVUser.logOut();
        startActivity(new Intent(this,LoginActivity.class));
        finish();
    }

    private void jumpToCreatePage(){
        startActivity(new Intent(this,CreateStoryActivity.class));
    }

    private void jumpToGamePage(int game) {
        jumpToGamePage(game,0);
    }

    private void jumpToGamePage(int game, int chapter) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("game", game);
        intent.putExtra("chapter", chapter);
        startActivity(intent);
    }
}
