package com.neer.algo;

import com.neer.Graph;
import com.neer.Vertex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class IsoMorphicGraphSignatureSelfDP extends IsoMorphicAlgo {

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
        int numVertices = g.getVertices().size();

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
                signature[i] = "self:"+Integer.toString(u.getNumEdges());
            else
                signature[i] = Integer.toString(u.getNumEdges());
            i++;
        }
        if (v.getId() == self.getId())
            return serialize(signature, "self:"+v.getNumEdges());
        else
            return serialize(signature, v.getNumEdges() + "");
    }

    public List<List<IDAndSig>> getAllStepsVerticesSignature(Graph g) {
        int numVertices = g.getVertices().size();
        List<List<IDAndSig>> result = new ArrayList<>();

        IDAndSig[] prevDepthSignature = new IDAndSig[numVertices];
        IDAndSig[] currDepthSignature = new IDAndSig[numVertices];

        for (Vertex v : g.getVertices().values()) {
            prevDepthSignature[v.getId()] = getVertexSignatureFull(v);
        }
        result.add(Arrays.asList(prevDepthSignature));
        //Util.printArray(prevDepthSignature);
        for (int i = 1; i < numVertices; i++) {
            for (Vertex v : g.getVertices().values()) {
                String[] signature = new String[v.getNumEdges()];
                int j = 0;
                for (Vertex u : v.getEdges().values()) {
                    signature[j] = prevDepthSignature[u.getId()].id;
                    j++;
                }
                currDepthSignature[v.getId()] = serializeFull(signature, prevDepthSignature[v.getId()].id);
                /** if (i == numVertices) {
                 result.put(v.getId(), currDepthSignature[v.getId()]);
                 }*/
            }
            result.add(Arrays.asList(currDepthSignature));
            prevDepthSignature = currDepthSignature;
            currDepthSignature = new IDAndSig[numVertices];
        }

        return result;
    }

    public String getVertexSignature(Vertex v) {
        String[] signature = new String[v.getNumEdges()];
        int i = 0;
        for (Vertex u : v.getEdges().values()) {
            signature[i] = Integer.toString(u.getNumEdges());
            i++;
        }

        return serialize(signature, v.getNumEdges() + "");
    }

    public IDAndSig getVertexSignatureFull(Vertex v) {
        String[] signature = new String[v.getNumEdges()];
        int i = 0;
        for (Vertex u : v.getEdges().values()) {
            signature[i] = Integer.toString(u.getNumEdges());
            i++;
        }

        return serializeFull(signature, v.getNumEdges() + "");
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

    public IDAndSig serializeFull(String[] signature, String signatureV) {
        //Util.printArray(rs);
        Arrays.sort(signature);
        StringBuilder sb = new StringBuilder();
        if (signatureV != null)
            sb.append(signatureV).append("->");
        sb.append("[");
        sb.append(String.join(",", signature));
        sb.append("]");
        String result = sb.toString();
        //return Util.getHashHex(result);
        return new IDAndSig(this.idGenerator.getID(result), result);
    }
}


