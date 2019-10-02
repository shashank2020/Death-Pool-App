package com.example.sm472.assignmenteight;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Intent intent;
    MediaPlayer intro_media;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//
//        //Hide actionbar
        ActionBar actionBar = (ActionBar) getSupportActionBar();
        actionBar.hide();
        intro_media = MediaPlayer.create(this,R.raw.game);
        intro_media.setLooping(true);

        intro_media.start();


        //set fullscreen sticky immersive
        int uioptions = View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        getWindow().getDecorView().setSystemUiVisibility(uioptions);



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
