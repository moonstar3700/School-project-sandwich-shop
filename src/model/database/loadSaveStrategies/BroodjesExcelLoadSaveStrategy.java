package model.database.loadSaveStrategies;

import model.Broodje;
import model.database.util.ExcelLoadSaveTemplate;

import java.util.ArrayList;



public class BroodjesExcelLoadSaveStrategy extends ExcelLoadSaveTemplate implements LoadSaveStrategy {
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
    public ArrayList<String> getParts(Object object) {
        Broodje broodje = (Broodje) object;
        ArrayList<String> parts = new ArrayList<>();
        parts.add(broodje.getNaam());
        parts.add("" + broodje.getPrijs());
        parts.add("" + broodje.getVoorraad());
        parts.add("" + broodje.getVerkocht());
        return parts;
    }
}
