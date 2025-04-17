package bellmanford;

import java.util.Arrays;
import java.util.List;

public class BellmanFord {
    public static boolean bellmanFord(EdgeList graph, int source, int[] costs, int[] parents) {
        int v = graph.getV();
        List<Edge> edges = graph.getEdges();
        System.out.println(edges);
        System.out.println("Number of edges: " + edges.size());
        System.out.println("Number of vertices: " + v);

        if (source < 0 || source >= v)
            throw new IllegalArgumentException("Source vertex must be between 0 and " + (v - 1));

        Arrays.fill(costs, Integer.MAX_VALUE);
        Arrays.fill(parents, -1);
        costs[source] = 0;

        for (int i = 0; i < v - 1; i++) {
            for (Edge edge : edges) {
                int from = edge.source();
                int to = edge.destination();
                int weight = edge.weight();
                if (costs[from] != Integer.MAX_VALUE && costs[from] + weight < costs[to]) {
                    costs[to] = costs[from] + weight;
                    parents[to] = from;
                }
            }
        }

        for (Edge edge : edges) {
            int from = edge.source();
            int to = edge.destination();
            int weight = edge.weight();
            if (costs[from] != Integer.MAX_VALUE && costs[from] + weight < costs[to]) {
                return false;
            }
        }
        return true;
    }
}