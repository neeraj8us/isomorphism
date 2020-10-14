package com.neer;

import java.security.MessageDigest;
import java.util.*;

public class Graph {
    private Map<Integer, Vertex> vertices = new TreeMap<Integer, Vertex>();
    public Map<Integer, Vertex> getVertices() {
        return vertices;
    }

    Map<String, String> cache =  new HashMap<String,String>();

    public void setVertices(Map<Integer, Vertex> vertices) {
        this.vertices = vertices;
    }

    public String getNestedGraphSignature() {
        cache = new HashMap<String, String>();
        List<String> rs =  new ArrayList<String>();
        for (Vertex v : getVertices().values()) {
            StringBuilder sb = new StringBuilder();
            getVertexSignature(v,sb, 0);
            rs.add(sb.toString());
        }
        StringBuilder sb = new StringBuilder();


        Collections.sort(rs);
        for( String s: rs) {
            sb.append(s).append("\n");
        }
        cache = new HashMap<String, String>();
        return sb.toString();
    }

    public String getCacheKey(Vertex v, int tabs) {
        return v.getId() +"##" + tabs;
    }


    public String getCacheEntry(Vertex v, int tabs) {
        return cache.get(getCacheKey(v, tabs));
    }

    static MessageDigest md;
    static {
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
        }
    }

    public String getHashHex(String input) {
        byte[] digest = md.digest(input.getBytes());
        StringBuffer hexString = new StringBuffer();

        for (int i = 0; i < digest.length; i++) {
            hexString.append(Integer.toHexString(0xFF & digest[i]));
        }
        return hexString.toString();
    }
    public void getVertexSignature(Vertex v, StringBuilder sb) {
        String cacheKey = v.getId() +  "#" +0;
        if (cache.containsKey(cacheKey)) {
            sb.append(cache.get(cacheKey));
            return;
        }
        StringBuilder sb2 = new StringBuilder();

        sb2.append(v.getNumEdges());
        sb2.append("=");
        sb2.append("[");
        //v.sortEdgesNested();
        ArrayList<String> rs = new ArrayList<String>();
        for (Vertex u : v.getEdges().values()) {
            StringBuilder sb1 = new StringBuilder();
            sb1.append(u.getNumEdges()).append(",");
            rs.add(sb1.toString());
        }
        Collections.sort(rs);
        for (String str : rs) {
            sb2.append(str);
        }
        sb2.append("]");
        sb.append(sb2);

        cache.put(v.getId()+"#"+0, sb2.toString());
    }

    public void getVertexSignature(Vertex v,  StringBuilder resultBuffer, int depth) {
        if (depth > v.getGraph().getVertices().size()) {
            getVertexSignature(v, resultBuffer);
            resultBuffer.append(",");
            return;
        }
        String key = getCacheKey(v, depth);
        if (cache.containsKey(key)) {
            resultBuffer.append(getCacheEntry(v, depth));
            return;

        }
        StringBuilder sb = new StringBuilder();
        sb.append(v.getNumEdges());
        if (v.getEdges().size() > 0) {
            sb.append("=");
            sb.append("[");
            ArrayList<String> rs1 = new ArrayList<String>();
            for (Vertex u : v.getEdges().values()) {
                StringBuilder sb1 = new StringBuilder();
                getVertexSignature(u,  sb1, depth + 1);
                rs1.add(sb1.toString());
            }
            Collections.sort(rs1);
            for (int i =0; i < rs1.size(); i++) {
                sb.append(rs1.get(i));
            }
            sb.append("]");

        }
        sb.append(",");


        putCache(key, sb.toString());
        resultBuffer.append(cache.get(key));
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

        HashSet<Integer> visited = new HashSet<Integer>();
        doDFS(getVertices().get(0), visited);
        return visited.size() == getVertices().size();
    }

    public Graph getIsoMorphicGraph() {
        Set<Integer> keys = getVertices().keySet();

        ArrayList<Integer> list = 		(new ArrayList<Integer>());

        list.addAll(keys);
        Collections.shuffle(list);
        Iterator<Integer> itr = list.iterator();
        Graph g = new Graph();
        Map<Integer, Integer> m = new HashMap<Integer, Integer>();
        Map<Integer, Integer> mReverse = new HashMap<Integer, Integer>();

        for (int i = 0 ; i < keys.size(); i++) {
            if (itr.hasNext()) {
                g.getVertices().put(i, new Vertex(i,g));
                int oldId = itr.next();
                m.put(i, oldId);
                mReverse.put(oldId, i);
            }
        }

        for (int newId = 0 ; newId < keys.size(); newId++) {
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
