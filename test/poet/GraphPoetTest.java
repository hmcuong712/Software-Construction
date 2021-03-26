/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package poet;

import static org.junit.Assert.*;
import java.util.*;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

/**
 * Tests for GraphPoet.
 */
public class GraphPoetTest {
    
    // Testing strategy
    //   file content: words duplicate, non duplicate
    //                 lower-case, upper-case
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    // tests read corpus file
    @Test public void testReadFileOneWord() {
        File f = new File("test/poet/mugar-omni-theater.txt");
        try {
            GraphPoet gp = new GraphPoet(f);
            Set<String> words = new HashSet<>();
            String text = new String("This is a test of the Mugar Omni Theater sound system.").toLowerCase();
            String[] arr = text.split(" ");
            for (String s : arr) {
                words.add(s);
            }
            assertEquals(words, gp.vertices());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Test public void testReadFileDuplicateWords() {
        File f = new File("test/poet/hello_goodbye.txt");
        try {
            GraphPoet gp = new GraphPoet(f);
            Set<String> words = new HashSet<>();
            words.add("hello");
            words.add("goodbye!");
            assertEquals(words, gp.vertices());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // test multiple words, no duplicates
    @Test public void testPoemMultipleWords() {
        File f = new File("test/poet/mugar-omni-theater.txt");
        try {
            GraphPoet gp = new GraphPoet(f);
            assertEquals(gp.poem("Test the system."), "test of the system.");
            assertEquals(gp.poem("Mugar Theater"), "mugar omni theater");
            assertEquals(gp.poem("theater system."), "theater sound system.");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // test multiple words, with duplicates
    @Test public void testPoemMultipleWordsDuplicate() throws IOException {
        File f = new File("test/poet/duplicate-pairs.txt");
        GraphPoet gp = new GraphPoet(f);
        assertEquals(gp.poem("Good no"), "good news no");
        assertEquals(gp.poem("a nEws"), "a good news");
        assertEquals(gp.poem("haVe old"), "have old");
    }
}
