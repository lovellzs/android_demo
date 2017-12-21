package com.app.testinflater;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.app.main.R;
import com.app.ui.Section;

/**
 * Created by apple on 2017/12/19.
 */

public class TestInflaterActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_inflater);

        View add_view = findViewById(R.id.add_view);
        add_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadLayout( R.layout.inflater_ll_layout ,(LinearLayout)findViewById(R.id.ll_layout));
            }
        });

        View inflate_section = findViewById(R.id.inflate_section);
        inflate_section.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadLayout( R.layout.inflater_vg_section ,(ViewGroup) findViewById(R.id.vg_section));
            }
        });

        View remove_view = findViewById(R.id.remove_view);
        remove_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ( (LinearLayout)findViewById(R.id.ll_layout) ).removeViewAt(0);
            }
        });
    }

    public void loadLayout(int layId , ViewGroup viewGroup) {
        LayoutInflater mInflater = LayoutInflater.from(getBaseContext());
        mInflater.inflate(layId, viewGroup);
    }
}
