package org.example.clientLibrary.windowLibrary.GameScreenHandling;

import org.example.clientLibrary.windowLibrary.Interfaces.IModelContainer;
import org.example.clientLibrary.windowLibrary.Interfaces.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class GameScreen implements IScreen, IModelContainer < IModel > {
    private final Set < IModel > userModels;

    public GameScreen( ) {
        userModels = Collections.synchronizedSet ( new HashSet <> () );
    }

    @Override
    public void addModel( IModel model ) {
        userModels.add ( model );
    }

    @Override
    public void removeModel( IModel model ) {
        userModels.remove ( model );
    }

    @Override
    public Set < IModel > getModels( ) {
        return userModels;
    }

    @Override
    public void initModels( ) {
        for ( IModel models : userModels ) {
            models.init ();
        }
    }

    @Override
    public void displayModels( ) {
        for ( IModel models : userModels ) {
            models.display ();
        }
    }
}
