package model.bestelling;

import model.Beleg;
import model.Broodje;
import model.bestelling.state.*;
import model.database.BelegDB;
import model.database.BroodjesDB;
import model.database.DbException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class Bestelling {
    static int volgnr = 0;
    private int volgnummer;
    private BestellingState inWacht;
    private BestellingState inBestelling;
    private BestellingState afgesloten;
    private BestellingState betaald;
    private BestellingState inWachtrij;
    private BestellingState inBereiding;
    private List<Bestellijn> bestellijnen;

    private BestellingState state;


    public Bestelling() {
        this.volgnummer = volgnr;
        volgnr++;
        this.bestellijnen = new ArrayList<>(0);

        inWacht = new InWacht(this);
        inBestelling = new InBestelling(this);
        afgesloten = new Afgesloten(this);
        betaald = new Betaald(this);
        inWachtrij = new InWachtrij(this);
        inBereiding = new InBereiding(this);
        this.state = inWacht;
    }

    public int getVolgnr() {
        return volgnr;
    }

    public List getLijstBestellijnen(){
        return this.bestellijnen;
    }

    public void voegBestellijnToe(String naamBroodje, BroodjesDB broodjesDB) throws IOException {
        Bestellijn bestellijn = new Bestellijn(naamBroodje, broodjesDB);
        this.bestellijnen.add(bestellijn);
        state.toevoegenBroodje();
    }

    public void voegBelegToeAanBestellijn(Bestellijn bestellijn, String beleg, BelegDB belegDB) {
        bestellijn.voegBelegToe(beleg, belegDB);
        state.toevoegenBeleg();
    }

    public void verwijderBestellijn(Bestellijn bestellijn) {
        bestellijn.maakKlaarOmVerwijderdTeWorden();
        bestellijnen.remove(bestellijn);
        state.verwijderBroodje();
    }

    public void voegZelfdeBroodjeToe(Bestellijn bestellijn, BroodjesDB broodjesDB, BelegDB belegDB) throws IOException {
        Broodje broodje = bestellijn.getBroodje();
        if (broodje.getVoorraad() < 1){
            throw new DbException("Geen broodje meer in voorraad");
        }
        HashMap<String, Integer> belegAantal = new HashMap<>();
        for (Beleg beleg : bestellijn.getBeleggen()){
            if (belegAantal.containsKey(beleg.getNaam())){
                belegAantal.put(beleg.getNaam(), belegAantal.get(beleg.getNaam()) + 1);
            } else {
                belegAantal.put(beleg.getNaam(), 1);
            }
        }
        for (String belegnaam : belegAantal.keySet()){
            Beleg beleg = belegDB.getBeleg(belegnaam);
            if (beleg.getVoorraad() < belegAantal.get(belegnaam)){
                throw new DbException("Geen beleg genoeg");
            }
        }
        Bestellijn dubbeleBestellijn = new Bestellijn(bestellijn.getNaamBroodje(), broodjesDB);
        for (Beleg beleg : bestellijn.getBeleggen()){
            dubbeleBestellijn.voegBelegToe(beleg.getNaam(), belegDB);
        }
        this.bestellijnen.add(dubbeleBestellijn);
        state.toevoegenIdentiekBroodje();
    }

    public BestellingState getState() {
        return state;
    }

    public void changeState(BestellingState state){
        this.state = state;
    }

    public int getVolgnummer() {
        return volgnummer;
    }

    public BestellingState getInWacht() {
        return inWacht;
    }

    public BestellingState getInBestelling() {
        return inBestelling;
    }

    public BestellingState getAfgesloten() {
        return afgesloten;
    }

    public BestellingState getBetaald() {
        return betaald;
    }

    public BestellingState getInWachtrij() {
        return inWachtrij;
    }

    public BestellingState getInBereiding() {
        return inBereiding;
    }

    public List<Bestellijn> getBestellijnen() {
        return bestellijnen;
    }

    public String getBestellingAsString(){
        Map<String, Integer> countMap = new HashMap<>();
        for (Bestellijn bestellijn : bestellijnen){
            if (countMap.containsKey(bestellijn.getBestellijnAsString())){
                countMap.put(bestellijn.getBestellijnAsString(), countMap.get(bestellijn.getBestellijnAsString()) + 1);
            } else {
                countMap.put(bestellijn.getBestellijnAsString(), 1);
            }
        }
        String result = "";
        for (String key : countMap.keySet()){
            int aantal = countMap.get(key);
            result += aantal + " x " + key + "\n";
        }
        return result;
    }
}
