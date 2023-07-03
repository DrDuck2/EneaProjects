package org.example.clientlibrary.character;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glColor3f;
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
    public void draw(float x,float y) {
        glColor3f ( red,green,blue );
        glBegin(GL_QUADS);
        glVertex2f (-0.2f,0.9f);
        glVertex2f (0.2f,0.9f);
        glVertex2f (0.2f,0.5f);
        glVertex2f (-0.2f,0.5f);
        glEnd ();
    }
}
