/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;
import java.util.*;

import org.junit.Test;

/**
 * Tests for ConcreteVerticesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteVerticesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteVerticesGraphTest extends GraphInstanceTest {
    
    /*
     * Provide a ConcreteVerticesGraph for tests in GraphInstanceTest.
     */
    @Override public Graph<String> emptyInstance() {
        return new ConcreteVerticesGraph<String>();
    }
    
    /*
     * Testing ConcreteVerticesGraph...
     */
    
    // Testing strategy for ConcreteVerticesGraph.toString()
    //   TODO
    
    // TODO tests for ConcreteVerticesGraph.toString()
    
    /*
     * Testing Vertex...
     */
    
    // Testing strategy for Vertex
    //   to: 0, 1, 2 vertices
    //   edge: 1, > 1
    
    // tests for operations of Vertex
    @Test public void testAddWeightToOneVertex() {
        Vertex<String> v1 = new Vertex<>("Chicken");
        Vertex<String> v2 = new Vertex<>("Dog");
        assertEquals(v2.addWeightTo(v1, 2), 0);
        assertEquals(v2.addWeightTo(v1, 2), 2);
        assertEquals(v2.addWeightTo(v1, 3), 2);
        assertEquals(v1.addWeightTo(v2, 7), 0);
        assertEquals(v1.addWeightTo(v2, 2), 7);
    }
    
    @Test public void testAddWeightToTwoVertex() {
        Vertex<String> v1 = new Vertex<>("Chicken");
        Vertex<String> v2 = new Vertex<>("Dog");
        Vertex<String> v3 = new Vertex<>("Cat");
        assertEquals(v1.addWeightTo(v2, 8), 0);
        assertEquals(v1.addWeightTo(v3, 8), 0);
        assertEquals(v3.addWeightTo(v1, 1), 0);
        assertEquals(v2.addWeightTo(v1, 8), 0);
    }
    
    @Test public void testAddWeightToOneVertexThenRemove() {
        Vertex<String> v1 = new Vertex<>("Chicken");
        Vertex<String> v2 = new Vertex<>("Dog");
        assertEquals(v1.addWeightTo(v2, 8), 0);
        assertEquals(v1.addWeightTo(v2, 0), 8);
        assertTrue(v1.getVerticesFrom().isEmpty());
    }
    
    @Test public void testAddWeightToTwoVerticesThenRemoveOne() {
        Vertex<String> v1 = new Vertex<>("Chicken");
        Vertex<String> v2 = new Vertex<>("Dog");
        Vertex<String> v3 = new Vertex<>("Cat");
        assertEquals(v1.addWeightTo(v2, 2), 0);
        assertEquals(v1.addWeightTo(v3, 3), 0);
        assertEquals(v1.addWeightTo(v2, 0), 2);
        assertEquals(v1.addWeightTo(v2, 0), 0);
    }
    
    
    
    @Test public void testGetVerticesToZeroVertex() {
        Vertex<String> v1 = new Vertex<>("Chicken");
        assertTrue(v1.getVerticesFrom().isEmpty());
    }
    
    @Test public void testGetVerticesToOneVertexOneWay() {
        Vertex<String> v1 = new Vertex<>("Chicken");
        Vertex<String> v2 = new Vertex<>("");
        Map<Vertex<String>, Integer> target = new HashMap<>();
        
        target.put(v2, 3);
        v1.addWeightTo(v2, 3);
        assertEquals(v1.getVerticesFrom(), target.keySet());
    }
    
    @Test public void testGetVerticesToOneVertexOneWaySize() {
        Vertex<String> v1 = new Vertex<>("Chicken");
        Vertex<String> v2 = new Vertex<>("");
        Map<Vertex<String>, Integer> target = new HashMap<>();
        
        target.put(v2, 3);
        v1.addWeightTo(v2, 3);
        assertEquals(v1.getVerticesFrom().size(), 1);
    }
    
    @Test public void testGetVerticesToOneVertexTwoWay() {
        Vertex<String> v1 = new Vertex<>("Chicken");
        Vertex<String> v2 = new Vertex<>("");
        Map<Vertex<String>, Integer> target1 = new HashMap<>();
        Map<Vertex<String>, Integer> target2 = new HashMap<>();

        target1.put(v2, 3);
        v1.addWeightTo(v2, 3);
        target2.put(v1, 2);
        v2.addWeightTo(v1, 2);
        
        assertEquals(v1.getVerticesFrom(), target1.keySet());
        assertEquals(v2.getVerticesFrom(), target2.keySet());
    }
    
    @Test public void TestGetVerticesToTwoVerticesSize() {
        Vertex<String> v1 = new Vertex<>("Chicken");
        Vertex<String> v2 = new Vertex<>("");
        Vertex<String> v3 = new Vertex<>("Cat");

        v1.addWeightTo(v2, 3);
        assertEquals(v1.getVerticesFrom().size(), 1);
        v1.addWeightTo(v3, 1000);
        assertEquals(v1.getVerticesFrom().size(), 2);
    }
    
    @Test public void TestGetVerticesToTwoVerticesAddWeightOnce() {
        Vertex<String> v1 = new Vertex<>("Chicken");
        Vertex<String> v2 = new Vertex<>("");
        Vertex<String> v3 = new Vertex<>("Cat");
        Map<Vertex<String>, Integer> target = new HashMap<>();

        v1.addWeightTo(v2, 3);
        target.put(v2,  3);
        assertEquals(target.keySet(), v1.getVerticesFrom());
        
        v1.addWeightTo(v3, 1000);
        target.put(v3,  1000);
        assertEquals(target.keySet(), v1.getVerticesFrom());
    }
    
    @Test public void TestGetVerticesToTwoVerticesAddWeightTwice() {
        Vertex<String> v1 = new Vertex<>("Chicken");
        Vertex<String> v2 = new Vertex<>("");
        Vertex<String> v3 = new Vertex<>("Cat");
        Map<Vertex<String>, Integer> target = new HashMap<>();

        v1.addWeightTo(v2, 3);
        target.put(v2,  3);
        assertEquals(target.keySet(), v1.getVerticesFrom());
        
        v1.addWeightTo(v3, 1000);
        v1.addWeightTo(v3, 871);
        target.put(v3,  871);
        assertEquals(target.keySet(), v1.getVerticesFrom());
    }    
    
    @Test public void testGetWeightZeroEdge() {
        Vertex<String> v1 = new Vertex<>("Chicken");
        Vertex<String> v2 = new Vertex<>("");
        assertEquals(v1.getWeightTo(v2), 0);
    }
    
    @Test public void testGetWeightOneEdge() {
        Vertex<String> v1 = new Vertex<>("Chicken");
        Vertex<String> v2 = new Vertex<>("");
        v1.addWeightTo(v2, 3);
        assertEquals(v1.getWeightTo(v2), 3);
        
        v1.addWeightTo(v2, 5);
        assertEquals(v1.getWeightTo(v2), 5);
    }
    
    @Test public void testGetWeightTwoEdges() {
        Vertex<String> v1 = new Vertex<>("Chicken");
        Vertex<String> v2 = new Vertex<>("");
        Vertex<String> v3 = new Vertex<>("Cat");
        v1.addWeightTo(v2, 4);
        assertEquals(v1.getWeightTo(v2), 4);
        
        v2.addWeightTo(v3, 5);
        assertEquals(v2.getWeightTo(v3), 5);
    }
}
