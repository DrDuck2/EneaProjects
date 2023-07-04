package org.example.clientLibrary;

import org.example.clientLibrary.windowLibrary.ScreenManager;
import org.example.clientLibrary.windowLibrary.SetupManager;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;
import java.util.Objects;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class ClientWindowThread extends Thread {
    private long window;

    private final ScreenManager screenManager;
    private final SetupManager setupManager;
    public ClientWindowThread() {
        super ( "ClientWindowThread" );
        this.screenManager = new ScreenManager ();
        this.setupManager = new SetupManager ();
    }
    public void run( ) {

        init ();
        loop ();
        cleanup ();

    }

    private void init() {
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


        //Todo
        //Screen setup and screen init
        screenManager.setCurrentScreen ( setupManager.getSimpleServerSelectionScreen ( window ) ); //returns server selection screen
        screenManager.initScreen ();
        //////




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

            /////
            //Displaying current screen Server selection screen or Character creation screen
            screenManager.displayScreen ();
            /////

            glfwSwapBuffers ( window );
        }
    }


    //Allows others to make changes to the window
    public SetupManager getSetupManager(){
        return setupManager;
    }
    private void cleanup( ) {
        glfwFreeCallbacks ( window );
        glfwDestroyWindow ( window );

        glfwTerminate ();
        Objects.requireNonNull ( glfwSetErrorCallback ( null ) ).free ();
    }
}
