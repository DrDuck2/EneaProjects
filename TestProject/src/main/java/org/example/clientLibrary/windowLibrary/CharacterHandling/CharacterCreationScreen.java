package org.example.clientLibrary.windowLibrary.CharacterHandling;

import org.example.clientLibrary.windowLibrary.Interfaces.*;

import java.util.HashSet;
import java.util.Set;

public class CharacterCreationScreen implements IScreen, IModelContainer<ICreate> {
    private final Set < ICreate > models;
    public CharacterCreationScreen(){
        this.models = new HashSet<> ();
    }
    public void addModel(ICreate model){
        this.models.add ( model );
    }
    public void removeModel(ICreate model){
        this.models.remove ( model );
    }
    public Set <ICreate> getModels(){
        return this.models;
    }
    public void init(){
        for(ICreate model : models){
            model.init ();
        }
    }
    public void display(){
        for(ICreate model: models){
            model.display ();
        }
    }
}
