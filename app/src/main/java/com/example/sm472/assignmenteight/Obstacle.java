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


        float x = xpos;
        float y = ypos;
        float x1 = player.xpos;
        float y1 = player.ypos;

        float perp = x-x1;
        float base = y-y1;
        float hyp = (float)Math.sqrt(Math.pow(base,2)+Math.pow(perp,2));



        if(xpos<=player.xpos && ypos<=player.ypos) {

            if (player.ypos < this.ypos) {
                ypos += speed * (base / hyp);
            } else {
                ypos -= speed * (base / hyp);
            }
            if (player.xpos < this.xpos) {
                xpos += speed * (perp / hyp);
            } else {
                xpos -= speed * (perp / hyp);
            }
        }
        if(xpos>=player.xpos && ypos<=player.ypos) {

            if (player.ypos < this.ypos) {
                ypos += speed * (base / hyp);
            } else {
                ypos -= speed * (base / hyp);
            }
            if (player.xpos < this.xpos) {
                xpos -= speed * (perp / hyp);
            } else {
                xpos += speed * (perp / hyp);
            }
        }
        if(xpos>=player.xpos && ypos>=player.ypos) {

            if (player.ypos < this.ypos) {
                ypos -= speed * (base / hyp);
            } else {
                ypos += speed * (base / hyp);
            }
            if (player.xpos < this.xpos) {
                xpos -= speed * (perp / hyp);
            } else {
                xpos += speed * (perp / hyp);
            }
        }
        if(xpos<=player.xpos && ypos>=player.ypos) {

            if (player.ypos < this.ypos) {
                ypos -= speed * (base / hyp);
            } else {
                ypos += speed * (base / hyp);
            }
            if (player.xpos < this.xpos) {
                xpos += speed * (perp / hyp);
            } else {
                xpos -= speed * (perp / hyp);
            }
        }

    }
}
