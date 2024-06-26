package model.comparators;

import model.Beleg;

import java.util.Comparator;

public class BelegComparatorByNaam implements Comparator<Beleg> {
    @Override
    public int compare(Beleg o1, Beleg o2) {
        return o1.getNaam().compareTo(o2.getNaam());
    }
}
