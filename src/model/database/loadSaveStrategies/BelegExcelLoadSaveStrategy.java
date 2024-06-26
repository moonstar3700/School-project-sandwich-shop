package model.database.loadSaveStrategies;

import model.Beleg;
import model.database.util.ExcelLoadSaveTemplate;

import java.util.ArrayList;



public class BelegExcelLoadSaveStrategy extends ExcelLoadSaveTemplate implements LoadSaveStrategy {
    @Override
    public Object maakObject(String[] tokens) {
        String naam = tokens[0];
        Double prijs = (Double.parseDouble(tokens[1]));
        int vooraad = (Integer.parseInt(tokens[2]));
        int verkocht = (Integer.parseInt(tokens[3]));

        Beleg beleg = new Beleg(naam, prijs, vooraad, verkocht);
        return beleg;
    }

    @Override
    public Object getKey(String[] tokens) {
        return tokens[0];
    }

    @Override
    public ArrayList<String> getParts(Object object) {
        Beleg beleg = (Beleg) object;
        ArrayList<String> parts = new ArrayList<>();
        parts.add(beleg.getNaam());
        parts.add("" + beleg.getPrijs());
        parts.add("" + beleg.getVoorraad());
        parts.add("" + beleg.getVerkocht());
        return parts;
    }
}
