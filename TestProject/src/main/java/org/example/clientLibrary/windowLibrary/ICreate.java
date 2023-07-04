package org.example.clientLibrary.windowLibrary;
import java.util.Set;

public interface ICreate {

    void addScreenObject(IScreenObject object);
    void removeScreenObject(IScreenObject object);
    Set<IScreenObject> getScreenObjects();
    void init();
    void display();

}
