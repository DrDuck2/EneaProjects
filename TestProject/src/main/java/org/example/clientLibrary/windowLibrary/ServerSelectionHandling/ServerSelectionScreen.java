package org.example.clientLibrary.windowLibrary.ServerSelectionHandling;

import org.example.clientLibrary.windowLibrary.Interfaces.IModelContainer;


import java.util.HashSet;
import java.util.Set;

import org.example.clientLibrary.windowLibrary.Interfaces.*;

public class ServerSelectionScreen implements IScreen, IModelContainer < ICreate > {

    private final Set < ICreate > models;

    public ServerSelectionScreen( ) {
        this.models = new HashSet <> ();
    }

    @Override
    public synchronized void addModel( ICreate model ) {
        boolean modelExists = models.stream ()
                .anyMatch ( screenModel -> screenModel.getClass ().equals ( model.getClass () ) );

        if ( ! modelExists ) {
            this.models.add ( model );
        }
    }

    @Override
    public synchronized void removeModel( ICreate model ) {
        this.models.remove ( model );
    }

    @Override
    public synchronized Set < ICreate > getModels( ) {
        return this.models;
    }

    @Override
    public synchronized void initModels( ) {
        for ( ICreate model : models ) {
            model.init ();
        }
    }

    @Override
    public synchronized void displayModels( ) {
        for ( ICreate model : models ) {
            model.display ();
        }
    }

}
