package model;

import model.bestelling.Bestellijn;
import model.bestelling.Bestelling;
import model.bestelling.BestellingEvents;
import model.database.BelegDB;
import model.database.BroodjesDB;
import model.korting.KortingEnum;
import model.korting.KortingFactory;
import model.korting.KortingStrategy;

import java.io.*;
import java.util.*;


public class BestelFacade implements Subject {
    private BelegDB belegDB;
    private BroodjesDB broodjesDB;
    private Map<BestellingEvents, List<Observer>> observers;
    private List<Bestelling> bestellingen;
    private List<Bestelling> alleVerkochte = new ArrayList<>();
    private LinkedList<Bestelling> wachtrij;
    private int rijLengte;


    public BestelFacade() throws IOException {
        Properties properties = new Properties();
        InputStream is = new FileInputStream("src/bestanden/settings.properties");
        properties.load(is);
        String broodjesFileLocatie = properties.getProperty("broodjesFile");
        String belegFileLocatie = properties.getProperty("belegFile");
        File broodjesFile = new File(broodjesFileLocatie);
        File belegFile = new File(belegFileLocatie);
        String fileType = properties.getProperty("fileType");
        is.close();

        this.belegDB = new BelegDB(belegFile, fileType);
        this.broodjesDB = new BroodjesDB(broodjesFile, fileType);
        this.observers = new HashMap<>();
        for (BestellingEvents event : BestellingEvents.values()){
            this.observers.put(event, new ArrayList<>());
        }
        this.bestellingen = new ArrayList<>();
        this.wachtrij = new LinkedList<>();
    }

    @Override
    public void addObserver(Observer observer, BestellingEvents event) {
        this.observers.get(event).add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        for (BestellingEvents events : observers.keySet()){
            this.observers.get(events).remove(observer);
        }
    }

    @Override
    public void notifyObservers(BestellingEvents event) {
        for (Observer observer : this.observers.get(event)){
            observer.update();
        }
    }

    public List<Broodje> getBroodjes(){
        return broodjesDB.getBroodjes();
    }

    public List<Beleg> getBeleggen(){
        return belegDB.getBeleggen();
    }

    public List<Bestelling> getBestellingen() {
        return bestellingen;
    }

    public List<Bestellijn> getLijstBestellijnen(Bestelling bestelling){
        return bestelling.getLijstBestellijnen();
    }

    public LinkedList<Bestelling> getWachtrij() {
        return wachtrij;
    }

    public void voegBestellijnToe(String broodje, Bestelling bestelling) throws IOException {
        bestelling.voegBestellijnToe(broodje, broodjesDB);

        notifyObservers(BestellingEvents.TOEVOEGEN_BROODJE);
    }

    public Map<String, Integer> getVoorraadlijstBroodjes(){
        return broodjesDB.getVoorraadlijstBroodjes();
    }

    public BelegDB getBelegDB() {
        return belegDB;
    }

    public BroodjesDB getBroodjesDB() {
        return broodjesDB;
    }

    public void voegBelegToeAanBestellijn(Bestellijn bestellijn, String beleg, Bestelling bestelling) {
        bestelling.voegBelegToeAanBestellijn(bestellijn, beleg, belegDB);

        notifyObservers(BestellingEvents.TOEVOEGEN_BELEG);
    }

    public Map<String, Integer> getVoorraadlijstBeleg(){
        return belegDB.getVoorraadlijstBeleg();
    }

    public void verwijderBestellijn(Bestellijn bestellijn, Bestelling bestelling) {
        bestelling.verwijderBestellijn(bestellijn);

        notifyObservers(BestellingEvents.VERWIJDER_BROODJE);
    }

    public void verwijderBestelling(Bestelling bestelling) {
        bestelling.getState().annuleren();
        Iterator<Bestellijn> iterator = getLijstBestellijnen(bestelling).iterator();
        while (iterator.hasNext()){
            Bestellijn bestellijn = iterator.next();
            bestellijn.maakKlaarOmVerwijderdTeWorden();
            iterator.remove();
        }
        notifyObservers(BestellingEvents.VERWIJDER_BROODJE);
    }

