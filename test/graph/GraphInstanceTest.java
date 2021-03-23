/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.Test;

/**
 * Tests for instance methods of Graph.
 * 
 * <p>PS2 instructions: you MUST NOT add constructors, fields, or non-@Test
 * methods to this class, or change the spec of {@link #emptyInstance()}.
 * Your tests MUST only obtain Graph instances by calling emptyInstance().
 * Your tests MUST NOT refer to specific concrete implementations.
 */
public abstract class GraphInstanceTest {
    
    // Testing strategy
    //   TODO
    
    /**
     * Overridden by implementation-specific test classes.
     * 
     * @return a new empty graph of the particular implementation being tested
     */
    public abstract Graph<String> emptyInstance();
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testInitialVerticesEmpty() {
        // TODO you may use, change, or remove this test
        assertEquals("expected new graph to have no vertices",
                Collections.emptySet(), emptyInstance().vertices());
    }
    
    // TODO other tests for instance methods of Graph
    // add():
    //  return: true, false
    @Test
    public void testAddVertexWhenThereIsNot() {
        Graph<String> graph = emptyInstance();
        assertTrue(graph.add("something"));
    }
    
    @Test
    public void testAddVertexWhenThereAlreadyIs() {
        Graph<String> graph = emptyInstance();
        String s1 = new String("something");
        String s2 = new String("something");
        graph.add(s1);
        assertFalse(graph.add(s2));
    }
    
    // set():
    //    weight: 0, > 0 
    //    vertices: exist, do not exist
    //    source: the same/not the same as target
    //    string length: empty, not empty
    //    position: swap target and source
            
    @Test
    public void testSetNonZeroWeight() {
        Graph<String> graph = emptyInstance();
        String s1 = new String("one");
        String s2 = new String("two");
        graph.add(s1);
        graph.add(s2);
        int prevWeight = graph.set(s1, s2, 1);
        assertEquals(0, prevWeight);
        prevWeight = graph.set(s1, s2, 2);
        assertEquals(1, prevWeight);
    }
    
    @Test
    public void testSetZeroWeightNotRemove() {
        Graph<String> graph = emptyInstance();
        String s1 = new String("one");
        String s2 = new String("two");
        graph.add(s1);
        graph.add(s2);
        graph.set(s1, s2, 0);
        int prevWeight = graph.set(s1, s2, 2);
        assertEquals(prevWeight, 0);
    }
    
    @Test
    public void testSetZeroWeightDoesRemove() {
        Graph<String> graph = emptyInstance();
        String s1 = new String("one");
        String s2 = new String("two");
        graph.add(s1);
        graph.add(s2);
        graph.set(s1, s2, 5);
        graph.set(s1, s2, 0);
        int prevWeight = graph.set(s1, s2, 2);
        assertEquals(prevWeight, 0);
    }
    
    @Test
    public void testSourceExistsTargetEmpty() {
        Graph<String> graph = emptyInstance();
        String s1 = new String("one");
        String s2 = new String("");
        graph.add(s1);
        graph.add(s2);
        int prevWeight = graph.set(s1, s2, 2);
        assertEquals(prevWeight, 0);
        prevWeight = graph.set(s1, s2, 8);
        assertEquals(prevWeight, 2);
    }
    
    @Test
    public void testSourceEmptyTargetExists() {
        Graph<String> graph = emptyInstance();
        String s1 = new String("one");
        String s2 = new String("");
        graph.add(s1);
        graph.add(s2);
        int prevWeight = graph.set(s2, s1, 2);
        assertEquals(prevWeight, 0);
        prevWeight = graph.set(s2, s1, 3);
        assertEquals(prevWeight, 2);
    }   
    
    @Test
    public void testSwapSourceAndTarget() {
        Graph<String> graph = emptyInstance();
        String s1 = new String("one");
        String s2 = new String("");
        graph.add(s1);
        graph.add(s2);
        int prevWeight = graph.set(s2, s1, 2);
        assertEquals(prevWeight, 0);
        prevWeight = graph.set(s1, s2, 10);
        assertEquals(prevWeight, 0);
    }
    
    // remove(): 
    //    existing vertices: 0, 1, 2
    //    existing edges: 0, > 0
    
    @Test
    public void testRemoveWhenThereIsZeroVertice() {
        Graph<String> graph = emptyInstance();
        boolean removed = graph.remove(new String("fly"));
        assertEquals("expected new graph to have no vertices",
                Collections.emptySet(), graph.vertices());
        assertFalse(removed);
    }
    
