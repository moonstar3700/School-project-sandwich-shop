package controller;

import javafx.beans.Observable;
import model.BestelFacade;
import model.Observer;
import model.bestelling.Bestelling;
import model.bestelling.BestellingEvents;
import view.KitchenView;
import view.OrderView;

import java.util.LinkedList;



public class KitchenViewController implements Observer {
    private KitchenView view;
    private BestelFacade model;

    public KitchenViewController(BestelFacade facade) {
        this.model = facade;
        model.addObserver(this, BestellingEvents.ZEND_NAAR_KEUKEN);
        model.addObserver(this, BestellingEvents.AFGEWERKT);
        model.addObserver(this, BestellingEvents.START_BEREIDING);
    }

    public void setView(KitchenView view){this.view = view;}

    @Override
    public void update() {
        view.huidigeBestellingAfgewerkt();
        view.updateWachtrij(model.getRijLengte());
    }

    public Bestelling getEersteBestelling(){
        return model.getEersteBestellingUitWachtrij();
    }

    public void werkBestellingAf() {
        model.werkEersteBestellingAf();
    }
}
