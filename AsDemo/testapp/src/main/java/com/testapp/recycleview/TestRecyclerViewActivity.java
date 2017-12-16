package com.testapp.recycleview;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.testapp.main.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 2017/12/16.
 */

public class TestRecyclerViewActivity extends Activity {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private MyAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        //创建默认的线性LayoutManager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mRecyclerView.setHasFixedSize(true);
        //创建并设置Adapter
        mAdapter = new MyAdapter(this ,getDummyDatas());
        mRecyclerView.setAdapter(mAdapter);
    }

    private List<String> getDummyDatas(){

        List<String> list  = new ArrayList<String>();
        for(int i = 0; i < 30 ; i++ ){
            list.add( " 我是帅哥 === "  + i );
        }
        return list;
    }
}
