package org.example.clientLibrary;

import org.example.CountdownLatchWithInfo;
import org.example.clientLibrary.windowManager.ScreenManager;
import org.example.clientLibrary.windowManager.SetupManager;
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
    private final CountdownLatchWithInfo < String > eventLatch;

    public ClientWindowThread( CountdownLatchWithInfo < String > eventLatch ) {
        super ( "ClientWindowThread" );
        this.eventLatch = eventLatch;
    }

    @Override
    public void run( ) {

        init ();
        loop ();
        cleanup ();

    }

    private void init( ) {
        GLFWErrorCallback.createPrint ( System.err ).set ();

        if ( ! glfwInit () )
            throw new IllegalStateException ( "Unable to initialize GLFW" );

        glfwDefaultWindowHints ();
        glfwWindowHint ( GLFW_VISIBLE , GLFW_FALSE );
        glfwWindowHint ( GLFW_RESIZABLE , GLFW_FALSE );

        window = glfwCreateWindow ( 800 , 600 , "Hello World!" , NULL , NULL );
        if ( window == NULL )
            throw new RuntimeException ( "Failed to create the GLFW window" );


        //TODO: Refactor SetupManager and ScreenManager for more clean code
        SetupManager.setLatch ( eventLatch );
        ScreenManager.setCurrentScreen ( SetupManager.getSimpleServerSelectionScreen ( window ) );
        ScreenManager.initScreen ();


        try ( MemoryStack stack = stackPush () ) {
            IntBuffer pWidth = stack.mallocInt ( 1 ); // int*
            IntBuffer pHeight = stack.mallocInt ( 1 ); // int*

            glfwGetWindowSize ( window , pWidth , pHeight );

            GLFWVidMode vidMode = glfwGetVideoMode ( glfwGetPrimaryMonitor () );

            assert vidMode != null;
            glfwSetWindowPos (
                    window ,
                    (vidMode.width () - pWidth.get ( 0 )) / 2 ,
                    (vidMode.height () - pHeight.get ( 0 )) / 2
            );
        }

        glfwMakeContextCurrent ( window );

        glfwSwapInterval ( 1 );

        glfwShowWindow ( window );
    }

    private void loop( ) {

        GL.createCapabilities ();

        glClearColor ( 1.0f , 0.0f , 0.0f , 0.0f );

        while ( ! glfwWindowShouldClose ( window ) ) {
            glfwPollEvents ();

            glClear ( GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT );

            ScreenManager.displayScreen ();

            glfwSwapBuffers ( window );
        }
    }

    private void cleanup( ) {
        glfwFreeCallbacks ( window );
        glfwDestroyWindow ( window );

        glfwTerminate ();
        Objects.requireNonNull ( glfwSetErrorCallback ( null ) ).free ();
    }
}
