package com.app.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.main.R;

import java.util.List;

/**
 * Created by apple on 2017/6/21.
 */

public class PersonAdapter extends RecyclerView.Adapter {

    private List<Person> list = null;


    public static  interface OnRecyclerViewListener{
        void onItemClick(int position);
        boolean onItemLongClick(int position);
    }

    private  OnRecyclerViewListener onRecyclerViewListener;

    public void setOnRecyclerViewListener(OnRecyclerViewListener onRecyclerViewListener){
        this.onRecyclerViewListener = onRecyclerViewListener;
    }

    public PersonAdapter(List<Person> list){
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item_person,null);
//        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        itemView.setLayoutParams(lp);
        return new PersonViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        PersonViewHolder holder = (PersonViewHolder) viewHolder;
        holder.position = position;
        Person person = list.get(position);
        holder.item_person_name.setText(person.name);
        holder.item_person_age.setText(person.age + "岁");
    }

    @Override
    public int getItemCount() {

        if(list == null){
            return 0;
        }
        return list.size();
    }

    class PersonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        public View rootView = null;
        public TextView item_person_name = null;
        public TextView item_person_age = null;
        public int position = 0;

        public PersonViewHolder(View itemView) {
            super(itemView);

            rootView = itemView.findViewById(R.id.recycler_view_test_item_person_view);
            item_person_name = itemView.findViewById(R.id.item_person_name);
            item_person_age = itemView.findViewById(R.id.item_person_age);

            rootView.setOnClickListener(this);
            rootView.setOnLongClickListener(this);
        }


        @Override
        public void onClick(View view) {
            if(onRecyclerViewListener !=null){
                onRecyclerViewListener.onItemClick( position );
            }
        }

        @Override
        public boolean onLongClick(View view) {
            if( null != onRecyclerViewListener ){
                onRecyclerViewListener.onItemLongClick( position );
            }
            return false;
        }
    }
}
