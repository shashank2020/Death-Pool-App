package com.example.sm472.assignmenteight;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;


public class Target extends GameElement {

    Paint paint; Paint strokeC;

    public  Target(float x, float y, int r, int color,int stroke)
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
        canvas.drawCircle(xpos,ypos,radius-15,paint);

    }
}
