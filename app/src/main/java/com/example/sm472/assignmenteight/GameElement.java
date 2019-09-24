package com.example.sm472.assignmenteight;

public abstract class GameElement {

    private float xpos;
    private float ypos;
    private int radius;

    public GameElement(float x, float y, int r)
    {
        xpos =x;
        ypos =y;
        radius =r;
    }
}
