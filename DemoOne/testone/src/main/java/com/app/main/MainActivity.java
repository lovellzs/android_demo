package com.app.main;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.app.recyclerview.TestRecyclerviewActivity;
import com.app.testinflater.TestInflaterActivity;
import com.app.util.Util;

/**
 * Created by apple on 2017/12/16.
 */

public class MainActivity extends Activity implements View.OnClickListener{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_recyclelistview = findViewById(R.id.btn_recyclelistview);
        btn_recyclelistview.setOnClickListener(this);

        Button btn_inflater = findViewById(R.id.btn_inflater);
        btn_inflater.setOnClickListener(this);

        Button btn_huanxing = findViewById(R.id.btn_huanxing);
        btn_huanxing.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btn_recyclelistview:
                Util.gotoActivity(this, TestRecyclerviewActivity.class);
                break;
            case R.id.btn_inflater:
                Util.gotoActivity(this, TestInflaterActivity.class);
                break;
            case R.id.btn_huanxing:
                Util.gotoActivity(this, TestInflaterActivity.class);
                break;
        }
    }
}
