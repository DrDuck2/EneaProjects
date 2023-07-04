package org.example.serverlibrary;

public interface ISubject {
    void AddClient( IObserver client );

    void RemoveClient( IObserver client );

    void SendMessage( IObserver client , String message );
}
