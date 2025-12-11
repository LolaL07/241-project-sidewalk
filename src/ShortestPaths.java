import java.util.Map;
import java.util.PriorityQueue;
import java.util.HashMap;
import java.util.LinkedList;
import java.io.File;
import java.io.FileNotFoundException;


public class ShortestPaths {

    private HashMap<Node,PathData> paths;


    public void compute(Graph g, Node origin) {

        paths = new HashMap<Node,PathData>();
        PriorityQueue<Node> u = new PriorityQueue<>();
        LinkedList<Node> processed = new LinkedList<Node>();

        //assign distances: 0 for origin, rest = infinity
        for(Map.Entry<String, Node> entry : g.getNodes().entrySet()) {
            if(entry.getValue().equals(origin)) {
                entry.getValue().setDist(0.0);
            } else {
                entry.getValue().setDist(Double.POSITIVE_INFINITY);
            }
        }

        Node current = origin;
        u.add(current);

        //traverse through nodes, starting from origin
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


    // Returns the length of the shortest path from the origin to destination.
    public double shortestPathLength(Node destination) {

        return paths.get(destination).distance;
    }

    // Returns a LinkedList of the nodes along the shortest path from origin to destination
    public LinkedList<Node> shortestPath(Node destination) {

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

    public HashMap<Node,PathData> getPaths() {
        return paths;
    }


    class PathData {
        double distance; // distance of the shortest path from source
        Node previous; // previous node in the path from the source

        public PathData(double dist, Node prev) {
            distance = dist;
            previous = prev;
        }
    }


    //opens and parses given file, returns created graph
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


      // TODOS 4 THROUGH 6 HERE
      // LOOK HERE  
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
        //print the nodes in the path from origin to destination, or no path found

        Node end = graph.getNode(SidewalkDestCode);

        if(sp.getPaths().containsKey(end)) {
            LinkedList<Node> path = sp.shortestPath(end);
            System.out.println("The path is: ");
            for(int i = 0; i < path.size(); i++) {
                System.out.print(path.get(i).getId() + " ");
            }
            System.out.println("");
            System.out.println("The path length is :" + sp.shortestPathLength(end));
        } else {
            System.out.println("No path found.");
        }

      }

    }
}
