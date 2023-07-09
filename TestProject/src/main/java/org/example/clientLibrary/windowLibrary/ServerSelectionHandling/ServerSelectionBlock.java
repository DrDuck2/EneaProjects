package org.example.clientLibrary.windowLibrary.ServerSelectionHandling;

import org.example.clientLibrary.windowLibrary.Interfaces.IScreenObject;

import static org.lwjgl.opengl.GL11.*;

public class ServerSelectionBlock implements IScreenObject {

    private final String serverInformation;
    private float red;
    private float blue;
    private float green;

    public ServerSelectionBlock( String serverInformation ) {
        this.serverInformation = serverInformation;
        this.red = 0.0f;
        this.blue = 1.0f;
        this.green = 0.0f;
    }

    @Override
    public String getBlockInformation( ) {
        return serverInformation;
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
    public void draw( float leftOffset , float rightOffset , float downOffset , float upOffset , float scale ) {
        glColor3f ( red , green , blue );
        glBegin ( GL_QUADS );
        glVertex2f ( - 0.4f - leftOffset + rightOffset , 0.8f - downOffset + upOffset );
        glVertex2f ( 0.4f - leftOffset + rightOffset , 0.8f - downOffset + upOffset );
        glVertex2f ( 0.4f - leftOffset + rightOffset , 0.6f - downOffset + upOffset );
        glVertex2f ( - 0.4f - leftOffset + rightOffset , 0.6f - downOffset + upOffset );
        glEnd ();
    }
}
