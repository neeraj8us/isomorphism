package com.neer.algo;

import com.neer.Graph;
import com.neer.Vertex;
import com.neer.cache.Cache;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class IsoMorphicSignature {

    Cache cache = new Cache();
    public String getNestedGraphSignature(Graph g) {
        cache = new Cache();
        List<String> rs = new ArrayList<>();
        for (Vertex v : g.getVertices().values()) {
            rs.add(getVertexSignature(v, 0));
        }
        StringBuilder sb = new StringBuilder();
        Collections.sort(rs);
        for (String s : rs) {
            sb.append(s).append("\n");
        }
        cache = new Cache();
        return sb.toString();
    }




    public String getVertexSignature(Vertex v) {
        String cacheKey = cache.getCacheKey(v, v.getGraph().getVertices().size());
        if (cache.containsKey(cacheKey)) {
            return cache.getCacheEntry(v, v.getGraph().getVertices().size());

        }
        StringBuilder sb2 = new StringBuilder();

        sb2.append(v.getNumEdges());
        sb2.append("=");
        sb2.append("[");
        //v.sortEdgesNested();
        ArrayList<String> rs = new ArrayList<>();
        for (Vertex u : v.getEdges().values()) {
            rs.add(u.getNumEdges() + ",");
        }
        Collections.sort(rs);
        for (String str : rs) {
            sb2.append(str);
        }
        sb2.append("]");
        /* sb.append(sb2); */
        String result = sb2.toString();
        //System.out.println(sb2.toString());
        cache.putCache(cacheKey, result);
        return cache.getCacheEntry(v,v.getGraph().getVertices().size());
    }

    public String getVertexSignature(Vertex v, int depth) {
        if (depth >= v.getGraph().getVertices().size()) {
            return getVertexSignature(v);
        }
        String key = cache.getCacheKey(v, depth);
        if (cache.containsKey(key)) {
            return (cache.getCacheEntry(v, depth));
        }
        StringBuilder sb = new StringBuilder();
        sb.append(v.getNumEdges());
        if (v.getEdges().size() > 0) {
            sb.append("=");
            sb.append("[");
            ArrayList<String> rs1 = new ArrayList<>();
            for (Vertex u : v.getEdges().values()) {

                String sb1 = getVertexSignature(u, depth + 1);
                rs1.add(sb1);
            }
            //System.out.println(rs1);
            Collections.sort(rs1);
            for (String s : rs1) {
                sb.append(s);
            }
            sb.append("]");

        }
        //sb.append(",");

        String result = sb.toString();
        cache.putCache(key, result);
        return cache.getCacheEntry(v,depth);

    }
}
