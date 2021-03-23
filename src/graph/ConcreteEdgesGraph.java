/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import java.util.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * An implementation of Graph.
 * 
 * <p>PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteEdgesGraph implements Graph<String> {
    
    private final Set<String> vertices = new HashSet<>();
    private final List<Edge> edges = new ArrayList<>();
    
    // Abstraction function:
    //   Represent a graph that has String vertices and edges that connect the said vertices: f(G) = {e | e \in G}
    // Representation invariant:
    //   Vertices do not duplicate and non-null (do not have to indicate this in the future)
    //   Edge goes from a source to a target vertex, having positive weight
    // Safety from rep exposure:
    //   Vertices and edges are immutable data types
    //   All fields are private and final
    
    // constructor
    public ConcreteEdgesGraph() {
    }
    
    // checkRep
    private void checkRep() {
        for (Edge e : edges) {
            assert e.getWeight() > 0;
            assert !e.getSource().equals(e.getTarget());
            assert vertices.contains(e.getSource()) && vertices.contains(e.getTarget());
        }
    }
    
    @Override public boolean add(String vertex) {
        if (!vertices.contains(vertex)) { 
            vertices.add(vertex);
            checkRep();
            return true;
        }
        return false;
    }
    
    @Override public int set(String source, String target, int weight) {
        if (weight < 0) throw new RuntimeException();
        int prev = 0;
        if (vertices.contains(source) && vertices.contains(target)){
            for (Iterator<Edge> iterator = edges.iterator(); iterator.hasNext();) {
                Edge e = iterator.next();
                if (e.getSource().equals(source) && e.getTarget().equals(target)) {
                  prev = e.getWeight();
                  iterator.remove();
                }
            }
        }
        else {
            vertices.add(target);
            vertices.add(source);
        }
        if (weight > 0) edges.add(new Edge(source, target, weight));
        checkRep();
        return prev;
    }
    
    @Override public boolean remove(String vertex) {
        if (vertices.contains(vertex)) {
            vertices.remove(vertex);
            for (Iterator<Edge> iterator = edges.iterator(); iterator.hasNext();) {
                Edge e = iterator.next();
                if (e.getSource().equals(vertex) || e.getTarget().equals(vertex)) {
                    iterator.remove();
                }
            }
            checkRep();
            return true;
        }
        return false;
    }
    
    @Override public Set<String> vertices() {
        return new TreeSet<String>(vertices);
    }
    
    @Override public Map<String, Integer> sources(String target) {
        Map<String, Integer> sources = new HashMap<>();
        for (Edge e : edges) {
            if (e.getTarget().equals(target)) sources.put(e.getSource(), e.getWeight());
        }
        return sources;
    }
    
    @Override public Map<String, Integer> targets(String source) {
        Map<String, Integer> targets = new HashMap<>();
        for (Edge e : edges) {
            if (e.getSource().equals(source)) targets.put(e.getTarget(), e.getWeight());
        }
        return targets;
    }
    
    // toString()
    // Strengthen the spec?
    @Override public String toString() {
        if (vertices.isEmpty()) return new String("The graph is empty, nothing to print");
        String all = new String("Vertices: ");
        for (String v : vertices) {
            all = all + v + " ";
        }
        all = all + "\nEdges: ";
        if (edges.isEmpty()) return all + "empty edges";
        for (Edge e : edges) {
            all = all + e.toString();
        }
        return all;
    }
}

/**
 * 
 * Immutable.
 * This class is internal to the rep of ConcreteEdgesGraph.
 * 
 * <p>PS2 instructions: the specification and implementation of this class is
 * up to you.
 * 
 * Edge is an immutable data type that contains non-negative weight.
 * Edge is directed. 
 * 
 * @param weight weight of the connection
 * @param source the vertex source
 * @param target the vertex target
 * 
 */
class Edge {
    
    private final int weight;
    private final String source;
    private final String target;
    
    // Abstraction function:
    //   Represent the connection between source -> target and its weight
    // Representation invariant:
    //   weight > 0 and non-null (doesn't need to specify this though)
    //   source != target
    // Safety from rep exposure:
    //   All fields are private and all types in the rep are immutable.
    
    // constructor
    public Edge(String source, String target, int weight) {
        this.source = source;
        this.target = target;
        this.weight = weight;
        checkRep();
    }
    
    // checkRep
    // Check that rep invariant is true
    private void checkRep() {
        assert weight > 0;
        assert (!source.equals(target));
    }
    
    // methods
    /** @return source of the edge */
    public String getSource() {
        return source;
    }
    
    /** @return target of the edge */
    public String getTarget() {
        return target;
    }
    
    /** @return weight of the edge */
    public int getWeight() {
        return weight;
    }
    
    // toString()
    @Override public String toString() {
        String rep = getSource() + " ---> " + getTarget() + ", weight = " + getWeight() + "\n";
        return rep;
    }
}
