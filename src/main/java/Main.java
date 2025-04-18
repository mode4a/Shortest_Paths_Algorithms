import java.util.*;

public class Main {
    // ANSI color codes
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String CYAN = "\u001B[36m";
    public static final String BOLD = "\u001B[1m";

    static ArrayList<Integer> singleSourceCosts = new ArrayList<>();
    static ArrayList<Integer> singleSourceParents = new ArrayList<>();
    static ArrayList<ArrayList<Integer>> allPairsCosts = new ArrayList<>();
    static ArrayList<ArrayList<Integer>> allPairsParents = new ArrayList<>();

    private static int getIntInput(Scanner scanner, int min, int max) {
        while (true) {
            try {
                int input = scanner.nextInt();
                scanner.nextLine();
                if (input < min || input > max) {
                    System.out.print(RED + "Invalid input. Try again: " + RESET);
                    continue;
                }
                return input;
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.print(RED + "Invalid input. Please enter a number: " + RESET);
            }
        }
    }

    private static void printPath(int src, int dest, ArrayList<Integer> parents) {
        if (parents.get(dest) == -1 && src != dest) {
            System.out.println(RED + "No path exists from " + src + " to " + dest + RESET);
            return;
        }

        Stack<Integer> path = new Stack<>();
        int current = dest;

        while (current != -1 && current != src) {
            path.push(current);
            current = parents.get(current);
        }

        if (current == -1) {
            System.out.println(RED + "No path exists from " + src + " to " + dest + RESET);
            return;
        }

        path.push(src);

        System.out.print(GREEN + "Path: ");
        while (!path.isEmpty()) {
            System.out.print(path.pop());
            if (!path.isEmpty())
                System.out.print(" -> ");
        }
        System.out.println(RESET);
    }

    private static void singleSourceMenu(Scanner scanner, Graph graph) {
        singleSourceCosts.clear();
        singleSourceParents.clear();
        allPairsCosts.clear();
        allPairsParents.clear();
        System.out.println(BOLD + "\n=== SINGLE-SOURCE SHORTEST PATHS ===" + RESET);

        System.out.print(CYAN + "\nEnter source node: " + RESET);
        int source = getIntInput(scanner, 0, graph.size() - 1);

        System.out.println(BOLD + "\nChoose algorithm:" + RESET);
        System.out.println("1. Dijkstra's Algorithm");
        System.out.println("2. Bellman-Ford Algorithm");
        System.out.println("3. Floyd-Warshall Algorithm");
        System.out.println("4. Back to main menu");
        System.out.print(YELLOW + "Choose an option: " + RESET);

        int algoChoice = getIntInput(scanner, 1, 4);
        if (algoChoice == 4)
            return;

        singleSourceCosts = new ArrayList<>(Collections.nCopies(graph.size(), 0));
        singleSourceParents = new ArrayList<>(Collections.nCopies(graph.size(), -1));

        switch (algoChoice) {
            case 1:
                graph.Dijkstra(source, singleSourceCosts, singleSourceParents);
                System.out.println(GREEN + "Dijkstra's algorithm completed." + RESET);
                break;
            case 2:
                graph.bellmanFord(source, singleSourceCosts, singleSourceParents);
                System.out.println(GREEN + "Bellman-Ford's algorithm completed." + RESET);
                break;
            case 3:
                allPairsCosts = new ArrayList<>(graph.size());
                allPairsParents = new ArrayList<>(graph.size());
                for (int i = 0; i < graph.size(); i++) {
                    allPairsCosts.add(new ArrayList<>(Collections.nCopies(graph.size(), Integer.MAX_VALUE)));
                    allPairsParents.add(new ArrayList<>(Collections.nCopies(graph.size(), -1)));
                }

                graph.floydWarshall(allPairsCosts, allPairsParents);

                singleSourceCosts = allPairsCosts.get(source);
                singleSourceParents = allPairsParents.get(source);
                System.out.println(GREEN + "Floyed-Warshall's algorithm completed." + RESET);
                break;
            default:
                System.out.println(RED + "Invalid option. Returning to main menu." + RESET);
                return;
        }

        while (true) {
            System.out.println(BOLD + "\n=== SINGLE-SOURCE RESULTS ===" + RESET);
            System.out.println("1. Get cost to a node");
            System.out.println("2. Get path to a node");
            System.out.println("3.Back to main menu");
            System.out.print(YELLOW + "Choose an option: " + RESET);

            int queryChoice = getIntInput(scanner, 1, 3);
            if (queryChoice == 3)
                break;

            System.out.print(CYAN + "Enter target node: " + RESET);
            int target = getIntInput(scanner, 0, graph.size() - 1);

            switch (queryChoice) {
                case 1:

                    if (singleSourceCosts.get(target) == Integer.MAX_VALUE) {
                        System.out.println(RED + "No path exists from " + source + " to " + target + RESET);
                    } else {
                        System.out.println(
                                GREEN + "Cost from " + source + " to " + target + ": " + singleSourceCosts.get(target)
                                        + RESET);
                    }
                    break;
                case 2:
                    printPath(source, target, singleSourceParents);
                    break;
            }
        }
    }

