package model.korting;

import model.database.loadSaveStrategies.LoadSaveStrategyFactory;



public class KortingFactory {
    private static KortingFactory uniqueInstance;

    private KortingFactory(){
    }

    public static KortingFactory getInstance(){
        if (uniqueInstance == null){
            uniqueInstance = new KortingFactory();
        }
        return uniqueInstance;
    }

    public KortingStrategy createKortingStrategy(String strategyNaam){
        KortingEnum kortingEnum = KortingEnum.valueOf(strategyNaam);
        String classPath = kortingEnum.getPath();
        KortingStrategy strategy = null;
        try {
            Class kort = Class.forName(classPath);
            Object KortingObject = kort.newInstance();
            strategy = (KortingStrategy) KortingObject;
        }catch (Exception e){
            throw new IllegalStateException("Problem with the korting factory");
        }
        return strategy;
    }
}
