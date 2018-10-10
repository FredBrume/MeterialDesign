package com.example.fredbrume.meterialdesign.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by fredbrume on 12/23/17.
 */

public class AppUtils {

    public static final String MY_PREFS_NAME = "editMode";

    public static Drawable mainBackgroundLogo(Context context)
    {
        final String uri = "@drawable/school_item_logo";

        final int imageResource = context.getResources().getIdentifier(uri, null, context.getPackageName());
        Drawable res = context.getResources().getDrawable(imageResource);

        return res;
    }
//
//    public static String editMode(Context context, String mode)
//    {
//        SharedPreferences.Editor editor = context.getSharedPreferences(MY_PREFS_NAME, context.MODE_PRIVATE).edit();
//        editor.putString("name", "Elena");
//        editor.putInt("idName", 12);
//        editor.apply();
//    }

}
