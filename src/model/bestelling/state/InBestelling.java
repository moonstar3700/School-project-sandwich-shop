package model.bestelling.state;

import model.bestelling.Bestelling;



public class InBestelling implements BestellingState {
    Bestelling bestelling;

    public InBestelling(Bestelling bestelling) {
        this.bestelling = bestelling;
    }

    @Override
    public void startBestelling() {
        System.out.println("doet niets");
    }

    @Override
    public void toevoegenBroodje() {
        System.out.println("Nieuw broodje toegevoegd aan bestelling met volg nummer: " + bestelling.getVolgnr());
    }

    @Override
    public void verwijderBroodje() {
        System.out.println("Broodje verwijderd uit bestelling met volg nummer: " + bestelling.getVolgnr());
    }

    @Override
    public void toevoegenIdentiekBroodje() {
        System.out.println("Identiek broodje toegevoegd aan bestelling met volg nummer: " + bestelling.getVolgnr());
    }

    @Override
    public void toevoegenBeleg() {
        System.out.println("Nieuw beleg toegevoegd aan broodje bij bestelling met volg nummer: " + bestelling.getVolgnr());
    }

    @Override
    public void afsluiten() {
        System.out.println("Bestelling wordt afgesloten");
        bestelling.changeState(bestelling.getAfgesloten());
    }

    @Override
    public void annuleren() {
        System.out.println("Bestelling is geannuleerd");
    }

    @Override
    public void betalen() {
        System.out.println("doet niets");
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
