import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * An implementation of simplified Romania map problem
 *
 * @author Poom Pianpak
 *
 */

enum City {
  ORADEA, ZERIND, ARAD, TIMISOARA, LUGOJ, MEHADIA, DOBRETA, SIBIU, RIMNICU_VILCEA, CRAIOVA, FAGARAS, PITESTI, GIURGIU,
  BUCHAREST, NEAMT, URZICENI, IASI, VASLUI, HIRSOVA, EFORIE
}

class Node {
  City city;
  Node parent;
  int depth;
  int path_cost;

  Node(City city) {
    this.city = city;
  }

  Node(City city, Node parent, int depth, int path_cost) {
    this.city = city;
    this.parent = parent;
    this.depth = depth;
    this.path_cost = path_cost;
  }
}

public class Romania {

  private static Map<City, Map<City, Integer>> map = new HashMap<>();

  public static void main(String[] args) {
    // Initialize the map
    Map<City, Integer> dest_map = new HashMap<>();
    dest_map.put(City.ZERIND, 71);
    dest_map.put(City.SIBIU, 151);
    map.put(City.ORADEA, dest_map);

    dest_map = new HashMap<>();
    dest_map.put(City.ORADEA, 71);
    dest_map.put(City.ARAD, 75);
    map.put(City.ZERIND, dest_map);

    dest_map = new HashMap<>();
    dest_map.put(City.ZERIND, 75);
    dest_map.put(City.TIMISOARA, 118);
    dest_map.put(City.SIBIU, 140);
    map.put(City.ARAD, dest_map);

    dest_map = new HashMap<>();
    dest_map.put(City.ARAD, 118);
    dest_map.put(City.LUGOJ, 111);
    map.put(City.TIMISOARA, dest_map);

    dest_map = new HashMap<>();
    dest_map.put(City.TIMISOARA, 111);
    dest_map.put(City.MEHADIA, 70);
    map.put(City.LUGOJ, dest_map);

    dest_map = new HashMap<>();
    dest_map.put(City.LUGOJ, 70);
    dest_map.put(City.DOBRETA, 75);
    map.put(City.MEHADIA, dest_map);

    dest_map = new HashMap<>();
    dest_map.put(City.MEHADIA, 75);
    dest_map.put(City.CRAIOVA, 120);
    map.put(City.DOBRETA, dest_map);

    dest_map = new HashMap<>();
    dest_map.put(City.ORADEA, 151);
    dest_map.put(City.ARAD, 140);
    dest_map.put(City.RIMNICU_VILCEA, 80);
    dest_map.put(City.FAGARAS, 99);
    map.put(City.SIBIU, dest_map);

    dest_map = new HashMap<>();
    dest_map.put(City.SIBIU, 80);
    dest_map.put(City.CRAIOVA, 146);
    dest_map.put(City.PITESTI, 97);
    map.put(City.RIMNICU_VILCEA, dest_map);

    dest_map = new HashMap<>();
    dest_map.put(City.DOBRETA, 120);
    dest_map.put(City.RIMNICU_VILCEA, 146);
    dest_map.put(City.PITESTI, 138);
    map.put(City.CRAIOVA, dest_map);

    dest_map = new HashMap<>();
    dest_map.put(City.SIBIU, 99);
    dest_map.put(City.BUCHAREST, 211);
    map.put(City.FAGARAS, dest_map);

    dest_map = new HashMap<>();
    dest_map.put(City.RIMNICU_VILCEA, 97);
    dest_map.put(City.CRAIOVA, 138);
    dest_map.put(City.BUCHAREST, 101);
    map.put(City.PITESTI, dest_map);

    dest_map = new HashMap<>();
    dest_map.put(City.BUCHAREST, 90);
    map.put(City.GIURGIU, dest_map);

    dest_map = new HashMap<>();
    dest_map.put(City.FAGARAS, 211);
    dest_map.put(City.PITESTI, 101);
    dest_map.put(City.GIURGIU, 90);
    dest_map.put(City.URZICENI, 85);
    map.put(City.BUCHAREST, dest_map);

    dest_map = new HashMap<>();
    dest_map.put(City.IASI, 87);
    map.put(City.NEAMT, dest_map);

    dest_map = new HashMap<>();
    dest_map.put(City.BUCHAREST, 85);
    dest_map.put(City.VASLUI, 142);
    dest_map.put(City.HIRSOVA, 98);
    map.put(City.URZICENI, dest_map);

    dest_map = new HashMap<>();
    dest_map.put(City.NEAMT, 87);
    dest_map.put(City.VASLUI, 92);
    map.put(City.IASI, dest_map);

    dest_map = new HashMap<>();
    dest_map.put(City.IASI, 92);
    dest_map.put(City.URZICENI, 142);
    map.put(City.VASLUI, dest_map);

    dest_map = new HashMap<>();
    dest_map.put(City.URZICENI, 98);
    dest_map.put(City.EFORIE, 86);
    map.put(City.HIRSOVA, dest_map);

    dest_map = new HashMap<>();
    dest_map.put(City.HIRSOVA, 86);
    map.put(City.EFORIE, dest_map);

    // Search
    bfs(City.ARAD, City.EFORIE);
    dfs(City.ARAD, City.BUCHAREST);
    dls(City.ARAD, City.BUCHAREST, 10);
    ids(City.ARAD, City.EFORIE);
  }

