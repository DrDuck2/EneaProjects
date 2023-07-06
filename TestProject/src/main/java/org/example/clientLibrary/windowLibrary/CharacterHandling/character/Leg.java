package org.example.clientLibrary.windowLibrary.CharacterHandling.character;

import java.util.Objects;

import static org.lwjgl.opengl.GL11.*;

public class Leg extends BodyPart {

    private float legSize;
    private String legIndicator;

    public Leg(float legSize, String legIndicator){
        super();
        this.legSize = legSize;
        this.legIndicator = legIndicator;
    }

    public String getLegIndicator()
    {
        return legIndicator;
    }
    public void setLegSize(float legSize) {
        this.legSize = legSize;
    }
    public float getLegSize() {
        return legSize;
    }

    public void draw(float leftOffset,float rightOffset,float downOffset, float upOffset,float scale) {
        glColor3f ( red,green,blue );
        glBegin(GL_QUADS);
        if( Objects.equals ( this.legIndicator , "Left" ) ){
            glVertex2f (-0.2f*scale-leftOffset+rightOffset,-0.25f*scale-downOffset+upOffset);
            glVertex2f (-0.05f*scale-leftOffset+rightOffset,-0.25f*scale-downOffset+upOffset);
            glVertex2f (-0.05f*scale-leftOffset+rightOffset,-0.8f*scale-downOffset+upOffset);
            glVertex2f (-0.2f*scale-leftOffset+rightOffset,-0.8f*scale-downOffset+upOffset);
        }else{
            glVertex2f (0.2f*scale-leftOffset+rightOffset,-0.25f*scale-downOffset+upOffset);
            glVertex2f (0.05f*scale-leftOffset+rightOffset,-0.25f*scale-downOffset+upOffset);
            glVertex2f (0.05f*scale-leftOffset+rightOffset,-0.8f*scale-downOffset+upOffset);
            glVertex2f (0.2f*scale-leftOffset+rightOffset,-0.8f*scale-downOffset+upOffset);
        }
        glEnd ();
    }

    @Override
    public String getBlockInformation( ) {
        return null;
    }
}