    @Test
    public void testRemoveWhenThereIsOneVertice() {
        Graph<String> graph = emptyInstance();
        graph.add(new String("Nice"));
        boolean removed = graph.remove(new String("Nice"));
        assertTrue(removed);
        assertEquals("expected new graph to have no vertices",
                Collections.emptySet(), graph.vertices());
    }
    
    @Test
    public void testRemoveWhenThereAreTwoVerticesRemoveTrue() {
        Graph<String> graph = emptyInstance();
        Set<String> collections = new TreeSet<>();
        graph.add(new String("Nice"));
        graph.add(new String("Haha"));
        boolean removed = graph.remove(new String("Nice"));
        assertTrue(removed);
        
        collections.add("Haha");
        assertTrue(collections.equals(graph.vertices()));
    }
    
    @Test
    public void testRemoveWhenThereAreTwoVerticesRemoveFalse() {
        Graph<String> graph = emptyInstance();
        Set<String> collections = new TreeSet<>();
        graph.add(new String("Nice"));
        graph.add(new String("Haha"));
        
        collections.add(new String("Nice"));
        collections.add(new String("Haha"));
        boolean removed = graph.remove(new String("What?"));
        assertFalse(removed);
        assertTrue(collections.equals(graph.vertices()));
    }
    
    @Test
    public void testRemoveWhenThereIsOneEdge() {
        Graph<String> graph = emptyInstance();
        String s1 = new String("one");
        String s2 = new String("two");
        graph.add(s1);
        graph.add(s2);
        
        graph.set(s2, s1, 2);
        boolean removed = graph.remove(new String("one"));
        assertTrue(removed);
        assertEquals(0, graph.set(s2, s1, 2));
    }
    
    // vertices():
    //    Size: 0, 1, 2
    //    content: empty, non-empty
    
    @Test
    public void testVerticesOfSizeZero() {
        Graph<String> graph = emptyInstance();
        Set<String> collections = new TreeSet<>();
        assertTrue(collections.equals(graph.vertices()));
    }
    
    @Test
    public void testVerticesOfSizeOne() {
        Graph<String> graph = emptyInstance();
        graph.add(new String("Something"));
        Set<String> collections = new TreeSet<>();
        collections.add(new String("Something"));
        assertTrue(collections.equals(graph.vertices()));
    }
    
    @Test
    public void testVerticesOfSizeTwo() {
        Graph<String> graph = emptyInstance();
        graph.add(new String("Something"));
        graph.add(new String(""));
        Set<String> collections = new TreeSet<>();
        collections.add(new String("Something"));
        collections.add(new String(""));
        assertTrue(collections.equals(graph.vertices()));
    }
    
    // sources():
    //    size of source vertices: 0, 1, 2, >2
    //    weight: 0, > 0 
    //    number of edges: 0, 1, >1
    //    target: exist, not exist
    
    @Test
    public void testSourcesWhenGraphIsEmpty() {
        Graph<String> graph = emptyInstance();
        Map<String, Integer> sourceGraph = graph.sources("ye");
        assertTrue(sourceGraph.isEmpty());
    }
    
    @Test
    public void testSourcesWhenGraphHasOneVertex() {
        Graph<String> graph = emptyInstance();
        graph.add("Something");
        Map<String, Integer> sourceGraph = graph.sources("Something");
        assertTrue(sourceGraph.isEmpty());
    }
    
    @Test
    public void testSourcesNoEdge() {
        Graph<String> graph = emptyInstance();
        graph.add("Something");
        graph.add("King");
        
        Map<String, Integer> sourceGraph = graph.sources("Something");
        assertTrue(sourceGraph.isEmpty());
        
        sourceGraph = graph.sources("King");
        assertTrue(sourceGraph.isEmpty());
    }
    
    @Test
    public void testSourcesOneEdgeTwoVertices() { 
        Graph<String> graph = emptyInstance();
        graph.add("Something");
        graph.add("King");
        graph.set("Something", "King", 1);
        
        Map<String, Integer> sourceGraph = graph.sources("King");
        Map<String, Integer> sourceTest = new HashMap<>();
        sourceTest.put("Something", 1);
        assertTrue(sourceTest.equals(sourceGraph));        
    }
    