    private static void allPairsMenu(Scanner scanner, Graph graph) {
        allPairsCosts.clear();
        allPairsParents.clear();
        System.out.println(BOLD + "\n=== ALL-PAIRS SHORTEST PATHS ===" + RESET);
        System.out.println(BOLD + "\nChoose algorithm:" + RESET);
        System.out.println("1. Dijkstra's Algorithm (for each node)");
        System.out.println("2. Bellman-Ford Algorithm (for each node)");
        System.out.println("3. Floyd-Warshall Algorithm");
        System.out.println("4. Back to main menu");
        System.out.print(YELLOW + "Choose an option: " + RESET);

        int algoChoice = getIntInput(scanner, 1, 4);
        if (algoChoice == 4)
            return;

        allPairsCosts = new ArrayList<>(graph.size());
        allPairsParents = new ArrayList<>(graph.size());
        for (int i = 0; i < graph.size(); i++) {
            allPairsCosts.add(new ArrayList<>(Collections.nCopies(graph.size(), Integer.MAX_VALUE)));
            allPairsParents.add(new ArrayList<>(Collections.nCopies(graph.size(), -1)));
        }

        switch (algoChoice) {
            case 1:
                for (int i = 0; i < graph.size(); i++) {
                    ArrayList<Integer> costs = new ArrayList<>(Collections.nCopies(graph.size(), Integer.MAX_VALUE));
                    ArrayList<Integer> parents = new ArrayList<>(Collections.nCopies(graph.size(), -1));
                    graph.Dijkstra(i, costs, parents);
                    allPairsCosts.set(i, new ArrayList<>(costs));
                    allPairsParents.set(i, new ArrayList<>(parents));
                }
                System.out.println(GREEN + "Dijkstra's algorithm (all nodes) completed." + RESET);
                break;
            case 2:
                for (int i = 0; i < graph.size(); i++) {
                    ArrayList<Integer> costs = new ArrayList<>(Collections.nCopies(graph.size(), Integer.MAX_VALUE));
                    ArrayList<Integer> parents = new ArrayList<>(Collections.nCopies(graph.size(), -1));
                    graph.bellmanFord(i, costs, parents);
                    allPairsCosts.set(i, new ArrayList<>(costs));
                    allPairsParents.set(i, new ArrayList<>(parents));
                }
                System.out.println(GREEN + "Bellman-Ford's algorithm (all nodes) completed." + RESET);
                break;
            case 3:
                graph.floydWarshall(allPairsCosts, allPairsParents);
                System.out.println(GREEN + "Floyed-Warshall's algorithm completed." + RESET);
                break;
            default:
                System.out.println(RED + "Invalid option. Returning to main menu." + RESET);
                return;
        }

        while (true) {
            System.out.println(BOLD + "\n=== ALL-PAIRS RESULTS ===" + RESET);
            System.out.println("1. Get cost between two nodes");
            System.out.println("2. Get path between two nodes");
            System.out.println("3.Back to main menu");
            System.out.print(YELLOW + "Choose an option: " + RESET);

            int queryChoice = getIntInput(scanner, 1, 3);
            if (queryChoice == 3)
                break;

            System.out.print(CYAN + "Enter source node: " + RESET);
            int source = getIntInput(scanner, 0, graph.size() - 1);
            System.out.print(CYAN + "Enter target node: " + RESET);
            int target = getIntInput(scanner, 0, graph.size() - 1);

            switch (queryChoice) {
                case 1:

                    if (allPairsCosts.get(source).get(target) == Integer.MAX_VALUE) {
                        System.out.println(RED + "No path exists from " + source + " to " + target + RESET);
                    } else {
                        System.out.println(GREEN + "Cost from " + source + " to " + target + ": "
                                + allPairsCosts.get(source).get(target) + RESET);
                    }
                    break;
                case 2:
                    printPath(source, target, allPairsParents.get(source));
                    break;
            }
        }
    }

