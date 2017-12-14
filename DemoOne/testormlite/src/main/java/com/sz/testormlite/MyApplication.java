package com.sz.testormlite;

import android.app.Application;
import android.support.multidex.MultiDex;

import com.sz.testormlite.dao.DaoManager;
import com.sz.testormlite.utils.MLog;

/**
 * Created by apple on 2017/11/22.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DaoManager.Instance().init(this);
        MultiDex.install(this);
    }

    @Override
    public void onTerminate() {
        // 程序终止的时候执行
        MLog.d("sz", "onTerminate");
        DaoManager.Instance().onDestroy();
        super.onTerminate();
    }

}
