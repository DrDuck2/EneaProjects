package org.example.clientLibrary.windowLibrary.character;

public abstract class BodyPart {

    protected float red;
    protected float green;
    protected float blue;

    public BodyPart(){
        this.red = 0.0f;
        this.green = 0.0f;
        this.blue = 1.0f;
    }
    public void setRed(float red) {
        this.red = red;
    }
    public void setGreen(float green){
        this.green = green;
    }
    public void setBlue(float blue){
        this.blue = blue;
    }

    public abstract void draw();
}
