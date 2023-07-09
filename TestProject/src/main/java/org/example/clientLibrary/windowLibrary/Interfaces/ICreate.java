package org.example.clientLibrary.windowLibrary.Interfaces;

import org.example.clientLibrary.windowLibrary.IClickable;

import java.util.List;

public interface ICreate extends IModel {
    void addScreenObject( IScreenObject object );

    void removeScreenObject( IScreenObject object );

    List < IScreenObject > getScreenObjects( );

    void addClickableArea( IClickable area );

    void removeClickableArea( IClickable area );
}
