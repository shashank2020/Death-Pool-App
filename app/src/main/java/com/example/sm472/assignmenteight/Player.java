package com.example.sm472.assignmenteight;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Player extends GameElement {

    Paint paint ;

    public Player(float x,float y,int r,int color)
    {
        super(x,y,r);
        paint = new Paint();
        paint.setColor(color);

    }

    @Override
    protected void Draw(Canvas canvas) {
        super.Draw(canvas);
        canvas.drawCircle(xpos,ypos,radius,paint);

    }
}
