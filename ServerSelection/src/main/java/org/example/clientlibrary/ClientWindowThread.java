package org.example.clientlibrary;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Logger;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class ClientWindowThread extends Thread {
    private final Logger logger = Logger.getLogger (ClientWindowThread.class.getName ());
    private final HashMap < ClickableArea, String > hash = new HashMap <> (); // clickable area and server data stored as key-value pair for easier access
    private final CountDownLatch eventLatch;
    private long window;
    private float positionY ; // Server clickable area position offset (on y-axis)
    private int offset; // Server box offset from each other (on y-axis)
    private String serverData; // Selected server data
    private final ScreenManager screenManager;

    public ClientWindowThread( CountDownLatch eventLatch ) {
        super ( "ClientWindowThread" );
        this.eventLatch = eventLatch;
        positionY = 0.0f;
        offset = 0;
        serverData = "";
        screenManager = new ScreenManager();
    }

    public void run( ) {

        init ();
        loop ();
        cleanup ();

    }

    private void init( ) {
        GLFWErrorCallback.createPrint ( System.err ).set ();

        if ( ! glfwInit () )
            throw new IllegalStateException ( "Unable to initialize GLFW" );

        // Configure GLFW
        glfwDefaultWindowHints (); // optional, the current window hints are already the default
        glfwWindowHint ( GLFW_VISIBLE , GLFW_FALSE ); // the window will stay hidden after creation
        glfwWindowHint ( GLFW_RESIZABLE , GLFW_FALSE ); // the window will be resizable

        // Create the window
        window = glfwCreateWindow ( 800 , 600 , "Hello World!" , NULL , NULL );
        if ( window == NULL )
            throw new RuntimeException ( "Failed to create the GLFW window" );


        // Callback for left mouse button click when choosing the server
        glfwSetMouseButtonCallback ( window , ( window , button , action , mods ) -> {
            if ( button == GLFW_MOUSE_BUTTON_LEFT && action == GLFW_PRESS ) {
                double[] mouseX = new double[1];
                double[] mouseY = new double[1];
                glfwGetCursorPos ( window , mouseX , mouseY );

                for ( ClickableArea area : hash.keySet () ) {
                    area.setClicked ( area.contains ( mouseX[0] , mouseY[0] ) );
                }
            } else {
                for ( ClickableArea area : hash.keySet () ) {
                    area.setClicked ( false );
                }
            }
        } );

        // Set up a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback ( window , ( window , key , scancode , action , mods ) -> {
            if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
                glfwSetWindowShouldClose ( window , true ); // We will detect this in the rendering loop
        } );

        // Get the thread stack and push a new frame
        try ( MemoryStack stack = stackPush () ) {
            IntBuffer pWidth = stack.mallocInt ( 1 ); // int*
            IntBuffer pHeight = stack.mallocInt ( 1 ); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize ( window , pWidth , pHeight );

            // Get the resolution of the primary monitor
            GLFWVidMode vidmode = glfwGetVideoMode ( glfwGetPrimaryMonitor () );

            // Center the window
            assert vidmode != null;
            glfwSetWindowPos (
                    window ,
                    (vidmode.width () - pWidth.get ( 0 )) / 2 ,
                    (vidmode.height () - pHeight.get ( 0 )) / 2
            );
        } // the stack frame is popped automatically


        //Creating default character for the client
        CreationHandler.getInstance ().createDefaultCharacter ();

        //Setting server selection screen first
        screenManager.setCurrentScreen (  );

        // Make the OpenGL context current
        glfwMakeContextCurrent ( window );
        // Enable v-sync
        glfwSwapInterval ( 1 );
        // Make the window visible
        glfwShowWindow ( window );
    }

    private void loop( ) {

        GL.createCapabilities ();

        glClearColor ( 1.0f , 0.0f , 0.0f , 0.0f );

        while ( ! glfwWindowShouldClose ( window ) ) {
            glfwPollEvents ();

            glClear ( GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT );

            // Displaying boxes that act like buttons, and setting clickable area on each of the boxes
            displayServerArea ();

            glfwSwapBuffers ( window );
        }
    }

    // Every time a client broadcast thread catches a new packet it will add it to the window.
    // Window takes care of the duplicates
    public void addServer( String server ) {
        if(!hash.containsValue ( server )) {
            hash.put ( new ClickableArea ( 240 , 60 + offset , 320 , 60 , positionY  ),server);
            offset += 75;
            positionY += 0.25f;
        }
    }

    // Displaying the boxes and click areas for those boxes for each server inside the HashMap
    private void displayServerArea( ) {
        for ( ClickableArea area : hash.keySet () ) {
            if ( area.isClicked () ) {
                glColor3f ( 0.0f , 1.0f , 0.0f ); //Change color of clicked area

                setClickedServerData ( hash.get ( area ) ); //Save chosen server data for later access
            } else {
                glColor3f ( 0.0f , 0.0f , 1.0f ); //Change color of clicked area
            }
            area.draw ();
        }
    }

    // After client clicks on one of the servers it will close the window and selected
    // server data will be stored in serverData
    private void setClickedServerData( String serverData ) {
        this.serverData = serverData;
    }

    // Allowing the client to access the server data from outside
    public String getClickedServerData( ) {
        return this.serverData;
    }

    private void cleanup( ) {
        glfwFreeCallbacks ( window );
        glfwDestroyWindow ( window );

        glfwTerminate ();
        Objects.requireNonNull ( glfwSetErrorCallback ( null ) ).free ();
    }
}
