/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import java.util.ArrayList;
import java.util.*;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * An implementation of Graph.
 * 
 * <p>PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteVerticesGraph<L> implements Graph<L> {
    
    private final List<Vertex<L>> vertices = new ArrayList<>();
    
    // Abstraction function:
    //   A collection of vertices that comprise a graph
    // Representation invariant:
    //   Labels of the vertices in {@code vertices} don't duplicate
    // Safety from rep exposure:
    //   Field is private
    //   Do not return direct reference to {@code vertices}
    
    // constructor
    public ConcreteVerticesGraph() {
    }
    
    // checkRep
    public void checkRep() {
//        Set<Vertex> set = new HashSet<>(vertices);
//        assert set.size() == vertices.size();
        Set<L> s = new HashSet<>();
        for (Vertex<L> v : vertices) {
            s.add(v.getLabel());
        }
        assert s.size() == vertices.size();
    }
    
    
    @Override public boolean add(L vertex) {
        for (Vertex<L> ver : vertices) {
            if (ver.getLabel().equals(vertex)) return false;
        }
        vertices.add(new Vertex<L>(vertex));
        checkRep();
        return true;
    }
    
    @Override public int set(L source, L target, int weight) {
        int prev = 0;
//        Vertex<L> s = new Vertex<L>("");
//        Vertex<L> t = new Vertex<L>("");
        Vertex<L> s = null;
        Vertex<L> t = null;
        add(source);
        add(target);
        for (Vertex<L> v : vertices) {
            if (v.getLabel().equals(source)) s = v;
            if (v.getLabel().equals(target)) t = v;
        }
        prev = s.addWeightTo(t, weight);
        checkRep();
        return prev;
    }
    
    @Override public boolean remove(L vertex) {
        for (Iterator<Vertex<L>> iterator = vertices.iterator(); iterator.hasNext();) {
            Vertex<L> v = iterator.next();
            if (v.getLabel().equals(vertex)) {
                iterator.remove();
                checkRep();
                return true;
            }
        }
        checkRep();
        return false;
    }
    
    @Override public Set<L> vertices() {
        Set<L> vers = new HashSet<>();
        for (Vertex<L> v : vertices) {
            vers.add(v.getLabel());
        }
        return vers;
    }
    
    @Override public Map<L, Integer> sources(L target) {
        Map<L, Integer> sources = new HashMap<>();
        for (Vertex<L> v : vertices) {
            Set<Vertex<L>> setV = v.getVerticesFrom();
            for (Vertex<L> j : setV) {
                if (j.getLabel().equals(target)) {
                    sources.put(v.getLabel(), v.getWeightTo(j));
                    break;
                }
            }
        }
        checkRep();
        return sources;
    }
    
    @Override public Map<L, Integer> targets(L source) {
        Map<L, Integer> targets = new HashMap<>();
        for (Vertex<L> v : vertices) {
            if (v.getLabel().equals(source)) {
                Set<Vertex<L>> setV = v.getVerticesFrom();
                for (Vertex<L> ve : setV) {
                    targets.put(ve.getLabel(), v.getWeightTo(ve));
                }
                break;
            }
        }
        checkRep();
        return targets;
    }
    
    // toString()
    @Override public String toString() {
        StringBuilder rep = new StringBuilder();
        for (Vertex<L> v : vertices) {
            rep.append(v.toString() + "\n");
        }
        return rep.toString();
    }
}

/**
 * specification
 * Mutable.
 * This class is internal to the rep of ConcreteVerticesGraph.
 * 
 * This mutable data type represents role of vertex in a graph.
 * Each vertex indicates the vertices that are pointing to that vertex, and the vertices
 * that vertex is pointing to. 
 * 
 * Vertex contains information about the edges: having non-negative weight of type {@code int} and are directed.
 * Vertices have distinct labels of type {@code String}.
 * 
 * @param 
 */
class Vertex<L> {
    
    // fields
    private final Map<Vertex<L>, Integer> to = new HashMap<>();
    private final L label;
    
    // Abstraction function:
    //   Each vertex indicates the vertices that are pointing to that vertex, and the vertices
    //   that vertex is pointing to. 
    // Representation invariant:
    //   Vertices cannot be a key in filed 'to'
    //   Vertices don't duplicate
    //   Edge goes from a source to a target vertex, having positive weight
    // Safety from rep exposure:
    //   All fields are private.
    //   Label is immutable
    //   Does not return direct references to the mutable fields.
    
    // constructor
    public Vertex(L label) {
        this.label = label;
        checkRep();
    }
    
//    public Vertex() {
//        this.label = new L;
//    }
    
    // checkRep
    private void checkRep() {
        for (Vertex<L> v : to.keySet()) {
            assert (v.getLabel() != getLabel());
        }
        for (Integer i : to.values()) {
            assert (i > 0);
        }
    }
    // methods
    
    /** @return the weight to vertex v from the main vertex. 
     * Return zero if no weight exists */ 
    public int getWeightTo(Vertex<L> v) {
        if (to.containsKey(v)) return to.get(v);
        return 0;
    }
    
    /** @return the vertices that point to the main vertex */
    public Set<Vertex<L>> getVerticesFrom() {
        return to.keySet();
    }
    
    /** 
     * Set the (new) weight for a vertex connection (main -> target). 
     * If the connection didn't exist,
     * add target vertex to the field. 
     * If {@code weight} is specified as 0, remove the connection.
     * 
     * @param target the target vertex
     * @param weight the non-negative weight.
     * @return the previous weight; return 0 if the vertices were not connected
     */
    public int addWeightTo(Vertex<L> target, int weight) {
        if (weight < 0) throw new RuntimeException("Weight must be larger than 0");
        int prev = 0;
        if (to.containsKey(target)) {
            prev = getWeightTo(target);
            if (weight == 0) to.remove(target);
        }
        if (weight != 0) to.put(target, weight);
        checkRep();
        return prev;
    }
    
    /**
     * Get label of the main vertex
     * 
     * @return label of the main vertex
     */
    public L getLabel() {
        return label;
    }
    
    /**
     * Check if the vertices are equal
     * 
     * @return true if the two vertices are equal, false otherwise
     */
    
    // toString()
    @Override public String toString() {
        StringBuilder rep = new StringBuilder("From vertex: " + getLabel());
        Set<Vertex<L>> target = getVerticesFrom();
        for (Vertex<L> v : target) {
            rep.append(getLabel() + " ---> " + v.getLabel() + ", weight = " + getWeightTo(v) + "\n");
        }
        return rep.toString();
    }
}
