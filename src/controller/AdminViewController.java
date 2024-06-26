package controller;

import model.*;
import model.bestelling.Bestellijn;
import model.bestelling.Bestelling;
import model.bestelling.BestellingEvents;
import model.database.BelegDB;
import model.database.BroodjesDB;
import view.AdminView;

import java.util.List;
import java.util.Map;



public class AdminViewController implements Observer {
    private AdminView view;
    private BestelFacade model;


    public AdminViewController(BestelFacade model) {
        this.model = model;
        model.addObserver(this, BestellingEvents.WIJZIGING_VOORRAAD);
        model.addObserver(this, BestellingEvents.ZEND_NAAR_KEUKEN);
    }

    public void setView(AdminView view){
        this.view = view;
    }

    public List<Broodje> getBroodjes(){
        return model.getBroodjes();
    }

    public List<Beleg> getBeleggen(){
        return model.getBeleggen();
    }

    public List<Bestelling>  getStatistiek(){
        return model.getAlleVerkochte();
    }

    public Map<String, Integer> getVerkochteBroodjes(){return model.getAlleverkochteBroodjes();}

    public Map<String, Integer> getVerkochteBelegen(){return model.getAlleverkochteBelegen();}

    @Override
    public void update() {
        this.view.updateVoorraad();
    }

    public void slaVoorraadVeranderingOp() {
        model.slaVoorraadVeranderingOp();
    }
}
