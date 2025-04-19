import java.util.ArrayList;


public class FloydWarshall {

    final static int infinity = 10000000;

    ArrayList<ArrayList<Integer>> matrix;
    ArrayList<ArrayList<Integer>> parent;
    int v;

//    public static ArrayList<ArrayList<Integer>> listToMatrix(ArrayList<ArrayList<Edge>> adjList) {
//        ArrayList<ArrayList<Integer>> adjMatrix = new ArrayList<>();
//        int v = adjList.size();
//        for (int i = 0; i < v; ++i) {
//            adjMatrix.add(new ArrayList<>());
//            for (int j = 0; j < v; ++j) {
//                adjMatrix.get(i).add(infinity);
//            }
//        }
//        for (int i = 0; i < v; ++i) {
//            for (Edge e : adjList.get(i)) {
//                adjMatrix.get(i).set(e.getDist(), e.getWeight());
//            }
//        }
//        return adjMatrix;
//    }

    FloydWarshall(ArrayList<ArrayList<Integer>> cost, ArrayList<ArrayList<Integer>> parent) {
        matrix = cost;
        this.parent = parent;
        v = matrix.size();
    }

    boolean floydWarshall() {

        for (int k = 0; k < v; ++k) {
            for (int j = 0; j < v; ++j) {
                for (int i = 0; i < v; ++i) {
                    int relax = matrix.get(i).get(k) + matrix.get(k).get(j);
                    int withoutK = matrix.get(i).get(j);
                    if(relax < withoutK) {
                        matrix.get(i).set(j, relax);
                        parent.get(i).set(j, parent.get(k).get(j));
                    }
                }
            }
        }

        for(int i = 0; i < v; ++i){
            if(matrix.get(i).get(i) < 0)
                    return false;
        }

        return true;
    }


}
