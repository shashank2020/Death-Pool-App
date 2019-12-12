package com.example.sm472.assignmenteight;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class GameActivity extends AppCompatActivity {

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
    SharedPreferences.Editor editor;
    Set<String> s;
    List<String> sc;
    SharedPreferences sharedPreferences;
    MediaPlayer target_hit;
    MediaPlayer obstacle_hit;

    TextView pauseText;
    private Boolean pauseOn = false;
    Button pause;
    Boolean flag=false;
    TextView gameOverText;
    Button restartButton;
    Button homeButton;
    TextView scoreText;
    TextView scoreValue;
    TextView hScoreTxt;
    TextView hScoreValue;
    int uioptions;
    String highScore;
    private RewardedAd rewardedAd;
    private Button Rewardbutton;
    Activity act ;
    ImageView freezeButton;
    Set<String> f;
    List<String> fc;
    SharedPreferences freezeTimeStatus;
    SharedPreferences.Editor freezeEditor;
    ProgressBar progress ;
    public Boolean Freeze_time;
    CountDownTimer ctr;
    long counterValue;
    int n;

    class GraphicsView extends View implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener{
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
            if(pauseOn==false) {
                if (player == null) {
                    StartX = canvas.getWidth() / 2;
                    StartY = ((canvas.getHeight() / 5) * 4);

                    player = new Player(StartX, StartY, 50, getColor(R.color.playerColor), getColor(R.color.white), canvas);
                }
                //if target is null create new reason: SAME AS ABOVE
                if (target == null) {
                    StartY = ((canvas.getHeight() / 6));
                    StartX = (canvas.getWidth() / 2);
                    target = new Target(StartX, StartY, 65, getColor(R.color.targetColor), getColor(R.color.white));
                }
                if (ob1 == null) {
                    StartX = (canvas.getWidth() / 5);
                    StartY = canvas.getHeight() / 2;
                    ob1 = new Obstacle(StartX, StartY, 35, getColor(R.color.colorPrimaryDark), getColor(R.color.colorAccent), canvas);
                    StartX = (canvas.getWidth() / 5) * 4;
                    ob2 = new Obstacle(StartX, StartY, 35, getColor(R.color.colorPrimaryDark), getColor(R.color.colorAccent), canvas);
                }
                player.move(Xvelocity_player, Yvelocity_player);

                //DRAW PLAYER
                player.Draw(canvas);
                //DRAW TARGET
                target.Draw(canvas);
                //DRAW OBSTACLES
                if(!Freeze_time) {
                    ob1.Move(player, ob1speed);
                    ob2.Move(player, ob2speed);
                    ob1.Draw(canvas);
                    ob2.Draw(canvas);
                }

                //if player touches the target reset player position and increase score
                if (player.collision(target)) {
                    respawnTarget(canvas);
                    this.startAnimation(shake);
                    scoreView.setText(getScore());
                    //Log.i("TAG","BOOOOOOOOOOOOOOOOOOOOOOOOOOOM");
                    Xvelocity_player = 0;
                    Yvelocity_player = 0;

                    //Increase speed of obstacles
                    if(!Freeze_time) {
                        ob1speed *= 1.05;
                        ob2speed *= 1.05;
                    }
                    play_target_hit();


                }
                if(!Freeze_time) {
                    if (player.collision(ob1) || player.collision(ob2)) {

                        this.startAnimation(shake);
                        highScore = scoreView.getText().toString();
                        sc.add(score);
                        score = "0";

                        play_obstacle_hit();
                        gameOver();

                    }
                }
            }

                invalidate();


            }
            public void reset ()
            {
                loadActivity();
            }


            @Override
            public boolean onTouchEvent (MotionEvent event){
                if (gestureDetector.onTouchEvent(event)) {
                    if(pauseOn==false)
                    return true;
                }
                return super.onTouchEvent(event);
            }

            @Override
            public boolean onDown (MotionEvent motionEvent){
                if(pauseOn==false)
                    return true;
                else
                    return false;
            }

            @Override
            public void onShowPress (MotionEvent motionEvent){

            }

            @Override
            public boolean onSingleTapUp (MotionEvent motionEvent){
                return false;
            }

            @Override
            public boolean onScroll (MotionEvent motionEvent, MotionEvent motionEvent1,float v,
            float v1){
                return false;
            }

            @Override
            public void onLongPress (MotionEvent motionEvent){

            }

        @Override
            public boolean onFling (MotionEvent motionEvent, MotionEvent motionEvent1,float v,
            float v1){

                if(pauseOn==false) {
                    Xvelocity_player = (v / 500);
                    Yvelocity_player = (v1 / 500);
                    player.flickReset();
                    return true;
                }
                else
                    return false;
            }


        @Override
        public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
            return false;
        }

        @Override
        public boolean onDoubleTap(MotionEvent motionEvent) {
            if(freezeButton.getVisibility()== scoreView.VISIBLE) {
                freezeButton.setVisibility(View.INVISIBLE);
                fc.remove(0);
                fc.add(0, "false");
                Freeze_time = true;
                Countdown(5000);

                return true;
            }
            else
                return false;
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent motionEvent) {
            return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        act=this;

       loadReward();
        loadActivity();
    }



    protected void loadActivity()
    {
        setContentView(R.layout.activity_game);
        ActionBar actionBar = (ActionBar) getSupportActionBar();
        actionBar.hide();

        //set fullscreen sticky immersive
        uioptions = View.SYSTEM_UI_FLAG_FULLSCREEN
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
        pauseText = findViewById(R.id.pauseText);
        pause = findViewById(R.id.pauseButton);
        gameOverText = findViewById(R.id.GameOverText);
        restartButton = findViewById(R.id.restartButton);
        homeButton = findViewById(R.id.homeButton);
        scoreText = findViewById(R.id.scoreCurrentText);
        scoreValue = findViewById(R.id.scoreCurrentValue);
        hScoreTxt = findViewById(R.id.highScoreText);
        hScoreValue = findViewById(R.id.highScoreValue);
        Rewardbutton = findViewById(R.id.reward);
        progress = findViewById(R.id.progressBar);
        freezeButton = findViewById(R.id.freezeButton);
        Freeze_time = false;
        counterValue =0;
        n=100;
        progress.setProgress(100);


        if(freezeTimeStatus==null && freezeEditor==null && fc == null) {
            freezeTimeStatus = getSharedPreferences("Freeze_Time", MODE_PRIVATE);
            freezeEditor = freezeTimeStatus.edit();

            freezeEditor.putStringSet("stat", new HashSet<String>());
            Set<String> temp = freezeTimeStatus.getStringSet("stat",null);
            if(temp!=null)
            {
                fc = new ArrayList<>(temp);
                Toast.makeText(this,"THERE",Toast.LENGTH_LONG).show();

            }
            else
            {
                fc = new ArrayList<>();

                fc.add(0,"false");
            }




        }


        if(fc.get(0).equals("true"))
        {
            freezeButton.setVisibility(View.VISIBLE);
            Toast.makeText(this,fc.get(0),Toast.LENGTH_LONG).show();
        }
        else {
            freezeButton.setVisibility(View.INVISIBLE);
            Toast.makeText(this, fc.get(0), Toast.LENGTH_LONG).show();


        }


        if(editor == null && sc ==null && sharedPreferences ==null) {
            sharedPreferences= getSharedPreferences("high_score",MODE_PRIVATE);

            editor = sharedPreferences.edit();
            editor.putStringSet("score",new HashSet<String>());
            Set<String> temp = sharedPreferences.getStringSet("score",null);

            if(temp!=null) {
                sc = new ArrayList<String>(temp);
                showToast();
            }
            else
            sc = new ArrayList<>();

            //display the flick gesture toast

        }




        //set graphics view to the layout
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
        if (score.equals("0"))
            sc.add("0");
        else
            sc.add(score);
        s = new HashSet<String>(sc);
        editor.putStringSet("score", s);
        editor.commit();

        f = new HashSet<>(fc);
        freezeEditor.putStringSet("stat",f);
        freezeEditor.commit();

    }
    @Override
    protected void onResume() {
        super.onResume();
        getWindow().getDecorView().setSystemUiVisibility(uioptions);
    }

    private void showToast()
    {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.flick_toast_layout,(ViewGroup)findViewById(R.id.flick_toast));
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER,0,0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

    private void play_target_hit()
    {
        if(target_hit==null)
        target_hit = MediaPlayer.create(this,R.raw.targethit);
        target_hit.start();
    }
    private void play_obstacle_hit()
    {
        if(obstacle_hit==null)
        obstacle_hit = MediaPlayer.create(this,R.raw.obstaclehit);
        obstacle_hit.start();
    }
    public void onClickPause(View view) {
        pauseOn = !pauseOn;

        if(pauseOn) {
            pauseText.setVisibility(View.VISIBLE);
            restartButton.setVisibility(View.VISIBLE);
            homeButton.setVisibility(View.VISIBLE);
            scoreView.setVisibility(View.INVISIBLE);
            if(progress.getVisibility()== View.VISIBLE) {
                progress.setVisibility(View.INVISIBLE);
                ctr.cancel();
            }

            if(freezeButton.getVisibility()== view.VISIBLE) {
                freezeButton.setVisibility(View.INVISIBLE);
                flag=true;
            }
            pause.setCompoundDrawablesWithIntrinsicBounds(null,null,getResources().getDrawable( R.drawable.play),null);

        }
        else {
            if(flag) {
                freezeButton.setVisibility(View.VISIBLE);
                flag=false;
            }
            if(counterValue >0)
            {
                progress.setVisibility(View.VISIBLE);
                Countdown(counterValue);
            }
            scoreView.setVisibility(View.VISIBLE);
            pauseText.setVisibility(View.INVISIBLE);
            restartButton.setVisibility(View.INVISIBLE);
            homeButton.setVisibility(View.INVISIBLE);

            pause.setCompoundDrawablesWithIntrinsicBounds(null,null,getResources().getDrawable( R.drawable.pause),null);
        }


    }
    public void onClickRstart(View view) {
        pause.setVisibility(View.VISIBLE);
        scoreView.setVisibility(View.VISIBLE);
        gameOverText.setVisibility(View.INVISIBLE);
        restartButton.setVisibility(View.INVISIBLE);
        scoreValue.setVisibility(View.INVISIBLE);
        scoreText.setVisibility(View.INVISIBLE);
        hScoreValue.setVisibility(View.INVISIBLE);
        hScoreTxt.setVisibility(View.INVISIBLE);
        Rewardbutton.setVisibility(View.INVISIBLE);

        pauseOn = false;
        loadActivity();

    }
    public void onClickHome(View view) {
        finish();
    }


    public void gameOver()
    {



        gameOverText.setVisibility(View.VISIBLE);
        restartButton.setVisibility(View.VISIBLE);
        scoreValue.setVisibility(View.VISIBLE);
        scoreText.setVisibility(View.VISIBLE);
        hScoreValue.setVisibility(View.VISIBLE);
        hScoreTxt.setVisibility(View.VISIBLE);
        freezeButton.setVisibility(View.INVISIBLE);
        progress.setVisibility(View.INVISIBLE);
        scoreValue.setText(highScore);
        if(rewardedAd.isLoaded())
        Rewardbutton.setVisibility(View.VISIBLE);


            try {


                List<Integer> sa = new ArrayList<Integer>();
                for (String x : sc) {
                    if (!x.equals("0"))
                        sa.add(Integer.parseInt(x));
                }
                Collections.sort(sa, Collections.<Integer>reverseOrder());

                 if(sa.get(0) <= Integer.parseInt(highScore))
                    hScoreValue.setText(highScore);

                else
                hScoreValue.setText(sa.get(0).toString());

            }
            catch (Exception e){}
         if(hScoreValue.getText().equals(""))
        hScoreValue.setText("0");

        pauseOn = true;
        pause.setVisibility(View.INVISIBLE);
        scoreView.setVisibility(View.INVISIBLE);
        homeButton.setVisibility(View.VISIBLE);

    }


    public void onClickReward(View view) {


        if (rewardedAd.isLoaded()) {

            RewardedAdCallback adCallback = new RewardedAdCallback() {
                @Override
                public void onRewardedAdOpened() {
                    // Ad opened.   Toast.makeText(act,"REWARDED",Toast.LENGTH_SHORT).show();
                    Toast.makeText(act,"open",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onRewardedAdClosed() {

                     Toast.makeText(act,"closed",Toast.LENGTH_SHORT).show();
                    loadReward();
                }

                @Override
                public void onUserEarnedReward(@NonNull RewardItem reward) {
                    Toast.makeText(act,"REWARDED",Toast.LENGTH_SHORT).show();
                    Rewardbutton.setVisibility(View.INVISIBLE);
                    fc.remove(0);
                    fc.add(0,"true");
                    loadReward();

                }

                @Override
                public void onRewardedAdFailedToShow(int errorCode) {
                    Toast.makeText(act,"failed",Toast.LENGTH_SHORT).show();
                }
            };
            rewardedAd.show(act, adCallback);
        } else {
            Log.d("TAG", "The rewarded ad wasn't loaded yet.");

        }
    }

    private void loadReward()
    {
        rewardedAd = new RewardedAd(this,"ca-app-pub-3940256099942544/5224354917");
        RewardedAdLoadCallback adLoadCallback = new RewardedAdLoadCallback() {
            @Override
            public void onRewardedAdLoaded() {
                // Ad successfully loaded.
                Log.d("TAG","AD LOADED");
            }

            @Override
            public void onRewardedAdFailedToLoad(int errorCode) {
                Log.d("TAG","AD DIDNT LOAD");
            }
        };
        rewardedAd.loadAd(new AdRequest.Builder().build(), adLoadCallback);
    }


    public void Countdown(long x)
    {

         ctr = new CountDownTimer(x,50) {


            @Override
            public void onTick(long l) {
                counterValue = l;
                progress.setVisibility(View.VISIBLE);
                progress.setProgress(n);
                n--;

            }

            @Override
            public void onFinish() {
                if(counterValue==0)
                progress.setProgress(100);
                if(n<=0)
                    n=100;
                progress.setVisibility(View.INVISIBLE);
                Freeze_time = false;

            }


        }.start();


    }




}
