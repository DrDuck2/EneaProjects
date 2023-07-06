package org.example.clientLibrary.windowLibrary.ServerSelectionHandling;

import org.example.clientLibrary.windowLibrary.Interfaces.IModelContainer;


import java.util.HashSet;
import java.util.Set;
import org.example.clientLibrary.windowLibrary.Interfaces.*;

public class ServerSelectionScreen implements IScreen, IModelContainer<ICreate> {

    private final Set < ICreate > models;
    public ServerSelectionScreen(){
        this.models = new HashSet <> ();
    }
    public synchronized void addModel(ICreate model)
    {
        boolean modelExists = models.stream()
                .anyMatch(screenModel -> screenModel.getClass().equals(model.getClass()));

        if (!modelExists) {
            this.models.add(model);
        }
    }
    public synchronized void removeModel(ICreate model){
        this.models.remove(model);
    }
    public synchronized Set<ICreate> getModels(){
        return this.models;
    }

    //Initializes callbacks for all models on the screen
    public synchronized void init(){
        for(ICreate model : models)
        {
            model.init();
        }
    }
    //Displays all the blocks and clickable areas necessary
    public synchronized void display(){
        for(ICreate model : models)
        {
            model.display();
        }
    }

}
