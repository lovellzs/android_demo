package com.sz.testormlite.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.sz.testormlite.bean.User;
import com.sz.testormlite.utils.MLog;

import java.sql.SQLException;

/**
 * Created by apple on 2017/11/22.
 */

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {


    private static final String TABLE_NAME = "sz-test.db";
    private static final int DATABASE_VERSION = 3;


    /**
     * userDao ，每张表对于一个
     */
    private Dao<User, Integer> userDao;

    public DatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        CreateTable(sqLiteDatabase,connectionSource);
    }

    public void CreateTable(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTableIfNotExists(connectionSource, User.class);
            MLog.i(DatabaseHelper.class.getName(),"created new entries in onCreate: ");
        } catch (SQLException e) {
            MLog.e(DatabaseHelper.class.getName(), "Can't create database");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        MLog.d("oldVersion:" + oldVersion + "   newVersion:" + newVersion);
        if(newVersion>oldVersion){
            try{
//                TableUtils.dropTable(connectionSource, User.class, true);


                this.CreateTable(sqLiteDatabase,connectionSource);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void close() {
        super.close();
        userDao = null;
    }

    public Dao<User, Integer> getUserDao(){
        if (userDao == null)
        {
            try {
                userDao = getDao(User.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return userDao;
    }

//    private static DatabaseHelper instance;
//
//    /**
//     * 单例获取该Helper
//     *
//     * @param context
//     * @return
//     */
//    public static synchronized DatabaseHelper getHelper(Context context)
//    {
//        if (instance == null)
//        {
//            synchronized (DatabaseHelper.class)
//            {
//                if (instance == null)
//                    instance = new DatabaseHelper(context);
//            }
//        }
//        return instance;
//    }

    public void init(){
        this.getWritableDatabase().close();
    }

}
