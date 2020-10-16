package com.neer;

import com.neer.algo.IsoMorphicSignature;
import com.neer.util.Util;

import java.util.TreeSet;

public class Main {

    public static void main(String[] args) {
        int numNodes = 20;
        int numEdges = 180; //(int) (numNodes * numNodes / 2 - (numNodes * Math.log(numNodes)));
        IsoMorphicSignature algo  = new IsoMorphicSignature();

        TreeSet<String> uniqueSigs = new TreeSet<>();
        long time;
        long totalTime = 0;
        for (int i = 1; i < 1000000; i++) {
            if (i % 1000 == 0) {
                System.out.println(i + "   " + uniqueSigs.size() + "   " + (totalTime) / i);
            }
            Graph g = RandomGraphGenerator.generateRandomGraph(numNodes, numEdges);
            //System.out.println("Graph Generated");
            time = System.currentTimeMillis();
            String sig = algo.getNestedGraphSignature(g);
            totalTime += System.currentTimeMillis() - time;
            if (i % 1000 == 0) {
                System.out.println(i + "   " + uniqueSigs.size() + "   " + (totalTime) / i);
            }
            //g.printGraph();
            //System.out.println(sig);
            sig = Util.getHashHex(sig);

            //System.out.println(sig.length());

            for (int j = 0; j < 1; j++) {
                Graph g2 = g.getIsoMorphicGraph();
                //g.printGraph();
                //g2.printGraph();
                String sig2 = algo.getNestedGraphSignature(g2);
                sig2 = Util.getHashHex(sig2);

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
