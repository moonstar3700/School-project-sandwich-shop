package model.database.loadSaveStrategies;

import model.Beleg;
import model.database.util.TekstLoadSaveTemplate;



public class BelegTekstLoadSaveStrategy extends TekstLoadSaveTemplate implements LoadSaveStrategy{

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
    public String getLijn(Object object) {
        Beleg beleg = (Beleg) object;
        return beleg.getNaam() + ","+beleg.getPrijs()+","+beleg.getVoorraad()+","+beleg.getVoorraad() + "\n";
    }
}
