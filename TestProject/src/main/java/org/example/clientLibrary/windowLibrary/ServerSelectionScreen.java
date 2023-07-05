package org.example.clientLibrary.windowLibrary;

import java.util.HashSet;
import java.util.Set;

public class ServerSelectionScreen implements IScreen {

    private final Set < ICreate > models;
    public ServerSelectionScreen(){
        this.models = new HashSet <> ();
    }
    public synchronized void addModels(ICreate model)
    {
        boolean modelExists = models.stream()
                .anyMatch(screenModel -> screenModel.getClass().equals(model.getClass()));

        if (!modelExists) {
            this.models.add(model);
        }
    }
    public synchronized void removeModels(ICreate model){
        this.models.remove(model);
    }
    public synchronized Set<ICreate> getModels(){
        return this.models;
    }

    //Initializes callbacks for all models on the screen
    public synchronized void initModels(){
        for(ICreate model : models)
        {
            model.init();
        }
    }
    //Displays all the blocks and clickable areas necessary
    public synchronized void displayModels(){
        for(ICreate model : models)
        {
            model.display();
        }
    }

}
