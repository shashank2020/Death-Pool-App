package com.example.sm472.assignmenteight;

import android.graphics.Canvas;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

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
