package model;

import model.bestelling.BestellingEvents;


public interface Subject {
    void addObserver(Observer observer, BestellingEvents event);
    void removeObserver(Observer observer);
    void notifyObservers(BestellingEvents event);
}
