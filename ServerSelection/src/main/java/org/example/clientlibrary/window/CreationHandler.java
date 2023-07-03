package org.example.clientlibrary.window;

import org.example.clientlibrary.character.*;
import org.example.clientlibrary.character.Character;

import java.util.List;

public class CreationHandler implements ICreate {
    private final Character character = new Character ();
    public void create()
    {
        character.addBodyPart ( new Head ( 50 ) );
        character.addBodyPart ( new Body ( 20,100 ) );
        character.addBodyPart ( new Arm ( 80,20,"Left" ) );
        character.addBodyPart ( new Arm ( 80,20,"Right" ) );
        character.addBodyPart ( new Leg ( 30,"Left" ) );
        character.addBodyPart ( new Leg ( 30,"Right" ) );
    }
    public void draw()
    {
        float characterCenterX = 800/2.0f;
        float characterCenterY = 600/2.0f;
        float headSize = 0.8f;
        float headPosX = characterCenterX - headSize / 2f;
        float headPosY = characterCenterY + headSize / 2f;
        // Draw the body
        float bodyWidth = 0.2f;
        float bodyHeight = 0.6f;
        float bodyPosX = characterCenterX - bodyWidth / 2f;
        float bodyPosY = characterCenterY - headSize / 2f - bodyHeight;
        // Draw the left arm
        float armWidth = 0.2f;
        float armHeight = 0.6f;
        float leftArmPosX = bodyPosX - armWidth;
        float leftArmPosY = bodyPosY + bodyHeight / 2f - armHeight / 2f;
        // Draw the right arm
        float rightArmPosX = bodyPosX + bodyWidth;
        float rightArmPosY = leftArmPosY;
        // Draw the left foot
        float footSize = 0.3f;
        float leftFootPosX = characterCenterX - footSize - bodyWidth / 2f;
        float leftFootPosY = characterCenterY - headSize / 2f - bodyHeight - footSize;
        // Draw the right foot
        float rightFootPosX = characterCenterX + bodyWidth / 2f;
        float rightFootPosY = leftFootPosY;


        List <BodyPart> parts = character.getBodyParts ();

        for(BodyPart part : parts){
            if(part.getClass () == Head.class){
                part.draw ( headPosX,headPosY );
            }else if(part.getClass () == Body.class){
                part.draw ( bodyPosX,bodyPosY );
            }else if(part.getClass () == Arm.class){
                if(((Arm) part).getArmIndicator () == "Left"){
                    part.draw ( leftArmPosX,leftArmPosY );
                }else{
                    part.draw ( rightArmPosX,rightArmPosY );
                }
            }else if(part.getClass () == Leg.class) {
                if(((Leg) part).getLegIndicator () == "Left"){
                    part.draw ( leftFootPosX,leftFootPosY );
                }else{
                    part.draw ( rightFootPosX,rightFootPosY );
                }
            }
        }
    }
}
