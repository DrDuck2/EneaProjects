package org.example.clientLibrary.windowLibrary.GameScreenHandling;

import org.example.clientLibrary.windowManager.CommunicationManager;
import org.example.clientLibrary.windowLibrary.Interfaces.*;
import org.example.clientLibrary.windowManager.SetupManager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;

public class GameHandler implements IModel{

    private final Map <String, UserCharacter > otherUsers;
    private final UserCharacter personalCharacter;
    private final long window;
    private float offsetUp;
    private float offsetDown;
    private float offsetLeft;
    private float offsetRight;
    public GameHandler(UserCharacter personalCharacter,long window){
        this.window = window;
        this.personalCharacter = personalCharacter;

        otherUsers = new ConcurrentHashMap <> ();

        offsetUp = 0.0f;
        offsetDown = 0.0f;
        offsetLeft = 0.0f;
        offsetRight = 0.0f;
    }
    public synchronized void addCharacter(String userInformation,UserCharacter character){
        String newUserId = userInformation.split ( ":" )[0];
        boolean updated = false;
        for(String info : otherUsers.keySet ()){
            if(newUserId.equals ( info.split ( ":" )[0] )){ //If user with ID already exists just update information
                updateUser(userInformation,info);
                updated = true;
            }
        }
        if(!updated){
            otherUsers.put (userInformation ,character );
        }
    }

    private synchronized void updateUser(String newInformation, String key){
        UserCharacter character = otherUsers.get ( key );
        otherUsers.remove ( key ); //removing it from hashmap
        otherUsers.put ( newInformation,character ); //Adding it back with new key and new information
    }
    public synchronized void removeCharacter(String userInformation){
        for(String info:otherUsers.keySet ()){
            if(info.split ( ":" )[0].equals ( userInformation )){
                otherUsers.remove ( info );
            }
        }
    }


    @Override
    public void init(){
        glfwSetKeyCallback(window, (win, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
                glfwSetWindowShouldClose(win, true);
                cleanup ();
            }
            if (key == GLFW_KEY_W && (action == GLFW_REPEAT || action == GLFW_PRESS )) {
                offsetUp+= 0.01f;
            }
            if (key == GLFW_KEY_A &&  (action == GLFW_REPEAT || action == GLFW_PRESS)) {
                offsetLeft += 0.01f;
            }
            if (key == GLFW_KEY_S && (action == GLFW_REPEAT || action == GLFW_PRESS)) {
                offsetDown += 0.01f;
            }
            if (key == GLFW_KEY_D &&  (action == GLFW_REPEAT || action == GLFW_PRESS)){
                offsetRight+= 0.01f;
            }
        });

        CommunicationManager.addInformation (offsetLeft,offsetRight,offsetDown,offsetUp);
        SetupManager.dropLatch ();
    }

    @Override
    public void display(){
        if(!glfwWindowShouldClose ( window )){
            for(String key : otherUsers.keySet ()){
                String[] values = key.split ( ":" );
                float offsetLeft = Float.parseFloat ( values[1] );
                float offsetRight = Float.parseFloat ( values[2] );
                float offsetDown = Float.parseFloat ( values[3] );
                float offsetUp = Float.parseFloat ( values[4] );

                otherUsers.get ( key ).draw ( offsetLeft,offsetRight,offsetDown,offsetUp );
            }
            personalCharacter.draw (offsetLeft,offsetRight,offsetDown,offsetUp);
            CommunicationManager.addInformation (offsetLeft,offsetRight,offsetDown,offsetUp);
        }
    }

    @Override
    public void cleanup()
    {
        CommunicationManager.closeConnection ();
    }

}
