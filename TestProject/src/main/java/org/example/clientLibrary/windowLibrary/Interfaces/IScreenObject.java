package org.example.clientLibrary.windowLibrary.Interfaces;

public interface IScreenObject {
    void draw(float leftOffset,float rightOffset,float downOffset, float upOffset,float scale);
    void setColor(float red, float green, float blue);
    float getRed();
    float getBlue();
    float getGreen();
    String getBlockInformation();
}
