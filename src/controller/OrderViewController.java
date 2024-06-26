package controller;

import model.Beleg;
import model.BestelFacade;
import model.Broodje;
import model.Observer;
import model.bestelling.Bestellijn;
import model.bestelling.Bestelling;
import model.bestelling.BestellingEvents;
import view.OrderView;

import java.io.IOException;
import java.util.List;
import java.util.Map;



public class OrderViewController implements Observer {
    private OrderView view;
    private BestelFacade model;

    public OrderViewController(BestelFacade facade) {
        this.model = facade;
        model.addObserver(this, BestellingEvents.TOEVOEGEN_BROODJE);
        model.addObserver(this, BestellingEvents.TOEVOEGEN_BELEG);
        model.addObserver(this, BestellingEvents.VERWIJDER_BROODJE);
        model.addObserver(this, BestellingEvents.ZEND_NAAR_KEUKEN);
    }

    public void setView(OrderView view){
        this.view = view;
    }

    public List<Broodje> getBroodjes(){
        return model.getBroodjes();
    }

    public List<Beleg> getBeleggen(){
        return model.getBeleggen();
    }

    public void voegBestellijnToe(String broodje, Bestelling bestelling) throws IOException {
        model.voegBestellijnToe(broodje, bestelling);
    }

    @Override
    public void update() {
        List<Bestellijn> bestellijnen = model.getLijstBestellijnen(model.getBestellingByVolgnummer(view.getVolgnr()));
        view.updateBestellijnen(bestellijnen);
        Map<String, Integer> voorraadLijstBroodjes = model.getVoorraadlijstBroodjes();
        view.updateBroodjesKnoppen(voorraadLijstBroodjes);
        Map<String, Integer> voorraadLijstBeleg = model.getVoorraadlijstBeleg();
        view.updateBelegKnoppen(voorraadLijstBeleg);
    }

    public void voegBelegToeAanBestellijn(Bestellijn bestellijn, String beleg, Bestelling bestelling) {
        model.voegBelegToeAanBestellijn(bestellijn, beleg, bestelling);
    }

    public void verwijderBestellijn(Bestellijn bestellijn, Bestelling bestelling) {
        model.verwijderBestellijn(bestellijn, bestelling);
    }

    public void verwijderBestelling(Bestelling bestelling) {
        model.verwijderBestelling(bestelling);
    }

    public void voegZelfdeBroodjeToe(Bestellijn bestellijn, Bestelling bestelling) throws IOException {
        model.voegZelfdeBroodjeToe(bestellijn, bestelling);
    }

    public Bestelling voegBestellingToe() {
        return model.voegBestellingToe();
    }

    public Bestelling getBestelling(int volgnr) {
        return model.getBestellingByVolgnummer(volgnr);
    }

    public double berekenPrijs(Bestelling bestelling, String promotie){ return model.berekenPrijs(bestelling, promotie);}

    public void naarKeuken(Bestelling bestelling){
        model.bestellingNaarKeuken(bestelling);
    }

    public void startBestelling(Bestelling bestelling) {
        view.updateBestellijnen(model.getLijstBestellijnen(bestelling));
        model.startBestelling(bestelling);
    }

    public void sluitBestellingAf(Bestelling bestelling) {
        model.sluitBestellingAf(bestelling);
    }

    public void betaalBestelling(Bestelling bestelling) {
        model.betaalBestelling(bestelling);
    }
}
