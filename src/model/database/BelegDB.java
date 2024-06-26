package model.database;

import model.Beleg;
import model.Broodje;
import model.database.loadSaveStrategies.BelegExcelLoadSaveStrategy;
import model.database.loadSaveStrategies.BelegTekstLoadSaveStrategy;
import model.database.loadSaveStrategies.LoadSaveStrategy;
import model.database.loadSaveStrategies.LoadSaveStrategyFactory;

import java.io.File;
import java.io.IOException;
import java.util.*;



public class BelegDB {
    private TreeMap<String, Beleg> beleggen;

    public BelegDB(File file, String fileType) throws IOException {
        String strategyNaam = fileType.toUpperCase() + "BELEG";
        LoadSaveStrategyFactory factory = LoadSaveStrategyFactory.getInstance();
        LoadSaveStrategy loadSaveStrategy = factory.createLoadSaveStrategy(strategyNaam);
        this.beleggen = new TreeMap<>(loadSaveStrategy.load(file));
    }

    public List<Beleg> getBeleggen() {
        ArrayList<Beleg> result = new ArrayList<>();
        for (String key : beleggen.keySet()){
            result.add(beleggen.get(key));
        }
        return result;
    }

    public void save(File file, String strategy){
        String strategyNaam = strategy.toUpperCase() + "BELEG";
        LoadSaveStrategy loadSaveStrategy = LoadSaveStrategyFactory.getInstance().createLoadSaveStrategy(strategyNaam);
        loadSaveStrategy.save(file, this.beleggen);
    }

    public Map<String, Integer> getVoorraadlijstBeleg(){
        HashMap<String, Integer> map = new HashMap<>();
        for (Beleg beleg : getBeleggen()){
            map.put(beleg.getNaam(), beleg.getVoorraad());
        }
        return map;
    }

    public Beleg getBeleg(String belegnaam){
        return this.beleggen.get(belegnaam);
    }
}
