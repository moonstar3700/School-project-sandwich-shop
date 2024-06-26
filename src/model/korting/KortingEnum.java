package model.korting;



public enum KortingEnum {
    GEENKORTING("model.korting.GeenKortingStrategy", "GeenKortingStrategy"),
    GOEDKOOPSTEGRATIS("model.korting.GoedkoopsteGratisStrategy", "GoedkoopsteGratisStrategy"),
    TIENPERCENTKORTING("model.korting.TienPercentKortingStrategy", "TienPercentKortingStrategy");

    private final String path;
    private final String classname;

    KortingEnum(String path, String className) {
        this.path = path;
        this.classname = className;
    }

    public String getPath() {
        return path;
    }

    public String getClassname() {
        return classname;
    }
}
