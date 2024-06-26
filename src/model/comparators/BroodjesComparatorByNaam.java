package model.comparators;

import model.Broodje;

import java.util.Comparator;

public class BroodjesComparatorByNaam implements Comparator<Broodje> {
    @Override
    public int compare(Broodje o1, Broodje o2) {
        return o1.getNaam().compareTo(o2.getNaam());
    }
}
