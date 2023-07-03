package org.example.clientlibrary;

import org.example.clientlibrary.IServerScreen;

public class ScreenManager {
    private IServerScreen currentScreen;
    public void setCurrentScreen(IServerScreen screen)
    {
        this.currentScreen = screen;
    }
    public void displayCurrentScreen(){
        currentScreen.display ();
    }
}
