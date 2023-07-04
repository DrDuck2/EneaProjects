package org.example.clientlibrary.window;

import org.example.clientlibrary.character.*;
import org.example.clientlibrary.character.Character;

import java.util.List;

public class CreationHandler implements IHandle{
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
        List <BodyPart> parts = character.getBodyParts ();
        for(BodyPart part : parts){
            part.draw ();
        }
    }
}
