package org.example.clientLibrary.windowLibrary;

public class ScreenManager {

    private IScreen currentScreen;
    public void setCurrentScreen(IScreen newScreen)
    {
        this.currentScreen = newScreen;
    }

    //This is supposed to initialize screen callbacks
    public void initScreen(){
        currentScreen.initModels();
    }

    // This is supposed to draw necessary blocks and clickableAreas
    public void displayScreen(){
        currentScreen.displayModels();
    }

}
