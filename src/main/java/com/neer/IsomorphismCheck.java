package com.neer;

import com.neer.algo.IsoMorphicAlgo;
import com.neer.algo.IsoMorphicGraphSignatureSelfDP;

import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;

public class IsomorphismCheck {
    public static void main(String[] args) throws IOException {
        if (args.length <2) {
            System.out.println("Usage: java com.neer.IsomorphismCheck graphFile1 graphFile2");
            System.exit(1);
        }
        IsoMorphicAlgo algo = new IsoMorphicGraphSignatureSelfDP();
        String graph1 = args[0];
        String graph2 = args[1];
        Graph g1 = Graph.deserialize(new FileReader(graph1));
        Graph g2 = Graph.deserialize(new FileReader(graph2));
        String sig1 = algo.getNestedGraphSignature(g1);
        String sig2 = algo.getNestedGraphSignature(g2);
        if (sig1.equals(sig2)) {
            algo.verifyMappings(algo.getVerticesSignature(g1), algo.getVerticesSignature(g2));
            System.out.println("Graphs are isomorphic");
        } else {
            System.out.println("Graphs are not isomorphic");
        }
    }
}
