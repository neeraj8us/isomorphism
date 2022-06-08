package com.neer.algo;

import com.neer.Graph;
import com.neer.Vertex;
import com.neer.util.IDGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class IsoMorphicAlgo {

    protected IDGenerator idGenerator = new IDGenerator();

    public void resetIDGenerator(){
        this.idGenerator =  new IDGenerator();
    }
    /**
     * Generates the signatue from the Graph
     * @param @{@link Graph} from which signature needs to be generated. Ids of the vertices should vary from 0 to numVertices -1.
     * @return Integer ID of the Signature.
     */
    public abstract String getNestedGraphSignature(Graph g);

    /**
     * Returns the signature for every vertex in the Graph.
     * @param
     * @return Map of vertex id to the ID has of the signature.
     */
    public abstract  HashMap<Integer, String> getVerticesSignature(Graph g);

    public abstract String serialize(String[] signature, String signatureV) ;
    public boolean isIsomorphic(Graph g, Graph g2) {
        HashMap<Integer, String> sig1 = getVerticesSignature(g);
        HashMap<Integer, String> sig2 = getVerticesSignature(g2);
        boolean atLeastOneMatch = true;
        Map<Integer, List<Integer>> mappings = new HashMap<>();
        for (Map.Entry<Integer, String> e : sig1.entrySet()) {

            for (Map.Entry<Integer, String> e2 : sig2.entrySet()) {
                if (e.getValue().equals(e2.getValue())) {
                    List<Integer> l = mappings.computeIfAbsent(e.getKey(), k -> new ArrayList<>());
                    l.add(e2.getKey());
                }
            }

        }

        for (int vId = 0; vId < g.getVertices().size(); vId++) {
            int newID = g.getVertices().size();
            Vertex v1 = g.getVertices().get(vId);
            Vertex u1 = new Vertex(newID, g);
            g.getVertices().put(u1.getId(), u1);
            v1.addEdge(u1);
            for (Integer possibleBi : mappings.get(vId)) {
                Vertex v2 = g2.getVertices().get(possibleBi);
                Vertex u2 = new Vertex(newID, g2);
                g2.getVertices().put(u2.getId(), u2);


                v2.addEdge(u2);
                String sig1New = getNestedGraphSignature(g);
                String sig2New = getNestedGraphSignature(g2);

                //System.out.println(sig1New + "   ==? " + sig2New);
                if (!sig1New.equalsIgnoreCase(sig2New))
                {
                    //System.out.println(sig1New + "   ==? " + sig2New);
                    g2.getVertices().remove(newID);
                    v2.getEdges().remove(u2.getId());
                    g.getVertices().remove(newID);
                    v1.getEdges().remove(u1.getId());
                    return false;
                }

                g2.getVertices().remove(newID);
                v2.getEdges().remove(u2.getId());
            }
            g.getVertices().remove(newID);
            v1.getEdges().remove(u1.getId());

        }
        return true;
    }
    /**
     * Computes the possible mapping of nodes from the first graphs to the nodes in the second graphs.
     *
     * @param signatures  Map of vertex id to the signature from first graph.
     * @param signatures2 Map of vertex id to the signature for second graph.
     * @return true if there is a possible mapping. Returns false otherwise.
     */

    public boolean verifyMappings(HashMap<Integer, String> signatures, HashMap<Integer, String> signatures2 ) {
        Map<Integer, List<Integer>> mappings = new HashMap<>();
        for (Map.Entry<Integer, String> e : signatures.entrySet()) {

            for (Map.Entry<Integer, String> e2: signatures2.entrySet()) {
                if (e.getValue().equals(e2.getValue())){
                    List<Integer> l = mappings.computeIfAbsent(e.getKey(), k -> new ArrayList<>());
                    l.add(e2.getKey());

                }
            }
        }
        /* Un comment if you want to get the mappings from Graph 1 to Graph 2.

        */
        System.out.println("possible 1-1 mappings is " );
        for (int i : mappings.keySet()) {
            System.out.println("Node " + i + "of graph 1 can map to " + mappings.get(i) + " Ndoes of Graph 2");
        }



        return mappings.size() == signatures.size() && mappings.size() == signatures2.size();
    }
}
