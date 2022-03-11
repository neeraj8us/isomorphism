package com.neer.util;

import com.neer.Graph;
import com.neer.Vertex;

import java.util.Random;

public class RandomGraphGenerator {

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
                if (uid != vid && !g.getVertices().get(uid).getEdges().containsKey(vid)) {
                    g.getVertices().get(uid).addEdge(g.getVertices().get(vid));
                    numEdgesAdded++;
                }
            }
        } while (!g.isConnected());
        return g;
    }
}
