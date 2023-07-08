package org.example.clientLibrary.windowLibrary.CharacterScreenHandling;

import org.example.clientLibrary.windowLibrary.*;
import org.example.clientLibrary.windowLibrary.GameScreenHandling.UserCharacter;
import org.example.clientLibrary.windowLibrary.Interfaces.*;
import org.example.clientLibrary.windowManager.ScreenManager;
import org.example.clientLibrary.windowManager.SetupManager;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;

public class CharacterCreateHandler implements ICreate {

    private final List < IScreenObject > bodyParts;
    private final List< IClickable > clickableArea;

    private boolean screenSwitch;

    private long window;
    public CharacterCreateHandler(long window){
        bodyParts = new CopyOnWriteArrayList <> ();
        clickableArea = new CopyOnWriteArrayList <> ();
        this.window = window;
        screenSwitch = false;
    }

    public synchronized void addScreenObject(IScreenObject object){
        //Adds ScreenObject
        bodyParts.add ( object );
    }
    public void removeScreenObject(IScreenObject object){
        bodyParts.remove ( object );
    }
    public List <IScreenObject> getScreenObjects(){
        return bodyParts;
    }


    public void addClickableArea(IClickable area){
        clickableArea.add ( area );
    }

    public void removeClickableArea(IClickable area){
        clickableArea.remove ( area );
    }
    public void init(){

        glfwSetMouseButtonCallback ( window , ( win, button , action , mods ) -> {
            if ( button == GLFW_MOUSE_BUTTON_LEFT && action == GLFW_PRESS ) {
                double[] mouseX = new double[1];
                double[] mouseY = new double[1];
                glfwGetCursorPos ( win , mouseX , mouseY );

                for ( IClickable area : clickableArea ) {
                    area.setClicked ( area.contains ( mouseX[0] , mouseY[0] ) );
                }
            } else {
                for ( IClickable area : clickableArea ) {
                    area.setClicked ( false );
                }
            }
        } );

        glfwSetKeyCallback ( window , ( win , key , scancode , action , mods ) -> {
            if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
                glfwSetWindowShouldClose ( win , true );
        } );

        glfwSetKeyCallback ( window , ( win , key , scancode , action , mods ) -> {
            if ( key == GLFW_KEY_ENTER && action == GLFW_RELEASE )
                setSwitchScreen(true);
        } );
    }
    public void display(){
        if(screenSwitch){
            cleanup ();
        }else{
            for(int i = 0;i<clickableArea.size ();i++){
                if(clickableArea.get ( i ).isClicked ()){
                    if(bodyParts.get ( i ).getRed () == 0.5f){
                        bodyParts.get ( i ).setColor ( 0.0f,1.0f,0.0f );
                    }else if(bodyParts.get ( i ).getGreen () == 1.0f){
                        bodyParts.get ( i ).setColor ( 0.0f,0.0f,1.0f );
                    }else{
                        bodyParts.get ( i ).setColor ( 0.5f,0.0f,0.0f );
                    }
                }
                bodyParts.get ( i ).draw ( 0,0,0,0,1 );
            }
        }
    }

    private void setSwitchScreen(boolean value){
        screenSwitch = value;
    }
    public void cleanup(){
        glfwFreeCallbacks ( window );
        ScreenManager.setCurrentScreen ( SetupManager.getSimpleGameScreen (new UserCharacter ( bodyParts ), window ) );
        ScreenManager.initScreen ();
        SetupManager.dropLatch();
    }
}
