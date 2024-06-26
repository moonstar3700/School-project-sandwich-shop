package model.database.loadSaveStrategies;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;



public interface LoadSaveStrategy <K,V> {
    Map<K,V> load(File file) throws IOException;
    void save(File file, Map<K,V> data);
}
