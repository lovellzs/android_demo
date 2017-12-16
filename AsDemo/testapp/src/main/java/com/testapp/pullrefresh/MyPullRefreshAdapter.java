package com.testapp.pullrefresh;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.testapp.main.R;

import java.util.List;

/**
 * Created by apple on 2017/12/16.
 */

public class MyPullRefreshAdapter extends BaseAdapter {


    public static int TO_UP = 1;
    public static int TO_DOWN = 2;
    public int direction = 1;

    private Context mContext ;
    private List<String> datalist ;
    private LayoutInflater inflater;
    private ListView mlistview;

    public MyPullRefreshAdapter(Context mContext , List<String> datalist ,ListView listview ) {
        this.mContext = mContext;
        this.datalist = datalist;
        this.mlistview = listview;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return datalist==null?0:datalist.size();
    }

    @Override
    public Object getItem(int position) {
        return datalist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //加载布局为一个视图
        View view=inflater.inflate(R.layout.item_pulllistview1,parent, false);
        TextView txt_recyclerview = view.findViewById(R.id.txt_recyclerview);
        txt_recyclerview.setText(datalist.get(position));
        return view;
    }

    private int firstPosition=0;
    private int firstY=0;

    @Override
    public void notifyDataSetChanged() {
        this.firstPosition=mlistview.getFirstVisiblePosition();
        this.firstY = getFirstVisibleTop(mlistview);

        super.notifyDataSetChanged();

        this.mlistview.setSelectionFromTop(this.firstPosition, this.firstY);
    }

    private int getFirstVisibleTop(ListView v){
        View view = v.getChildAt(0);
        return (view == null) ? 0 : view.getTop();
    }

    public void addData(){

        if( direction == TO_UP){
            for(int i = 0; i < 30 ; i++ ){
                datalist.add( " 我是帅哥 === "  + datalist.size() );
            }

            if(callback !=null){
                callback.databack();
            }
        }else{
            for(int i = 0; i < 30 ; i++ ){
                datalist.add( 0," 我是帅哥 === "  + datalist.size() );
            }
            if(callback !=null){
                callback.databack();
            }
        }
    }

    public void setDirection(int direction){
        this.direction = direction;
    }

    private MyCallback callback ;
    public void setCallBack(MyCallback callback){
        this.callback = callback;
    }
}
