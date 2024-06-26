package model.database;

import model.Broodje;
import model.database.loadSaveStrategies.*;

import java.io.File;
import java.io.IOException;
import java.util.*;



public class BroodjesDB {
    private TreeMap<String, Broodje> broodjes;

    public BroodjesDB(File file, String fileType) throws IOException {
        String strategyNaam = fileType.toUpperCase() + "BROODJE";
        LoadSaveStrategy loadSaveStrategy = LoadSaveStrategyFactory.getInstance().createLoadSaveStrategy(strategyNaam);
        this.broodjes = new TreeMap<>(loadSaveStrategy.load(file));
    }

    public List<Broodje> getBroodjes() {
        ArrayList<Broodje> result = new ArrayList<>();
        for (String key : broodjes.keySet()){
            result.add(broodjes.get(key));
        }
        return result;
    }

    public void save(File file, String strategy){
        String strategyNaam = strategy.toUpperCase() + "BROODJE";
        LoadSaveStrategy loadSaveStrategy = LoadSaveStrategyFactory.getInstance().createLoadSaveStrategy(strategyNaam);
        loadSaveStrategy.save(file, this.broodjes);
    }

    public Broodje getBroodje(String broodje){
        return broodjes.get(broodje);
    }

    public Map<String, Integer> getVoorraadlijstBroodjes(){
        HashMap<String, Integer> map = new HashMap<>();
        for (Broodje broodje : getBroodjes()){
            map.put(broodje.getNaam(), broodje.getVoorraad());
        }
        return map;
    }

    public Map<String, Integer> getVoorraadlijstBroodje(String broodjesNaam){
        HashMap<String, Integer> map = new HashMap<>();
        Broodje broodje = getBroodje(broodjesNaam);
        map.put(broodjesNaam, broodje.getVoorraad());
        return map;
    }

    public void updateBroodjesVoorraad(Broodje broodje){
        this.broodjes.put(broodje.getNaam(), broodje);
    }

    public boolean checkOfErGenoegVoorraadIs(String broodjesnaam) {
        Broodje broodje = getBroodje(broodjesnaam);
        if (broodje.getVoorraad() < 1){
            return false;
        } else {
            return true;
        }
    }
}
