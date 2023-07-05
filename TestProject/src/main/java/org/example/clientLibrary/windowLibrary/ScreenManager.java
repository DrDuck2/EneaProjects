package org.example.clientLibrary.windowLibrary;

public class ScreenManager {

    private static IScreen currentScreen;
    public static void setCurrentScreen(IScreen newScreen)
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
