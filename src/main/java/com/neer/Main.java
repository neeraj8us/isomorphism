package com.neer;


import com.neer.algo.IsoMorphicGraphSignatureDP;
import com.neer.algo.IsoMorphicAlgo;
import com.neer.util.RandomGraphGenerator;

import java.util.Arrays;
import java.util.HashSet;

public class Main {
    static final IsoMorphicAlgo algo = new IsoMorphicGraphSignatureDP();
    public static void main(String[] args) {
        int numNodes = 20;
        int numEdges = 180; //(int) (numNodes * numNodes / 2 - (numNodes * Math.log(numNodes)));
        HashSet<String> uniqueSigs = new HashSet<>();
        long time;
        long totalTime = 0;
        System.out.println("Generating random graphs with " + numNodes  + " vertices and " + numEdges + " edges");
        for (int i = 1; i < 1000000; i++) {
            Graph g = RandomGraphGenerator.generateRandomGraph(numNodes, numEdges);
            time = System.currentTimeMillis();
            String sig = algo.getNestedGraphSignature(g);
            totalTime += System.currentTimeMillis() - time;
            if (i % 1000 == 0) {
                System.out.println("###############################################################");
                System.out.println("Number of random graphs, #vertices = " + numNodes+" edges, =  "+ numEdges+"  considered = " + i);
                System.out.println("Number of unique signature(# non isomorphic graphs)  = " + uniqueSigs.size());
                System.out.println("Avg time to compute the signature =" + (totalTime/i) + "   milli seconds");
                //System.out.println("###############################################################");
            }
            for (int j = 0; j < 1; j++) {
                Graph g2 = g.getIsoMorphicGraph();
                String sig2 = algo.getNestedGraphSignature(g2);
                String[] verticesSig2 = algo.getVerticesSignature(g2);
                String[] verticesSig = algo.getVerticesSignature(g);

                if (!Arrays.equals(verticesSig, verticesSig2)  || !sig.equals(sig2)) {
                    System.out.println("Graphs are not isomorphic");
                    System.out.println("" + sig);
                    System.out.println("" + sig2);
                }


            }
            uniqueSigs.add(sig);
        }

    }
}
