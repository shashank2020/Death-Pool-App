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
    public Player(float x,float y,int r,int color,int stroke)
    {
        super(x,y,r);
        paint = new Paint();
        paint.setColor(color);
        strokeC = new Paint();
        strokeC.setColor(stroke);
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
}
