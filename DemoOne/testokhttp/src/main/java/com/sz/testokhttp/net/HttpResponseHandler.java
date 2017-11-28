package com.sz.testokhttp.net;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.Response;

/**
 * Created by apple on 2017/11/28.
 */

public abstract class HttpResponseHandler implements Callback {

    public HttpResponseHandler() {
    }

    @Override
    public void onFailure(Call call, IOException e) {
        if(e!=null&&e.getMessage()!=null)
            onFailure(-1, e.getMessage().getBytes());
        else{
            onFailure(-1,"网络异常".getBytes());
        }
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        int code =response.code();
        byte[] body = response.body().bytes();
        if(code>299){
            onFailure(response.code(),body);
        }
        else{
            Headers headers = response.headers();
            Header[] hs = new Header[headers.size()];

            for (int i=0;i<headers.size();i++){
                hs[i] = new Header(headers.name(i),headers.value(i));
            }
            onSuccess(code,hs,body);
        }
    }

    public abstract void onFailure(int status,byte[] data);

    public abstract void onSuccess(int statusCode, Header[] headers, byte[] responseBody);

    public void onProgress(int bytesWritten, int totalSize){}

}
