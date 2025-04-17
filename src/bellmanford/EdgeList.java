package bellmanford;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EdgeList {

    private int v;
    private int e;
    private List<Edge> edges;

    public EdgeList(String filePath) throws IOException{
        readGraphFromFile(filePath);
    }

    private void readGraphFromFile(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String[] firstLint = reader.readLine().trim().split("\\s+");
            int v = Integer.parseInt(firstLint[0]);
            int e = Integer.parseInt(firstLint[1]);

            this.edges = new ArrayList<>();
            for (int i = 0; i < e; i++) {
                String[] edgeLine = reader.readLine().trim().split("\\s+");
                int source = Integer.parseInt(edgeLine[0]);
                int destination = Integer.parseInt(edgeLine[1]);
                int weight = Integer.parseInt(edgeLine[2]);

                edges.add(new Edge(source, destination, weight));
            }
        }
    }

    public int getV() {
        return v;
    }

    public int getE() {
        return e;
    }

    public List<Edge> getEdges() {
        return edges;
    }
}
