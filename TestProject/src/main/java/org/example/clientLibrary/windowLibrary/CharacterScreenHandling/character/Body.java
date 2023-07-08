package org.example.clientLibrary.windowLibrary.CharacterScreenHandling.character;

import static org.lwjgl.opengl.GL11.*;

public class Body extends BodyPart {

    private float bodyWidth;
    private float bodyHeight;

    public Body( float bodyWidth , float bodyHeight ) {
        super ();
        this.bodyHeight = bodyHeight;
        this.bodyWidth = bodyWidth;
    }

    public void changeWidth( float bodyWidth ) {
        this.bodyWidth = bodyWidth;
    }

    public void changeHeight( float bodyHeight ) {
        this.bodyHeight = bodyHeight;
    }

    public float getWidth( ) {
        return bodyWidth;
    }

    public float getHeight( ) {
        return bodyHeight;
    }
    @Override
    public void draw( float leftOffset , float rightOffset , float downOffset , float upOffset , float scale ) {
        glColor3f ( red , green , blue );
        glBegin ( GL_QUADS );
        glVertex2f ( - 0.2f * scale - leftOffset + rightOffset , 0.45f * scale - downOffset + upOffset );
        glVertex2f ( 0.2f * scale - leftOffset + rightOffset , 0.45f * scale - downOffset + upOffset );
        glVertex2f ( 0.2f * scale - leftOffset + rightOffset , - 0.2f * scale - downOffset + upOffset );
        glVertex2f ( - 0.2f * scale - leftOffset + rightOffset , - 0.2f * scale - downOffset + upOffset );
        glEnd ();
    }

}
