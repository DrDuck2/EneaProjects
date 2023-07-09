package org.example.clientLibrary.windowLibrary.CharacterScreenHandling;

import org.example.clientLibrary.windowLibrary.Interfaces.*;

import java.util.HashSet;
import java.util.Set;

public class CharacterCreationScreen implements IScreen, IModelContainer < ICreate > {
    private final Set < ICreate > models;

    public CharacterCreationScreen( ) {
        this.models = new HashSet <> ();
    }

    @Override
    public void addModel( ICreate model ) {
        this.models.add ( model );
    }

    @Override
    public void removeModel( ICreate model ) {
        this.models.remove ( model );
    }

    @Override
    public Set < ICreate > getModels( ) {
        return this.models;
    }

    @Override
    public void initModels( ) {
        for ( ICreate model : models ) {
            model.init ();
        }
    }

    @Override
    public void displayModels( ) {
        for ( ICreate model : models ) {
            model.display ();
        }
    }
}
