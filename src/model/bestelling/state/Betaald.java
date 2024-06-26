package model.bestelling.state;

import model.bestelling.Bestelling;

public class Betaald implements BestellingState {
    Bestelling bestelling;

    public Betaald(Bestelling bestelling) {
        this.bestelling = bestelling;
    }

    @Override
    public void startBestelling() {
        System.out.println("doet niets");
    }

    @Override
    public void toevoegenBroodje() {
        System.out.println("doet niets");
    }

    @Override
    public void verwijderBroodje() {
        System.out.println("doet niets");
    }

    @Override
    public void toevoegenIdentiekBroodje() {
        System.out.println("doet niets");
    }

    @Override
    public void toevoegenBeleg() {
        System.out.println("doet niets");
    }

    @Override
    public void afsluiten() {
        System.out.println("doet niets");
    }

    @Override
    public void annuleren() {
        System.out.println("doet niets");
    }

    @Override
    public void betalen() {
        System.out.println("doet niets");
    }

    @Override
    public void zendNaarKeuken() {
        System.out.println("Bestelling is naar de keuken verzonden");
        bestelling.changeState(bestelling.getInWachtrij());
    }

    @Override
    public void startBereiding() {
        System.out.println("doet niets");
    }

    @Override
    public void afgewerkt() {
        System.out.println("doet niets");
    }
}
