package org.example.clientLibrary.windowLibrary.ServerSelectionHandling;

import org.example.clientLibrary.windowLibrary.*;
import org.example.clientLibrary.windowLibrary.Interfaces.*;
import org.example.clientLibrary.windowManager.ScreenManager;
import org.example.clientLibrary.windowManager.SetupManager;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.glfwGetCursorPos;

public class ServerSelectHandler implements IModel, IHandle {

    private final List < IScreenObject > screenObjects;
    private final List < IClickable > clickableArea;
    private final long window;
    private int offset;

    public ServerSelectHandler( long window ) {
        this.window = window;
        this.screenObjects = new CopyOnWriteArrayList <> ();
        this.clickableArea = new CopyOnWriteArrayList <> ();
        this.offset = 0;
    }

    @Override
    public synchronized void addScreenObject( IScreenObject object ) {

        boolean shouldAdd = screenObjects.stream ()
                .noneMatch ( existingObject -> existingObject.getBlockInformation ().equals ( object.getBlockInformation () ) );

        if ( shouldAdd ) {
            screenObjects.add ( object );
            addClickableArea ( new ClickableArea ( 240 , 60 + offset , 320 , 60 ) );
        }
    }

    @Override
    public synchronized void removeScreenObject( IScreenObject object ) {
        this.screenObjects.remove ( object );
    }

    @Override
    public synchronized List < IScreenObject > getScreenObjects( ) {
        return this.screenObjects;
    }

    @Override
    public synchronized void addClickableArea( IClickable area ) {
        clickableArea.add ( area );
        offset += 75;
    }

    @Override
    public synchronized void removeClickableArea( IClickable area ) {
        clickableArea.remove ( area );
    }

    @Override
    public void init( ) {
        glfwSetMouseButtonCallback ( window , ( win , button , action , mods ) -> {
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
    }

    @Override
    public void display( ) {
        float positionY = 0;
        float red;
        float green;
        float blue;

        for ( int i = 0 ; i < clickableArea.size () ; i++ ) {
            if ( clickableArea.get ( i ).isClicked () ) {

                red = 0.0f;
                green = 1.0f;
                blue = 0.0f;

                SetupManager.setLatchInfo ( getServerInformation ( clickableArea.get ( i ) ) );

                cleanup ();

            } else {
                red = 0.0f;
                green = 0.0f;
                blue = 1.0f;
            }

            screenObjects.get ( i ).setColor ( red , green , blue );
            screenObjects.get ( i ).draw ( 0 , 0 , positionY , 0 , 0 );
            positionY += 0.25f;
        }
    }

    private String getServerInformation( IClickable area ) {
        int index = clickableArea.indexOf ( area );
        return screenObjects.get ( index ).getBlockInformation ();
    }

    @Override
    public void cleanup( ) {
        glfwFreeCallbacks ( window );
        ScreenManager.setCurrentScreen ( SetupManager.getSimpleCharacterCreationScreen ( window ) );
        ScreenManager.initScreen ();
    }
}
