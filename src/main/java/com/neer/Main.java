package com.neer;


import com.neer.algo.IsoMorphicAlgo;
import com.neer.algo.IsoMorphicGraphSignatureDPV2;
import com.neer.util.GraphGenerator;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {
    static final IsoMorphicAlgo algo = new IsoMorphicGraphSignatureDPV2();
    public static final String NUM_VERTICES = "NUM_VERTICES";
    public static final String NUM_EDGES = "NUM_EDGES";
    public static final String PRINT_AFTER_ITERATIONS = "PRINT_AFTER_ITERATIONS";


    public static void main(String[] args) throws IOException {
        Random rand = new Random();
        GraphGenerator rg = new GraphGenerator();
        Properties p = new Properties();
        p.load(new FileReader(args[0]));


        int numNodes = Integer.parseInt(p.getProperty(NUM_VERTICES));
        int numEdges = Integer.parseInt(p.getProperty(NUM_EDGES));
        int printAfterInterations = Integer.parseInt(p.getProperty(PRINT_AFTER_ITERATIONS));
        rg.init(numNodes);
         //(int) (numNodes * numNodes / 2 - (numNodes * Math.log(numNodes)));
        HashSet<String> uniqueSigs = new HashSet<>();
        long time;
        long totalTime = 0;
        System.out.println("Generating random graphs with " + numNodes + " vertices and " + numEdges + " edges");
        for (long i = 1; i < 1000000000000L; i++) {
            //algo.resetIDGenerator();
            Graph g;
            try {
                g = rg.getNextGraph(numEdges);
            } catch (Exception e) {
                break;
            }
            //System.out.println("Generated Graph");
            time = System.nanoTime();
            String sig = algo.getNestedGraphSignature(g);
            totalTime += System.nanoTime() - time;
            if (i % printAfterInterations == 0) {
                System.out.println("###############################################################");
                System.out.println("Number of random graphs, #vertices = " + numNodes + ", edges ( -1 -> all possible)=  " + numEdges + "  considered = " + (i));
                System.out.println("Number of unique signature(# non isomorphic graphs)  = " + uniqueSigs.size() );
                System.out.println("Avg time to compute the signature =" + (totalTime / i) + "   nano seconds");
                System.out.println(Arrays.toString(rg.getEdges()));
            }
            if (!uniqueSigs.contains(sig)) {
                uniqueSigs.add(sig);
            }
        }


        System.out.println("Number of simple graphs with "+numNodes+" vertices and  "+numEdges+" edges (-1 -> all possible graphs) is " + uniqueSigs.size());
    }

}
