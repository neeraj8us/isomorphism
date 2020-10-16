package com.neer.algo;

import com.neer.Graph;
import com.neer.Vertex;
import com.neer.util.Util;
import java.util.Arrays;

public class IsoDP implements IsoMorphicAlgo{

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
                String[] rs = new String[v.getNumEdges()];
                int j = 0;
                for (Vertex u : v.getEdges().values()) {
                    rs[j] = prevDepthSignature[u.getId()];
                    j++;
                }
                currDepthSignature[v.getId()]  = serialize(rs,v);
            }
            prevDepthSignature = currDepthSignature;
            currDepthSignature =  new String[numVertices];
        }

        return serialize(prevDepthSignature, null);
    }

    public String getVertexSignature(Vertex v) {
        String[] rs = new String[v.getNumEdges()];
        int i = 0;
        for (Vertex u : v.getEdges().values()) {
            rs[i] = Integer.toString(u.getNumEdges());
            i++;
        }

        return serialize(rs,v);
    }

    public String serialize(String[] rs, Vertex v) {
        //Util.printArray(rs);
        Arrays.sort(rs);
        StringBuilder sb = new StringBuilder();
        if (v != null)
            sb.append(v.getNumEdges()).append("=");
        sb.append("[");
        sb.append(String.join(",",rs));
        sb.append("]");
        String result = sb.toString();
        return Util.getHashHex(result);
    }
}
