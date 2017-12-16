package com.sz.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

/**
 * Created by apple on 2017/12/16.
 */

public class Util {

    public static void gotoActivity(Context context , Class clazz){
        Intent intent = new Intent(context,clazz);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
