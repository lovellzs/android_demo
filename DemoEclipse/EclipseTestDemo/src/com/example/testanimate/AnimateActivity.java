package com.example.testanimate;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.Animator.AnimatorListener;
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

	DisplayMetrics dm = null;
	
	private GiftPositionB[] pppp  = new GiftPositionB[10];
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_annim);
		
		getScreen();
		initPPPPP();
		
		test_anim_move = (TextView)findViewById(R.id.test_anim_move);
		test_anim_back = (TextView)findViewById(R.id.test_anim_back);
		test_anim_back1 = (TextView)findViewById(R.id.test_anim_back1);
		test_img = (ImageView)findViewById(R.id.test_img);
		
		test_anim_move.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				
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
				animate1(1, 9 , 0);
			}
		} );
	}
	
	private void getScreen(){
		dm = new DisplayMetrics();
		AnimateActivity.this.getWindowManager().getDefaultDisplay().getMetrics(dm);
	}
	
	private void initPPPPP() {
		pppp[0] = new GiftPositionB();
		pppp[0].index = 0;
		pppp[0].px = 0 + 300;
		pppp[0].py = dm.heightPixels/2 -300;
		
		pppp[1] = new GiftPositionB();
		pppp[1].index = 0;
		pppp[1].px = 0 + 100;
		pppp[1].py = dm.heightPixels -400;
		
		pppp[9] = new GiftPositionB();
		pppp[9].index = 9;
		pppp[9].px = dm.widthPixels/2 - 150;
		pppp[9].py = dm.heightPixels/2 + 250;
	}
	
	private  void initXY( int from1){
		test_img.setVisibility(8);
		ViewHelper.setScaleX(test_img, 0.3f);
		ViewHelper.setScaleY(test_img, 0.3f);
		ViewHelper.setX(test_img, pppp[from1].px);
		ViewHelper.setY(test_img, pppp[from1].py);
		test_img.setVisibility(0);
	}
	
	private void animate1(final int from1 ,final int toCenter ,final int to1){
		initXY(from1);
		com.nineoldandroids.view.ViewPropertyAnimator.animate(test_img)
		.translationX(  pppp[toCenter].px - pppp[from1].px)
		.translationY(  pppp[toCenter].py - pppp[from1].py)
//		.translationXBy(pppp[from1].px - pppp[toCenter].px)
//		.translationYBy(pppp[from1].py - pppp[toCenter].py)
		.setInterpolator(new AccelerateInterpolator())
		.setDuration(1000)
		.rotation(0)
		.scaleX(1)
		.scaleY(1)
		.setListener(new AnimatorListener() {
			@Override
			public void onAnimationStart(Animator arg0) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void onAnimationRepeat(Animator arg0) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void onAnimationEnd(Animator arg0) {
				// TODO Auto-generated method stub
				animate2( from1,toCenter,to1  );
			}
			
			@Override
			public void onAnimationCancel(Animator arg0) {
				// TODO Auto-generated method stub
			}
		});
	}
	
	private void animate2( int from1 ,int toCenter ,int to1 ){
		com.nineoldandroids.view.ViewPropertyAnimator.animate(test_img)
		.translationX( pppp[to1].px -  pppp[toCenter].px )
		.translationY( pppp[to1].py - pppp[toCenter].py )
//		.translationXBy(pppp[from1].px - pppp[toCenter].px)
//		.translationYBy(pppp[from1].py - pppp[toCenter].py)
		.setInterpolator(new AccelerateInterpolator())
		.setDuration(1000)
		.rotation(0)
		.scaleX(0.3f)
		.scaleY(0.3f)
		.setListener(new AnimatorListener() {
			@Override
			public void onAnimationStart(Animator arg0) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void onAnimationRepeat(Animator arg0) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void onAnimationEnd(Animator arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationCancel(Animator arg0) {
				// TODO Auto-generated method stub
			}
		});
	}
}
