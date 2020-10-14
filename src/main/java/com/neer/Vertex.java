package com.neer;

import java.util.Map;
import java.util.TreeMap;

public class Vertex {
    private int id;
    private Map<Integer, Vertex> edges = new TreeMap<Integer, Vertex>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Map<Integer, Vertex> getEdges() {
        return edges;
    }

    public void setEdges(Map<Integer, Vertex> edges) {
        this.edges = edges;
    }

    public void addEdge(Vertex v) {
        edges.put(v.getId(), v);
        v.getEdges().put(this.id, this);
    }

    public void removeEdge(Vertex v) {
        edges.remove(v.getId());
        v.getEdges().remove(this.id);
    }

    private Graph g;

    public Vertex(int i, Graph g) {
        this.id =i;
        this.g = g;
    }



    public Graph getGraph() {
        return g;
    }

    public int getNumEdges() {
        return getEdges().size();
    }

    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        Vertex v1 = (Vertex) o;
        return v1.getId() == getId();
    }

    public int hashCode() {
        return getId();
    }
}

