import static org.junit.Assert.*;

import org.junit.Test;

import java.net.URL;
import java.io.FileNotFoundException;

import java.util.LinkedList;

public class ShortestPathsTest {


    /* Returns the Graph loaded from the file with filename fn. */
    private Graph loadBasicGraph(String fn) {
        Graph result = null;
        try {
          result = ShortestPaths.parseGraph("basic", fn);
        } catch (FileNotFoundException e) {
          fail("Could not find graph " + fn);
        }
        return result;
    }

    /** Dummy test case demonstrating syntax to create a graph from scratch.
     * TODO Write your own tests below. */
    @Test
    public void test00Nothing() {
        Graph g = new Graph();
        Node a = g.getNode("A");
        Node b = g.getNode("B");
        g.addEdge(a, b, 1);

        // sample assertion statements:
        assertTrue(true);
        assertEquals(2+2, 4);
    }

    /** Minimal test case to check the path from A to B in Simple0.txt */
    @Test
    public void test01Simple0() {
        Graph g = loadBasicGraph("data/Simple0.txt");
        g.report();
        ShortestPaths sp = new ShortestPaths();
        Node a = g.getNode("A");
        sp.compute(g, a);
        Node b = g.getNode("B");
        LinkedList<Node> abPath = sp.shortestPath(b);
        assertEquals(abPath.size(), 2);
        assertEquals(abPath.getFirst(), a);
        assertEquals(abPath.getLast(),  b);
        assertEquals(sp.shortestPathLength(b), 1.0, 1e-6);
    }

    @Test
    public void test02Complex() {
      //multi-edge path
      Graph g = loadBasicGraph("data/Simple1.txt");
        g.report();
        ShortestPaths sp = new ShortestPaths();
        Node a = g.getNode("A");
        sp.compute(g, a);
        Node d = g.getNode("D");
        LinkedList<Node> adPath = sp.shortestPath(d);
        assertEquals(adPath.size(), 3);
        assertEquals(adPath.getFirst(), a);
        assertEquals(adPath.getLast(),  d);
        assertEquals(sp.shortestPathLength(d), 4.0);

    }

    @Test 
    public void test03NoNeighbors() {
      //origin node with no neighbors
      Graph g = loadBasicGraph("data/Simple2.txt");
        g.report();
        ShortestPaths sp = new ShortestPaths();
        Node origin = g.getNode("G");
        sp.compute(g, origin);
        assertEquals(sp.getPaths().size(), 1);
        //the only path is the one to itself with length 0 with is always there.
    }

    @Test 
    public void test04NoPath() {
      //destination node that has no path to it
      Graph g = loadBasicGraph("data/Simple2.txt");
        g.report();
        ShortestPaths sp = new ShortestPaths();
        Node origin = g.getNode("A");
        Node d = g.getNode("D");
        Node f = g.getNode("F");
        sp.compute(g, origin);
        assertEquals(sp.getPaths().containsKey(d), false);
        assertEquals(sp.getPaths().containsKey(f), true);
        //node D has no other nodes pointing to it, but it points to 2 others
        //node F has edges going to and from it, so it should have a path
    }

    /* Pro tip: unless you include @Test on the line above your method header,
     * JUnit will not run it! This gets me every time. */
}
