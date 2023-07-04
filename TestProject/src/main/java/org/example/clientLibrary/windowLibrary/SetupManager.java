package org.example.clientLibrary.windowLibrary;

import java.util.Set;

public class SetupManager {

    private IScreen currentScreen;

    public void setCurrentScreen(IScreen newScreen)
    {
        this.currentScreen = newScreen;
    }
    public IScreen getSimpleServerSelectionScreen(long window){
        ServerSelectionScreen serverSelectionScreen = new ServerSelectionScreen ();
        ServerSelectHandler serverSelectHandler = new ServerSelectHandler ( window );
        serverSelectionScreen.addModels ( serverSelectHandler );
        setCurrentScreen ( serverSelectionScreen );
        return serverSelectionScreen;

    }
    public IScreen getSimpleCharacterCreationScreen(long window){
        CharacterCreationScreen characterCreationScreen = new CharacterCreationScreen ();
        CharacterCreateHandler characterCreateHandler = new CharacterCreateHandler ();
        characterCreationScreen.addModels ( characterCreateHandler );
        currentScreen = characterCreationScreen;
        return characterCreationScreen;
    }
    public void addModel( ICreate model){
        currentScreen.addModels ( model );
    }
    public void addObject( String screenModel, IScreenObject screenObject){
        Set<ICreate> screenModels = currentScreen.getModels ();
        for(ICreate model : screenModels){
            if(!model.getClass ().toString ().equals ( "ServerSelectHandler" )){
                model.addScreenObject ( screenObject );
            }
        }
    }


}
