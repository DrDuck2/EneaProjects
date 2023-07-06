package org.example.clientLibrary.windowLibrary.GameScreenHandling;

import org.example.clientLibrary.windowLibrary.Interfaces.IModelContainer;
import org.example.clientLibrary.windowLibrary.Interfaces.*;

import java.util.HashSet;
import java.util.Set;

public class GameScreen implements IScreen, IModelContainer<IShow> {
    private Set< IShow > userModels;

    public GameScreen(){
        userModels = new HashSet <> ();
    }
    public void addModel(IShow model){
        userModels.add ( model );
    }
    public void removeModel(IShow model){
        userModels.remove ( model );
    }
    public Set <IShow> getModels(){
        return userModels;
    }

    public void init( ) {
        for(IShow models : userModels){
            models.init ();
        }
    }
    public void display( ) {
        for(IShow models : userModels){
            models.display ();
        }
    }
}
