package org.example.clientLibrary.windowLibrary.character;

import java.util.ArrayList;
import java.util.List;

public class Character{

    private final List <BodyPart> bodyParts;
    public Character(){
        bodyParts = new ArrayList <> ();
    }
    public void addBodyPart(BodyPart part)
    {
        bodyParts.add ( part );
    }
    public void removeBodyPart(BodyPart part)
    {
        bodyParts.remove ( part );
    }
    public List<BodyPart> getBodyParts(){
        return bodyParts;
    }
}
