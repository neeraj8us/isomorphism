package com.neer.algo;

import com.neer.Graph;
import com.neer.Vertex;

import java.util.Arrays;
import java.util.HashMap;

public class IsoMorphicGraphSignatureDPV2 extends IsoMorphicAlgo {

    @Override
    public String getNestedGraphSignature(Graph g) {
        String[] signature = new String[g.getVertices().size()];

        for (Vertex v: g.getVertices().values()) {
            signature[v.getId()] = getSelfSignature(v);
        }
        return serialize(signature, null);
    }
    @Override
    public HashMap<Integer, String> getVerticesSignature(Graph g) {
        HashMap<Integer, String> result = new HashMap<>();
        
        for (Vertex v: g.getVertices().values()) {
            result.put(v.getId(), getSelfSignature(v));
        }

        return result;
    }

    public String getSelfSignature( Vertex self) {
        HashMap<Integer, String> result = new HashMap<>();
        Graph g = self.getGraph();
        int numVertices = g.getVertices().size();
        String[] prevDepthSignature = new String[numVertices];
        String[] currDepthSignature = new String[numVertices];

        for (Vertex v : g.getVertices().values()) {
            prevDepthSignature[v.getId()] = getVertexSignatureSelf(v, self);
        }
        //Util.printArray(prevDepthSignature);
        for (int i = 1; i <= numVertices; i++) {
            for (Vertex v : g.getVertices().values()) {
                String[] signature = new String[v.getNumEdges()];
                int j = 0;
                for (Vertex u : v.getEdges().values()) {
                    signature[j] = prevDepthSignature[u.getId()];
                    j++;
                }
                currDepthSignature[v.getId()] = serialize(signature, prevDepthSignature[v.getId()]);
                if (i == numVertices) {
                    result.put(v.getId(), currDepthSignature[v.getId()]);
                }
            }
            prevDepthSignature = currDepthSignature;
            currDepthSignature = new String[numVertices];
        }

        return result.get(self.getId());
    }

    private String getVertexSignatureSelf(Vertex v, Vertex self) {
        String[] signature = new String[v.getNumEdges()];
        int i = 0;
        for (Vertex u : v.getEdges().values()) {
            if (u.getId() == self.getId())
                signature[i] = "self:"+u.getNumEdges();
            else
                signature[i] = Integer.toString(u.getNumEdges());
            i++;
        }
        if (v.getId() == self.getId())
            return serialize(signature, "self:"+v.getNumEdges());
        else
            return serialize(signature, v.getNumEdges() + "");
    }



    public String serialize(String[] signature, String signatureV) {
        //Util.printArray(rs);
        Arrays.sort(signature);
        StringBuilder sb = new StringBuilder();
        if (signatureV != null)
            sb.append(signatureV).append("=");
        sb.append("[");
        sb.append(String.join(",", signature));
        sb.append("]");
        String result = sb.toString();
        //return Util.getHashHex(result);
        return this.idGenerator.getID(result);
    }
}


