package org.example.clientLibrary.windowManager;

import org.example.clientLibrary.windowLibrary.Interfaces.IScreen;

public class ScreenManager {

    private static IScreen currentScreen;

    public static void setCurrentScreen( IScreen newScreen ) {
        currentScreen = newScreen;
    }

    public static void initScreen( ) {
        currentScreen.initModels ();
    }

    public static void displayScreen( ) {
        currentScreen.displayModels ();
    }

}
