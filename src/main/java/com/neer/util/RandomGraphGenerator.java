package com.neer.util;

import com.neer.Graph;
import com.neer.Vertex;

import java.util.Arrays;
import java.util.Random;

class Edge {
    boolean exists;
    int i, j;

    public Edge(boolean e, int i, int j) {
        this.exists = e;
        this.i = i;
        this.j = j;
    }

    public String toString() {
        final String s = "[" + exists + "," + i + "," + j + "]";
        return s;
    }
}

public class RandomGraphGenerator {
    int numVertices = 0;
    private int numEdges;
    Edge[] edges;
    boolean first = true;

    public static void main(String[] args) throws Exception {
        RandomGraphGenerator rg = new RandomGraphGenerator();
        rg.init(4);
        for (int i = 0; i < Math.pow(2, rg.edges.length); i++) {

            System.out.println(Arrays.asList(rg.edges));
            rg.increment();
        }
    }

    public void init(int numVertices) {
        this.numVertices = numVertices;
        numEdges = (numVertices * (numVertices - 1)) / 2;
        edges = new Edge[(int) (numVertices * (numVertices - 1) / 2)];
        int index = 0;
        for (int i = 0; i < numVertices; i++) {
            for (int j = i + 1; j < numVertices; j++) {
                edges[index] = new Edge(false, i, j);
                index++;
            }
        }
    }

    public Graph getNextGraph(int ed) throws Exception {
        Graph g;
        g = new Graph();
        while (edgeCount() != ed && ed != -1)
            increment();


        for (int i = 0; i < numVertices; i++) {
            g.getVertices().put(i, new Vertex(i, g));
        }
        int index = 0;
        for (int i = 0; i < numVertices; i++) {
            for (int j = i + 1; j < numVertices; j++) {
                if (edges[index].exists)
                    g.getVertices().get(i).addEdge(g.getVertices().get(j));
                index++;
            }
        }
        increment();
        //System.out.println(g.serialize());
        return g;
    }

    public int edgeCount() {
        int count = 0;
        for (int i = edges.length - 1; i >= 0; i--) {
            if (edges[i].exists) {
                count++;
            }
        }
        return count;
    }

    private void increment() throws Exception {

        if (edgeCount() == 0 && !first) {
            System.out.println("Completed");
            throw new Exception("No more Graphs. Completed");
        }
        first = false;
        for (int i = edges.length - 1; i >= 0; i--) {
            if (edges[i].exists)
                edges[i].exists = false;
            else {
                edges[i].exists = true;
                break;
            }
        }
    }

    public static Graph generateRandomGraph(int numVertices, int numEdges) {
        Graph g;
        do {
            g = new Graph();
            for (int i = 0; i < numVertices; i++) {
                g.getVertices().put(i, new Vertex(i, g));
            }
            Random rand = new Random(System.currentTimeMillis());
            int numEdgesAdded = 0;
            while (numEdgesAdded < numEdges) {
                int uid = rand.nextInt(numVertices);
                int vid = rand.nextInt(numVertices);
                if ((uid != vid) && !g.getVertices().get(uid).getEdges().containsKey(vid)) {
                    g.getVertices().get(uid).addEdge(g.getVertices().get(vid));
                    numEdgesAdded++;
                }
            }
        } while (!g.isConnected());
        return g;
    }
}