    @Test
    public void testSourcesTwoEdgeTwoVertices() { 
        Graph<String> graph = emptyInstance();
        graph.add("Something");
        graph.add("King");
        graph.set("Something", "King", 1); // source is Something, target is King
        graph.set("King", "Something", 2); // source is King, target is Something
        
        Map<String, Integer> sourceGraph1 = graph.sources("Something");
        Map<String, Integer> sourceGraph2 = graph.sources("King");
        Map<String, Integer> sourceTest1 = new HashMap<>();
        Map<String, Integer> sourceTest2 = new HashMap<>();
        
        sourceTest1.put("King", 2);
        sourceTest2.put("Something", 1);
        assertTrue(sourceTest1.equals(sourceGraph1));
        assertTrue(sourceTest2.equals(sourceGraph2));
    }

    @Test
    public void testSourcesLabelNotFound() {
        Graph<String> graph = emptyInstance();
        graph.add("Something");
        graph.add("King");
        graph.set("Something", "King", 1); // source is Something, target is King
        
        Map<String, Integer> sourceGraph1 = graph.sources("king");
        assertTrue(sourceGraph1.isEmpty());
    }
    
    @Test
    public void testSourcesZeroWeight() {
        Graph<String> graph = emptyInstance();
        graph.add("Something");
        graph.add("King");
        graph.set("Something", "King", 0); // source is Something, target is King
        
        Map<String, Integer> sourceGraph = graph.sources("King");
        assertTrue(sourceGraph.isEmpty());
    }
    
    @Test
    public void testSourcesThreeVerticesOneEdge() {
        Graph<String> graph = emptyInstance();
        graph.add("Pig");
        graph.add("Chicken");
        graph.add("Dog");
        graph.set("Pig", "Dog", 999);
        
        Map<String, Integer> sourceGraph = graph.sources("Dog");
        Map<String, Integer> sourceTest = new HashMap<>();
        sourceTest.put("Pig", 999);
        assertFalse(sourceGraph.isEmpty());
        assertEquals(sourceGraph.size(), 1);
        assertTrue(sourceGraph.equals(sourceTest));
        assertFalse(sourceGraph.containsKey("Dog"));
    }
    
    @Test
    public void testSourcesThreeVerticesTwoEdgeSameTarget() {
        Graph<String> graph = emptyInstance();
        graph.add("Pig");
        graph.add("Chicken");
        graph.add("Dog");
        graph.set("Pig", "Dog", 999);
        graph.set("Pig", "Chicken", 1);
        
        Map<String, Integer> sourceGraph1 = graph.sources("Dog");
        Map<String, Integer> sourceGraph2 = graph.sources("Chicken");
        assertEquals(sourceGraph1.size(), 1);
        assertEquals(sourceGraph1.keySet(), sourceGraph2.keySet());
        
        Map<String, Integer> sourceTest1 = new HashMap<>();
        Map<String, Integer> sourceTest2 = new HashMap<>();
        
        sourceTest1.put("Pig", 999);
        sourceTest2.put("Pig", 1);
        assertTrue(sourceTest1.equals(sourceGraph1));
        assertTrue(sourceTest2.equals(sourceGraph2));
    }
    
    @Test
    public void testSourcesThreeVerticesTwoEdgeDifferentTarget() {
        Graph<String> graph = emptyInstance();
        graph.add("Pig");
        graph.add("Chicken");
        graph.add("Dog");
        graph.set("Pig", "Dog", 999);
        graph.set("Dog", "Chicken", 1);
        
        Map<String, Integer> sourceGraph1 = graph.sources("Dog");
        Map<String, Integer> sourceGraph2 = graph.sources("Chicken");
        assertEquals(sourceGraph1.size(), 1);
        assertEquals(sourceGraph2.size(), 1);
        
        Map<String, Integer> sourceTest1 = new HashMap<>();
        Map<String, Integer> sourceTest2 = new HashMap<>();
        sourceTest1.put("Pig", 999);
        sourceTest2.put("Dog", 1);
        assertTrue(sourceTest1.equals(sourceGraph1));
        assertTrue(sourceTest2.equals(sourceGraph2));
    }
    
    // target():
    //    size of target vertices: 0, 1, 2, >2
    //    weight: 0, > 0 
    //    number of edges: 0, 1, >1
    //    source: exist, not exist
    
    @Test
    public void testTargetsWhenGraphIsEmpty() {
        Graph<String> graph = emptyInstance();
        Map<String, Integer> sourceGraph = graph.targets("ye");
        assertTrue(sourceGraph.isEmpty());
    }
    
    @Test
    public void testTargetsWhenGraphHasOneVertex() {
        Graph<String> graph = emptyInstance();
        graph.add("Something");
        Map<String, Integer> sourceGraph = graph.targets("Something");
        assertTrue(sourceGraph.isEmpty());
    }
    
