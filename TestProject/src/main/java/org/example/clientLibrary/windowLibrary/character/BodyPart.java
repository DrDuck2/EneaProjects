package org.example.clientLibrary.windowLibrary.character;

import org.example.clientLibrary.windowLibrary.IScreenObject;

public abstract class BodyPart implements IScreenObject {

    protected float red;
    protected float green;
    protected float blue;

    protected String blockInformation;

    public BodyPart(){
        this.red = 0.0f;
        this.green = 0.0f;
        this.blue = 1.0f;
        blockInformation = null;
    }
    public void setColor(float red, float green, float blue){
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public float getRed() {return red;}
    public float getBlue(){return blue;}
    public float getGreen(){return green;}

    public String getBlockInformation(){
        return blockInformation;
    }
    public abstract void draw(float leftOffset,float rightOffset,float downOffset, float upOffset);
}
