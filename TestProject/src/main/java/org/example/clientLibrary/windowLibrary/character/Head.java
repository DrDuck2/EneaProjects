package org.example.clientLibrary.windowLibrary.character;

import static org.lwjgl.opengl.GL11.*;
public class Head extends BodyPart{

    private float headSize;
    public Head(float headSize)
    {
        super();
        this.headSize = headSize;
    }

    public void setHeadSize(float headSize)
    {
        this.headSize = headSize;
    }
    public float getHeadSize()
    {
        return headSize;
    }
    public void draw(float leftOffset,float rightOffset,float downOffset, float upOffset) {
        glColor3f ( red,green,blue );
        glBegin(GL_QUADS);
        glVertex2f (-0.2f,0.9f);
        glVertex2f (0.2f,0.9f);
        glVertex2f (0.2f,0.5f);
        glVertex2f (-0.2f,0.5f);
        glEnd ();
    }
    @Override
    public String getBlockInformation( ) {
        return null;
    }
}