    @Test
    public void testTargetsNoEdge() {
        Graph<String> graph = emptyInstance();
        graph.add("Something");
        graph.add("King");
        
        Map<String, Integer> sourceGraph = graph.targets("Something");
        assertTrue(sourceGraph.isEmpty());
        
        sourceGraph = graph.targets("King");
        assertTrue(sourceGraph.isEmpty());
    }
    
    @Test
    public void testTargetsOneEdgeTwoVertices() { 
        Graph<String> graph = emptyInstance();
        graph.add("Something");
        graph.add("King");
        graph.set("Something", "King", 1);
        
        Map<String, Integer> targetGraph = graph.targets("Something");
        Map<String, Integer> targetTest = new HashMap<>();
        targetTest.put("King", 1);
        assertTrue(targetTest.equals(targetGraph));        
    }
    
    @Test
    public void testTargetsTwoEdgeTwoVertices() { 
        Graph<String> graph = emptyInstance();
        graph.add("Something");
        graph.add("King");
        graph.set("Something", "King", 1); // source is Something, target is King
        graph.set("King", "Something", 2); // source is King, target is Something
        
        Map<String, Integer> targetGraph1 = graph.targets("Something");
        Map<String, Integer> targetGraph2 = graph.targets("King");
        Map<String, Integer> targetTest1 = new HashMap<>();
        Map<String, Integer> targetTest2 = new HashMap<>();
        
        targetTest1.put("King", 1);
        targetTest2.put("Something", 2);
        assertTrue(targetTest1.equals(targetGraph1));
        assertTrue(targetTest2.equals(targetGraph2));
    }
    
    @Test
    public void testTargetsLabelNotFound() {
        Graph<String> graph = emptyInstance();
        graph.add("Something");
        graph.add("King");
        graph.set("Something", "King", 1); // source is Something, target is King
        
        Map<String, Integer> targetGraph1 = graph.targets("king");
        assertTrue(targetGraph1.isEmpty());
    }
    
    @Test
    public void testTargetsZeroWeight() {
        Graph<String> graph = emptyInstance();
        graph.add("Something");
        graph.add("King");
        graph.set("Something", "King", 0); // source is Something, target is King
        
        Map<String, Integer> targetGraph = graph.targets("King");
        assertTrue(targetGraph.isEmpty());
    }
    
    @Test
    public void testTargetsThreeVerticesOneEdge() {
        Graph<String> graph = emptyInstance();
        graph.add("Pig");
        graph.add("Chicken");
        graph.add("Dog");
        graph.set("Pig", "Dog", 999);
        
        Map<String, Integer> targetGraph = graph.targets("Pig");
        Map<String, Integer> targetTest = new HashMap<>();
        targetTest.put("Dog", 999);
        assertFalse(targetGraph.isEmpty());
        assertEquals(targetGraph.size(), 1);
        assertFalse(targetGraph.containsKey("Pig"));
        assertFalse(targetGraph.containsKey("Chicken"));
        assertTrue(targetGraph.equals(targetTest));
    }
    
    @Test
    public void testTargetsThreeVerticesTwoEdgeSameSource() {
        Graph<String> graph = emptyInstance();
        graph.add("Pig");
        graph.add("Chicken");
        graph.add("Dog");
        graph.set("Pig", "Dog", 999);
        graph.set("Pig", "Chicken", 1);
        
        Map<String, Integer> targetGraph = graph.targets("Pig");
        assertEquals(targetGraph.size(), 2);
        
        Map<String, Integer> targetTest = new HashMap<>();
        targetTest.put("Dog", 999);
        targetTest.put("Chicken", 1);        
        assertTrue(targetTest.equals(targetGraph));
    }
    
    @Test
    public void testSourcesThreeVerticesTwoEdgeDifferentSource() {
        Graph<String> graph = emptyInstance();
        graph.add("Pig");
        graph.add("Chicken");
        graph.add("Dog");
        graph.set("Pig", "Dog", 999);
        graph.set("Dog", "Chicken", 1);
        
        Map<String, Integer> targetGraph1 = graph.targets("Dog");
        Map<String, Integer> targetGraph2 = graph.targets("Pig");
        assertEquals(targetGraph1.size(), 1);
        assertEquals(targetGraph2.size(), 1);
        
        Map<String, Integer> targetTest1 = new HashMap<>();
        Map<String, Integer> targetTest2 = new HashMap<>();
        targetTest1.put("Chicken", 1);
        targetTest2.put("Dog", 999);
        assertTrue(targetTest1.equals(targetGraph1));
        assertTrue(targetTest2.equals(targetGraph2));
    }
}

