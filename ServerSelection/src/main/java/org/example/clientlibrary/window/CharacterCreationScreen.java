package org.example.clientlibrary.window;

import java.util.HashSet;
import java.util.Set;

public class CharacterCreationScreen implements IServerScreen {

    private final Set < ICreate > models;
    private final long window;

    public CharacterCreationScreen(long window){
        models = new HashSet <> ();
        this.window = window;
    }
    public void addModel(ICreate model)
    {
        models.add ( model );
    }
    public void removeModel(ICreate model)
    {
        models.remove(model);
    }

    public void init(){

    }
    public IServerScreen display(){
        for(ICreate model : models)
        {
            model.draw ();
        }
        return this;
    }
}
