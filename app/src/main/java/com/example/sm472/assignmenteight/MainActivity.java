package com.example.sm472.assignmenteight;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Explode;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    Intent intent;
    MediaPlayer intro_media;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setHighscore();
        setFullscreen();
        playMusic();
    }
    protected void setFullscreen() {
        //Hide actionbar
        ActionBar actionBar = (ActionBar) getSupportActionBar();
        actionBar.hide();
        //set fullscreen sticky immersive
        int uioptions = View.SYSTEM_UI_FLAG_FULLSCREEN
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
        startActivity(intent);
    }
    public void onClickHiscore(View view) {
      //  Toast.makeText(this,"HISCORE_CLICKED",Toast.LENGTH_SHORT).show();
        intent = new Intent(this,HiscoreActivity.class);
        startActivity(intent);
    }
}
