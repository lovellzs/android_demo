package com.testapp.main;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.sz.util.Util;
import com.testapp.listview.TestListViewActivity;
import com.testapp.pullrefresh.TestPullRefreshListViewActivity;
import com.testapp.recycleview.TestRecyclerViewActivity;

/**
 * Created by apple on 2017/12/16.
 */

public class MainActivity extends Activity implements View.OnClickListener{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity);

        Button btn_recyclelistview = findViewById(R.id.btn_recyclelistview);
        btn_recyclelistview.setOnClickListener(this);

        Button btn_listview = findViewById(R.id.btn_listview);
        btn_listview.setOnClickListener(this);

        Button btn_pullrefreshlistview = findViewById(R.id.btn_pullrefreshlistview);
        btn_pullrefreshlistview.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btn_recyclelistview:
                Util.gotoActivity(this, TestRecyclerViewActivity.class);
                break;
            case R.id.btn_listview:
                Util.gotoActivity(this, TestListViewActivity.class);
                break;
            case R.id.btn_pullrefreshlistview:
                Util.gotoActivity(this, TestPullRefreshListViewActivity.class);
                break;
        }
    }


}
