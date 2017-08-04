package com.sz.testnewgallery;

import java.util.Arrays;

import com.crazysunj.cardslideview.CardViewPager;
import com.sz.main.test.R;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class TestNewGalleryActivity extends FragmentActivity{
	
	private final String[] imageArray = {
	        "start1",
	        "start2",
	        "start3",
	        "start4",
	        "start5"
	};
	private final Integer[] imageIdArray = {
			R.drawable.start1,
			R.drawable.start2,
			R.drawable.start3,
			R.drawable.start4,
			R.drawable.start5			
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_testnewgallery);
	    CardViewPager viewPager = (CardViewPager) findViewById(R.id.viewpager);
	    
//	    viewPager.bind(getSupportFragmentManager(), new MyCardHandler(), Arrays.asList(imageArray));
	    viewPager.bind(this.getSupportFragmentManager(), new MyCardHandler(null,imageArray,this), Arrays.asList(imageIdArray));
	}
}


