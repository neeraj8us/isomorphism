package com.neer;


import com.neer.algo.IsoMorphicGraphSignatureDP;
import com.neer.algo.IsoMorphicAlgo;
import com.neer.util.RandomGraphGenerator;

import com.neer.algo.IsoMorphicAlgo;
import com.neer.util.Util;

import java.util.TreeSet;

public class Main {
    static final IsoMorphicAlgo algo = new IsoMorphicGraphSignatureDP();
    public static void main(String[] args) {
        int numNodes = 20;
        int numEdges = (int) (numNodes * numNodes / 2 - (numNodes * Math.log(numNodes)));
        TreeSet<String> uniqueSigs = new TreeSet<>();
        long time;
        long totalTime = 0;
        for (int i = 1; i < 1000000; i++) {
            if (i % 1000 == 0) {
               System.out.println(i + "   " + uniqueSigs.size() + "   " + (totalTime) / i);
            }
            Graph g = RandomGraphGenerator.generateRandomGraph(numNodes, numEdges);
            time = System.currentTimeMillis();
            String sig = algo.getNestedGraphSignature(g);
            totalTime += System.currentTimeMillis() - time;
            if (i % 10000 == 0) {
                System.out.println(i + "   " + uniqueSigs.size() + "   " + (totalTime) / i);
            }
            for (int j = 0; j < 1; j++) {
                Graph g2 = g.getIsoMorphicGraph();
                String sig2 = algo.getNestedGraphSignature(g2);
                if (!sig.equals(sig2)) {
                    System.out.println("Graphs are not isomorphic");
                    System.out.println("" + sig);
                    System.out.println("" + sig2);
                }
            }
            uniqueSigs.add(sig);
        }

    }
}