    private static void negativeCycleMenu(Scanner scanner, Graph graph) {
        System.out.println(BOLD + "\n=== CHECK FOR NEGATIVE CYCLES ===" + RESET);
        System.out.println("1. Check for negative cycles using Bellman-Ford Algorithm");
        System.out.println("2. Check for negative cycles using Floyd-Warshall Algorithm");
        System.out.println("3. Back to main menu");
        System.out.print(YELLOW + "Choose an option: " + RESET);

        int choice = getIntInput(scanner, 1, 3);
        if (choice == 3)
            return;

        boolean hasNegativeCycle = false;
        switch (choice) {
            case 1:
                ArrayList<Integer> dummyCosts = new ArrayList<>(Collections.nCopies(graph.size(), Integer.MAX_VALUE));
                ArrayList<Integer> dummyParents = new ArrayList<>(Collections.nCopies(graph.size(), -1));
                hasNegativeCycle = graph.bellmanFord(0, dummyCosts, dummyParents);
                System.out.println(GREEN + "Bellman-Ford's algorithm completed." + RESET);
                break;
            case 2:
                ArrayList<ArrayList<Integer>> dummyCostsMatrix = new ArrayList<>(graph.size());
                ArrayList<ArrayList<Integer>> dummyParentsMatrix = new ArrayList<>(graph.size());
                for (int i = 0; i < graph.size(); i++) {
                    dummyCostsMatrix.add(new ArrayList<>(Collections.nCopies(graph.size(), Integer.MAX_VALUE)));
                    dummyParentsMatrix.add(new ArrayList<>(Collections.nCopies(graph.size(), -1)));
                }
                hasNegativeCycle = graph.floydWarshall(dummyCostsMatrix, dummyParentsMatrix);
                break;
            default:
                System.out.println(RED + "Invalid option. Returning to main menu." + RESET);
                return;
        }

        if (!hasNegativeCycle) {
            System.out.println(BOLD + RED + "\nThe graph contains a negative cycle." + RESET);
        } else {
            System.out.println(BOLD + GREEN + "\nThe graph does not contain any negative cycles." + RESET);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(CYAN + "Enter the path to the graph file: " + RESET);
        String filePath = scanner.nextLine();
        Graph graph = null;
        try {
            graph = new Graph(filePath);
        } catch (Exception e) {
            System.out.println(RED + "Error loading graph. Please check the file path and format." + RESET);
            return;
        }

        while (true) {
            System.out.println(BOLD + "\n=== MAIN MENU ===" + RESET);
            System.out.println("1. Single-source shortest paths");
            System.out.println("2. All-pairs shortest paths");
            System.out.println("3. Check for negative cycles");
            System.out.println("4. Exit");
            System.out.print(YELLOW + "Choose an option: " + RESET);

            int choice = getIntInput(scanner, 1, 4);

            switch (choice) {
                case 1:
                    singleSourceMenu(scanner, graph);
                    break;
                case 2:
                    allPairsMenu(scanner, graph);
                    break;
                case 3:
                    negativeCycleMenu(scanner, graph);
                    break;
                case 4:
                    System.out.println(GREEN + "Exiting program..." + RESET);
                    return;
            }
        }
    }

}