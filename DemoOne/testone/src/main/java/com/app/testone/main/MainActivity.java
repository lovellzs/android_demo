package com.app.testone.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.app.testone.R;
import com.app.testone.adapter.PersonAdapter;
import com.app.testone.beans.Person;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PersonAdapter.OnRecyclerViewListener {

    private List<Person> personList = new ArrayList<Person>();
    private PersonAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler_index_list);

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        initData();

        adapter = new PersonAdapter(personList);
        adapter.setOnRecyclerViewListener(this);
        recyclerView.setAdapter(adapter);
    }


    private void initData(){
        personList.add( new Person("张三",18) );
        personList.add( new Person("李四",18) );
        personList.add( new Person("王五",18) );
        personList.add( new Person("赵六",18) );
        personList.add( new Person("田七",18) );
        personList.add( new Person("张三",18) );
        personList.add( new Person("李四",18) );
        personList.add( new Person("王五",18) );
        personList.add( new Person("赵六",18) );
        personList.add( new Person("田七",18) );
        personList.add( new Person("张三",18) );
        personList.add( new Person("李四",18) );
        personList.add( new Person("王五",18) );
        personList.add( new Person("赵六",18) );
        personList.add( new Person("田七",18) );
    }

    @Override
    public void onItemClick(int position) {
        System.out.println( personList.get(position) + "=========onItemClick======" + position);
    }

    @Override
    public boolean onItemLongClick(int position) {
        System.out.println( personList.get(position) + "=========onItemLongClick======" + position );
        return false;
    }
}
