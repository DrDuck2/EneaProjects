package org.example.clientLibrary.windowLibrary.GameScreenHandling;

import org.example.clientLibrary.windowLibrary.Interfaces.IModelContainer;
import org.example.clientLibrary.windowLibrary.Interfaces.*;

import java.util.HashSet;
import java.util.Set;

public class GameScreen implements IScreen, IModelContainer<IShow> {
    private final Set< IShow > userModels;

    public GameScreen(){
        userModels = new HashSet <> ();
    }
    @Override
    public void addModel(IShow model){
        userModels.add ( model );
    }
    @Override
    public void removeModel(IShow model){
        userModels.remove ( model );
    }
    @Override
    public Set <IShow> getModels(){
        return userModels;
    }

    @Override
    public void initModels( ) {
        for(IShow models : userModels){
            models.init ();
        }
    }
    @Override
    public void displayModels( ) {
        for(IShow models : userModels){
            models.display ();
        }
    }
}