  private static void bfs(City start, City goal) {
    System.out.println("Running BFS...");
    Set<City> explored = new HashSet<>();
    Queue<Node> frontier = new LinkedList<>();
    frontier.add(new Node(start));

    while (!frontier.isEmpty()) {
      // Choose a node
      Node chosen_node = frontier.remove();

      // Check for solution
      if (chosen_node.city == goal) {
        Node backtrack_node = chosen_node;
        List<City> solution = new LinkedList<>();
        solution.add(backtrack_node.city);
        while (backtrack_node.parent != null) {
          backtrack_node = backtrack_node.parent;
          solution.add(0, backtrack_node.city);
        }

        System.out.println("Found a solution: " + solution + " with path cost = " + chosen_node.path_cost);
        return;
      }

      // Add the selected node to the explored set
      explored.add(chosen_node.city);

      // Expand the selected node
      for (Map.Entry<City, Integer> entry : map.get(chosen_node.city).entrySet()) {
        // Add the connected node only if it has not been explored
        if (!explored.contains(entry.getKey())) {
          frontier.add(
              new Node(entry.getKey(), chosen_node, chosen_node.depth + 1, chosen_node.path_cost + entry.getValue()));
        }
      }
    }

    System.out.println("Cannot find a solution");
  }

  private static void dfs(City start, City goal) {
    System.out.println("Running DFS...");
    Set<City> explored = new HashSet<>();
    Queue<Node> frontier = Collections.asLifoQueue(new LinkedList<>()); // Just changed this line from bfs()
    frontier.add(new Node(start));

    while (!frontier.isEmpty()) {
      // Choose a node
      Node chosen_node = frontier.remove();

      // Check for solution
      if (chosen_node.city == goal) {
        Node backtrack_node = chosen_node;
        List<City> solution = new LinkedList<>();
        solution.add(backtrack_node.city);
        while (backtrack_node.parent != null) {
          backtrack_node = backtrack_node.parent;
          solution.add(0, backtrack_node.city);
        }

        System.out.println("Found a solution: " + solution + " with path cost = " + chosen_node.path_cost);
        return;
      }

      // Add the selected node to the explored set
      explored.add(chosen_node.city);

      // Expand the selected node
      for (Map.Entry<City, Integer> entry : map.get(chosen_node.city).entrySet()) {
        // Add the connected node only if it has not been explored
        if (!explored.contains(entry.getKey())) {
          frontier.add(
              new Node(entry.getKey(), chosen_node, chosen_node.depth + 1, chosen_node.path_cost + entry.getValue()));
        }
      }
    }

    System.out.println("Cannot find a solution");
  }

  private static void dls(City start, City goal, int depth_limit) {
    System.out.println("Running DLS...");
    Set<City> explored = new HashSet<>();
    Queue<Node> frontier = Collections.asLifoQueue(new LinkedList<>());
    frontier.add(new Node(start));

    while (!frontier.isEmpty()) {
      // Choose a node
      Node chosen_node = frontier.remove();

      // Check for solution
      if (chosen_node.city == goal) {
        Node backtrack_node = chosen_node;
        List<City> solution = new LinkedList<>();
        solution.add(backtrack_node.city);
        while (backtrack_node.parent != null) {
          backtrack_node = backtrack_node.parent;
          solution.add(0, backtrack_node.city);
        }

        System.out.println("Found a solution: " + solution + " with path cost = " + chosen_node.path_cost);
        return;
      }

      // Expand the selected node
      if (chosen_node.depth < depth_limit) {  // Just add this line to dfs()
        // Add the selected node to the explored set
        explored.add(chosen_node.city);

        for (Map.Entry<City, Integer> entry : map.get(chosen_node.city).entrySet()) {
          // Add the connected node only if it has not been explored
          if (!explored.contains(entry.getKey())) {
            frontier.add(
                new Node(entry.getKey(), chosen_node, chosen_node.depth + 1, chosen_node.path_cost + entry.getValue()));
          }
        }
      }
    }

    System.out.println("Cannot find a solution");
  }

  private static void ids(City start, City goal) {
    System.out.println("Running IDS...");
    int depth_limit = 0;    // Just add this line to dls()
    while (true) {          // Just add this line to dls()
      Set<City> explored = new HashSet<>();
      Queue<Node> frontier = Collections.asLifoQueue(new LinkedList<>());
      frontier.add(new Node(start));

      while (!frontier.isEmpty()) {
        // Choose a node
        Node chosen_node = frontier.remove();

        // Check for solution
        if (chosen_node.city == goal) {
          Node backtrack_node = chosen_node;
          List<City> solution = new LinkedList<>();
          solution.add(backtrack_node.city);
          while (backtrack_node.parent != null) {
            backtrack_node = backtrack_node.parent;
            solution.add(0, backtrack_node.city);
          }

          System.out.println("Found a solution: " + solution + " with path cost = " + chosen_node.path_cost);
          return;
        }

        // Expand the selected node
        if (chosen_node.depth < depth_limit) {
          // Add the selected node to the explored set
          explored.add(chosen_node.city);

          for (Map.Entry<City, Integer> entry : map.get(chosen_node.city).entrySet()) {
            // Add the connected node only if it has not been explored
            if (!explored.contains(entry.getKey())) {
              frontier.add(new Node(entry.getKey(), chosen_node, chosen_node.depth + 1,
                  chosen_node.path_cost + entry.getValue()));
            }
          }
        }
      }

      depth_limit++;
    }
  }
}
