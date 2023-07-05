package org.example.clientLibrary.windowLibrary.Interfaces;

import java.util.List;

public interface IShowScreen<T> {
    void initModels();
    void displayModels();
    void addScreenObject(T object);
    void removeScreenObject(T object);
    List <T> getScreenObjects();
}
