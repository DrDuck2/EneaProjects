package org.example.clientLibrary.windowLibrary;

import org.example.clientLibrary.windowLibrary.Interfaces.IShowScreen;

public class ScreenManager {

    private static IShowScreen currentScreen;
    public static void setCurrentScreen(IShowScreen newScreen)
    {
        currentScreen = newScreen;
    }

    //This is supposed to initialize screen callbacks
    public static void initScreen(){
        currentScreen.initModels();
    }

    // This is supposed to draw necessary blocks and clickableAreas
    public static void displayScreen(){
        currentScreen.displayModels();
    }

}
