package com.example.sm472.assignmenteight;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HiscoreActivity extends AppCompatActivity {
    Typeface typeface;
    int uioptions;
    private AdView adview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hiscore);

        //ads
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });


        adview = findViewById(R.id.adView);
        AdRequest req = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        adview.loadAd(req);


        //Hide actionbar
        ActionBar actionBar = (ActionBar) getSupportActionBar();
        actionBar.hide();

        //set fullscreen sticky immersive
         uioptions = View.SYSTEM_UI_FLAG_FULLSCREEN
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
                if(Build.VERSION.SDK_INT >= Integer.parseInt("23") && Build.VERSION.SDK_INT < Integer.parseInt("26")) {
                     typeface = ResourcesCompat.getFont(getApplicationContext(), R.font.display);
                }
                else
                {
                    typeface = getResources().getFont( R.font.display);
                }
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

    @Override
    protected void onResume() {
        super.onResume();
        getWindow().getDecorView().setSystemUiVisibility(uioptions);
    }
}
