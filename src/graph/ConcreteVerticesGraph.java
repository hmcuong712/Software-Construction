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
public class ConcreteVerticesGraph implements Graph<String> {
    
    private final List<Vertex> vertices = new ArrayList<>();
    
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
        Set<String> s = new HashSet<>();
        for (Vertex v : vertices) {
            s.add(v.getLabel());
        }
        assert s.size() == vertices.size();
    }
    
    
    @Override public boolean add(String vertex) {
        for (Vertex ver : vertices) {
            if (ver.getLabel().equals(vertex)) return false;
        }
        vertices.add(new Vertex(vertex));
        checkRep();
        return true;
    }
    
    @Override public int set(String source, String target, int weight) {
        int prev = 0;
        Vertex s = new Vertex("");
        Vertex t = new Vertex("");
        add(source);
        add(target);
        for (Vertex v : vertices) {
            if (v.getLabel().equals(source)) s = v;
            if (v.getLabel().equals(target)) t = v;
        }
        prev = s.addWeightTo(t, weight);
        checkRep();
        return prev;
    }
    
    @Override public boolean remove(String vertex) {
        for (Iterator<Vertex> iterator = vertices.iterator(); iterator.hasNext();) {
            Vertex v = iterator.next();
            if (v.getLabel().equals(vertex)) {
                iterator.remove();
                checkRep();
                return true;
            }
        }
        checkRep();
        return false;
    }
    
    @Override public Set<String> vertices() {
        Set<String> vers = new HashSet<>();
        for (Vertex v : vertices) {
            vers.add(v.getLabel());
        }
        return vers;
    }
    
    @Override public Map<String, Integer> sources(String target) {
        Map<String, Integer> sources = new HashMap<>();
        for (Vertex v : vertices) {
            Set<Vertex> setV = v.getVerticesFrom();
            for (Vertex j : setV) {
                if (j.getLabel().equals(target)) {
                    sources.put(v.getLabel(), v.getWeightTo(j));
                    break;
                }
            }
        }
        checkRep();
        return sources;
    }
    
    @Override public Map<String, Integer> targets(String source) {
        Map<String, Integer> targets = new HashMap<>();
        for (Vertex v : vertices) {
            if (v.getLabel().equals(source)) {
                Set<Vertex> setV = v.getVerticesFrom();
                for (Vertex ve : setV) {
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
        for (Vertex v : vertices) {
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
class Vertex {
    
    // fields
    private final Map<Vertex, Integer> to = new HashMap<>();
    private final String label;
    
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
    public Vertex(String label) {
        this.label = label;
        checkRep();
    }
    
    // checkRep
    private void checkRep() {
        for (Vertex v : to.keySet()) {
            assert (v.getLabel() != getLabel());
        }
        for (Integer i : to.values()) {
            assert (i > 0);
        }
    }
    // methods
    
    /** @return the weight to vertex v from the main vertex. 
     * Return zero if no weight exists */ 
    public int getWeightTo(Vertex v) {
        if (to.containsKey(v)) return to.get(v);
        return 0;
    }
    
    /** @return the vertices that point to the main vertex */
    public Set<Vertex> getVerticesFrom() {
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
    public int addWeightTo(Vertex target, int weight) {
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
    public String getLabel() {
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
        Set<Vertex> target = getVerticesFrom();
        for (Vertex v : target) {
            rep.append(getLabel() + " ---> " + v.getLabel() + ", weight = " + getWeightTo(v) + "\n");
        }
        return rep.toString();
    }
}
