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
        int numEdges = 180; //(int) (numNodes * numNodes / 2 - (numNodes * Math.log(numNodes)));
        TreeSet<String> uniqueSigs = new TreeSet<>();
        long time;
        long totalTime = 0;
        System.out.println("Generating random graphs with " + numNodes  + " and " + numEdges);
        for (int i = 1; i < 1000000; i++) {
            Graph g = RandomGraphGenerator.generateRandomGraph(numNodes, numEdges);
            time = System.currentTimeMillis();
            String sig = algo.getNestedGraphSignature(g);
            totalTime += System.currentTimeMillis() - time;
            if (i % 1000 == 0) {
                System.out.println("###############################################################");
                System.out.println("Number of random graph considered = " + i);
                System.out.println("Number of Unique Signature(# non isomorphic graphs)  = " + uniqueSigs.size());
                System.out.println("Avg time =" + (totalTime/i) + "   milli seconds");
                //System.out.println("###############################################################");
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
