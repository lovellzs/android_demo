package com.sz.testormlite;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.sz.testormlite.bean.User;
import com.sz.testormlite.dao.DaoManager;
import com.sz.testormlite.dao.DatabaseHelper;

import java.sql.SQLException;
import java.util.List;

public class MainActivity extends Activity  {

    TextView txt_show_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView add = findViewById(R.id.add);
        TextView delete = findViewById(R.id.delete);
        TextView update = findViewById(R.id.update);
        TextView find = findViewById(R.id.find);

        txt_show_data = findViewById(R.id.txt_show_data);

    }

    public void add(View view){
        User user = null;
        try {
            user = new User("张三1","古今最出名的人1");
            DaoManager.Instance().getUserDao().create(user);

            user = new User("张三2","古今最出名的人2");
            DaoManager.Instance().getUserDao().create(user);

            user = new User("张三3","古今最出名的人3");
            DaoManager.Instance().getUserDao().create(user);

            user = new User("张三4","古今最出名的人4");
            DaoManager.Instance().getUserDao().create(user);

            user = new User("张三5","古今最出名的人5");
            DaoManager.Instance().getUserDao().create(user);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(View view){
        try
        {
            DaoManager.Instance().getUserDao().deleteById(2);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void update(View view){
        try
        {
            User u1 = new User("android", "2B青年");
            u1.setId(3);
            DaoManager.Instance().getUserDao().update(u1);

        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void find(View view){
        try
        {
            List<User> users = DaoManager.Instance().getUserDao().queryForAll();
            String  s = "";
            for( int i=0;i<users.size();i++ ){
                Log.e( "sz", users.get(i).toString() );
                s+=users.get(i).toString() + "\n";
            }
            txt_show_data.setText(s);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
