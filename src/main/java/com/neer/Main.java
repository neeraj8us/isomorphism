package com.neer;

import java.security.MessageDigest;
import java.util.TreeSet;

public class Main {

    public static void main(String[] args) throws Exception {
        TreeSet<String> uniqueSigs = new TreeSet<>();
        MessageDigest md = MessageDigest.getInstance("MD5");
        long time;
        long totalTime = 0;
        for (int i = 1; i < 100000000; i++) {
            if (i % 10 == 0) {
                System.out.println(i + "   " + uniqueSigs.size() + "   " + (totalTime) / i);
            }
            int numNodes = 100;
            int numEdges = (int) (numNodes * numNodes / 2 - (numNodes * Math.log(numNodes)));
            Graph g = RandomGraphGenerator.generateRandomGraph(numNodes, numEdges);
            System.out.println("Graph Generated");
            time = System.currentTimeMillis();
            String sig = g.getNestedGraphSignature();
            totalTime += System.currentTimeMillis() - time;
            if (i % 10 == 0) {
                System.out.println(i + "   " + uniqueSigs.size() + "   " + (totalTime) / i);
            }
            //g.printGraph();
            //System.out.println(sig);
            sig = new String(md.digest(sig.getBytes()));

            //System.out.println(sig.length());

            for (int j = 0; j < 1; j++) {
                Graph g2 = g.getIsoMorphicGraph();
                //g.printGraph();
                //g2.printGraph();
                String sig2 = g2.getNestedGraphSignature();
                sig2 = new String(md.digest(sig2.getBytes()));

                if (sig.equals(sig2)) {
                    //		System.out.println("Graphs are isomorphic" +  g.getVertices().size());
                } else {
                    System.out.println("Graphs are not isomorphic");
                    System.out.println("" + sig);
                    System.out.println("" + sig2);
                }
            }

            if (sig.equals("#")) {
                continue;
            }
            if (!uniqueSigs.contains(sig)) {
                uniqueSigs.add(sig);
                //System.out.println(g.isConnected());
            } else {
                //System.out.println("ISO MORPHIC");
                // g.printGraph();
            }
        }

    }

}
