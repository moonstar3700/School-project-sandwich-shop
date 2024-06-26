package model.bestelling;



public enum BestellingEvents {
    START_BESTELLING,
    TOEVOEGEN_BROODJE,
    TOEVOEGEN_IDENTIEK_BROODJE,
    VERWIJDER_BROODJE,
    TOEVOEGEN_BELEG,
    AFSLUITEN,
    ANNULEREN,
    BETALEN,
    ZEND_NAAR_KEUKEN,
    START_BEREIDING,
    AFGEWERKT,
    WIJZIGING_VOORRAAD;


    BestellingEvents() {
    }
}
