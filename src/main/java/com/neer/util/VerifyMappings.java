package com.neer.util;

import com.neer.Graph;
import com.neer.Vertex;
import com.neer.algo.IDAndSig;
import com.neer.algo.IsoMorphicGraphSignatureDP;
import com.neer.algo.IsoMorphicGraphSignatureSelfDP;

import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class VerifyMappings {
    public void genLatex(String args[]) throws IOException {
        //BufferedWriter bf = new BufferedWriter(new FileWriter(args[1]));

        Graph g = Graph.deserialize(new FileReader("ga1.txt"));
        IsoMorphicGraphSignatureDP algo = new IsoMorphicGraphSignatureDP();
        List<List<IDAndSig>> result  = algo.getAllStepsVerticesSignature(
                Graph.deserialize(new FileReader("ga2.txt"))
        );
        int count = 1;
        for (List<IDAndSig> ithSig: result) {
            System.out.print(count);
            for (IDAndSig idAndSig : ithSig) {
                System.out.print("&\\textbf{" + idAndSig.id + "}  ( "+idAndSig.signature.replaceAll("->","\\$->\\$")+")");
            }
            System.out.println("\\\\");
            count++;
        }

        System.out.println("\n\n\n");
        result = algo.getAllStepsVerticesSignature(g);
        count = 1;
        for (List<IDAndSig> ithSig: result) {
            System.out.print(count);
            for (IDAndSig idAndSig : ithSig) {
                System.out.print("&\\textbf{" + idAndSig.id + "} ( "+idAndSig.signature.replaceAll("->","\\$->\\$")+")");
            }
            System.out.println("\\\\");
            count++;
        }
    }

    public  void match(String args[]) throws IOException{
        Graph g = Graph.deserialize(new FileReader("gEdge.txt"));
        for (int i = 0 ; i < 10000; i++) {
            Graph g2 = g.getIsoMorphicGraph();
            IsoMorphicGraphSignatureDP algo = new IsoMorphicGraphSignatureDP();
            String sig1 = algo.getNestedGraphSignature(g);
            String sig2 = algo.getNestedGraphSignature(g2);
            //algo.verifyMappings(algo.getVerticesSignature(g), algo.getVerticesSignature(g2));
            boolean isMorphic = sig2.equalsIgnoreCase(sig2) && algo.isIsomorphic(g, g2);
            if (isMorphic) {
                System.out.println("Graphs are isomorphic");
            } else {
                System.out.println("Graphs are not isomorphic");

            }
            System.out.println(sig1 + " ==? " + sig2);
        }
    }

    public String getGraphSignature(Graph g) {
        IsoMorphicGraphSignatureSelfDP algo = new IsoMorphicGraphSignatureSelfDP();
        String[] signature = new String[g.getVertices().size()];

        for (Vertex v: g.getVertices().values()) {
            signature[v.getId()] = algo.getSelfSignature(v);
        }
        Arrays.sort(signature);
        System.out.println(Arrays.toString(signature));
        return algo.serialize(signature, null);
    }

    public  void matchSelf(String args[]) throws IOException{
        Graph g = Graph.deserialize(new FileReader("gEdge.txt"));
        for (int i = 0 ; i < 3; i++) {
            Graph g2 = g.getIsoMorphicGraph();
            IsoMorphicGraphSignatureSelfDP algo = new IsoMorphicGraphSignatureSelfDP();
            String sig1 = getGraphSignature(g);
            String sig2 = getGraphSignature(g2);

            //algo.verifyMappings(algo.getVerticesSignature(g), algo.getVerticesSignature(g2));
            boolean isMorphic = sig2.equalsIgnoreCase(sig1) && algo.isIsomorphic(g, g2);
            if (isMorphic) {
                System.out.println("Graphs are isomorphic");
            } else {
                System.out.println("Graphs are not isomorphic");

            }
            System.out.println(sig1 + " ==? " + sig2);
        }
    }

    public static void main(String args[]) throws IOException {
        VerifyMappings gx = new VerifyMappings();
        gx.matchSelf(args);
    }


}
