package org.example.clientLibrary.windowLibrary;

import java.util.Set;

public interface IScreen {

    void addModels(ICreate model);
    void removeModels(ICreate model);
    Set <ICreate> getModels();
    void initModels();
    void displayModels();
}
