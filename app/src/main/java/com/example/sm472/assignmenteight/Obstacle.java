package com.example.sm472.assignmenteight;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Obstacle extends GameElement {
    Paint paint ;
    Paint strokeC;
    Canvas canvas;
    public Obstacle(float x,float y,int r,int color,int stroke,Canvas c) {
        super(x, y, r);
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

    protected void Move(Player player, float speed)
    {
        if(player.ypos< this.ypos)
        {
            ypos-= speed;
        }
        else
        {
            ypos += speed;
        }
        if(player.xpos < this.xpos)
        {
            xpos -= speed;
        }
        else
        {
            xpos += speed;
        }
    }
}
