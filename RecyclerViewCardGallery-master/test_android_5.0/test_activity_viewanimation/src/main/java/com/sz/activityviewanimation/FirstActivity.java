package com.sz.activityviewanimation;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;

public class FirstActivity extends AppCompatActivity {

    View img_avatar ,txt_name ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        img_avatar = findViewById(R.id.img_avatar);
        txt_name = findViewById(R.id.txt_name);

        img_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Pair<View, String> p = new Pair<View, String>(img_avatar, "img_avatar");//haderIv是头像控件
                Pair<View, String> p1 = new Pair<View, String>(txt_name, "txt_name");//nameTv是名字控件

                Intent intent = new Intent(FirstActivity.this, SecondActivity.class);
                FirstActivity.this.startActivity(intent, ActivityOptions .makeSceneTransitionAnimation(FirstActivity.this, p, p1).toBundle());

            }
        });

    }

}
