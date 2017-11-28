package com.sz.testokhttp.func;

import com.sz.testokhttp.bean.User;
import com.sz.testokhttp.net.Header;
import com.sz.testokhttp.net.HttpCaller;

/**
 * Created by apple on 2017/11/28.
 */

public class AppRequestRouter {


    private static AppRequestRouter instance = null;

    private AppRequestRouter() {
    }

    public static AppRequestRouter getInstance(){
        if(instance==null){
            instance = new AppRequestRouter();
        }

        return instance ;
    }

    public void getUsers(RequestDataCallback<User> callback){
        String url = "http://192.168.39.124:3000/user";
        Header[] header = new Header[]{};

        HttpCaller.getInstance().get(User.class,"getuser",url,header, callback);
    }
}
