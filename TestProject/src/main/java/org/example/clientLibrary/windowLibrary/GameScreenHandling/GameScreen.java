package org.example.clientLibrary.windowLibrary.GameScreenHandling;

import org.example.clientLibrary.windowLibrary.Interfaces.IModelContainer;
import org.example.clientLibrary.windowLibrary.Interfaces.*;

import java.util.HashSet;
import java.util.Set;

public class GameScreen implements IScreen, IModelContainer<IHandle> {
    private final Set< IHandle > userModels;

    public GameScreen(){
        userModels = new HashSet <> ();
    }
    @Override
    public void addModel(IHandle model){
        userModels.add ( model );
    }
    @Override
    public void removeModel(IHandle model){
        userModels.remove ( model );
    }
    @Override
    public Set <IHandle> getModels(){
        return userModels;
    }

    @Override
    public void initModels( ) {
        for(IHandle models : userModels){
            models.init ();
        }
    }
    @Override
    public void displayModels( ) {
        for(IHandle models : userModels){
            models.display ();
        }
    }
}
