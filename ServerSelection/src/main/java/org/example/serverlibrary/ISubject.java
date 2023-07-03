package org.example.serverlibrary;

import org.example.serverlibrary.IObserver;

public interface ISubject {
    void AddClient( IObserver client );

    void RemoveClient( IObserver client );

    void SendMessage( IObserver client , String message );
}
