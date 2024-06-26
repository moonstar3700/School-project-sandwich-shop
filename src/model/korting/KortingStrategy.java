package model.korting;

import model.bestelling.Bestelling;



public interface KortingStrategy {
    double berekenPrijs(Bestelling bestelling);
}
