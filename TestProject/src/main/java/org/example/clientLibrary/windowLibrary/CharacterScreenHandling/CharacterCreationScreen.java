package org.example.clientLibrary.windowLibrary.CharacterScreenHandling;

import org.example.clientLibrary.windowLibrary.Interfaces.*;

import java.util.HashSet;
import java.util.Set;

public class CharacterCreationScreen implements IScreen, IModelContainer < IModel > {
    private final Set < IModel > models;

    public CharacterCreationScreen( ) {
        this.models = new HashSet <> ();
    }

    @Override
    public void addModel( IModel model ) {
        this.models.add ( model );
    }

    @Override
    public void removeModel( IModel model ) {
        this.models.remove ( model );
    }

    @Override
    public Set < IModel > getModels( ) {
        return this.models;
    }

    @Override
    public void initModels( ) {
        for ( IModel model : models ) {
            model.init ();
        }
    }

    @Override
    public void displayModels( ) {
        for ( IModel model : models ) {
            model.display ();
        }
    }
}
