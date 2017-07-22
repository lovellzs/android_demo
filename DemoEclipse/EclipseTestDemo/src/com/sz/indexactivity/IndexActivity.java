package com.sz.indexactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.testanimate.AnimateActivity;
import com.sz.main.test.R;
import com.sz.testgesture.TestGestureActivity;
import com.sz.testscrooll.TestScrollActivity;

public class IndexActivity extends Activity implements OnClickListener{
	private Button btn_test_gesture;
	private Button btn_test_animate;
	private Button btn_test_scrollview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_index);
		findId();
	}
	
	private void findId() {
		btn_test_gesture = (Button)findViewById(R.id.btn_test_gesture); 
		btn_test_gesture.setOnClickListener(this);
		btn_test_animate = (Button)findViewById(R.id.btn_test_animate); 
		btn_test_animate.setOnClickListener(this);
		btn_test_scrollview = (Button)findViewById(R.id.btn_test_scrollview); 
		btn_test_scrollview.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if( id == R.id.btn_test_gesture ){
			toActivity( TestGestureActivity.class );
		}else if( id == R.id.btn_test_animate ){
			toActivity( AnimateActivity.class );
		}else if( id == R.id.btn_test_scrollview ){
			toActivity( TestScrollActivity.class );
		}
	}
	
	private void toActivity(Class activity) {
		Intent intent = new Intent( this , activity );
		startActivity(intent);
	}
	
}