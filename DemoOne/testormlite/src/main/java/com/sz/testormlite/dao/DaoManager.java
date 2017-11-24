package com.sz.testormlite.dao;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.sz.testormlite.bean.User;
import com.sz.testormlite.utils.MLog;

import java.sql.SQLException;

/**
 * Created by apple on 2017/11/22.
 */

public class DaoManager {


    private static DaoManager instance = null;

    private  Context context = null;
    private  DatabaseHelper databaseHelper = null;
    public static synchronized DaoManager Instance()
    {
        if (instance == null)
        {
            synchronized (DaoManager.class)
            {
                if (instance == null)
                    instance = new DaoManager();
            }
        }
        return instance;
    }

    private DaoManager(){

    }

    public void init(Context context){
        this.context = context ;//如果没有初始化，context会为空
        getHelper().init() ;
    }


    private DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper( context , DatabaseHelper.class);
        }
        return databaseHelper;
    }

    public void onDestroy() {
        //super.onDestroy();
		/*
		 * You'll need this in your class to release the helper when done.
		 */
        if (databaseHelper != null) {
            databaseHelper.close();
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }

    private Dao<User, Integer> userDao = null;
    public Dao<User, Integer> getUserDao() {
        try {
            if (this.userDao == null) {
                this.userDao = getHelper().getDao(User.class);
            }
        } catch (SQLException e) {
            MLog.e("sz",e.getMessage());
        }
        return userDao;
    }
}
