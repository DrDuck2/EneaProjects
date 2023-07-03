package org.example.clientlibrary.window;

import org.example.clientlibrary.window.IServerScreen;
import org.example.serverlibrary.Server;
import org.lwjgl.glfw.GLFWErrorCallback;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwGetCursorPos;
import static org.lwjgl.opengl.GL11.glColor3f;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
public class ServerSelectionScreen implements IServerScreen  {

    private final HashMap < ClickableArea, String > hash;
    private final long window;
    private int offset;
    private float positionY;
    private String serverData;

    public ServerSelectionScreen(long window)
    {
        this.window = window;
        hash = new HashMap <> ();
        offset = 0;
        positionY = 0;
        serverData = null;
    }
    public void init()
    {

        glfwSetMouseButtonCallback ( window , ( win, button , action , mods ) -> {
            if ( button == GLFW_MOUSE_BUTTON_LEFT && action == GLFW_PRESS ) {
                double[] mouseX = new double[1];
                double[] mouseY = new double[1];
                glfwGetCursorPos ( win , mouseX , mouseY );

                for ( ClickableArea area : hash.keySet () ) {
                    area.setClicked ( area.contains ( mouseX[0] , mouseY[0] ) );
                }
            } else {
                for ( ClickableArea area : hash.keySet () ) {
                    area.setClicked ( false );
                }
            }
        } );

        glfwSetKeyCallback ( window , ( win , key , scancode , action , mods ) -> {
            if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
                glfwSetWindowShouldClose ( win , true ); // We will detect this in the rendering loop
        } );
    }


    public void addServer( String server ) {
        if(!hash.containsValue ( server )) {
            hash.put ( new ClickableArea ( 240 , 60 + offset , 320 , 60 , positionY  ),server);
            offset += 75;
            positionY += 0.25f;
        }
    }
    private void setClickedServerData( String serverData ) {
        this.serverData = serverData;
    }

    public String getClickedServerData( ) {
        return this.serverData;
    }

    public String display()
    {
        for ( ClickableArea area : hash.keySet () ) {
            if ( area.isClicked () ) {
                glColor3f ( 0.0f , 1.0f , 0.0f ); //Change color of clicked area

                setClickedServerData ( hash.get ( area ) ); //Save chosen server data for later access
                cleanup();
                return "CharacterCreationScreen";

            } else {
                glColor3f ( 0.0f , 0.0f , 1.0f ); //Change color of clicked area
            }
            area.draw ();
        }
        return "ServerSelectionScreen";
    }

    private void cleanup()
    {
        glfwFreeCallbacks ( window );
    }
}
