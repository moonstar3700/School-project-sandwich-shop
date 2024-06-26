package model.bestelling.state;



public interface BestellingState {
    void startBestelling();
    void toevoegenBroodje();
    void verwijderBroodje();
    void toevoegenIdentiekBroodje();
    void toevoegenBeleg();
    void afsluiten();
    void annuleren();
    void betalen();
    void zendNaarKeuken();
    void startBereiding();
    void afgewerkt();

}
