package com.example.sm472.assignmenteight;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Player extends GameElement {

    Paint paint ;
    Paint strokeC;
    Canvas canvas;
    boolean invertx;
    boolean inverty;
    public Player(float x,float y,int r,int color,int stroke,Canvas c)
    {
        super(x,y,r);
        paint = new Paint();
        paint.setColor(color);
        strokeC = new Paint();
        strokeC.setColor(stroke);
        canvas =c;
        invertx=false;
        inverty=false;

    }

    @Override
    protected void Draw(Canvas canvas) {
        super.Draw(canvas);

       canvas.drawCircle(xpos,ypos,radius,strokeC);
       canvas.drawCircle(xpos,ypos,radius-5,paint);

    }


    protected Boolean collision(GameElement object)
    {
        float x1 = xpos;
        float y1 = ypos;
        float x2 = object.xpos;
        float y2 = object.ypos;
        float rad = radius + object.radius;
        float dis = (float) Math.sqrt(Math.pow(x2-x1,2)+Math.pow(y2-y1,2));
        if(dis<=rad)
        {
            return true;
        }

            return false;

    }

    protected void move(float x,float y)
    {


        if(xpos+radius+x>=canvas.getWidth() || xpos-radius+x<=0)
        {
            invertx = !invertx;
        }
        if(ypos+radius+y>=canvas.getHeight() || ypos-radius+y<=0)
        {
            inverty = !inverty;
        }

        if(inverty)
        {
            y*=-1;


        }
        if(invertx)
        {
            x*=-1;
        }

        xpos+=x;
        ypos+=y;

    }


    protected void flickReset()
    {
        invertx=false;
        inverty=false;
    }


}
