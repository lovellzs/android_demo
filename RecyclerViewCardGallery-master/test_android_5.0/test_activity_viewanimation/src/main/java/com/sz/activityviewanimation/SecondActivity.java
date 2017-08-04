package com.sz.activityviewanimation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SecondActivity extends AppCompatActivity {
    View img_avatar ,txt_name ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        img_avatar = findViewById(R.id.img_avatar);
        txt_name = findViewById(R.id.txt_name);

        img_avatar.setTransitionName("img_avatar");//第二个Activity里的头像控件
        txt_name.setTransitionName("txt_name");//第二个Activity里的名字控件
        postponeEnterTransition();
        startPostponedEnterTransition();
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.finishAfterTransition();
    }
}
