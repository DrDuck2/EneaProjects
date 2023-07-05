package org.example.clientLibrary.windowLibrary;
import java.util.List;
import java.util.Set;

public interface ICreate {
    void addScreenObject(IScreenObject object);
    void removeScreenObject(IScreenObject object);

    void addClickableArea(IClickable area);
    void removeClickableArea(IClickable area);
    List <IScreenObject> getScreenObjects();
    void init();
    void display();

}
