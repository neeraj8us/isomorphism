package com.neer;

import java.security.MessageDigest;
import java.util.*;

public class Graph {
    static MessageDigest md;

    static {
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (Exception ignored) {
        }
    }

    private final Map<Integer, Vertex> vertices = new TreeMap<>();
    Map<String, String> cache = new HashMap<>();

    public Map<Integer, Vertex> getVertices() {
        return vertices;
    }

    public String getNestedGraphSignature() {
        cache = new HashMap<>();
        List<String> rs = new ArrayList<>();
        for (Vertex v : getVertices().values()) {
            rs.add(getVertexSignature(v, 0));
        }
        StringBuilder sb = new StringBuilder();
        Collections.sort(rs);
        for (String s : rs) {
            sb.append(s).append("\n");
        }
        cache = new HashMap<>();
        return sb.toString();
    }

    public String getCacheKey(Vertex v, int tabs) {
        return v.getId() + "##" + tabs;
    }

    public String getCacheEntry(Vertex v, int tabs) {
        return cache.get(getCacheKey(v, tabs));
    }

    public String getHashHex(String input) {
        byte[] digest = md.digest(input.getBytes());
        StringBuilder hexString = new StringBuilder();

        for (byte b : digest) {
            hexString.append(Integer.toHexString(0xFF & b));
        }
        return hexString.toString();
    }

    public String getVertexSignature(Vertex v) {
        String cacheKey = v.getId() + "#" + v.getGraph().getVertices().size();
        if (cache.containsKey(cacheKey)) {
            return cache.get(cacheKey);

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
        putCache(cacheKey, result);
        return cache.get(cacheKey);
    }

    public String getVertexSignature(Vertex v, int depth) {
        if (depth > v.getGraph().getVertices().size()) {
            return getVertexSignature(v);
        }
        String key = getCacheKey(v, depth);
        if (cache.containsKey(key)) {
            return (getCacheEntry(v, depth));
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
            for (int i = 0; i < rs1.size(); i++) {
                sb.append(rs1.get(i));
            }
            sb.append("]");

        }
        //sb.append(",");

        String result = sb.toString();
        putCache(key, result);
        return cache.get(key);

    }

    public void putCache(String key, String value) {
        String hash = getHashHex(value);
        cache.put(key, hash);
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

            for (Vertex oldu : oldVertex.getEdges().values()) {
                newVertex.addEdge(newMV.get(mReverse.get(oldu.getId())));
            }
        }
        return g;
    }

}
