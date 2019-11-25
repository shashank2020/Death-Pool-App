package com.example.sm472.assignmenteight;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    Intent intent;
    MediaPlayer intro_media;
    private AdView adview;
    int uioptions;
    int hs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });


         adview = findViewById(R.id.adView);
        AdRequest req = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        adview.loadAd(req);

        setHighscore();
        setFullscreen();
        playMusic();
    }
    protected void setFullscreen() {
        //Hide actionbar
        ActionBar actionBar = (ActionBar) getSupportActionBar();
        actionBar.hide();
        //set fullscreen sticky immersive
         uioptions = View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        getWindow().getDecorView().setSystemUiVisibility(uioptions);
    }
    protected void playMusic() {
        intro_media = MediaPlayer.create(this,R.raw.game);
        intro_media.setLooping(true);
        intro_media.start();
    }
    protected void setHighscore(){
        TextView highscore = (TextView)findViewById(R.id.highscore_val);
        SharedPreferences sharedPreferences = getSharedPreferences("high_score",this.MODE_PRIVATE);
        Set<String> set = sharedPreferences.getStringSet("score",null);
        if(set==null){
           highscore.setText("0");
           hs =0;
        }
        else {
            try {
                List<String> scores = new ArrayList<>(set);

                List<Integer> sa = new ArrayList<Integer>();
                for (String x : scores) {
                    if (!x.equals("0"))
                        sa.add(Integer.parseInt(x));
                }
                Collections.sort(sa, Collections.<Integer>reverseOrder());
                highscore.setText(sa.get(0).toString());
                hs = sa.get(0);
            }
            catch (Exception e){}
        }

    }
    @Override
    protected void onPause() {
        super.onPause();
        intro_media.pause();
    }
    @Override
    protected void onResume() {
        super.onResume();
        intro_media.start();

        setHighscore();
        getWindow().getDecorView().setSystemUiVisibility(uioptions);
    }
    @Override
    protected void onStart() {
        super.onStart();
       // Toast.makeText(this,"START",Toast.LENGTH_SHORT).show();
        if(!intro_media.isPlaying()) {
            intro_media = MediaPlayer.create(this, R.raw.game);
            intro_media.setLooping(true);
            intro_media.start();
        }
    }
    public void OnClickplay(View v) {
        intro_media.stop();
       // Toast.makeText(this,"CLICKED",Toast.LENGTH_SHORT).show();
        intent = new Intent(this,GameActivity.class);
        intent.putExtra("HS",Integer.toString(hs));
        startActivity(intent);
    }
    public void onClickHiscore(View view) {
      //  Toast.makeText(this,"HISCORE_CLICKED",Toast.LENGTH_SHORT).show();
        intent = new Intent(this,HiscoreActivity.class);
        startActivity(intent);
    }


}
