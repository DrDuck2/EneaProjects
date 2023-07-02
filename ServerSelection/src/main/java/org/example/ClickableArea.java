package org.example;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;
public class ClickableArea {
    private int areaX;
    private int areaY;
    private int width;
    private int height;
    private float drawingOffset;
    private boolean clicked;

    public ClickableArea(int areaX,int areaY, int width,int height, float drawingOffset)
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
