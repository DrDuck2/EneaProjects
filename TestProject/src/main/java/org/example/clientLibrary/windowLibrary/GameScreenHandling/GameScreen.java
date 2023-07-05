package org.example.clientLibrary.windowLibrary.GameScreenHandling;

import org.example.clientLibrary.windowLibrary.Interfaces.IShow;
import org.example.clientLibrary.windowLibrary.Interfaces.IShowScreen;

import java.util.Set;

public class GameScreen implements IShowScreen {
    private Set< IShow > userModels;
    public void addModel(IShow model){
        userModels.add ( model );
    }
    public void removeModel(IShow model){
        userModels.remove ( model );
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
