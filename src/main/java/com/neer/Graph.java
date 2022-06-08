package com.neer;

import com.neer.cache.Cache;

import java.io.*;
import java.util.*;

public class Graph {
    private final Map<Integer, Vertex> vertices = new TreeMap<>();
    public Map<Integer, Vertex> getVertices() {
        return vertices;
    }
    private Cache cache;

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

    public String serialize() {
        StringBuilder sb = new StringBuilder();
        for (Vertex v : getVertices().values()) {
            sb.append(v.getId()).append("=");
            boolean first = true;
            for (Vertex u : v.getEdges().values()) {
                if (first) {
                    sb.append(u.getId());
                    first = false;
                    continue;
                }
                sb.append(",").append(u.getId());
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public static Graph deserialize(Reader sr) throws IOException {
        BufferedReader br = new BufferedReader(sr);
        Graph g = new Graph();
        String line;
        Map<Integer, Vertex> vertices = g.getVertices();
        while((line = br.readLine()) != null) {
            String[] tokens = line.split("[=,]");
            Vertex v = new Vertex(Integer.parseInt(tokens[0]), g);
            vertices.put(v.getId(),v);
            for (int i = 1; i < tokens.length; i++) {
                int id = Integer.parseInt(tokens[i]);
                if(!vertices.containsKey(id)) {
                    vertices.put(id , new Vertex(id, g));
                }
                Vertex u = vertices.get(id);
                v.addEdge(u);
            }
        }
        return g;
    }
    public int getEdgeCount(){
        int edgeCount = 0;
        for (Vertex v : getVertices().values()) {
            edgeCount += v.getNumEdges();
        }
        return edgeCount/2;
    }

}
