package com.example.sm472.assignmenteight;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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

        String[] array = getResources().getStringArray(R.array.listArray);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, array);
        ListView listView = (ListView) findViewById(R.id.hiscore_list);
        listView.setAdapter(arrayAdapter);

    }

}
