package org.example.clientLibrary.windowLibrary.ServerSelectionHandling;

import org.example.clientLibrary.windowLibrary.Interfaces.IModelContainer;


import java.util.HashSet;
import java.util.Set;

import org.example.clientLibrary.windowLibrary.Interfaces.*;

public class ServerSelectionScreen implements IScreen, IModelContainer < IModel > {

    private final Set < IModel > models;

    public ServerSelectionScreen( ) {this.models = new HashSet <> ();}
    @Override
    public synchronized void addModel( IModel model ) {
        boolean modelExists = models.stream ()
                .anyMatch ( screenModel -> screenModel.getClass ().equals ( model.getClass () ) );

        if ( ! modelExists ) {
            this.models.add ( model );
        }
    }

    @Override
    public synchronized void removeModel( IModel model ) {this.models.remove ( model );
    }

    @Override
    public synchronized Set < IModel > getModels( ) {return this.models;}

    @Override
    public synchronized void initModels( ) {
        for ( IModel model : models ) {
            model.init ();
        }
    }

    @Override
    public synchronized void displayModels( ) {
        for ( IModel model : models ) {
            model.display ();
        }
    }

}
