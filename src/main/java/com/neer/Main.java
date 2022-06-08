package com.neer;


import com.neer.algo.IsoMorphicAlgo;
import com.neer.algo.IsoMorphicGraphSignatureDP;
import com.neer.util.RandomGraphGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Main {
    static final IsoMorphicAlgo algo = new IsoMorphicGraphSignatureDP();

    public static void main(String[] args) {
        Random rand = new Random();
        RandomGraphGenerator rg = new RandomGraphGenerator();
        rg.init(10);
        int numNodes = 5;
        int maxEdges = numNodes*(numNodes-1)/2;
        int numEdges = 15; //(int) (numNodes * numNodes / 2 - (numNodes * Math.log(numNodes)));
        HashMap<String, List<Graph>> uniqueSigs = new HashMap<>();
        long time;
        long totalTime = 0;
        System.out.println("Generating random graphs with " + numNodes + " vertices and " + numEdges + " edges");
        for (long i = 1; i < 1000000000000L; i++) {
            //numEdges = numNodes -1+ rand.nextInt(2+maxEdges-numNodes);
            //algo.resetIDGenerator();
            Graph g = rg.getNextGraph(10);
            //System.out.println("Generated Graph");
            time = System.nanoTime();
            String sig = algo.getNestedGraphSignature(g);
            totalTime += System.nanoTime() - time;
            if (i % 100 == 0) {
                int count = 0;
                for(List<Graph> gList : uniqueSigs.values()) {
                    count+= gList.size();
                }
                System.out.println("###############################################################");
                System.out.println("Number of random graphs, #vertices = " + numNodes + " edges, =  " + numEdges + "  considered = " + (i));
                System.out.println("Number of unique signature(# non isomorphic graphs)  = " + uniqueSigs.size() + " and unique graphs = " + count);
                System.out.println("Avg time to compute the signature =" + (totalTime / i) + "   milli seconds");
                //System.out.println("###############################################################");
            }
            for (int j = 0; j < 0; j++) {
                Graph g2 = g.getIsoMorphicGraph();
                String sig2 = algo.getNestedGraphSignature(g2);
                //algo.resetIDGenerator();
                HashMap<Integer, String> signatures = algo.getVerticesSignature(g2);
                HashMap<Integer, String> signatures2 = algo.getVerticesSignature(g);

                if (!algo.isIsomorphic(g, g2)) {
                    System.out.println("Graphs are not isomorphic");
                    System.out.println("" + sig);
                    System.out.println("" + sig2);
                    System.out.println(g.serialize());
                    System.out.println("Second Graph");
                    System.out.println(g2.serialize());
                    algo.verifyMappings(algo.getVerticesSignature(g), algo.getVerticesSignature(g2));
                    System.exit(1);
                }
            }
            if (uniqueSigs.containsKey(sig)) {
                List<Graph> origGList = uniqueSigs.get(sig);
                boolean unique = true;
                for (Graph origG : origGList) {
                    if (algo.isIsomorphic(origG, g)){
                        unique = false;
                        break;
                    }

                }
                if (unique) {
                    origGList.add(g);
                }
            } else {
                List<Graph> lg = new ArrayList<>();
                uniqueSigs.put(sig, lg);
                lg.add(g);
            }
        }
    }

}
