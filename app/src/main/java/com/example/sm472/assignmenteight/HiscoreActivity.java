package com.example.sm472.assignmenteight;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
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
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list){
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                // Get the Item from ListView
                View view = super.getView(position, convertView, parent);
                // Initialize a TextView for ListView each Item
                TextView tv = (TextView) view.findViewById(android.R.id.text1);


                // Set the text color of TextView (ListView Item)
                tv.setTextColor(Color.WHITE);
                tv.setTextSize(50);
                Typeface typeface = getResources().getFont(R.font.display);
                tv.setTypeface(typeface);
                tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                // Generate ListView Item using TextView
                return view;
            }
        };
        ListView listView = (ListView) findViewById(R.id.hiscore_list);

        listView.setAdapter(arrayAdapter);
        SharedPreferences sharedpref = getSharedPreferences("high_score", this.MODE_PRIVATE);
       Set<String> s = sharedpref.getStringSet("score",null);
        if(s==null)
        {
            SharedPreferences.Editor editor = sharedpref.edit();
            editor = sharedpref.edit();
            editor.putStringSet("score", new HashSet<String>());
            editor.commit();
            s = sharedpref.getStringSet("score",null);
        }
        ArrayList<String> a = new ArrayList<String>(s);
        List<Integer> sa = new ArrayList<Integer>();

        for(String x : a)
        {
            if(!x.equals("0"))
            sa.add(Integer.parseInt(x));
        }

        Collections.sort(sa, Collections.<Integer>reverseOrder());
        try {
            list.add(sa.get(0).toString());
            list.add(sa.get(1).toString());
            list.add(sa.get(2).toString());
            list.add(sa.get(3).toString());
            list.add(sa.get(4).toString());
        }
        catch (Exception e){}
//Toast.makeText(this,sa.toString(),Toast.LENGTH_LONG).show();

        arrayAdapter.notifyDataSetChanged();


    }

}
