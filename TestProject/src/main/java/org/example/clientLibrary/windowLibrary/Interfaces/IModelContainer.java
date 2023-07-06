package org.example.clientLibrary.windowLibrary.Interfaces;

import java.util.Set;

public interface IModelContainer <T>{
    void addModel(T model);
    void removeModel(T model);
    Set <T> getModels();
}
