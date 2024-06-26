package model.korting;

import model.Beleg;
import model.bestelling.Bestellijn;
import model.bestelling.Bestelling;

import java.util.List;



public class GeenKortingStrategy implements KortingStrategy {
    @Override
    public double berekenPrijs(Bestelling bestelling) {
        double prijs = 0;
        List<Bestellijn> bestellijnen = bestelling.getLijstBestellijnen();
        for (Bestellijn bestellijn: bestellijnen){
            prijs += bestellijn.getBroodje().getPrijs();
            for (Beleg beleg: bestellijn.getBeleggen()){
                prijs += beleg.getPrijs();
            }
        }
        return prijs;
    }
}
