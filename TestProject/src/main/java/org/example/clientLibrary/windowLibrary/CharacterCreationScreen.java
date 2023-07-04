package org.example.clientLibrary.windowLibrary;

import java.util.HashSet;
import java.util.Set;

public class CharacterCreationScreen implements IScreen {

    private final Set < ICreate > models;

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

    }
    public void displayModels(){

    }

    public CharacterCreationScreen(){
        this.models = new HashSet<> ();
    }
}
