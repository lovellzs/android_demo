package com.sz.testokhttp.net;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.sz.testokhttp.func.RequestDataCallback;
import com.sz.testokhttp.utils.MLog;

import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by apple on 2017/11/28.
 */

public class HttpCaller {

    private String TAG = "HTTP";
    private Context context = null;
    private Map<String, Call> requestHandleMap = null;//以URL为KEY存储的请求
    private static HttpCaller instance = null;
    private final OkHttpClient client;// 实例话对象
    private CacheControl cacheControl = null;
    private Gson gson = null;

    public final static int MESSAGE_SUCCESS = 1000;
    public final static int MESSAGE_FAILURE = -1000;

    private HttpCaller() {

        client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        requestHandleMap = Collections.synchronizedMap(new WeakHashMap<String, Call>());

        cacheControl = new CacheControl.Builder().noStore().noCache().build();

        gson = new Gson();

    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            CallbackMessage cm = (CallbackMessage) msg.obj;
            cm.callback();
        }
    };

    public static HttpCaller getInstance() {
        if (instance == null) {
            instance = new HttpCaller();
        }
        return instance;
    }

    public void setContext(Context ctx) {
        this.context = ctx;
        if (context == null) {
            MLog.e(TAG, "HTTPCaller的context是NULL");
        }
    }


    private void autoCancel(String function) {
        Call call = requestHandleMap.remove(function);
        if (call != null) {
            call.cancel();
        }
    }

    private void add(String function, Call call) {
        if (!TextUtils.isEmpty(function)) {
            autoCancel(function);
            requestHandleMap.put(function, call);
        }

    }

    //移除map中的见 键值对
    private void clear(String url) {
        requestHandleMap.remove(url);
    }


    private <T> void sendCallback(RequestDataCallback<T> callback) {
        sendCallback(-1, null, null, callback);
    }

    private <T> void sendCallback(int status, T data, byte[] body, RequestDataCallback<T> callback) {
        CallbackMessage<T> msgData = new CallbackMessage<T>();
        msgData.body = body;
        msgData.callback = callback;
        msgData.data = data;
        msgData.status = status;

        Message msg = Message.obtain();
        msg.obj = msgData;
        handler.sendMessage(msg);
    }

    private class CallbackMessage<T> {
        public RequestDataCallback<T> callback;
        public T data;
        public int status;
        public byte[] body;

        public void callback() {
            if (data == null) {
                callback.dataCallback(null);
            } else {
                callback.dataCallback(status, data, body);
            }
        }
    }

    public <T> void get(final Class<T> clazz, final String key, final String url, Header[] header, final RequestDataCallback<T> callback) {

        HttpResponseHandler handle = new HttpResponseHandler() {
            @Override
            public void onFailure(int status, byte[] data) {
                clear(key);
                String str = "";
                if (MLog.isDebug) {
                    try {
                        str = new String(data, "utf-8");
                        MLog.i(TAG, url + " " + status + " " + str);

                    } catch (Exception e) {

                    }
                }

                //处理OKHttp 取消请求后 抛出异常
                if("Canceled".equalsIgnoreCase(str)){
                    return ;
                }
                sendCallback(callback);//处理callback
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    clear(key);

                    String str = new String(responseBody, "utf-8");
                    if (MLog.isDebug) {
                        MLog.i(TAG, url + " " + statusCode + " " + str);
                    }
                    T t = gson.fromJson(str, clazz);

                    sendCallback(statusCode, t, responseBody, callback); //处理callback
                } catch (Exception e) {
                    if (MLog.isDebug) {
                        e.printStackTrace();
                        MLog.e(TAG, "自动解析错误:" + e.toString());   // js中的数字，boolean 转成java中的字符串都不会报错
                    }

                    sendCallback(callback);//处理callback
                }
            }
        };

        add(key, get(url, header, handle));
    }


    public Call get(String url, Header[] header, HttpResponseHandler callback) {
        return this.get(url, header, callback, true);
    }

    public Call get(String url, Header[] header, HttpResponseHandler callback, boolean sing) {
        if (sing) {
//            url = NUtil.get(this.context,url); 签名加密
        }

        Request.Builder builder = new Request.Builder()
                .url(url)
                .get()
                .cacheControl(cacheControl);

        if (header == null) {
            builder.header("Connection", "close");
            builder.header("Accept", "*/*");
        } else {
            for (Header h : header) {
                if (!TextUtils.isEmpty(h.getName()) && !TextUtils.isEmpty(h.getValue())) {
                    builder.header(h.getName(), h.getValue());
                }
            }
        }

        Request request = builder.build();
        Call call = client.newCall(request);
        call.enqueue(callback);

        return call;
    }


}
