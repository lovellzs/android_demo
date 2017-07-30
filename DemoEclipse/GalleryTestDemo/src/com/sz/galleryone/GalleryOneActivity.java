package com.sz.galleryone;

import android.app.Activity;
import android.os.Bundle;

import com.sz.gallery.R;

public class GalleryOneActivity extends Activity{

	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_galleryone);     
	        Integer[] images = { 
	        		R.drawable.img1, R.drawable.img2, R.drawable.img3, R.drawable.img4,
	                R.drawable.img5, R.drawable.img6, R.drawable.img7, R.drawable.img8
	         };
	        
	        ImageAdapter adapter = new ImageAdapter(this, images);
	        adapter.createReflectedImages();

	        GalleryFlow galleryFlow = (GalleryFlow) findViewById(R.id.Gallery01);
	        galleryFlow.setAdapter(adapter);
	        
	}
}