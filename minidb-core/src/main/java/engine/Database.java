package engine;

import java.util.HashMap;
import java.util.Map;

public class Database {

    private Map<String, String> dbList = new HashMap<>();

    public void set(String key, String value) {
        dbList.put(key, value);
    }

    public String get(String key) {
        return dbList.get(key);
    }

    public boolean delete(String key) {
        if (dbList.containsKey(key)) {
            dbList.remove(key);
            return true;
        }
       return false;
    }

}
