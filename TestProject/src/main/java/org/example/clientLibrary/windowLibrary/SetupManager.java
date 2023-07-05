package org.example.clientLibrary.windowLibrary;

import org.example.CountdownLatchWithInfo;
import org.example.clientLibrary.windowLibrary.character.Arm;
import org.example.clientLibrary.windowLibrary.character.Body;
import org.example.clientLibrary.windowLibrary.character.Head;
import org.example.clientLibrary.windowLibrary.character.Leg;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

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
    public static IScreen getSimpleServerSelectionScreen(long window){
        ServerSelectionScreen serverSelectionScreen = new ServerSelectionScreen ();
        ServerSelectHandler serverSelectHandler = new ServerSelectHandler ( window );
        serverSelectionScreen.addModels ( serverSelectHandler );
        setCurrentScreen ( serverSelectionScreen );
        return serverSelectionScreen;

    }
    public static IScreen getSimpleCharacterCreationScreen(long window){
        CharacterCreationScreen characterCreationScreen = new CharacterCreationScreen ();
        CharacterCreateHandler characterCreateHandler = new CharacterCreateHandler (window);
        characterCreationScreen.addModels ( characterCreateHandler );
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

        currentScreen = characterCreationScreen;
        return characterCreationScreen;
    }
    public static void addModel( ICreate model){
        currentScreen.addModels ( model );
    }
    public static void addObject(String modelType,IScreenObject object){
        Set<ICreate> screenModels = currentScreen.getModels ();
        for(ICreate model : screenModels){
            if(isCorrectModel(model,modelType)){
                model.addScreenObject ( object );
            }
        }
    }
    private static boolean isCorrectModel(ICreate model,String modelType){
        String className = model.getClass ().getName ();
        String simpleClassName = className.substring ( className.lastIndexOf ( '.' ) +1 );
        if(simpleClassName.equals ( modelType )) return true;
        return false;
    }


}
