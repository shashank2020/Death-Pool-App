package com.example.sm472.assignmenteight;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class HiscoreActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hiscore);

        //Hide actionbar
        ActionBar actionBar = (ActionBar) getSupportActionBar();
        actionBar.hide();

        //set fullscreen sticky immersive
        int uioptions = View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        getWindow().getDecorView().setSystemUiVisibility(uioptions);

       ArrayList<String> list = new ArrayList<String>();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        ListView listView = (ListView) findViewById(R.id.hiscore_list);
        listView.setAdapter(arrayAdapter);
        SharedPreferences sharedpref = getSharedPreferences("high_score", this.MODE_PRIVATE);
       Set<String> s = sharedpref.getStringSet("score",null);

        ArrayList<String> a = new ArrayList<String>(s);
        List<Integer> sa = new ArrayList<Integer>();

        for(String x : a)
        {
            sa.add(Integer.parseInt(x));
        }

        Collections.sort(sa, Collections.<Integer>reverseOrder());
        list.add(sa.get(0).toString());
        list.add(sa.get(1).toString());
        list.add(sa.get(2).toString());
        list.add(sa.get(3).toString());
        list.add(sa.get(4).toString());

Toast.makeText(this,sa.toString(),Toast.LENGTH_LONG).show();

        arrayAdapter.notifyDataSetChanged();


    }

}
