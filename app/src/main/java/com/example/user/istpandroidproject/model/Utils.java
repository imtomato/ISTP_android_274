package com.example.user.istpandroidproject.model;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;

import com.example.user.istpandroidproject.R;

/**
 * Created by user on 2016/9/22.
 */
public class Utils {

    public static Drawable getDrawable(Context context,
                                       int drawableId) {

        if(Build.VERSION.SDK_INT < 21) {
            return context.getResources().getDrawable(drawableId);
        }
        else {
            return context.getResources().getDrawable(drawableId, null);
        }

    }


}
