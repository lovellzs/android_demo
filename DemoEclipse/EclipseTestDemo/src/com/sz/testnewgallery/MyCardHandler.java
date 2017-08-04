package com.sz.testnewgallery;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.crazysunj.cardslideview.CardHandler;
import com.sz.main.test.R;

/**
 * description
 * <p>
 * Created by sunjian on 2017/6/24.
 */

public class MyCardHandler implements CardHandler<Integer> {

	private  String[]  urls = null;
	private  Context  ctx = null;
	
	public MyCardHandler(Object pre,String[] urls,Context ctx){
		this.urls = urls;
		this.ctx = ctx;
	}
	
    @Override
    public View onBind(final Context context, final Integer data, final int position) {
//        View view = View.inflate(ctx, R.layout.item, null);
//        ImageView imageView = (ImageView) view.findViewById(R.id.image);
////        Glide.with(context).load(data).into(imageView);
//        
//        imageView.setImageResource(data);
//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(ctx, "data:" + urls[position] + "position:" + position, Toast.LENGTH_SHORT).show();
//            }
//        });
    	
    	TextView view = new TextView(context);
        return view;
    }
}
