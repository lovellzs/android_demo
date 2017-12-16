package com.testapp.listview;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.AbsListView;
import android.widget.ListView;

import com.testapp.main.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by apple on 2017/12/16.
 */

public class TestListViewActivity extends Activity implements AbsListView.OnScrollListener{
    private ListView mListView;
    private MyListAdapter myAdapter;
    private List<String> list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);

        mListView = findViewById(R.id.my_recycler_view);
        myAdapter = new MyListAdapter(this, getData());
        mListView.setAdapter(myAdapter );
        mListView.post(new Runnable() {
            @Override
            public void run() {
                mListView.setSelection(myAdapter.getCount() - 1);
            }
        });

        mListView.setOnScrollListener( this );
    }

    private List<String> getData(){

        if(list ==null){
            list  = new ArrayList<String>();
        }

        for(int i = 0; i < 20 ; i++ ){
            list.add(0, " 我是帅哥 === "  + list.size() );
        }

        return list;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if( firstVisibleItem == 0){
            getData();
            myAdapter.notifyDataSetChanged();
        }
    }
}
