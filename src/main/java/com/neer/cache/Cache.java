package com.neer.cache;

import com.neer.Vertex;
import com.neer.util.Util;

import java.util.HashMap;
import java.util.Map;

public class Cache {

    final Map<String, String> cache = new HashMap<>();

    public String getCacheKey(Vertex v, int depth) {
        return v.getId() + "##" + depth;
    }

    public String getCacheEntry(Vertex v, int depth) {
        return cache.get(getCacheKey(v, depth));
    }

    public void putCache(String key, String value) {
        String hash = Util.getHashHex(value);
        cache.put(key, hash);
    }

    public boolean containsKey(String key) {
        return cache.containsKey(key);
    }
}
