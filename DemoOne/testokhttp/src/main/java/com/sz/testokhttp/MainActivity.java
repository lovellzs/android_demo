package com.sz.testokhttp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnClickListener{

    private Button txt_getrequest;
    private Button txt_postrequest;
    private TextView txt_showresponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt_getrequest = findViewById(R.id.txt_getrequest);
        txt_postrequest = findViewById(R.id.txt_postrequest);
        txt_showresponse = findViewById(R.id.txt_showresponse);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.txt_getrequest:

                break;
            case R.id.txt_postrequest:

                break;
            case R.id.txt_showresponse:

                break;
        }


    }
}
