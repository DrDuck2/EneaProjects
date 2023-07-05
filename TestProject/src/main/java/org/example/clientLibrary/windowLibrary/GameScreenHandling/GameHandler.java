package org.example.clientLibrary.windowLibrary.GameScreenHandling;

import org.example.clientLibrary.windowLibrary.CharacterHandling.UserCharacter;
import org.example.clientLibrary.windowLibrary.Interfaces.IShow;

import java.util.HashMap;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;

public class GameHandler implements IShow {

    private HashMap<String, UserCharacter > otherUsers;

    private float offsetUp;
    private float offsetDown;
    private float offsetLeft;
    private float offsetRight;
    private UserCharacter personalCharacter;
    private final long window;
    public GameHandler(UserCharacter personalCharacter,long window){
        this.window = window;
        this.personalCharacter = personalCharacter;

        otherUsers = new HashMap <> ();

        offsetUp = 0.0f;
        offsetDown = 0.0f;
        offsetLeft = 0.0f;
        offsetRight = 0.0f;
    }
    public void addCharacter(String userInformation,UserCharacter character){
        otherUsers.put (userInformation ,character );
    }
    public void removeCharacter(String userInformation){
        otherUsers.remove ( userInformation );
    }

    @Override
    public void init(){
        //When pressing escape just close the window
        glfwSetKeyCallback(window, (win, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
                glfwSetWindowShouldClose(win, true);
            } else if (key == GLFW_KEY_W && (action == GLFW_PRESS || action == GLFW_REPEAT)) {
                offsetUp+= 0.1f;
            } else if (key == GLFW_KEY_A && (action == GLFW_PRESS || action == GLFW_REPEAT)){
                offsetLeft+= 0.1f;
            } else if (key == GLFW_KEY_S && (action == GLFW_PRESS || action == GLFW_REPEAT)){
                offsetDown+= 0.1f;
            } else if (key == GLFW_KEY_D && (action == GLFW_PRESS || action == GLFW_REPEAT)){
                offsetRight+= 0.1f;
            }
        });
    }
    @Override
    public void display(){
        personalCharacter.draw (offsetLeft,offsetRight,offsetDown,offsetUp);


    }

}
