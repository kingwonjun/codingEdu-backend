package engine;

import java.util.HashMap;
import java.util.Map;

public class Database {

    private Map<String, String> dbList = new HashMap<>();

    public void set(String key, String value) {
        dbList.put(key, value);
    }

    public String get(String key) {
        if (dbList.get(key) == null) {
            return "(nil)";
        }
        return dbList.get(key);
    }

    public boolean delete(String key) {
        if (dbList.containsKey(key)) {
            dbList.remove(key);
            return true;
        }
       return false;
    }

    public boolean exists(String key) {
        return dbList.containsKey(key);
    }

    public String count() {
        return String.valueOf(dbList.size());
    }
    public void clear() {
        dbList.clear();
    }




}
