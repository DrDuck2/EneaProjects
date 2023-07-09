package org.example.clientLibrary.windowLibrary.CharacterScreenHandling.character;

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
    public void draw(float leftOffset,float rightOffset,float downOffset, float upOffset,float scale) {
        glColor3f ( red,green,blue );
        glBegin(GL_QUADS);
        glVertex2f (-0.2f*scale-leftOffset+rightOffset,0.9f*scale-downOffset+upOffset);
        glVertex2f (0.2f*scale-leftOffset+rightOffset,0.9f*scale-downOffset+upOffset);
        glVertex2f (0.2f*scale-leftOffset+rightOffset,0.5f*scale-downOffset+upOffset);
        glVertex2f (-0.2f*scale-leftOffset+rightOffset,0.5f*scale-downOffset+upOffset);
        glEnd ();
    }
    @Override
    public String getBlockInformation( ) {
        return null;
    }
}
