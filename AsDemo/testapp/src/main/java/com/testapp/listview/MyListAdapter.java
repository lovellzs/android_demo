package com.testapp.listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.testapp.main.R;

import java.util.List;

/**
 * Created by apple on 2017/12/16.
 */

public class MyListAdapter extends BaseAdapter {

    private Context mContext ;
    private List<String> datalist ;
    private LayoutInflater inflater;

    public MyListAdapter(Context mContext , List<String> datalist) {
        this.mContext = mContext;
        this.datalist = datalist;
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
        View view=inflater.inflate(R.layout.item_listview1,parent, false);
        TextView txt_recyclerview = view.findViewById(R.id.txt_recyclerview);
        txt_recyclerview.setText(datalist.get(position));
        return view;
    }
}
