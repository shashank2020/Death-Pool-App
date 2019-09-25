package com.example.sm472.assignmenteight;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Target extends GameElement {

    Paint paint;
    Bitmap bp;
    public  Target(float x, float y, int r, int color, Bitmap p)
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
