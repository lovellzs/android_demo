package com.example.testanimate;

import android.view.View;
import android.view.animation.AccelerateInterpolator;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import com.nineoldandroids.view.ViewHelper;

public class GiftAnimation {
	public static  void initXY(View gift_view , int from1 ,boolean show ,GiftPositionB[] pppp){
		gift_view.setVisibility(8);
		ViewHelper.setScaleX(gift_view, 0.3f);
		ViewHelper.setScaleY(gift_view, 0.3f);
		ViewHelper.setX(gift_view, pppp[from1].px);
		ViewHelper.setY(gift_view, pppp[from1].py - 50);//减去状态栏高度
		if( show ){
			gift_view.setVisibility(View.VISIBLE);
		}
	}
	
	public static void  animate1( final View gift_view ,final int from1 ,final int to1 ,final GiftPositionB[] pppp){
		animate1( gift_view, from1 , 9 , to1 , pppp );
	}
	
	public  static void animate1( final View gift_view ,final int from1 ,final int toCenter ,final int to1 ,final GiftPositionB[] pppp ){
		initXY(gift_view,from1,true , pppp);
//		System.out.println(  "pppp[toCenter].px" + (pppp[toCenter].px)  );
//		System.out.println(  "pppp[from1].px" + (pppp[from1].px)  );
//		System.out.println(  "pppp[toCenter].px - pppp[from1].px" + (pppp[toCenter].px - pppp[from1].px)  );
//		
//		System.out.println(  "pppp[toCenter].py" + (pppp[toCenter].py)  );
//		System.out.println(  "pppp[from1].py" + (pppp[from1].py)  );
//		System.out.println(  "pppp[toCenter].py - pppp[from1].py" + (pppp[toCenter].py - pppp[from1].py)  );
		
		com.nineoldandroids.view.ViewPropertyAnimator.animate(gift_view)
		.translationX(  0 )
		.translationY(  0 )
		.setInterpolator(new AccelerateInterpolator())
		.setDuration(500)
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
				animate2( gift_view ,from1 ,toCenter ,to1 , pppp);
			}
			
			@Override
			public void onAnimationCancel(Animator arg0) {
				// TODO Auto-generated method stub
			}
		});
	}
	
	public static void  animate2(final View gift_view ,final int from1 ,final int toCenter ,final int to1 ,final GiftPositionB[] pppp){
		com.nineoldandroids.view.ViewPropertyAnimator.animate(gift_view)
		.translationX( pppp[to1].px -  pppp[toCenter].px )
		.translationY( pppp[to1].py - pppp[toCenter].py )
		.setInterpolator(new AccelerateInterpolator())
		.setDuration(500)
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
				initXY(gift_view,to1,true , pppp);
			}
			
			@Override
			public void onAnimationCancel(Animator arg0) {
				// TODO Auto-generated method stub
			}
		});
	}

}
