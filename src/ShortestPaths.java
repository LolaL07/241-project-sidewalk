import java.util.Map;
import java.util.HashMap;
import java.util.LinkedList;
import java.io.File;
import java.io.FileNotFoundException;

/** Provides an implementation of Dijkstra's single-source shortest paths
 * algorithm.
 * Sample usage:
 *   Graph g = // create your graph
 *   ShortestPaths sp = new ShortestPaths();
 *   Node a = g.getNode("A");
 *   sp.compute(a);
 *   Node b = g.getNode("B");
 *   LinkedList<Node> abPath = sp.getShortestPath(b);
 *   double abPathLength = sp.getShortestPathLength(b);
 *   */
public class ShortestPaths {
    // stores auxiliary data associated with each node for the shortest
    // paths computation:
    private HashMap<Node,PathData> paths;

    /** Compute the shortest path to all nodes from origin using Dijkstra's
     * algorithm. Fill in the paths field, which associates each Node with its
     * PathData record, storing total distance from the source, and the
     * back pointer to the previous node on the shortest path.
     * Precondition: origin is a node in the Graph.*/
    public void compute(Graph g, Node origin) {
        
        // TODO 1: implement Dijkstra's algorithm to fill paths with
        // shortest-path data for each Node reachable from origin.

        paths = new HashMap<Node,PathData>();

        PriorityQueue<Node> u = new PriorityQueue<>();
        // PriorityQueue<Map.Entry<Node, Double>> u = new PriorityQueue<>(Map.Entry.comparingByValue());
        // PriorityQueue<Node> u = new PriorityQueue<>(Map.Entry.comparingByValue());

        LinkedList<Node> processed = new LinkedList<Node>();

        //assign distances: 0 for origin, rest = infinity
        // HashMap<Node, Double> distances = new HashMap<>();
        for(Map.Entry<String, Node> entry : g.getNodes().entrySet()) {
            if(entry.getValue().equals(origin)) {
                // distances.put(entry.getValue(), 0.0);
                entry.getValue().setDist(0.0);
            } else {
                // distances.put(entry.getValue(), Double.POSITIVE_INFINITY);
                entry.getValue().setDist(Double.POSITIVE_INFINITY);
            }
        }

        Node current = origin;
        u.add(current);
        // u.add(distances.get(origin)); //add map entry with same key from distances

        while(!u.isEmpty()) {
            current = u.poll();
            for(Node neighbor : current.getNeighbors().keySet()) {

                Double edgeWeight = current.getNeighbors().get(neighbor);
                if(!processed.contains(neighbor)) {
                    Double newDist = current.dist + edgeWeight;
                    if(newDist < neighbor.dist) {
                        PathData temp = new PathData(newDist, current);
                        paths.put(neighbor, temp);
                    }
                    neighbor.setDist(Math.min(neighbor.dist, newDist));
                    u.add(neighbor);
                }
            }
            
            if(current.equals(origin)) {
                PathData temp2 = new PathData(0.0, current);
                paths.put(current, temp2);
            }
            processed.add(current);
        }
    }

    /** Returns the length of the shortest path from the origin to destination.
     * If no path exists, return Double.POSITIVE_INFINITY.
     * Precondition: destination is a node in the graph, and compute(origin)
     * has been called. */
    public double shortestPathLength(Node destination) {
        // TODO 2 - implement this method to fetch the shortest path length
        // from the paths data computed by Dijkstra's algorithm.
        // throw new UnsupportedOperationException();

        return paths.get(destination).distance;        
    }

    /** Returns a LinkedList of the nodes along the shortest path from origin
     * to destination. This path includes the origin and destination. If origin
     * and destination are the same node, it is included only once.
     * If no path to it exists, return null.
     * Precondition: destination is a node in the graph, and compute(origin)
     * has been called. */
    public LinkedList<Node> shortestPath(Node destination) {
        // TODO 3 - implement this method to reconstruct sequence of Nodes
        // along the shortest path from the origin to destination using the
        // paths data computed by Dijkstra's algorithm.
        // throw new UnsupportedOperationException();

        LinkedList<Node> dp = new LinkedList<Node>();
        Node temp = destination;
        while(paths.get(temp).distance > 0) {
            dp.addFirst(temp);
            temp = paths.get(temp).previous;
        }
        dp.addFirst(temp);

        return dp;
    }

    //prints each node in paths and its associated pathdata
    public void print() {
        if(paths.isEmpty()) {
            System.out.println("paths is empty");
        } else {
            for(Map.Entry<Node, PathData> entry : paths.entrySet()) {
                System.out.println("Node: " + entry.getKey().getId() + " Path Distance and Previous node: " + entry.getValue().distance + " " + entry.getValue().previous);
            }
        }
    }

    /** Inner class representing data used by Dijkstra's algorithm in the
     * process of computing shortest paths from a given source node. */
    class PathData {
        double distance; // distance of the shortest path from source
        Node previous; // previous node in the path from the source

        /** constructor: initialize distance and previous node */
        public PathData(double dist, Node prev) {
            distance = dist;
            previous = prev;
        }
    }


    /** Static helper method to open and parse a file containing graph
     * information. Can parse either a basic file or a CSV file with
     * sidewalk data. See GraphParser, BasicParser, and DBParser for more.*/
    protected static Graph parseGraph(String fileType, String fileName) throws
        FileNotFoundException {
        // create an appropriate parser for the given file type
        GraphParser parser;
        if (fileType.equals("basic")) {
            parser = new BasicParser();
        } else if (fileType.equals("db")) {
            parser = new DBParser();
        } else {
            throw new IllegalArgumentException(
                    "Unsupported file type: " + fileType);
        }

        // open the given file
        parser.open(new File(fileName));

        // parse the file and return the graph
        return parser.parse();
    }

    public static void main(String[] args) {
      // read command line args
      String fileType = args[0];
      String fileName = args[1];
      String SidewalkOrigCode = args[2];

      String SidewalkDestCode = null;
      if (args.length == 4) {
        SidewalkDestCode = args[3];
      }

      // parse a graph with the given type and filename
      Graph graph;
      try {
          graph = parseGraph(fileType, fileName);
      } catch (FileNotFoundException e) {
          System.out.println("Could not open file " + fileName);
          return;
      }
      graph.report();


      // TODO 4: create a ShortestPaths object, use it to compute shortest
      // paths data from the origin node given by origCode.

      // TODO 5:
      // If destCode was not given, print each reachable node followed by the
      // length of the shortest path to it from the origin.

      // TODO 6:
      // If destCode was given, print the nodes in the path from
      // origCode to destCode, followed by the total path length
      // If no path exists, print a message saying so.

        ShortestPaths sp = new ShortestPaths();
        Node a = graph.getNode(SidewalkOrigCode);
        sp.compute(graph, a);

      if(SidewalkDestCode == null) {
        //print each node in paths and its distance
        System.out.println("All node paths");
        for(Map.Entry<Node, PathData> entry : sp.paths.entrySet()) {
            System.out.println("Node: " + entry.getKey().getId() + " Path length: " + entry.getValue().distance);
        }

      } else if(SidewalkDestCode != null) {
        //print the nodes in the path from origin to destination
        Node end = graph.getNode(SidewalkDestCode);
        LinkedList<Node> path = sp.shortestPath(end);
        System.out.println("The path is: ");
        for(int i = 0; i < path.size(); i++) {
            System.out.print(path.get(i).getId() + " ");
        }
        System.out.println("");
        System.out.println("The path length is :" + sp.shortestPathLength(end));
      }
    }
}
