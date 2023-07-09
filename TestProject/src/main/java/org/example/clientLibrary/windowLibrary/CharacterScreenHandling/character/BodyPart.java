package org.example.clientLibrary.windowLibrary.CharacterScreenHandling.character;

import org.example.clientLibrary.windowLibrary.Interfaces.IScreenObject;

public abstract class BodyPart implements IScreenObject {

    protected float red;
    protected float green;
    protected float blue;

    protected String blockInformation;

    public BodyPart( ) {
        this.red = 0.0f;
        this.green = 0.0f;
        this.blue = 1.0f;
        blockInformation = null;
    }

    @Override
    public void setColor( float red , float green , float blue ) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    @Override
    public float getRed( ) {
        return red;
    }

    @Override
    public float getBlue( ) {
        return blue;
    }

    @Override
    public float getGreen( ) {
        return green;
    }

    @Override
    public String getBlockInformation( ) {
        return blockInformation;
    }

    @Override
    public abstract void draw( float leftOffset , float rightOffset , float downOffset , float upOffset , float scale );
}
