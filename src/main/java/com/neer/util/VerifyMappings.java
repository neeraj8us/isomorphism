package com.neer.util;

import com.neer.Graph;
import com.neer.algo.IDAndSig;
import com.neer.algo.IsoMorphicGraphSignatureDP;

import java.io.FileReader;
import java.io.IOException;
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
        Graph g = Graph.deserialize(new FileReader("g10.txt"));
        Graph g2 = Graph.deserialize(new FileReader("g101.txt"));
        IsoMorphicGraphSignatureDP algo = new IsoMorphicGraphSignatureDP();
        String sig1 = algo.getNestedGraphSignature(g);
        String sig2 = algo.getNestedGraphSignature(g2);
        //algo.verifyMappings(algo.getVerticesSignature(g), algo.getVerticesSignature(g2));
        boolean isMorphic = sig2.equalsIgnoreCase(sig2) && algo.isIsomorphic(g,g2);
        if (isMorphic) {
            System.out.println("Graphs are isomorphic");
        } else {
            System.out.println("Graphs are not isomorphic");
        }
        System.out.println(sig1 + " ==? " + sig2);

    }

    public static void main(String args[]) throws IOException {
        VerifyMappings gx = new VerifyMappings();
        gx.match(args);
    }


}
