package model.database.util;

import jxl.read.biff.BiffException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



public abstract class ExcelLoadSaveTemplate<K,V> {

    public final Map<K,V> load(File file) throws IOException {
        ExcelPlugin plugin = new ExcelPlugin();
        Map<K, V> returnMap = new HashMap<K,V>();
        ArrayList<ArrayList<String>> input;
        try {
            input = plugin.read(file);
            String[] tokens = null;
            for (ArrayList<String> lijn : input){
                tokens = new String[lijn.size()];
                for (int i = 0; i != tokens.length; i++){
                    tokens[i] = lijn.get(i);
                }
                V element = maakObject(tokens);
                K key = getKey(tokens);
                returnMap.put(key, element);
            }
        } catch (BiffException e){
            throw new IOException(e.getMessage());
        }
        return returnMap;
    }

    public final void save(File file, Map<K, V> data){
        ArrayList<ArrayList<String>> lines = new ArrayList<>();
        for (K key : data.keySet()){
            ArrayList<String> line = getParts(data.get(key));
            lines.add(line);
        }
        ExcelPlugin plugin = new ExcelPlugin();
        try {
            plugin.write(file, lines);
        } catch (Exception e){
            throw new IllegalStateException("File not found");
        }
    }

    public abstract V maakObject(String[] tokens);

    public abstract K getKey(String[] tokens);

    public abstract ArrayList<String> getParts(Object object);
}
