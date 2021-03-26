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
public class ConcreteEdgesGraph<L> implements Graph<L> {
    
    private final Set<L> vertices = new HashSet<>();
    private final List<Edge<L>> edges = new ArrayList<>();
    
    // Abstraction function:
    //   Represent a graph that has String vertices and edges that connect the said vertices: f(G) = {e | e \in G}
    // Representation invariant:
    //   Vertices do not duplicate and non-null (do not have to indicate this in the future)
    //   Edge goes from a source to a target vertex, having positive weight
    // Safety from rep exposure:
    //   Vertices and edges are immutable data types
    //   All fields are private and final
    //   Methods do not return direct reference to mutable data types
    
    // constructor
    public ConcreteEdgesGraph() {
    }
    
    // checkRep
    private void checkRep() {
        for (Edge<L> e : edges) {
            assert e.getWeight() > 0;
//            assert !e.getSource().equals(e.getTarget());
            assert vertices.contains(e.getSource()) && vertices.contains(e.getTarget());
        }
    }
    
    @Override public boolean add(L vertex) {
        if (!vertices.contains(vertex)) { 
            vertices.add(vertex);
            checkRep();
            return true;
        }
        return false;
    }
    
    @Override public int set(L source, L target, int weight) {
        if (weight < 0) throw new RuntimeException();
        int prev = 0;
        if (vertices.contains(source) && vertices.contains(target)){
            for (Iterator<Edge<L>> iterator = edges.iterator(); iterator.hasNext();) {
                Edge<L> e = iterator.next();
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
        if (weight > 0) edges.add(new Edge<L>(source, target, weight));
        checkRep();
        return prev;
    }
    
    @Override public boolean remove(L vertex) {
        if (vertices.contains(vertex)) {
            vertices.remove(vertex);
            for (Iterator<Edge<L>> iterator = edges.iterator(); iterator.hasNext();) {
                Edge<L> e = iterator.next();
                if (e.getSource().equals(vertex) || e.getTarget().equals(vertex)) {
                    iterator.remove();
                }
            }
            checkRep();
            return true;
        }
        return false;
    }
    
    @Override public Set<L> vertices() {
        return new TreeSet<L>(vertices);
    }
    
    @Override public Map<L, Integer> sources(L target) {
        Map<L, Integer> sources = new HashMap<>();
        for (Edge<L> e : edges) {
            if (e.getTarget().equals(target)) sources.put(e.getSource(), e.getWeight());
        }
        return sources;
    }
    
    @Override public Map<L, Integer> targets(L source) {
        Map<L, Integer> targets = new HashMap<>();
        for (Edge<L> e : edges) {
            if (e.getSource().equals(source)) targets.put(e.getTarget(), e.getWeight());
        }
        return targets;
    }
    
    // toString()
    // Strengthen the spec?
    @Override public String toString() {
        if (vertices.isEmpty()) return new String("The graph is empty, nothing to print");
        String all = new String("Vertices: ");
        for (L v : vertices) {
            all = all + v + " ";
        }
        all = all + "\nEdges: ";
        if (edges.isEmpty()) return all + "empty edges";
        for (Edge<L> e : edges) {
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
class Edge<L> {
    
    private final int weight;
    private final L source;
    private final L target;
    
    // Abstraction function:
    //   Represent the connection between source -> target and its weight
    // Representation invariant:
    //   weight > 0 and non-null (doesn't need to specify this though)
    //   source != target - not correct! 
    // Safety from rep exposure:
    //   All fields are private and all types in the rep are immutable.
    
    // constructor
    public Edge(L source, L target, int weight) {
        this.source = source;
        this.target = target;
        this.weight = weight;
        checkRep();
    }
    
    // checkRep
    // Check that rep invariant is true
    private void checkRep() {
        assert weight > 0;
//        assert (!source.equals(target));
    }
    
    // methods
    /** @return source of the edge */
    public L getSource() {
        return source;
    }
    
    /** @return target of the edge */
    public L getTarget() {
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
