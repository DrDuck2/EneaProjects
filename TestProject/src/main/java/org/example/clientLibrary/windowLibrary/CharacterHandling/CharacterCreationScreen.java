package org.example.clientLibrary.windowLibrary.CharacterHandling;

import org.example.clientLibrary.windowLibrary.Interfaces.ICreate;
import org.example.clientLibrary.windowLibrary.Interfaces.IScreen;

import java.util.HashSet;
import java.util.Set;

public class CharacterCreationScreen implements IScreen {

    private final Set < ICreate > models;

    public CharacterCreationScreen(){
        this.models = new HashSet<> ();
    }
    public void addModels(ICreate model){
        this.models.add ( model );
    }
    public void removeModels(ICreate model){
        this.models.remove ( model );
    }
    public Set <ICreate> getModels(){
        return this.models;
    }
    public void initModels(){
        for(ICreate model : models){
            model.init ();
        }
    }
    public void displayModels(){
        for(ICreate model: models){
            model.display ();
        }
    }
}
