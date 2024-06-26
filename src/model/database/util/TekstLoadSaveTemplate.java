package model.database.util;

import java.io.*;
import java.util.HashMap;
import java.util.Map;



public abstract class TekstLoadSaveTemplate <K,V> {

    public final Map<K,V> load(File file) throws IOException {
        Map<K,V> returnMap = new HashMap<K,V>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))){
            String line = reader.readLine();
            while (line != null && !line.trim().equals("")) {
                String[] tokens = line.split(",");
                V element = maakObject(tokens);
                K key = getKey(tokens);
                returnMap.put(key,element);
                line = reader.readLine();
            }
        }
        return returnMap;
    }

    public final void save(File file, Map<K,V> data) {
        try {
            PrintWriter writer = new PrintWriter(file);
            String line = null;
            for (K s : data.keySet()) {
                line = getLijn(data.get(s));
                writer.write(line);
            }
            writer.close();
        } catch (FileNotFoundException exc) {
            throw new IllegalStateException("File not found");
        }
    }

    public abstract V maakObject(String[] tokens);

    public abstract K getKey(String[] tokens);

    public abstract String getLijn(Object object);
}

