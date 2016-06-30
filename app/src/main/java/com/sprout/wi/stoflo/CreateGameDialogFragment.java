package com.sprout.wi.stoflo;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.avos.avoscloud.AVObject;

/**
 * Created by purebluesong on 2016/6/30.
 */
public class CreateGameDialogFragment extends DialogFragment {

    public interface CreateGameListener {
        void onDialogPositiveClick(DialogFragment dialog);
    }

    CreateGameListener mGameListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mGameListener = (CreateGameListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()+"must implement CreateGAmeListener");
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setView(R.layout.custom_create_game)
                .setTitle(R.string.create_new_game)
                .setPositiveButton(R.string.info_confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mGameListener.onDialogPositiveClick(CreateGameDialogFragment.this);
                        getDialog().dismiss();
                    }
                })
                .setNegativeButton(R.string.info_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getDialog().cancel();
                        jumpTo(StoFloActivity.class);
                    }
                });

        return builder.create();
    }

    private void jumpTo(Class<?> activityClass) {
        startActivity(new Intent(getActivity(),activityClass));
        getActivity().finish();
    }

}
