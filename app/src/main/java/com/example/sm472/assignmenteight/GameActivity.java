package com.example.sm472.assignmenteight;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.media.MediaPlayer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class GameActivity extends AppCompatActivity{

    //PLAYER OBJECT
    Player player ;
    float Xvelocity_player;
    float Yvelocity_player;
    TextView scoreView;
    //TARGET OBJECT
    Target target;
    //OBSTACLE OBJECTS
    Obstacle ob1;
    Obstacle ob2;
    private String score;
    //variables
    float StartX, StartY;
    Animation shake;
    float ob1speed;
    float ob2speed;



    class GraphicsView extends View implements GestureDetector.OnGestureListener{
        private GestureDetector gestureDetector;
        public GraphicsView(Context context)
        {
            super(context);
            gestureDetector = new GestureDetector(context, this);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);


            //if player is null create with start position  .... DOING THIS CAUSE CANT SET POSITION OUTSIDE onDraw() CAUSE CAN'T ACCESS CANVAS IN ON CREATE
            if(player==null)
            {
                StartX = canvas.getWidth()/2;
                StartY = ((canvas.getHeight()/5)*4);

                player = new Player(StartX,StartY,50,getColor(R.color.playerColor),getColor(R.color.white),canvas);
            }
            //if target is null create new reason: SAME AS ABOVE
            if(target==null)
            {
                StartY = ((canvas.getHeight()/6));
                StartX = (canvas.getWidth()/2);
                target = new Target(StartX,StartY,65,getColor(R.color.targetColor),getColor(R.color.white));
            }
            if(ob1==null)
            {
                StartX = (canvas.getWidth()/5);
                StartY = canvas.getHeight()/2;
                ob1 = new Obstacle(StartX, StartY, 35, getColor(R.color.colorPrimaryDark), getColor(R.color.colorAccent), canvas);
                StartX = (canvas.getWidth()/5)*4;
                ob2 = new Obstacle(StartX, StartY, 35, getColor(R.color.colorPrimaryDark), getColor(R.color.colorAccent), canvas);
            }

            player.move(Xvelocity_player,Yvelocity_player);
            ob1.Move(player, ob1speed);
            ob2.Move(player, ob2speed);
            //DRAW PLAYER
            player.Draw(canvas);
            //DRAW TARGET
            target.Draw(canvas);
            //DRAW OBSTACLES
            ob1.Draw(canvas);
            ob2.Draw(canvas);


            //if player touches the target reset player position and increase score
            if(player.collision(target))
            {
                respawnTarget(canvas);
                this.startAnimation(shake);
                scoreView.setText(getScore());
                Log.i("TAG","BOOOOOOOOOOOOOOOOOOOOOOOOOOOM");
                Xvelocity_player=0;
                Yvelocity_player=0;

                //Increase speed of obstacles
                ob1speed *= 1.05;
                ob2speed *= 1.05;


                SharedPreferences sharedPreferences = getSharedPreferences("high_score",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("score",Integer.parseInt(score));
                editor.commit();

            }
            if (player.collision(ob1)|| player.collision(ob2))
            {
                score = "0";
                scoreView.setText(getScore());
                reset();

            }
            invalidate();



        }
        protected void reset()
        {
           loadActivity();
        }



        @Override
        public boolean onTouchEvent (MotionEvent event) {
            if(gestureDetector.onTouchEvent(event)) {
                return true;
            }
            return super.onTouchEvent(event);
        }

        @Override
        public boolean onDown(MotionEvent motionEvent) {
            return true;
        }

        @Override
        public void onShowPress(MotionEvent motionEvent) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent motionEvent) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent motionEvent) {

        }

        @Override
        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {




            Xvelocity_player = (v/500);
            Yvelocity_player = (v1/500);
            player.flickReset();
            return true;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        loadActivity();
    }
    protected void loadActivity()
    {
        setContentView(R.layout.activity_game);
        ActionBar actionBar = (ActionBar) getSupportActionBar();
        actionBar.hide();

        //set fullscreen sticky immersive
        int uioptions = View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        getWindow().getDecorView().setSystemUiVisibility(uioptions);

        shake = AnimationUtils.loadAnimation(this,R.anim.shake);

        ob1 = null;
        ob2 = null;
        player = null;
        target = null;
        Yvelocity_player=0;
        Xvelocity_player=0;
        score ="0";
        ob1speed = 1;
        ob2speed = ob1speed/2;

        //get the score textview
        scoreView = (TextView)findViewById(R.id.score_text);

        GraphicsView graphicsview = new GraphicsView(this);
        ConstraintLayout c = (ConstraintLayout)findViewById(R.id.gamelayout);
        c.addView(graphicsview);
    }




    //get string value of the updated score
    private String getScore()
    {
        int a = Integer.parseInt(score);
        a++;
        score = Integer.toString(a);
        return score;
    }

    private void respawnTarget(Canvas canvas)
    {
        Random rand = new Random();
        int n=0;
        while(!(n >0+target.radius && n<canvas.getWidth()-target.radius)  )
        {
            n = rand.nextInt(canvas.getWidth()-target.radius);
        }
        StartX=n;
        n=0;
        while(!(n >0+target.radius && n<canvas.getHeight()-target.radius ) )
        {
            n = rand.nextInt(canvas.getHeight()-target.radius);
        }
        StartY= n;
        target=null;
        target = new Target(StartX,StartY,65,getColor(R.color.targetColor),getColor(R.color.white));
    }

    @Override
    protected void onPause() {
        super.onPause();
        s = new HashSet<String>(sc);
        editor.putStringSet("score",s);
        editor.commit();
    }
}
