package com.sprout.wi.stoflo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.Toast;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.avos.avoscloud.okhttp.Callback;

import static com.sprout.wi.stoflo.R.string.info_chapter_content;
import static com.sprout.wi.stoflo.R.string.info_table_chapter_background;

/**
 * Created by purebluesong on 2016/6/24.
 */
public class CreateStoryActivity extends Activity implements CreateGameDialogFragment.CreateGameListener{

    private EditText mChapterNameEdit;
    private EditText mChapterContentEdit;
    private Button mChapterName;
    private Button mChapterContentBackground;
    private Button mChapterContentText;
    private Button mChaptersNext;
    private Button mSave;
    private Button mCancel;
    private Button mSaveNext;
    private HorizontalScrollView mNextChapters;
    private ChapterChooser mChapterChooser;
    private AVObject mChapter;
    private AVObject mGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_story);
        if (AVUser.getCurrentUser() == null) {
            jumpTo(LoginActivity.class);
        }
        initData();
        initView();
        fillContentWith(mChapter);
        registerOCL();

    }

    private void initData() {
        mGame = AVUser.getCurrentUser().getAVObject(getString(R.string.info_key_self_game));
        if (mGame == null) {
            showCreateGameDialog();
        }
        mChapter = AVUser.getCurrentUser().getAVObject(getString(R.string.info_self_game_current_edit));
    }

    private void fillContentWith(AVObject chapter) {
        String name = chapter.getString(getString(R.string.info_table_chapter_name));
        String content = chapter.getString(getString(R.string.info_table_chapter_content));
//        Drawable background = chapter.getBytes(getString(R.string.info_table_chapter_background));

        mChapterNameEdit.setText(name);
        mChapterContentEdit.setText(content);
//        mChapterContentEdit.setBackground();
    }

    private void showCreateGameDialog() {
        DialogFragment dialog = new CreateGameDialogFragment();
        dialog.show(getFragmentManager(), "CreateGameDialogFragment");
    }

    private void initView() {
        mChapterNameEdit = (EditText) findViewById(R.id.chapter_name_edit);
        mChapterContentEdit = (EditText) findViewById(R.id.chapter_content_edit);
        mChapterContentBackground = (Button) findViewById(R.id.edit_function_chapter_background);
        mChapterContentText = (Button) findViewById(R.id.chapter_content_text);
        mChaptersNext = (Button) findViewById(R.id.chapter_next_chooser);
        mChapterName = (Button) findViewById(R.id.chapter_name_button);
        mNextChapters = (HorizontalScrollView) findViewById(R.id.function_next_chapters_scroll);

        mSave = (Button) findViewById(R.id.create_save);
        mSaveNext = (Button) findViewById(R.id.create_save_next);
        mCancel = (Button) findViewById(R.id.create_cancel);

        mChapterChooser = new ChapterChooser();
    }

    private void registerOCL() {
        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptSave();
            }
        });
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });
        mSaveNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptSave();
                clearPage();
                mChapterChooser.choose(ChapterChooser.CHOOSE_SINGLE);
            }
        });
        mChaptersNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mChapterChooser.choose(ChapterChooser.CHOOSE_MULTIPLE);
            }
        });
        mChapterContentBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setChapterBackground();
            }
        });
        mChapterContentText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mChapterName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mChapterChooser.choose(ChapterChooser.CHOOSE_SINGLE);
            }
        });
    }

    private void setChapterBackground() {

    }

    private void clearPage() {
        mChapterContentEdit.setText("");
        mChapterNameEdit.setText("");
    }

    private void cancel() {
        clearPage();
        jumpTo(StoFloActivity.class);
    }

    private void jumpTo(Class<?> activityClass) {
        startActivity(new Intent(this,activityClass));
        finish();
    }

    private boolean attemptSave() {
        boolean cancel = false;
        View focusView = null;

        if (isInvalidateChapterName()) {
            focusView = mChapterNameEdit;
            cancel = true;
        }

        if (isInvalidateChapterContent()) {
            focusView = mChapterContentEdit;
            cancel = true;
        }

        if (cancel ){
            focusView.requestFocus();
        } else {
            try {
                saveChapterInstance();
            } catch (AVException e) {
                cancel = true;
                e.printStackTrace();
            }
        }

        return !cancel;
    }

    private void saveChapterInstance() throws AVException {
        String gameName = mGame.getString(getString(R.string.info_table_game_name));
        String title = mChapterNameEdit.getText().toString();
        String content = mChapterContentEdit.getText().toString();

        saveChapter(gameName, title, content, null, null);
    }

    private boolean isInvalidateChapterContent() {
        boolean success = mChapterContentEdit.getText().length() != 0;
        if (!success) {
            mChapterContentEdit.setError(getString(R.string.error_invalid_chapter_content));
        }
        return success;
    }

    private boolean isInvalidateChapterName() {
        boolean success = mChapterNameEdit.getText().length() != 0;
        if (! success) {
            mChapterContentEdit.setError(getString(R.string.error_invalid_chapter_name));
        }
        return success;
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        EditText nameEdit = (EditText)dialog.getView().findViewById(R.id.create_game_name);
        String gameName = nameEdit.getText().toString();
        String description = ((EditText)dialog.getView().findViewById(R.id.create_game_description)).getText().toString();
        boolean cancel = false;

        if (gameName.length() == 0) {
            nameEdit.setError(getString(R.string.error_invalid_game_name));
            cancel = true;
        }

        if (!cancel) {
            AVObject game = new AVObject(getString(R.string.info_table_game));
            game.put(getString(R.string.info_table_game_name), gameName);
            game.put(getString(R.string.info_table_game_description), description);
            game.saveInBackground(new SaveCallback() {
                @Override
                public void done(AVException e) {
                    Toast.makeText(getApplicationContext(),R.string.success_create_game,Toast.LENGTH_SHORT);
                }
            });
            createStartChapterSilence(gameName);
            AVUser.getCurrentUser().put(getString(R.string.info_table_owner_game), game.getObjectId());
            AVUser.getCurrentUser().saveInBackground();
        } else {
            Toast.makeText(this,R.string.error_create_game_failed,Toast.LENGTH_LONG);
        }
    }

    private void createStartChapterSilence(String gameName) {
        try {
            saveChapter(gameName,getString(R.string.info_chapter_start_name),"",null,null);
        } catch (AVException e) {
            e.printStackTrace();
        }
    }

    private void saveChapter(String gameName, String title, String content, byte[] background, SaveCallback callback) throws AVException {
        AVObject chapter = new AVObject(getString(R.string.info_game_prefix)+gameName);
        chapter.put(getString(R.string.info_table_chapter_name),title);
        chapter.put(getString(R.string.info_table_chapter_content), content);
        chapter.put(getString(R.string.info_table_chapter_background), background);
        chapter.saveInBackground(callback);
    }

    class ChapterChooser {

        public static final int CHOOSE_SINGLE = 1;
        public static final int CHOOSE_MULTIPLE = 2;

        public void choose(int chooseflag) {
            if (chooseflag == CHOOSE_SINGLE) {

            } else if ( chooseflag == CHOOSE_MULTIPLE) {

            } else {

            }
        }
    }
}
