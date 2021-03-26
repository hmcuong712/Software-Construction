/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package poet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.IOException;

import graph.Graph;

/**
 * A graph-based poetry generator.
 * 
 * <p>GraphPoet is initialized with a corpus of text, which it uses to derive a
 * word affinity graph.
 * Vertices in the graph are words. Words are defined as non-empty
 * case-insensitive strings of non-space non-newline characters. They are
 * delimited in the corpus by spaces, newlines, or the ends of the file.
 * Edges in the graph count adjacencies: the number of times "w1" is followed by
 * "w2" in the corpus is the weight of the edge from w1 to w2.
 * 
 * <p>For example, given this corpus:
 * <pre>    Hello, HELLO, hello, goodbye!    </pre>
 * <p>the graph would contain two edges:
 * <ul><li> ("hello,") -> ("hello,")   with weight 2
 *     <li> ("hello,") -> ("goodbye!") with weight 1 </ul>
 * <p>where the vertices represent case-insensitive {@code "hello,"} and
 * {@code "goodbye!"}.
 * 
 * <p>Given an input string, GraphPoet generates a poem by attempting to
 * insert a bridge word between every adjacent pair of words in the input.
 * The bridge word between input words "w1" and "w2" will be some "b" such that
 * w1 -> b -> w2 is a two-edge-long path with maximum-weight weight among all
 * the two-edge-long paths from w1 to w2 in the affinity graph.
 * If there are no such paths, no bridge word is inserted.
 * In the output poem, input words retain their original case, while bridge
 * words are lower case. The whitespace between every word in the poem is a
 * single space.
 * 
 * <p>For example, given this corpus:
 * <pre>    This is a test of the Mugar Omni Theater sound system.    </pre>
 * <p>on this input:
 * <pre>    Test the system.    </pre>
 * <p>the output poem would be:
 * <pre>    Test of the system.    </pre>
 * 
 * <p>PS2 instructions: this is a required ADT class, and you MUST NOT weaken
 * the required specifications. However, you MAY strengthen the specifications
 * and you MAY add additional methods.
 * You MUST use Graph in your rep, but otherwise the implementation of this
 * class is up to you.
 */
public class GraphPoet {
    
    private final Graph<String> graph = Graph.empty();
    
    // Abstraction function:
    //   Directed graph: Vertices are words, 
    //   the vertices are connected by directed edges 
    // Representation invariant:
    //   Edges have positive weights. Vertices as words are defined as non-empty
    //   case-insensitive strings of non-space non-newline characters
    // Safety from rep exposure:
    //   Field is private and immutable
    
    /**
     * Create a new poet with the graph from corpus (as described above).
     * 
     * @param corpus text file from which to derive the poet's affinity graph
     * @throws IOException if the corpus file cannot be found or read
     */
    public GraphPoet(File corpus) throws IOException {
        String strCurrentLine;
        List<String> words = new ArrayList<>();
        BufferedReader read = new BufferedReader(new FileReader(corpus));
        while ((strCurrentLine = read.readLine()) != null) {
            String[] split = strCurrentLine.split(" ");
            words.addAll(Arrays.asList(split));
        }
        
        int prev = 0;
        for (int i = 0; i < words.size() - 1; i++) {
            prev = graph.set(words.get(i).toLowerCase(), words.get(i + 1).toLowerCase(), 1);
            assert prev >= 0;
            if (prev > 0) graph.set(words.get(i).toLowerCase(), words.get(i + 1).toLowerCase(), prev + 1); // detected duplicate word pairs
        }
        read.close();
        checkRep();
    }
    
    // checkRep
    public void checkRep() {
        Set<String> vertices = graph.vertices();
        Pattern pattern = Pattern.compile("^[^\n ]*$");
        for (String v : vertices) {
            Matcher matcher = pattern.matcher(v);
            assert matcher.matches();
        }
    }
    
    /**
     * Generate a poem.
     * 
     * @param input string from which to create the poem
     * @return poem (as described above)
     */
    public String poem(String input) {
//        List<String> bridges = new ArrayList<>();
        String inputLC = input.toLowerCase();
        StringBuilder result = new StringBuilder();
        String s;
        
        String[] split = inputLC.split(" ");
        result.append(split[0]);
        for (int i = 0; i < split.length - 1; i++) {
            s = getBridgeWords(split[i], split[i + 1]);
            if (s != null) result.append(" " + s + " " + split[i + 1]);
            else result.append(" " + split[i + 1]);
        }
        return result.toString(); 
    }
    
    /**
     * Generate the vertices.
     * 
     * @return vertices
     */
    public Set<String> vertices() {
        return graph.vertices();
    }
    
    /**
     * Given two strings, return a bridge word (between two words in a corpus) and sum of weights 
     * 
     * @return a map contains bridge word as Key and sum of weights as Value
     */
    private String getBridgeWords(String source, String target) {
        Map<String, Integer> map = new TreeMap<>();
        Map<String, Integer> targets = new HashMap<>();
        Map<String, Integer> sources = new HashMap<>();

        if (graph.vertices().contains(source) && graph.vertices().contains(target))
        {
            targets = graph.targets(source);
            sources = graph.sources(target);
            for (String v : targets.keySet()) {
                if (sources.containsKey(v)) map.put(v, sources.get(v) + targets.get(v));
            }
        }
        
        if (!map.isEmpty()) return Collections.max(map.entrySet(), Map.Entry.comparingByValue()).getKey();
        return null;
    }
    
    // TODO toString()
    
}
