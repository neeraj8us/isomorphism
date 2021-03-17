Algorithm to check if two graphs are isomorphic. If the two graphs are isomorphic, this signature is exactly same.

RunTime Complexity = O((N ^ 3) * log N).
Space Complexity = O(N);

I have not been able to find a example where it does not work.

I have been working so hard to find a negative example but have been unsuccessful.


Code in java file IsoMorphicGraphSignatureDP contains the method to generate the signature of the graph which is invariant
for all isomorphic graph. i.e if the two graphs are isomorphic, their signature  will be exactly same.

To Build execute the command.
$ ./gradlew build

To run. java -cp .:build/lib/*.jar com.neer.Main

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
        return Util.getHashHex(result);
    }
}
