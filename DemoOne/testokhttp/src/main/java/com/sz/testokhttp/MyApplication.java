package com.sz.testokhttp;

import android.app.Application;

import com.sz.testokhttp.net.HttpCaller;

/**
 * Created by apple on 2017/11/28.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        HttpCaller.getInstance().setContext( this );
    }
}
