package org.example.clientLibrary.windowLibrary;

import java.util.HashSet;
import java.util.Set;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.glfwGetCursorPos;

public class ServerSelectHandler implements ICreate {

    private final Set < IScreenObject > screenObjects;
    private final Set < IClickable > clickableArea;
    private final long window;

    private int offset;

    public ServerSelectHandler(long window){
        this.window = window;
        this.screenObjects = new HashSet <> ();
        this.clickableArea = new HashSet <> ();
        this.offset = 0;
    }
    public synchronized void addScreenObject(IScreenObject object){
        this.screenObjects.add ( object );
        setClickableArea ( new ServerBlockClickableArea (  240 , 60 + offset , 320 , 60 ) );
    }
    public synchronized void removeScreenObject(IScreenObject object){
        this.screenObjects.remove(object);
    }

    public synchronized Set<IScreenObject> getScreenObjects(){return this.screenObjects;}

    private synchronized void setClickableArea(IClickable area){
        clickableArea.add ( area );
        offset+=75;
    }
    private synchronized void removeClickableArea(IClickable area){
        clickableArea.remove ( area );
    }
    public void init()
    {
        glfwSetKeyCallback ( window , ( win , key , scancode , action , mods ) -> {
            if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
                glfwSetWindowShouldClose ( win , true );
        } );
    }
    public void display()
    {
        float positionY = 0;
        for(IScreenObject object : screenObjects){
            object.draw (positionY);
            positionY += 0.25f;
        }
    }
    public void cleanup(){
        glfwFreeCallbacks ( window );
    }
}
