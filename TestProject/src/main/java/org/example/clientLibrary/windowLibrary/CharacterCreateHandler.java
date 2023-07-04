package org.example.clientLibrary.windowLibrary;

import org.example.clientLibrary.windowLibrary.character.*;
import org.example.character.*;
import org.example.clientLibrary.windowLibrary.character.Character;

import java.util.Set;

public class CharacterCreateHandler implements ICreate {

    private final Character character = new Character ();


    public void addScreenObject( IScreenObject object){

    }
    public void removeScreenObject(IScreenObject object){

    }
    public Set <IScreenObject> getScreenObjects(){
        return null;
    }
    public void init(){

    }
    public void display(){

    }
    public void Create(){
        character.addBodyPart ( new Head ( 50 ) );
        character.addBodyPart ( new Body ( 20,100 ) );
        character.addBodyPart ( new Arm ( 80,20,"Left" ) );
        character.addBodyPart ( new Arm ( 80,20,"Right" ) );
        character.addBodyPart ( new Leg ( 30,"Left" ) );
        character.addBodyPart ( new Leg ( 30,"Right" ) );
    }
    public void draw(){

    }
}
