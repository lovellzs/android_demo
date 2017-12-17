package com.testapp.pullrefresh;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.testapp.main.R;
import com.testapp.recycleview.MyAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 2017/12/16.
 */

public class TestPullRefreshListViewActivity extends Activity {

    private PullToRefreshListView prlList;
    private MyPullRefreshAdapter myAdapter;
    private List<String> list;

    Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            prlList.onRefreshComplete();
            myAdapter.notifyDataSetChanged();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pullrefresh);

        prlList = findViewById(R.id.prl);

        prlList.setMode(PullToRefreshBase.Mode.BOTH);
        prlList.setShowIndicator(false);

        myAdapter = new MyPullRefreshAdapter(this , getData(), prlList.getListView());
        prlList.setAdapter(myAdapter);
        prlList.post(new Runnable() {
            @Override
            public void run() {
                prlList.setSelection(myAdapter.getCount() - 1);//创建listview  初始最后一条数据
            }
        });

        prlList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
//                syAdapter.getFirstPage();

                myAdapter.setCallBack(new MyCallback() {
                    @Override
                    public void databack() {
                        Message msg = Message.obtain();
                        msg.obj = "onPullDownToRefresh";
                        handler.sendMessage(msg);
                    }
                });
                myAdapter.setDirection(MyPullRefreshAdapter.TO_DOWN);
                myAdapter.addData();
            }

            @Override
            public void onPullUpToRefresh( PullToRefreshBase<ListView> refreshView) {
//                syAdapter.getNextPage();

                myAdapter.setCallBack(new MyCallback() {
                    @Override
                    public void databack() {
                        Message msg = Message.obtain();
                        msg.obj = "onPullUpToRefresh";
                        handler.sendMessage(msg);
                    }
                });
                myAdapter.setDirection(MyPullRefreshAdapter.TO_UP );
                myAdapter.addData();
            }

        });
    }

    private List<String> getData(){

        if( list == null ){
            list  = new ArrayList<String>();
        }
        for(int i = 0; i < 30 ; i++ ){
            list.add( " 我是帅哥 === "  + i );
        }
        return list;
    }
}
