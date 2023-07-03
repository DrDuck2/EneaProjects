package org.example.clientlibrary.window;

import org.example.clientlibrary.window.IServerScreen;

import java.util.HashSet;
import java.util.Set;

public class ScreenManager {
    private final Set<IServerScreen> serverScreens;
    private IServerScreen currentScreen;

    public ScreenManager(){
        serverScreens = new HashSet <> ();
    }
    public void setCurrentScreen(String newScreen)
    {
        for(IServerScreen screen : serverScreens)
        {
            if(screen.getClass ().getName ().equals ( newScreen )){
                currentScreen = screen;
            }
        }
    }

    public void addScreen(IServerScreen screen){
        serverScreens.add ( screen );
    }
    public IServerScreen getCurrentScreen()
    {
        return currentScreen;
    }
    public void initScreen(){
        currentScreen.init ();
    }
    public void displayCurrentScreen(){
        if(isChanged ( currentScreen.display () )){
            currentScreen.init ();
        }
    }
    private boolean isChanged(String newScreen) {
        if(currentScreen.getClass ().getName ().equals ( newScreen )){
            setCurrentScreen ( newScreen );
            return true;
        }
        return false;
    }

}
