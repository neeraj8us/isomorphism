package com.neer;

import com.neer.cache.Cache;

import java.security.MessageDigest;
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

}
