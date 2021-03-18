package com.neer.util;

import java.util.HashMap;

public class IDGenerator {
    int id = -1;

    // Need to externalize the idCache say in a RDBMS or key value store.
    private HashMap<String, Integer> _idCache = new HashMap<>();

    public String getID(String key) {
        //System.out.println(key);
        if (_idCache.containsKey(key)) {
            return _idCache.get(key) + "";
        }
        else {
            this.id++;
            _idCache.put(key, id);
            return id+"";
        }
    }
}
