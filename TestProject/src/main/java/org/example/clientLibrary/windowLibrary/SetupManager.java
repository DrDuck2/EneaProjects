package org.example.clientLibrary.windowLibrary;

import org.example.CountdownLatchWithInfo;
import org.example.clientLibrary.windowLibrary.CharacterHandling.CharacterCreateHandler;
import org.example.clientLibrary.windowLibrary.CharacterHandling.CharacterCreationScreen;
import org.example.clientLibrary.windowLibrary.GameScreenHandling.UserCharacter;
import org.example.clientLibrary.windowLibrary.CharacterHandling.character.Arm;
import org.example.clientLibrary.windowLibrary.CharacterHandling.character.Body;
import org.example.clientLibrary.windowLibrary.CharacterHandling.character.Head;
import org.example.clientLibrary.windowLibrary.CharacterHandling.character.Leg;
import org.example.clientLibrary.windowLibrary.GameScreenHandling.GameHandler;
import org.example.clientLibrary.windowLibrary.GameScreenHandling.GameScreen;
import org.example.clientLibrary.windowLibrary.Interfaces.*;
import org.example.clientLibrary.windowLibrary.ServerSelectionHandling.ServerSelectHandler;
import org.example.clientLibrary.windowLibrary.ServerSelectionHandling.ServerSelectionScreen;

import java.util.Set;

public class SetupManager {

    private static IScreen currentScreen;
    private static CountdownLatchWithInfo <String> countDownLatch;

    public static void setCurrentScreen(IScreen newScreen)
    {
        currentScreen = newScreen;
    }


    public static void setLatch(CountdownLatchWithInfo<String> latch){
        countDownLatch = latch;
    }
    public static void setLatchInfo( String information ){
        countDownLatch.setInformation ( information );
    }
    public static void dropLatch(){
        countDownLatch.countDown ();
    }



    public static ServerSelectionScreen getSimpleServerSelectionScreen( long window){
        ServerSelectionScreen serverSelectionScreen = new ServerSelectionScreen ();
        ServerSelectHandler serverSelectHandler = new ServerSelectHandler ( window );


        addModels ( serverSelectionScreen,serverSelectHandler );
        setCurrentScreen ( serverSelectionScreen );
        return  serverSelectionScreen;

    }
    public static CharacterCreationScreen getSimpleCharacterCreationScreen(long window){
        CharacterCreationScreen characterCreationScreen = new CharacterCreationScreen ();
        CharacterCreateHandler characterCreateHandler = new CharacterCreateHandler (window);

        characterCreateHandler.addScreenObject ( new Head ( 50 ) );
        characterCreateHandler.addScreenObject ( new Body ( 20,100 ) );
        characterCreateHandler.addScreenObject ( new Arm ( 80,20,"Left" ) );
        characterCreateHandler.addScreenObject ( new Arm ( 80,20,"Right" ) );
        characterCreateHandler.addScreenObject ( new Leg ( 30,"Left" ) );
        characterCreateHandler.addScreenObject ( new Leg ( 30,"Right" ) );

        characterCreateHandler.addClickableArea ( new ClickableArea ( 320,30,160,120 ) );
        characterCreateHandler.addClickableArea ( new ClickableArea ( 320,165,160,195 ) );
        characterCreateHandler.addClickableArea ( new ClickableArea ( 240,165,60,165 ) );
        characterCreateHandler.addClickableArea ( new ClickableArea ( 500,165,60,165 ) );
        characterCreateHandler.addClickableArea ( new ClickableArea ( 320,375,60,165 ) );
        characterCreateHandler.addClickableArea ( new ClickableArea ( 420,375,60,165 ) );

        addModels (characterCreationScreen ,characterCreateHandler );
        setCurrentScreen ( characterCreationScreen );
        return characterCreationScreen;
    }

    public static GameScreen getSimpleGameScreen( UserCharacter character, long window){
        GameScreen gameScreen = new GameScreen ();
        character.scale(0.2f);
        GameHandler gameHandler = new GameHandler (character,window);

        addModels(gameScreen,gameHandler);

        setCurrentScreen ( gameScreen );
        return gameScreen;
    }
    public static void addModels(Object screen, Object model) {
        if (screen instanceof IModelContainer) {
            if (model instanceof ICreate) {
                ((IModelContainer<ICreate>) screen).addModel((ICreate) model);
            } else if (model instanceof IShow) {
                ((IModelContainer<IShow>) screen).addModel((IShow) model);
            } else {
                throw new IllegalArgumentException("Unsupported model type");
            }
        } else {
            throw new IllegalArgumentException("Unsupported screen type");
        }
    }
    public static void addObject(String modelType, Object object) {
        if(currentScreen instanceof GameScreen){

        }else if(currentScreen instanceof CharacterCreationScreen || currentScreen instanceof ServerSelectionScreen){
            Set<ICreate> models = ((IModelContainer<ICreate>) currentScreen).getModels ();
            for(ICreate model : models){
                if(isCorrectModel ( model,modelType )){
                    if(object instanceof IScreenObject){
                        model.addScreenObject ( ((IScreenObject) object) );
                    }
                }
            }
        }
    }

    private static boolean isCorrectModel(ICreate model, String modelType) {
        String className = model.getClass().getName();
        String simpleClassName = className.substring(className.lastIndexOf('.') + 1);

        if (simpleClassName.equals(modelType)) {
            return true;
        }
        return false;
    }
}
