package model.database.loadSaveStrategies;



public enum LoadSaveStrategyEnum {
    EXCELBROODJE("model.database.loadSaveStrategies.BroodjesExcelLoadSaveStrategy", "BroodjesExcelLoadSaveStrategy"),
    TEKSTBROODJE("model.database.loadSaveStrategies.BroodjesTekstLoadSaveStrategy", "BroodjesTekstLoadSaveStrategy"),
    EXCELBELEG("model.database.loadSaveStrategies.BelegExcelLoadSaveStrategy", "BelegExcelLoadSaveStrategy"),
    TEKSTBELEG("model.database.loadSaveStrategies.BelegTekstLoadSaveStrategy", "BelegTekstLoadSaveStrategy");

    private final String path;
    private final String className;

    LoadSaveStrategyEnum(String path, String className) {
        this.path = path;
        this.className = className;
    }

    public String getPath() {
        return path;
    }

    public String getClassName() {
        return className;
    }
}
