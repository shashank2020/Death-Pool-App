package com.example.sm472.assignmenteight;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.widget.Button;

import static com.example.sm472.assignmenteight.R.color.freezebody;

public class Obstacle extends GameElement {
    Paint paint ;
    Paint strokeC;
    Paint freezepaint;
    Paint freezeStroke;
    Canvas canvas;
    Boolean freeze;
    public Obstacle(float x,float y,int r,int color,int stroke,int fcolor, int fstroke,Canvas c) {
        super(x, y, r);
        paint = new Paint();
        paint.setColor(color);
        strokeC = new Paint();
        strokeC.setColor(stroke);
        freeze=false;
         freezepaint = new Paint();
         freezepaint.setColor(fcolor);
         freezeStroke = new Paint(fstroke);
    }

    @Override
    protected void Draw(Canvas canvas) {
        super.Draw(canvas);
        if(!freeze) {
            canvas.drawCircle(xpos, ypos, radius, strokeC);
            canvas.drawCircle(xpos, ypos, radius - 5, paint);
        }
        else {
            canvas.drawCircle(xpos, ypos, radius, freezeStroke);
            canvas.drawCircle(xpos, ypos, radius - 5, freezepaint);
        }

    }

    public void freezeActivate(Boolean f)
    {

        freeze = f;
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
