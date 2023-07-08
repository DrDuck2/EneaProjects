package org.example.clientLibrary.windowLibrary;

public interface IClickable {
    boolean contains( double mouseX , double mouseY );

    void setClicked( boolean clicked );

    boolean isClicked( );

}
