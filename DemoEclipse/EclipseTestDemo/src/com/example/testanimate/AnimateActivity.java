package com.example.testanimate;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.nineoldandroids.view.ViewHelper;
import com.sz.main.test.R;

/**
 * @author shizhe
 *
 */
public class AnimateActivity extends Activity {

	private TextView test_anim_move;
	private TextView test_anim_back;
	private ImageView test_img;
	private TextView test_anim_back1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_annim);
		
		test_anim_move = (TextView)findViewById(R.id.test_anim_move);
		test_anim_back = (TextView)findViewById(R.id.test_anim_back);
		test_anim_back1 = (TextView)findViewById(R.id.test_anim_back1);
		test_img = (ImageView)findViewById(R.id.test_img);
		
		test_anim_move.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				DisplayMetrics dm = new DisplayMetrics();
				AnimateActivity.this.getWindowManager().getDefaultDisplay().getMetrics(dm);
				
//				ViewHelper.setTranslationX(test_img, -200);
//				ViewHelper.setTranslationY(test_img, -200);
				ViewHelper.setRotation(test_img , -45 );//逆時針負數
				System.out.println( "===="+ ViewHelper.getTranslationX(test_img)  );
			}
		} );
		
		test_anim_back.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				System.out.println( "===="+ -ViewHelper.getTranslationX(test_img)  );
//				ViewHelper.setTranslationX(test_img, 0 );
//				ViewHelper.setTranslationY(test_img, 0 );
				ViewHelper.setRotation(test_img , 0 );
			}
		} );
		
		test_anim_back1.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				com.nineoldandroids.view.ViewPropertyAnimator.animate(test_img)
//				.translationX( 0 )
//				.translationY( 0 )
				.setInterpolator(new AccelerateInterpolator())
				.setListener(null)
				.setDuration(150)
				.rotation(0);
			}
		} );
	}
}
