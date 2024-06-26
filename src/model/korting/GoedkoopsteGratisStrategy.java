package model.korting;

import model.Beleg;
import model.bestelling.Bestellijn;
import model.bestelling.Bestelling;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



public class GoedkoopsteGratisStrategy implements KortingStrategy{
    @Override
    public double berekenPrijs(Bestelling bestelling) {
        double totprijs = 0;
        List<Double> lijst = new ArrayList<Double>();
        List<Bestellijn> bestellijnen = bestelling.getLijstBestellijnen();
        for (Bestellijn bestellijn: bestellijnen){
            double prijs = 0;
            prijs += bestellijn.getBroodje().getPrijs();
            for (Beleg beleg: bestellijn.getBeleggen()){
                prijs += beleg.getPrijs();
            }
            lijst.add(prijs);
        }
        if (lijst.size() == 1){
            return lijst.get(0);
        }
        Collections.sort(lijst);
        for (int i = 1; i <= lijst.size()-1; i++){
            totprijs += lijst.get(i);
        }
        return totprijs;
    }
}
