package bellmanford;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        String filePath;
        
        if (args.length < 1) {
            System.out.println("No file path provided. Creating sample graph file...");
            filePath = createSampleGraphFile();
        } else {
            filePath = args[0];
        }
        
        try {
            // Load the graph
            EdgeList graph = new EdgeList(filePath);
            System.out.println("Graph loaded successfully.");
            System.out.println("Vertices: " + graph.getV() + ", Edges: " + graph.getE());
            
            // Choose source vertex
            int source = 0; // Using vertex 0 as the source
            
            // Arrays to store results
            int[] costs = new int[graph.getV()];
            int[] parents = new int[graph.getV()];
            
            // Run Bellman-Ford algorithm
            boolean noNegativeCycle = BellmanFord.bellmanFord(graph, source, costs, parents);
            
            // Display results
            if (noNegativeCycle) {
                System.out.println("\nShortest path results from source vertex " + source + ":");
                System.out.println("Vertex\tDistance\tPath");
                
                for (int i = 0; i < graph.getV(); i++) {
                    System.out.print(i + "\t");
                    
                    // Print distance
                    if (costs[i] == Integer.MAX_VALUE) {
                        System.out.print("INF\t\t");
                    } else {
                        System.out.print(costs[i] + "\t\t");
                    }
                    
                    // Print path
                    if (costs[i] == Integer.MAX_VALUE) {
                        System.out.println("No path");
                    } else {
                        StringBuilder path = new StringBuilder();
                        printPath(parents, i, path);
                        System.out.println(path);
                    }
                }
            } else {
                System.out.println("\nNegative cycle detected in the graph!");
                System.out.println("Cannot compute shortest paths.");
            }
            
        } catch (IOException e) {
            System.err.println("Error reading graph file: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    // Helper method to reconstruct and print path
    private static void printPath(int[] parents, int vertex, StringBuilder path) {
        if (vertex == -1) {
            return;
        }
        
        if (parents[vertex] == -1) {
            path.append(vertex);
        } else {
            printPath(parents, parents[vertex], path);
            path.append(" -> ").append(vertex);
        }
    }
    
    // Creates a sample graph file for testing
    private static String createSampleGraphFile() {
        String fileName = "sample_graph.txt";
        try {
            // Make sure we don't overwrite an existing file
            if (Files.exists(Paths.get(fileName))) {
                System.out.println("Using existing sample file: " + fileName);
                return fileName;
            }
            
            PrintWriter writer = new PrintWriter(fileName);
            
            // Write a sample graph with 5 vertices and 8 edges
            writer.println("5 8");
            writer.println("0 1 6");    // Edge from 0 to 1 with weight 6
            writer.println("0 3 7");    // Edge from 0 to 3 with weight 7
            writer.println("1 2 5");    // Edge from 1 to 2 with weight 5
            writer.println("1 3 8");    // Edge from 1 to 3 with weight 8
            writer.println("1 4 -4");   // Edge from 1 to 4 with weight -4
            writer.println("2 1 -2");   // Edge from 2 to 1 with weight -2 (creates a negative cycle with 1->2->1)
            writer.println("3 2 -3");   // Edge from 3 to 2 with weight -3
            writer.println("3 4 9");    // Edge from 3 to 4 with weight 9
            
            writer.close();
            System.out.println("Sample graph file created: " + fileName);
            return fileName;
            
        } catch (IOException e) {
            System.err.println("Error creating sample file: " + e.getMessage());
            return null;
        }
    }
}