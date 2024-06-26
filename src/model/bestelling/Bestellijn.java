package model.bestelling;

import model.Beleg;
import model.Broodje;
import model.database.BelegDB;
import model.database.BroodjesDB;

import java.io.File;
import java.io.IOException;
import java.util.*;



public class Bestellijn {
    private String naamBroodje, namenBeleg;
    private Broodje broodje;
    private List<Beleg> beleggen;
    private BroodjesDB broodjesDB;
    private BelegDB belegDB;

    public Bestellijn(String naamBroodje, BroodjesDB broodjesDB) throws IOException {
        this.naamBroodje = naamBroodje;
        this.broodjesDB = broodjesDB;
        broodjesDB.getBroodje(naamBroodje).aanpassenVoorraad(1);
        this.broodje = broodjesDB.getBroodje(naamBroodje);
        this.beleggen = new ArrayList<>();
    }

    public String getNaamBroodje() {
        return naamBroodje;
    }

    public String getNamenBeleg(){
        String result = "";
        for (Beleg beleg: beleggen) {
            result += beleg.getNaam() + ", ";
        }
        if (!result.equals("")){
            result = result.replaceAll(", $", "");
        }
        this.namenBeleg = result;
        return result;
    }

    public List<Beleg> getBeleggen() {
        return beleggen;
    }

    public void voegBelegToe(String belegnaam, BelegDB belegDB){
        this.belegDB = belegDB;
        Beleg beleg = belegDB.getBeleg(belegnaam);
        this.beleggen.add(beleg);
        beleg.aanpassenVoorraad(1);
    }

    public void maakKlaarOmVerwijderdTeWorden(){
        this.broodje.aanpassenVoorraad(-1);
        for (Beleg beleg : beleggen){
            beleg.aanpassenVoorraad(-1);
        }
    }

    public Broodje getBroodje() {
        return broodje;
    }

    public String getBestellijnAsString(){
        String result = naamBroodje + ":";
        Map<String, Integer> countMap = new HashMap<>();
        for (Beleg beleg : beleggen){
            if (countMap.containsKey(beleg.getNaam())){
                countMap.put(beleg.getNaam(), countMap.get(beleg.getNaam()) + 1);
            } else {
                countMap.put(beleg.getNaam(), 1);
            }
        }
        for (String key : countMap.keySet()){
            int aantal = countMap.get(key);
            if (aantal == 1){
                result += " " + key + ",";
            } else {
                result += " " + aantal + " x " + key + ",";
            }
        }
        result = result.replaceAll(",$", "");
        return result;
    }

    @Override
    public String toString() {
        return "Bestellijn{" +
                "naamBroodje='" + naamBroodje + '\'' +
                ", namenBeleg='" + namenBeleg + '\'' +
                ", broodje=" + broodje +
                ", beleggen=" + beleggen +
                ", broodjesDB=" + broodjesDB +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        Bestellijn b2 = (Bestellijn) o;
        return b2.getNaamBroodje().equals(this.getNaamBroodje()) && b2.getNamenBeleg().equals(this.getNamenBeleg());
    }

}
