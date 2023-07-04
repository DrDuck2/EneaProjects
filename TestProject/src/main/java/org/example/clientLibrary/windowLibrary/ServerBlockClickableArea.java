package org.example.clientLibrary.windowLibrary;

public class ServerBlockClickableArea implements IClickable {

    private final int areaX;
    private final int areaY;
    private final int width;
    private final int height;
    private boolean clicked;

    public ServerBlockClickableArea( int areaX, int areaY, int width, int height)
    {
        this.areaX = areaX;
        this.areaY = areaY;
        this.width = width;
        this.height = height;
        this.clicked = false;
    }
    public boolean contains(double mouseX,double mouseY){
        return mouseX >= areaX && mouseX <= areaX+width && mouseY >= areaY && mouseY <= areaY + height;
    }
    public void setClicked(boolean clicked){
        this.clicked = clicked;
    }
    public boolean isClicked(){
        return clicked;
    }
}
