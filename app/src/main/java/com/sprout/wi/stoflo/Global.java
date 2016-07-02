package com.sprout.wi.stoflo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.StatusCallback;

import java.io.ByteArrayOutputStream;

/**
 * Created by purebluesong on 2016/6/21.
 */

public class Global {
    public static final int REQUSET_CODE_GALLERY = 1;

    public static byte[] Bitmap2Bytes(Bitmap bm) {
        return Bitmap2Bytes(bm,Bitmap.CompressFormat.PNG);
    }

    public static byte[] Bitmap2Bytes(Bitmap bm, Bitmap.CompressFormat format) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(format, 100, baos);
        return baos.toByteArray();
    }

    public static Bitmap Bytes2Bimap(byte[] b) {
        if (b.length != 0) {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        } else {
            return null;
        }
    }

}