    public void voegZelfdeBroodjeToe(Bestellijn bestellijn, Bestelling bestelling) throws IOException {
        bestelling.voegZelfdeBroodjeToe(bestellijn, broodjesDB, belegDB);

        notifyObservers(BestellingEvents.TOEVOEGEN_BELEG);
    }

    public Bestelling getBestellingByVolgnummer(int volgnummer){
        Bestelling bestelling = null;
        for (Bestelling b : bestellingen){
            if (b.getVolgnr() == volgnummer){
                bestelling = b;
            }
        }
        return bestelling;
    }

    public Bestelling voegBestellingToe() {
        Bestelling bestelling = new Bestelling();
        this.bestellingen.add(bestelling);
        return bestelling;
    }

    public double berekenPrijs(Bestelling bestelling, String promotie){
        KortingStrategy kortingStrategy = KortingFactory.getInstance().createKortingStrategy(promotie);
        return kortingStrategy.berekenPrijs(bestelling);
    }

    public void bestellingNaarKeuken(Bestelling bestelling){
        bestelling.getState().zendNaarKeuken();
        wachtrij.add(bestelling);
        alleVerkochte.add(bestelling);
        notifyObservers(BestellingEvents.WIJZIGING_VOORRAAD);
        notifyObservers(BestellingEvents.ZEND_NAAR_KEUKEN);
    }

    public int getRijLengte(){
        return wachtrij.size();
    }

    public void startBestelling(Bestelling bestelling) {
        bestelling.getState().startBestelling();
    }

    public void sluitBestellingAf(Bestelling bestelling) {
        bestelling.getState().afsluiten();
    }

    public void betaalBestelling(Bestelling bestelling) {
        bestelling.getState().betalen();
    }

    public Bestelling getEersteBestellingUitWachtrij() {
        if (wachtrij.size() > 0){
            Bestelling bestelling = wachtrij.get(0);
            bestelling.getState().startBereiding();
            return bestelling;
        }
        return null;
    }

    public void werkEersteBestellingAf() {
        if (wachtrij.size() > 0){
            Bestelling bestelling = wachtrij.get(0);
            bestelling.getState().afgewerkt();
            if (bestelling.getState().equals(bestelling.getInBereiding())) {
                wachtrij.remove(0);
                notifyObservers(BestellingEvents.AFGEWERKT);
            }
        }
    }

    public List<Bestelling> getAlleVerkochte() {
        return alleVerkochte;
    }

    public Map<String, Integer> getAlleverkochteBroodjes(){
        Map<String, Integer> verkochteBroodjes = new HashMap<>();
        ArrayList<Broodje> broodjes = (ArrayList<Broodje>) broodjesDB.getBroodjes();
        for (Broodje broodje : broodjes){
            if (broodje.getVerkocht() != 0){
                verkochteBroodjes.put(broodje.getNaam(), broodje.getVerkocht());
            }
        }
        return verkochteBroodjes;
    }
    public Map<String, Integer> getAlleverkochteBelegen() {
        Map<String, Integer> verkochteBelegen = new HashMap<>();
        ArrayList<Beleg> beleggen = (ArrayList<Beleg>) belegDB.getBeleggen();
        for (Beleg beleg : beleggen){
            if (beleg.getVerkocht() != 0){
                verkochteBelegen.put(beleg.getNaam(), beleg.getVerkocht());
            }
        }
        return verkochteBelegen;
    }
    public void slaVoorraadVeranderingOp(){
        Properties properties = new Properties();
        try {
            InputStream is = new FileInputStream("src/bestanden/settings.properties");
            properties.load(is);
            String broodjesFileLocatie = properties.getProperty("broodjesFile");
            String belegFileLocatie = properties.getProperty("belegFile");
            File broodjesFile = new File(broodjesFileLocatie);
            File belegFile = new File(belegFileLocatie);
            String fileType = properties.getProperty("fileType");

            belegDB.save(belegFile, fileType);
            broodjesDB.save(broodjesFile, fileType);

            is.close();
        } catch (Exception e){
            System.out.println("Fout met properties");
        }
    }
}
