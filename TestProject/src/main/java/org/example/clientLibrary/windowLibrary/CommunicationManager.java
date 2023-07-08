package org.example.clientLibrary.windowLibrary;

import org.example.clientLibrary.windowLibrary.GameScreenHandling.UserCharacter;
import org.example.clientLibrary.windowLibrary.Interfaces.IScreen;
import org.example.clientLibrary.windowLibrary.CharacterHandling.character.*;

import java.util.Set;

public class CommunicationManager {
    public static UserCharacter userCharacter = null;
    public static String sendInformation = null;
    public synchronized static void addInformation(float offsetLeft,float offsetRight,float offsetDown,float offsetUp){
        if(userCharacter!=null){
            String id = Integer.toHexString ( userCharacter.hashCode () );
            sendInformation = id + ":"+ userCharacter.getScaleFactor () + ":" + offsetLeft + ":" + offsetRight + ":" + offsetDown + ":" + offsetUp;
        }
        else{
            throw new NullPointerException ();
        }
    }
    public synchronized static void setReceivedInformation(String receivedInformation){
        String[] values = receivedInformation.split ( ":" );
        float scaleFactor = Float.parseFloat ( values[1] );

        String information = values[0]+":" + values[2] +":"+ values[3]+ ":"+ values[4] +":"+ values[5];

        UserCharacter character = SetupManager.getSimpleCharacter ();

        character.scale ( scaleFactor );
        SetupManager.addCharacter ( information,"GameHandler",character );

    }

}
