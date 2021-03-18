package com.neer.algo;

import com.neer.Graph;
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
     * @return SHA-256 hash of the Signature.
     */
    public abstract String getNestedGraphSignature(Graph g);

    /**
     * Returns the signature for every vertex in the Graph.
     * @param
     * @return Map of vertex id to the SHA-256 has of the signature.
     */
    public abstract  HashMap<Integer, String> getVerticesSignature(Graph g);

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
        /* Un comment if you want to get the mappings from Graph 1 to Graph 2.*/

        /*
        System.out.println("possible 1-1 mappings is " );
        for (int i : mappings.keySet()) {
            System.out.println("Node " + i + "of graph 1 can map to " + mappings.get(i) + " Ndoes of Graph 2");
        }
        */
        return mappings.size() == signatures.size() && mappings.size() == signatures2.size();
    }
}
