package com.sz.testokhttp.utils;

import android.content.Context;
import android.telephony.TelephonyManager;

/**
 * Created by apple on 2017/11/29.
 */

public class Util {

    public static String getPhoneNumber(Context ctx) {
        String number = "";

        TelephonyManager manager = (TelephonyManager) ctx.getSystemService(ctx.TELEPHONY_SERVICE);

        try {
            number = manager.getLine1Number();
        } catch (Exception e) {
            // TODO: handle exception
        }

        return number;
    }



}
