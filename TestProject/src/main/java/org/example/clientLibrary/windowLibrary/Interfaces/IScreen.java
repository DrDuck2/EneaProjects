package org.example.clientLibrary.windowLibrary.Interfaces;

import java.util.Set;

public interface IScreen extends  IShowScreen{

    void addModels(ICreate model);
    void removeModels(ICreate model);
    Set <ICreate> getModels();

}
