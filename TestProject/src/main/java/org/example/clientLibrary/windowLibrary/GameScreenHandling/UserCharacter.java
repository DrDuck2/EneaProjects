package org.example.clientLibrary.windowLibrary.GameScreenHandling;

import org.example.clientLibrary.windowLibrary.Interfaces.IScreenObject;

import java.util.List;

public class UserCharacter{

    private List < IScreenObject > bodyParts;

    private float scaleFactor;
    public UserCharacter(List<IScreenObject> bodyParts){
        this.bodyParts = bodyParts;
        this.scaleFactor = 0.0f;
    }

    public void scale(float scaleFactor){
        this.scaleFactor = scaleFactor;
    }
    public void draw(float leftOffset,float rightOffset,float downOffset, float upOffset){
        for(IScreenObject part : bodyParts){
            part.draw ( leftOffset,rightOffset,downOffset,upOffset, scaleFactor);
        }
    }


}
