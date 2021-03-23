/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests for ConcreteEdgesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteEdgesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteEdgesGraphTest extends GraphInstanceTest {
    
    /*
     * Provide a ConcreteEdgesGraph for tests in GraphInstanceTest.
     */
    @Override public Graph<String> emptyInstance() {
        return new ConcreteEdgesGraph<String>();
    }
    
    /*
     * Testing ConcreteEdgesGraph...
     */
    
    // Testing strategy for ConcreteEdgesGraph.toString()
    //   size: 0, 1, 2
    //   edge: 0, 1
    //   label: empty and non-empty
    
    // tests for ConcreteEdgesGraph.toString()
    @Test public void testToStringEmptyGraph() {
        Graph<String> graph = emptyInstance();
        String print = new String("The graph is empty, nothing to print");
        assertTrue(print.equals(graph.toString()));
    }
    
    @Test public void testToStringGraphOneVertice() {
        Graph<String> graph = emptyInstance();
        graph.add("Chicken");
        String print = new String("Vertices: Chicken \nEdges: empty edges");
        assertTrue(print.equals(graph.toString()));
    }
    
    @Test public void testToStringGraphTwoVerticesNoEdge() {
        Graph<String> graph = emptyInstance();
        graph.add("Chicken");
        graph.add("Dog");
        String print = new String("Vertices: Chicken Dog \nEdges: empty edges");
        assertTrue(print.equals(graph.toString()));
    }
    
    @Test public void testToStringGraphTwoVerticesOneEdge() {
        Graph<String> graph = emptyInstance();
        graph.add("Chicken");
        graph.add("Dog");
        graph.set("Chicken", "Dog", 2);
        String print = new String("Vertices: Chicken Dog \nEdges: Chicken ---> Dog, weight = 2\n");
        assertTrue(print.equals(graph.toString()));
    }
    
    @Test public void testToStringGraphTwoVerticesTwoEdges() {
        Graph<String> graph = emptyInstance();
        graph.add("Chicken");
        graph.add("Dog");
        graph.set("Chicken", "Dog", 2);
        graph.set("Dog", "Chicken", 19);
        String print = new String("Vertices: Chicken Dog \nEdges: Chicken ---> Dog, weight = 2\nDog ---> Chicken, weight = 19\n");
        assertTrue(print.equals(graph.toString()));
    }
    
    /*
     * Testing Edge...
     */
    
    // Testing strategy for Edge
    //   weight: 1, >1
    //   source length = 0, 1, >1
    //   target length = 0, 1, > 1
    
    // tests for operations of Edge
    @Test public void testWeightOfEdge() {
        Edge<String> edge = new Edge<>("Pig", "Chicken", 1);
        assertEquals(edge.getWeight(), 1);
    }
    
    @Test public void testSourceOfEdge() {
        Edge<String> edge = new Edge<>("Pig", "Chicken", 1);
        assertEquals(edge.getSource(), "Pig");
    }
    
    @Test public void testTargetOfEdge() {
        Edge<String> edge = new Edge<>("Pig", "Chicken", 1);
        assertEquals(edge.getTarget(), "Chicken");
    }
    
    @Test public void testOneCharacterSource() {
        Edge<String> edge = new Edge<>("a", "Chicken", 1);
        assertEquals(edge.getSource(), "a");    
    }
    
    @Test public void testOneCharacterTarget() {
        Edge<String> edge = new Edge<>("Pig", "c", 1);
        assertEquals(edge.getTarget(), "c");    
    }
    
    @Test public void testZeroLengthSource() {
        Edge<String> edge = new Edge<>("", "Pig", 2);
        assertEquals(edge.getSource(), "");
    }
    
    @Test public void testZeroLengthTarget() {
        Edge<String> edge = new Edge<>("Pig", "", 2);
        assertEquals(edge.getTarget(), "");
    }
    
    @Test public void testMultipleWeightsOfEdges() {
        Edge<String> edge = new Edge<>("Pig", "Chicken", 999);
        assertEquals(edge.getWeight(), 999);
        
        edge = new Edge<>("Pig", "Chicken", 1425);
        assertEquals(edge.getWeight(), 1425);
        
        edge = new Edge<>("Pig", "Chicken", 100);
        assertEquals(edge.getWeight(), 100);
    }
}
