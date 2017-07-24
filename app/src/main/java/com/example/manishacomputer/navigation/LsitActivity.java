package com.example.manishacomputer.navigation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;

public class LsitActivity extends AppCompatActivity {
    private ListView list;
    ArrayList<String> arrayList;
    String[] name={"roshan","sandesh","santosh","sanjay"};
    String[] des={"brj","delhi","delhi","brj"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lsit);
        arrayList=new ArrayList<>();
        list=(ListView)findViewById(R.id.listView);
       ArrayAdapter<String> stringArrayAdapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,name);
        list.setAdapter(stringArrayAdapter);
    }
}
