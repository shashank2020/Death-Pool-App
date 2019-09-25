package com.example.sm472.assignmenteight;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Player extends GameElement {

    Paint paint ;
    Bitmap bp;
    public Player(float x,float y,int r,int color,Bitmap p)
    {
        super(x,y,r);
        paint = new Paint();
        paint.setColor(color);
        bp=p;
    }

    @Override
    protected void Draw(Canvas canvas) {
        super.Draw(canvas);

       canvas.drawBitmap(bp,xpos,ypos,null);

    }
}
