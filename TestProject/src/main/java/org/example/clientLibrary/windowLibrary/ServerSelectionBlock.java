package org.example.clientLibrary.windowLibrary;

import static org.lwjgl.opengl.GL11.*;

public class ServerSelectionBlock implements IScreenObject {
    public void draw(float drawingOffset)
    {
        glBegin(GL_QUADS);
        glVertex2f (-0.4f,0.8f-drawingOffset);
        glVertex2f (0.4f,0.8f-drawingOffset);
        glVertex2f (0.4f,0.6f-drawingOffset);
        glVertex2f (-0.4f,0.6f-drawingOffset);
        glEnd ();
    }
}
