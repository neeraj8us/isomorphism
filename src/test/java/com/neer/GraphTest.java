package com.neer;

import com.neer.util.GraphGenerator;
import junit.framework.TestCase;
import org.junit.Assert;

import java.io.IOException;
import java.io.StringReader;

public class GraphTest extends TestCase {

    public void testSerialize() throws IOException {
        Graph g = GraphGenerator.generateRandomGraph(5, 8);
        String serialized = g.serialize();
        //System.out.println(serialized);
        Graph gClone = Graph.deserialize(new StringReader(serialized));
        Assert.assertEquals(g.serialize(), gClone.serialize());
    }
}