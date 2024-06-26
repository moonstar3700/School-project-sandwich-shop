package model.database.loadSaveStrategies;

import model.Broodje;
import model.database.util.TekstLoadSaveTemplate;



public class BroodjesTekstLoadSaveStrategy extends TekstLoadSaveTemplate implements LoadSaveStrategy{
    @Override
    public Object maakObject(String[] tokens) {
        String naam = tokens[0];
        Double prijs = (Double.parseDouble(tokens[1]));
        int vooraad = (Integer.parseInt(tokens[2]));
        int verkocht = (Integer.parseInt(tokens[3]));
        Broodje broodje = new Broodje(naam, prijs, vooraad, verkocht);
        return broodje;
    }

    @Override
    public Object getKey(String[] tokens) {
        return tokens[0];
    }

    @Override
    public String getLijn(Object object) {
        Broodje broodje = (Broodje) object;
        return broodje.getNaam() + ","+broodje.getPrijs()+","+broodje.getVoorraad()+","+broodje.getVoorraad() + "\n";
    }
}
