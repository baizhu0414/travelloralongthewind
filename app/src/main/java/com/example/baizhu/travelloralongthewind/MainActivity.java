package com.example.baizhu.travelloralongthewind;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private ListView listview;

    private List<Thing> lists;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listview = (ListView) findViewById(R.id.listview);

        //初始化数据

        lists = getLists();

        //设置适配器

        listview.setAdapter(new MyAdapter(this, lists));

        //设置监听

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "选中了："+",id="+id, Toast.LENGTH_SHORT).show();

            }
        });
    }
    //返回数据

    private List<Thing> getLists() {

        List<Thing> lists = new ArrayList<Thing>();

        for (int i = 0; i < 20; i++) {

            Thing thing = new Thing();

            thing.setPicture(R.drawable.ic_launcher_background);

            thing.setTitle("我是标题" + i);

            thing.setIntroduce("我是简介" + i);

            lists.add(thing);

        }

        return lists;

    }


}
