package com.neer;

import com.neer.cache.Cache;

import java.security.MessageDigest;
import java.util.*;

public class Graph {
    static MessageDigest md;



    private final Map<Integer, Vertex> vertices = new TreeMap<>();

    public Map<Integer, Vertex> getVertices() {
        return vertices;
    }
    private Cache cache;
    public String getNestedGraphSignature() {
        cache = new Cache();
        List<String> rs = new ArrayList<>();
        for (Vertex v : getVertices().values()) {
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
        String cacheKey = v.getId() + "#" + v.getGraph().getVertices().size();
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
        if (depth > v.getGraph().getVertices().size()) {
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



    public void doDFS(Vertex v, HashSet<Integer> visited) {
        if (!visited.contains(v.getId())) {
            visited.add(v.getId());
            for (Vertex u : v.getEdges().values()) {
                doDFS(u, visited);
            }
        }
    }

    public boolean isConnected() {

        HashSet<Integer> visited = new HashSet<>();
        doDFS(getVertices().get(0), visited);
        return visited.size() == getVertices().size();
    }

    public Graph getIsoMorphicGraph() {
        Set<Integer> keys = getVertices().keySet();

        ArrayList<Integer> list = (new ArrayList<>());

        list.addAll(keys);
        Collections.shuffle(list);
        Iterator<Integer> itr = list.iterator();
        Graph g = new Graph();
        Map<Integer, Integer> m = new HashMap<>();
        Map<Integer, Integer> mReverse = new HashMap<>();

        for (int i = 0; i < keys.size(); i++) {
            if (itr.hasNext()) {
                g.getVertices().put(i, new Vertex(i, g));
                int oldId = itr.next();
                m.put(i, oldId);
                mReverse.put(oldId, i);
            }
        }

        for (int newId = 0; newId < keys.size(); newId++) {
            Map<Integer, Vertex> newMV = g.getVertices();
            Map<Integer, Vertex> oldMV = getVertices();

            int oldId = m.get(newId);
            Vertex oldVertex = oldMV.get(oldId);
            Vertex newVertex = newMV.get(newId);

            for (Vertex oldU : oldVertex.getEdges().values()) {
                newVertex.addEdge(newMV.get(mReverse.get(oldU.getId())));
            }
        }
        return g;
    }

}
