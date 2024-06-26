package model.database.loadSaveStrategies;



public class LoadSaveStrategyFactory {
    private static LoadSaveStrategyFactory uniqueInstance;

    private LoadSaveStrategyFactory() {
    }

    public static LoadSaveStrategyFactory getInstance(){
        if (uniqueInstance == null){
            uniqueInstance = new LoadSaveStrategyFactory();
        }
        return uniqueInstance;
    }

    public LoadSaveStrategy createLoadSaveStrategy(String strategyNaam){
        LoadSaveStrategyEnum strategyEnum = LoadSaveStrategyEnum.valueOf(strategyNaam);
        String classPath = strategyEnum.getPath();
        LoadSaveStrategy strategy = null;
        try {
            Class dbClass = Class.forName(classPath);
            Object dbObject = dbClass.newInstance();
            strategy = (LoadSaveStrategy) dbObject;
        } catch (Exception e){
            throw new IllegalStateException("Problem with the factory");
        }
        return strategy;
    }
}