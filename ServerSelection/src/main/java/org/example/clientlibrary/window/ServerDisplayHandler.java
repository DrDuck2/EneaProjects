package org.example.clientlibrary.window;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;
public class ServerDisplayHandler implements IHandle{
    private final int areaX;
    private final int areaY;
    private final int width;
    private final int height;
    private final float drawingOffset;
    private boolean clicked;

    public ServerDisplayHandler( int areaX, int areaY, int width, int height, float drawingOffset)
    {
        this.areaX = areaX;
        this.areaY = areaY;
        this.width = width;
        this.height = height;
        this.drawingOffset = drawingOffset;
        this.clicked = false;
    }

    public boolean contains(double mouseX, double mouseY){
        return mouseX >= areaX && mouseX <= areaX+width && mouseY >= areaY && mouseY <= areaY + height;
    }

    public void setClicked(boolean clicked)
    {
        this.clicked = clicked;
    }
    public boolean isClicked(){
        return clicked;
    }

    public void draw(){
        glBegin(GL_QUADS);
        glVertex2f (-0.4f,0.8f-drawingOffset);
        glVertex2f (0.4f,0.8f-drawingOffset);
        glVertex2f (0.4f,0.6f-drawingOffset);
        glVertex2f (-0.4f,0.6f-drawingOffset);
        glEnd ();
    }
}