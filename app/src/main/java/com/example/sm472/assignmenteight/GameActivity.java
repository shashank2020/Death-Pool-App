package com.example.sm472.assignmenteight;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class GameActivity extends AppCompatActivity{

    //PLAYER OBJECT
    Player player ;
    //TARGET OBJECT
    Target target;

    //variables
    float StartX, StartY;





    class GraphicsView extends View{

        public GraphicsView(Context context)
        {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);


            //if player is null create with start position  .... DOING THIS CAUSE CANT SET POSITION OUTSIDE onDraw() CAUSE CAN'T ACCESS CANVAS IN ON CREATE
            if(player==null)
            {
                StartX = canvas.getWidth()/2;
                StartY = ((canvas.getHeight()/5)*4);

                player = new Player(StartX,StartY,50,getColor(R.color.playerColor),getColor(R.color.white));
            }
            //if target is null create new reason: SAME AS ABOVE
            if(target==null)
            {
                StartY = ((canvas.getHeight()/6));
                StartX = (canvas.getWidth()/2);
                target = new Target(StartX,StartY,65,getColor(R.color.targetColor),getColor(R.color.white));
            }

            //DRAW PLAYER
            player.Draw(canvas);
            //DRAW TARGET


            target.Draw(canvas);

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


        GraphicsView graphicsview = new GraphicsView(this);
        ConstraintLayout c = (ConstraintLayout)findViewById(R.id.gamelayout);
        c.addView(graphicsview);

    }


}
