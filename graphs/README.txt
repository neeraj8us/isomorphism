Algorithm to check if two graphs are isomorphic. If the two graphs are isomorphic, this signature is exactly same.

RunTime Complexity = O((N ^ 3) * log N).
Space Complexity = O(N^2);

I have not been able to find a example where it does not work.

I have been working so hard to find a negative example but have been unsuccessful.


Code in java file IsoMorphicGraphSignatureDP contains the method to generate the signature of the graph which is invariant
for all isomorphic graph. i.e if the two graphs are isomorphic, their signature  will be exactly same.

I have switched from SHA256 hash to auto increment integer id. I am now keep the cache of signature to int id. This makes the algorithm O(N^2) memory.
If we want the signature to be globally unique we have to use the same ID across signature generation.


To Build execute the command.
$ ./gradlew build

To run. java -cp .:build/libs/*.jar com.neer.Main

it generates the random graphs for specific number of vertices and edges and then generates the isomorphic graphs of the random graphs to test correctness of algorithms.

Algorithm


public class IsoMorphicGraphSignatureDP implements IsoMorphicAlgo{

    @Override
    public String getNestedGraphSignature(Graph g) {
        int numVertices = g.getVertices().size();
        String[] prevDepthSignature = new String[numVertices];
        String[] currDepthSignature = new String[numVertices];

        for (Vertex v: g.getVertices().values()) {
            prevDepthSignature[v.getId()] = getVertexSignature(v);
        }
        //Util.printArray(prevDepthSignature);
        for (int i =1; i <= numVertices; i++) {
            for (Vertex v: g.getVertices().values()) {
                String[] signature = new String[v.getNumEdges()];
                int j = 0;
                for (Vertex u : v.getEdges().values()) {
                    signature[j] = prevDepthSignature[u.getId()];
                    j++;
                }
                currDepthSignature[v.getId()]  = serialize(signature,v);
            }
            prevDepthSignature = currDepthSignature;
            currDepthSignature =  new String[numVertices];
        }

        return serialize(prevDepthSignature, null);
    }

    public String getVertexSignature(Vertex v) {
        String[] signature = new String[v.getNumEdges()];
        int i = 0;
        for (Vertex u : v.getEdges().values()) {
            signature[i] = Integer.toString(u.getNumEdges());
            i++;
        }

        return serialize(signature,v);
    }

    public String serialize(String[] signature, Vertex v) {
        //Util.printArray(rs);
        Arrays.sort(signature);
        StringBuilder sb = new StringBuilder();
        if (v != null)
            sb.append(v.getNumEdges()).append("=");
        sb.append("[");
        sb.append(String.join(",",signature));
        sb.append("]");
        String result = sb.toString();
        //return Util.getHashHex(result);
        return this.idGenerator.getID(result);
    }
    
    public boolean generateMappings(HashMap<Integer, String> signatures, HashMap<Integer, String> signatures2 ) {
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
        System.out.println("Psiible 1-1 mappings is " );
        for (int i : mappings.keySet()) {
            System.out.println("Node " + i + "of graph 1 can map to " + mappings.get(i) + " Ndoes of Graph 2");
        }
        */
        return mappings.size() == signatures.size() && mappings.size() == signatures2.size();
    }
}
