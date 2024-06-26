package model;

import java.util.Objects;



public class Broodje {
    String naam;
    int voorraad, verkocht;
    double prijs;


    public Broodje(String naam, double prijs, int vooraad, int verkocht) {
        this.naam = naam;
        this.prijs = prijs;
        this.voorraad = vooraad;
        this.verkocht = verkocht;
    }

    public String getNaam() {
        return naam;
    }

    public double getPrijs() {
        return prijs;
    }

    public int getVoorraad() {
        return voorraad;
    }

    public int getVerkocht() {
        return verkocht;
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
        Broodje broodje = (Broodje) o;
        return Objects.equals(naam, broodje.naam);
    }

    @Override
    public int hashCode() {
        return Objects.hash(naam);
    }
}
