package com.sz.indexactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.testanimate.AnimateActivity;
import com.example.testanimate.GridAnimateActivity;
import com.sz.main.test.R;
import com.sz.testgesture.TestGestureActivity;
import com.sz.testlayout.TestFrameBgActivity;
import com.sz.testlayout.TestRippleActivity;
import com.sz.testnewgallery.TestNewGalleryActivity;
import com.sz.testscrooll.TestScrollActivity;

public class IndexActivity extends Activity implements OnClickListener{
	private Button btn_test_gesture;
	private Button btn_test_animate;
	private Button btn_test_scrollview;
	private Button btn_test_framelayout_bg;
	private Button btn_test_newgallery;
	private Button btn_test_testripple;
	private Button btn_test_giftanimate;

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
		
		btn_test_framelayout_bg = (Button)findViewById(R.id.btn_test_framelayout_bg); 
		btn_test_framelayout_bg.setOnClickListener(this);
		
		btn_test_newgallery = (Button)findViewById(R.id.btn_test_newgallery); 
		btn_test_newgallery.setOnClickListener(this);
		
		btn_test_testripple = (Button)findViewById(R.id.btn_test_testripple); 
		btn_test_testripple.setOnClickListener(this);
		btn_test_giftanimate = (Button)findViewById(R.id.btn_test_giftanimate); 
		btn_test_giftanimate.setOnClickListener(this);
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
		}else if( id == R.id.btn_test_framelayout_bg){
			toActivity( TestFrameBgActivity.class );
		}else if( id == R.id.btn_test_newgallery){
			toActivity( TestNewGalleryActivity.class );
		}else if( id == R.id.btn_test_testripple){
			toActivity( TestRippleActivity.class );
		}else if( id == R.id.btn_test_giftanimate){
			toActivity( GridAnimateActivity.class );
		}
	}
	
	private void toActivity(Class activity) {
		Intent intent = new Intent( this , activity );
		startActivity(intent);
	}
	
}