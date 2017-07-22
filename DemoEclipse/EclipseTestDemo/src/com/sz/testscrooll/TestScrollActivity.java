package com.sz.testscrooll;
  
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.sz.main.test.R;
  
public class TestScrollActivity extends Activity implements ScrollViewListener,OnClickListener {  
  
    private ObservableScrollView scrollView1 = null;  
	private View ll_bottom;
	private TextView txt_long_text;
	private TextView totop;
	private TextView tobottom;
	private Button btn_get_height;
	private Button btn_get_content_height;  
  
    @Override  
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.activity_testscrooll);  
  
        scrollView1 = (ObservableScrollView) findViewById(R.id.view1);  
        scrollView1.setScrollViewListener(this);  
        
        ll_bottom =  findViewById(R.id.ll_bottom); 
        txt_long_text =  (TextView)findViewById(R.id.txt_long_text3); 
        
        
        totop =  (TextView)findViewById(R.id.totop); 
        tobottom =  (TextView)findViewById(R.id.tobottom); 
        totop.setOnClickListener(this);
        tobottom.setOnClickListener(this);
        
        String txt = "";
        for (int i = 0; i < 300; i++) {
        	txt += "习近平同巴勒斯坦国总统举行会谈";
		}
        txt_long_text.setText(txt);
        
        btn_get_height =  (Button)findViewById(R.id.btn_get_height); 
        btn_get_content_height =  (Button)findViewById(R.id.btn_get_content_height); 

        btn_get_height.setOnClickListener(this);
        btn_get_content_height.setOnClickListener(this);
    }  
    
    
    @Override  
    public void onScrollChanged(ObservableScrollView scrollView, int x, int y,  
            int oldx, int oldy) {
    	scrollView.scrollTo(x, y);
    	System.out.println( "y " + y + "===oldy " + oldy + "  getScrollY " + scrollView1.getScrollY() + " getY"  + scrollView1.getY());
    	
    	closeKeyboard(txt_long_text);//滚动就关闭软键盘
    	
    	if( Math.abs(y - oldy)>1 ){
    		ll_bottom.setVisibility(View.GONE);
    	}else{
    		ll_bottom.setVisibility(View.VISIBLE);
    	}
    	
        if(y == 0){
        	System.out.println( "===滚到顶部了" );
        	ll_bottom.setVisibility(View.VISIBLE);
        }else if(y== ( scrollView.getChildAt(0).getHeight()-scrollView.getHeight() )){
        	System.out.println( "===滚到底部了" );
        	ll_bottom.setVisibility(View.VISIBLE);
        }
        
    }  
    
	public static void closeKeyboard(View view){
		InputMethodManager imm = (InputMethodManager)view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);   
        imm.hideSoftInputFromWindow(view.getWindowToken(),0); 
	}

	//scrollView1.getY()   得到的是距离父控件的Y轴位置
	//scrollView1.getChildAt(0).getHeight() = scrollview.getHeight() + scrollView1.getScrollY() ;
	//内容高度 = scrollview的高度 + scrollView可滚动的最大值) ;
	
	Handler mHandler = new Handler();  
	@Override
	public void onClick(View view) {
		int id = view.getId();
		if( id == R.id.tobottom ){
			mHandler.post(new Runnable() {  
			    @Override  
			    public void run() {  
			        scrollView1.fullScroll(ScrollView.FOCUS_DOWN);  
			    }  
			});  
		}else if( id == R.id.totop){
			mHandler.post(new Runnable() {  
			    @Override  
			    public void run() {  
			        scrollView1.fullScroll(ScrollView.FOCUS_UP);  
			    }  
			});  
		}else if( id == R.id.btn_get_height ){
			int height = scrollView1.getHeight();
			System.out.println( "scrollView1.getHeight()  " + height );
		}else if( id == R.id.btn_get_content_height ){
			int height = scrollView1.getChildAt(0).getHeight();
			System.out.println( "scrollView1.getChildAt(0).getHeight()  " + height);
		}
	}
}  
