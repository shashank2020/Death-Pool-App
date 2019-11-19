package com.example.sm472.assignmenteight;

import android.graphics.Canvas;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public abstract class GameElement {

    protected float xpos;
    protected float ypos;
    protected int radius;

    public GameElement(float x, float y, int r)
    {
        xpos =x;
        ypos =y;
        radius =r;
    }

    protected void Draw(Canvas canvas)
    {

    }


}
