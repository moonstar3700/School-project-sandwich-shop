package model;

import java.util.Objects;



public class Beleg {
    String naam;
    int voorraad, verkocht;
    double prijs;

    public Beleg(String naam, double prijs, int vooraad, int verkocht) {
        this.naam = naam;
        this.voorraad = vooraad;
        this.verkocht = verkocht;
        this.prijs = prijs;
    }

    public String getNaam() {
        return naam;
    }

    public int getVoorraad() {
        return voorraad;
    }

    public int getVerkocht() {
        return verkocht;
    }

    public double getPrijs() {
        return prijs;
    }

    @Override
    public String toString() {
        return this.naam + " " + prijs + " " + voorraad;
    }

    public void aanpassenVoorraad(int aantal){
        this.voorraad = this.voorraad - aantal;
        this.verkocht = this.verkocht + aantal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Beleg beleg = (Beleg) o;
        return Objects.equals(naam, beleg.naam);
    }

    @Override
    public int hashCode() {
        return Objects.hash(naam);
    }
}
