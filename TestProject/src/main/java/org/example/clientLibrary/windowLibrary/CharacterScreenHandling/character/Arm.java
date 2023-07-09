package org.example.clientLibrary.windowLibrary.CharacterScreenHandling.character;

import java.util.Objects;

import static org.lwjgl.opengl.GL11.*;

public class Arm extends BodyPart {
    private float armWidth;
    private float armHeight;
    private final String armIndicator;

    public Arm(float armWidth,float armHeight, String armIndicator){
        super();
        this.armWidth = armWidth;
        this.armHeight = armHeight;
        this.armIndicator = armIndicator;
    }

    public String getArmIndicator()
    {
        return armIndicator;
    }
    public void setWidth(float armWidth) {
        this.armWidth = armWidth;
    }
    public void setHeight(float armHeight){
        this.armHeight = armHeight;
    }
    public float getWidth() {
        return armWidth;
    }
    public float getHeight(){
        return armHeight;
    }
    public void draw(float leftOffset,float rightOffset,float downOffset, float upOffset,float scale) {
        glColor3f ( red,green,blue );
        glBegin(GL_QUADS);
        if( Objects.equals ( this.armIndicator , "Left" ) ){
            glVertex2f (-0.4f*scale-leftOffset+rightOffset,0.45f*scale-downOffset+upOffset);
            glVertex2f (-0.25f*scale-leftOffset+rightOffset,0.45f*scale-downOffset+upOffset);
            glVertex2f (-0.25f*scale-leftOffset+rightOffset,-0.1f*scale-downOffset+upOffset);
            glVertex2f (-0.4f*scale-leftOffset+rightOffset,-0.1f*scale-downOffset+upOffset);
        }else{
            glVertex2f (0.25f*scale-leftOffset+rightOffset,0.45f*scale-downOffset+upOffset);
            glVertex2f (0.4f*scale-leftOffset+rightOffset,0.45f*scale-downOffset+upOffset);
            glVertex2f (0.4f*scale-leftOffset+rightOffset,-0.1f*scale-downOffset+upOffset);
            glVertex2f (0.25f*scale-leftOffset+rightOffset,-0.1f*scale-downOffset+upOffset);
        }
        glEnd ();
    }

    @Override
    public String getBlockInformation( ) {
        return null;
    }
}
