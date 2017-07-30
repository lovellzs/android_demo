package com.sz.main.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.sz.gallery.R;
import com.sz.galleryone.GalleryOneActivity;
import com.sz.gallerytwo.GalleryTwoActivity;

public class IndexActivity extends Activity implements OnClickListener{

	private Button btn_test_gallery_one;
	private Button btn_test_gallery_two;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_index);
		findId();
	}
	
	private void findId() {
		btn_test_gallery_one = (Button)findViewById(R.id.btn_test_gallery_one); 
		btn_test_gallery_one.setOnClickListener(this);
		
		btn_test_gallery_two = (Button)findViewById(R.id.btn_test_gallery_two); 
		btn_test_gallery_two.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if( id == R.id.btn_test_gallery_one ){
			toActivity(GalleryOneActivity.class);
		}else if( id == R.id.btn_test_gallery_two ){
			toActivity(GalleryTwoActivity.class);
		}
	}
	
	private void toActivity(Class activity) {
		Intent intent = new Intent( this , activity );
		startActivity(intent);
	}
	
}