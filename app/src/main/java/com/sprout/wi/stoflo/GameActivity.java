package com.sprout.wi.stoflo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;

import java.util.List;

/**
 * Created by purebluesong on 2016/6/24.
 */
public class GameActivity extends Activity{
    private List<AVObject> mChapterList;
    private AVObject mGame;
    private AVObject mCurrentChapter;
    private Bitmap mBackground;
    private AVFile mBackgroundFile;
    private AVQuery<AVObject> mQuery;
    private List<AVObject> mCurrentNextChapters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        String gameID = getIntent().getStringExtra(getString(R.string.info_intent_game));
        String chapterID = getIntent().getStringExtra(getString(R.string.info_intent_chapter));
        iniData(gameID,chapterID);
        addChapterToView(mCurrentChapter);
    }

    private void addChapterToView(AVObject chapter) {
        String title = chapter.getString(getString(R.string.info_table_chapter_name));
        String content = chapter.getString(getString(R.string.info_table_chapter_content));
        mBackgroundFile = chapter.getAVFile(getString(R.string.info_table_chapter_background));
        mQuery = chapter.getRelation(getString(R.string.info_table_chapter_nexts)).getQuery();
        mCurrentNextChapters = null;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mBackground = Global.Bytes2Bimap(mBackgroundFile.getData());
                    mCurrentNextChapters = mQuery.find();
                } catch (AVException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();

        LayoutInflater inflater = getLayoutInflater();
        LinearLayout container = (LinearLayout) inflater.inflate(R.layout.chapter_show_asset,null);
        TextView contentView = (TextView) container.findViewById(R.id.chapter_content_show);
        HorizontalScrollView nextsView = (HorizontalScrollView) container.findViewById(R.id.chapter_next_container);
        contentView.setText(content);
    }

    private void iniData(final String gameID, final String chapterID) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mGame = AVObject.createWithoutData(getString(R.string.info_table_game), gameID);
                    mGame.fetch();
                    mCurrentChapter = AVObject.createWithoutData(mGame.getString(getString(R.string.info_table_chapter_table_name)) ,chapterID);
                    mCurrentChapter.fetch();
                } catch (AVException e) {
                    Toast.makeText(getApplicationContext(),getString(R.string.error_fetch_game_failed),Toast.LENGTH_LONG);
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
}
