package org.example.clientLibrary.windowManager;

import org.example.clientLibrary.windowLibrary.GameScreenHandling.UserCharacter;

public class CommunicationManager {
    public static UserCharacter userCharacter = null;
    public static String sendInformation = "";
    public synchronized static void addInformation( float offsetLeft , float offsetRight , float offsetDown , float offsetUp ) {
        if ( userCharacter != null && !sendInformation.equals ( Integer.toHexString ( userCharacter.hashCode () )+":"+"Bye" )) {
            String id = Integer.toHexString ( userCharacter.hashCode () );
            sendInformation = id + ":" + userCharacter.getScaleFactor () + ":" + offsetLeft + ":" + offsetRight + ":" + offsetDown + ":" + offsetUp;
        } else {
            throw new NullPointerException ();
        }
    }

    public synchronized  static void closeConnection(){
        String id = Integer.toHexString ( userCharacter.hashCode () );
        sendInformation = id+":"+"Bye";
    }

    public synchronized static void setReceivedInformation( String receivedInformation ) {
        if(receivedInformation.split ( ":" )[1].equals ( "Disconnected" )){
            SetupManager.removeCharacter(receivedInformation.split ( ":" )[0],"GameHandler");
        }else{
            String[] values = receivedInformation.split ( ":" );
            float scaleFactor = Float.parseFloat ( values[1] );

            String information = values[0] + ":" + values[2] + ":" + values[3] + ":" + values[4] + ":" + values[5];

            UserCharacter character = SetupManager.getSimpleCharacter ();

            character.scale ( scaleFactor );
            SetupManager.addCharacter ( information , "GameHandler" , character );
        }
    }

}
