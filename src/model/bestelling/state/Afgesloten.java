package model.bestelling.state;

import model.bestelling.Bestelling;



public class Afgesloten implements BestellingState {
    Bestelling bestelling;

    public Afgesloten(Bestelling bestelling) {
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
        System.out.println("Bestelling is geannuleerd");
    }

    @Override
    public void betalen() {
        System.out.println("Bestelling is betaald");
        bestelling.changeState(bestelling.getBetaald());
    }

    @Override
    public void zendNaarKeuken() {
        System.out.println("doet niets");
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
