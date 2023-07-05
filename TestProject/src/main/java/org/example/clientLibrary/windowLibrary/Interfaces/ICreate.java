package org.example.clientLibrary.windowLibrary.Interfaces;
import org.example.clientLibrary.windowLibrary.IClickable;

public interface ICreate extends IShow {

    void addClickableArea( IClickable area);
    void removeClickableArea(IClickable area);

}
