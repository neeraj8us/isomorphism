package com.neer;

import java.util.Map;
import java.util.TreeMap;

public class Vertex {
    private final int id;
    private final Map<Integer, Vertex> edges = new TreeMap<>();
    private final Graph g;

    public Vertex(int i, Graph g) {
        this.id = i;
        this.g = g;
    }

    public int getId() {
        return id;
    }

    public Map<Integer, Vertex> getEdges() {
        return edges;
    }

    public void addEdge(Vertex v) {
        edges.put(v.getId(), v);
        v.getEdges().put(this.id, this);
    }

    public Graph getGraph() {
        return g;
    }

    public int getNumEdges() {
        return getEdges().size();
    }

    public boolean equals(Object o) {

        if (o == null || !(o instanceof Vertex)) {
            return false;
        }
        Vertex v1 = (Vertex) o;
        return v1.getId() == getId();
    }

    public int hashCode() {
        return getId();
    }
}

