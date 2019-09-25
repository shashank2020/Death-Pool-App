package com.example.sm472.assignmenteight;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class GameActivity extends AppCompatActivity{

    Player player ;
    class GraphicsView extends View{

        public GraphicsView(Context context)
        {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            player.Draw(canvas);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);


        ActionBar actionBar = (ActionBar) getSupportActionBar();
        actionBar.hide();

        //set fullscreen sticky immersive
        int uioptions = View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        getWindow().getDecorView().setSystemUiVisibility(uioptions);





        player = new Player(100,100,50,getColor(R.color.colorPrimary));
        GraphicsView graphicsview = new GraphicsView(this);
        ConstraintLayout c = (ConstraintLayout)findViewById(R.id.gamelayout);
        c.addView(graphicsview);

    }


}
