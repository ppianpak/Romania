import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
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

class Node implements Comparable<Node> {
  City city;
  Node parent;
  int depth;
  int path_cost;
  int f;

  Node(City city) {
    this.city = city;
  }

  Node(City city, Node parent, int depth, int path_cost, int f) {
    this.city = city;
    this.parent = parent;
    this.depth = depth;
    this.path_cost = path_cost;
    this.f = f;
  }

  @Override
  public String toString() {
    String s = this.city + ">";
    Node node = this.parent;
    while (node != null) {
      s = node.city + ", " + s;
      node = node.parent;
    }
    return "<" + s;
  }

  // This is needed for PriorityQueue to work with an object
  @Override
  public int compareTo(Node node) {
    return Integer.valueOf(this.f).compareTo(node.f);
  }
}

public class Romania {

  private static Map<City, Map<City, Integer>> map = new HashMap<>();
  private static Map<City, Map<City, Integer>> heuristic = new HashMap<>();

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

    // Initialize the heuristic
    dest_map = new HashMap<>();
    dest_map.put(City.ORADEA, 0);
    dest_map.put(City.ZERIND, 71);
    dest_map.put(City.ARAD, 146);
    dest_map.put(City.TIMISOARA, 264);
    dest_map.put(City.LUGOJ, 375);
    dest_map.put(City.MEHADIA, 445);
    dest_map.put(City.DOBRETA, 497);
    dest_map.put(City.SIBIU, 151);
    dest_map.put(City.RIMNICU_VILCEA, 231);
    dest_map.put(City.CRAIOVA, 377);
    dest_map.put(City.FAGARAS, 250);
    dest_map.put(City.PITESTI, 328);
    dest_map.put(City.GIURGIU, 519);
    dest_map.put(City.BUCHAREST, 429);
    dest_map.put(City.NEAMT, 835);
    dest_map.put(City.URZICENI, 514);
    dest_map.put(City.IASI, 748);
    dest_map.put(City.VASLUI, 656);
    dest_map.put(City.HIRSOVA, 612);
    dest_map.put(City.EFORIE, 698);
    heuristic.put(City.ORADEA, dest_map);

    dest_map = new HashMap<>();
    dest_map.put(City.ORADEA, 71);
    dest_map.put(City.ZERIND, 0);
    dest_map.put(City.ARAD, 75);
    dest_map.put(City.TIMISOARA, 193);
    dest_map.put(City.LUGOJ, 304);
    dest_map.put(City.MEHADIA, 374);
    dest_map.put(City.DOBRETA, 449);
    dest_map.put(City.SIBIU, 215);
    dest_map.put(City.RIMNICU_VILCEA, 295);
    dest_map.put(City.CRAIOVA, 441);
    dest_map.put(City.FAGARAS, 314);
    dest_map.put(City.PITESTI, 392);
    dest_map.put(City.GIURGIU, 583);
    dest_map.put(City.BUCHAREST, 493);
    dest_map.put(City.NEAMT, 899);
    dest_map.put(City.URZICENI, 578);
    dest_map.put(City.IASI, 812);
    dest_map.put(City.VASLUI, 720);
    dest_map.put(City.HIRSOVA, 676);
    dest_map.put(City.EFORIE, 762);
    heuristic.put(City.ZERIND, dest_map);

    dest_map = new HashMap<>();
    dest_map.put(City.ORADEA, 146);
    dest_map.put(City.ZERIND, 75);
    dest_map.put(City.ARAD, 0);
    dest_map.put(City.TIMISOARA, 118);
    dest_map.put(City.LUGOJ, 229);
    dest_map.put(City.MEHADIA, 299);
    dest_map.put(City.DOBRETA, 374);
    dest_map.put(City.SIBIU, 140);
    dest_map.put(City.RIMNICU_VILCEA, 220);
    dest_map.put(City.CRAIOVA, 366);
    dest_map.put(City.FAGARAS, 239);
    dest_map.put(City.PITESTI, 317);
    dest_map.put(City.GIURGIU, 508);
    dest_map.put(City.BUCHAREST, 418);
    dest_map.put(City.NEAMT, 824);
    dest_map.put(City.URZICENI, 503);
    dest_map.put(City.IASI, 737);
    dest_map.put(City.VASLUI, 645);
    dest_map.put(City.HIRSOVA, 601);
    dest_map.put(City.EFORIE, 687);
    heuristic.put(City.ARAD, dest_map);

    dest_map = new HashMap<>();
    dest_map.put(City.ORADEA, 264);
    dest_map.put(City.ZERIND, 193);
    dest_map.put(City.ARAD, 118);
    dest_map.put(City.TIMISOARA, 0);
    dest_map.put(City.LUGOJ, 111);
    dest_map.put(City.MEHADIA, 181);
    dest_map.put(City.DOBRETA, 256);
    dest_map.put(City.SIBIU, 258);
    dest_map.put(City.RIMNICU_VILCEA, 338);
    dest_map.put(City.CRAIOVA, 376);
    dest_map.put(City.FAGARAS, 357);
    dest_map.put(City.PITESTI, 435);
    dest_map.put(City.GIURGIU, 626);
    dest_map.put(City.BUCHAREST, 536);
    dest_map.put(City.NEAMT, 942);
    dest_map.put(City.URZICENI, 621);
    dest_map.put(City.IASI, 855);
    dest_map.put(City.VASLUI, 763);
    dest_map.put(City.HIRSOVA, 719);
    dest_map.put(City.EFORIE, 805);
    heuristic.put(City.TIMISOARA, dest_map);

    dest_map = new HashMap<>();
    dest_map.put(City.ORADEA, 375);
    dest_map.put(City.ZERIND, 304);
    dest_map.put(City.ARAD, 229);
    dest_map.put(City.TIMISOARA, 111);
    dest_map.put(City.LUGOJ, 0);
    dest_map.put(City.MEHADIA, 70);
    dest_map.put(City.DOBRETA, 145);
    dest_map.put(City.SIBIU, 369);
    dest_map.put(City.RIMNICU_VILCEA, 411);
    dest_map.put(City.CRAIOVA, 265);
    dest_map.put(City.FAGARAS, 468);
    dest_map.put(City.PITESTI, 403);
    dest_map.put(City.GIURGIU, 594);
    dest_map.put(City.BUCHAREST, 504);
    dest_map.put(City.NEAMT, 910);
    dest_map.put(City.URZICENI, 589);
    dest_map.put(City.IASI, 823);
    dest_map.put(City.VASLUI, 731);
    dest_map.put(City.HIRSOVA, 687);
    dest_map.put(City.EFORIE, 773);
    heuristic.put(City.LUGOJ, dest_map);

    dest_map = new HashMap<>();
    dest_map.put(City.ORADEA, 445);
    dest_map.put(City.ZERIND, 374);
    dest_map.put(City.ARAD, 299);
    dest_map.put(City.TIMISOARA, 181);
    dest_map.put(City.LUGOJ, 70);
    dest_map.put(City.MEHADIA, 0);
    dest_map.put(City.DOBRETA, 75);
    dest_map.put(City.SIBIU, 421);
    dest_map.put(City.RIMNICU_VILCEA, 341);
    dest_map.put(City.CRAIOVA, 195);
    dest_map.put(City.FAGARAS, 520);
    dest_map.put(City.PITESTI, 333);
    dest_map.put(City.GIURGIU, 524);
    dest_map.put(City.BUCHAREST, 434);
    dest_map.put(City.NEAMT, 840);
    dest_map.put(City.URZICENI, 519);
    dest_map.put(City.IASI, 753);
    dest_map.put(City.VASLUI, 661);
    dest_map.put(City.HIRSOVA, 617);
    dest_map.put(City.EFORIE, 703);
    heuristic.put(City.MEHADIA, dest_map);

    dest_map = new HashMap<>();
    dest_map.put(City.ORADEA, 497);
    dest_map.put(City.ZERIND, 449);
    dest_map.put(City.ARAD, 374);
    dest_map.put(City.TIMISOARA, 256);
    dest_map.put(City.LUGOJ, 145);
    dest_map.put(City.MEHADIA, 75);
    dest_map.put(City.DOBRETA, 0);
    dest_map.put(City.SIBIU, 346);
    dest_map.put(City.RIMNICU_VILCEA, 266);
    dest_map.put(City.CRAIOVA, 120);
    dest_map.put(City.FAGARAS, 445);
    dest_map.put(City.PITESTI, 258);
    dest_map.put(City.GIURGIU, 449);
    dest_map.put(City.BUCHAREST, 359);
    dest_map.put(City.NEAMT, 765);
    dest_map.put(City.URZICENI, 444);
    dest_map.put(City.IASI, 678);
    dest_map.put(City.VASLUI, 586);
    dest_map.put(City.HIRSOVA, 542);
    dest_map.put(City.EFORIE, 628);
    heuristic.put(City.DOBRETA, dest_map);

    dest_map = new HashMap<>();
    dest_map.put(City.ORADEA, 151);
    dest_map.put(City.ZERIND, 215);
    dest_map.put(City.ARAD, 140);
    dest_map.put(City.TIMISOARA, 258);
    dest_map.put(City.LUGOJ, 369);
    dest_map.put(City.MEHADIA, 421);
    dest_map.put(City.DOBRETA, 346);
    dest_map.put(City.SIBIU, 0);
    dest_map.put(City.RIMNICU_VILCEA, 80);
    dest_map.put(City.CRAIOVA, 226);
    dest_map.put(City.FAGARAS, 99);
    dest_map.put(City.PITESTI, 177);
    dest_map.put(City.GIURGIU, 368);
    dest_map.put(City.BUCHAREST, 278);
    dest_map.put(City.NEAMT, 684);
    dest_map.put(City.URZICENI, 363);
    dest_map.put(City.IASI, 597);
    dest_map.put(City.VASLUI, 505);
    dest_map.put(City.HIRSOVA, 461);
    dest_map.put(City.EFORIE, 547);
    heuristic.put(City.SIBIU, dest_map);

    dest_map = new HashMap<>();
    dest_map.put(City.ORADEA, 231);
    dest_map.put(City.ZERIND, 295);
    dest_map.put(City.ARAD, 220);
    dest_map.put(City.TIMISOARA, 338);
    dest_map.put(City.LUGOJ, 411);
    dest_map.put(City.MEHADIA, 341);
    dest_map.put(City.DOBRETA, 266);
    dest_map.put(City.SIBIU, 80);
    dest_map.put(City.RIMNICU_VILCEA, 0);
    dest_map.put(City.CRAIOVA, 146);
    dest_map.put(City.FAGARAS, 179);
    dest_map.put(City.PITESTI, 97);
    dest_map.put(City.GIURGIU, 288);
    dest_map.put(City.BUCHAREST, 198);
    dest_map.put(City.NEAMT, 604);
    dest_map.put(City.URZICENI, 283);
    dest_map.put(City.IASI, 517);
    dest_map.put(City.VASLUI, 425);
    dest_map.put(City.HIRSOVA, 381);
    dest_map.put(City.EFORIE, 467);
    heuristic.put(City.RIMNICU_VILCEA, dest_map);

    dest_map = new HashMap<>();
    dest_map.put(City.ORADEA, 377);
    dest_map.put(City.ZERIND, 441);
    dest_map.put(City.ARAD, 366);
    dest_map.put(City.TIMISOARA, 376);
    dest_map.put(City.LUGOJ, 265);
    dest_map.put(City.MEHADIA, 195);
    dest_map.put(City.DOBRETA, 120);
    dest_map.put(City.SIBIU, 226);
    dest_map.put(City.RIMNICU_VILCEA, 146);
    dest_map.put(City.CRAIOVA, 0);
    dest_map.put(City.FAGARAS, 325);
    dest_map.put(City.PITESTI, 138);
    dest_map.put(City.GIURGIU, 329);
    dest_map.put(City.BUCHAREST, 239);
    dest_map.put(City.NEAMT, 645);
    dest_map.put(City.URZICENI, 324);
    dest_map.put(City.IASI, 558);
    dest_map.put(City.VASLUI, 466);
    dest_map.put(City.HIRSOVA, 422);
    dest_map.put(City.EFORIE, 508);
    heuristic.put(City.CRAIOVA, dest_map);

    dest_map = new HashMap<>();
    dest_map.put(City.ORADEA, 250);
    dest_map.put(City.ZERIND, 314);
    dest_map.put(City.ARAD, 239);
    dest_map.put(City.TIMISOARA, 357);
    dest_map.put(City.LUGOJ, 468);
    dest_map.put(City.MEHADIA, 520);
    dest_map.put(City.DOBRETA, 445);
    dest_map.put(City.SIBIU, 99);
    dest_map.put(City.RIMNICU_VILCEA, 179);
    dest_map.put(City.CRAIOVA, 325);
    dest_map.put(City.FAGARAS, 0);
    dest_map.put(City.PITESTI, 276);
    dest_map.put(City.GIURGIU, 301);
    dest_map.put(City.BUCHAREST, 211);
    dest_map.put(City.NEAMT, 617);
    dest_map.put(City.URZICENI, 296);
    dest_map.put(City.IASI, 530);
    dest_map.put(City.VASLUI, 438);
    dest_map.put(City.HIRSOVA, 394);
    dest_map.put(City.EFORIE, 480);
    heuristic.put(City.FAGARAS, dest_map);

    dest_map = new HashMap<>();
    dest_map.put(City.ORADEA, 328);
    dest_map.put(City.ZERIND, 392);
    dest_map.put(City.ARAD, 317);
    dest_map.put(City.TIMISOARA, 435);
    dest_map.put(City.LUGOJ, 403);
    dest_map.put(City.MEHADIA, 333);
    dest_map.put(City.DOBRETA, 258);
    dest_map.put(City.SIBIU, 177);
    dest_map.put(City.RIMNICU_VILCEA, 97);
    dest_map.put(City.CRAIOVA, 138);
    dest_map.put(City.FAGARAS, 276);
    dest_map.put(City.PITESTI, 0);
    dest_map.put(City.GIURGIU, 191);
    dest_map.put(City.BUCHAREST, 101);
    dest_map.put(City.NEAMT, 507);
    dest_map.put(City.URZICENI, 186);
    dest_map.put(City.IASI, 420);
    dest_map.put(City.VASLUI, 328);
    dest_map.put(City.HIRSOVA, 284);
    dest_map.put(City.EFORIE, 370);
    heuristic.put(City.PITESTI, dest_map);

    dest_map = new HashMap<>();
    dest_map.put(City.ORADEA, 519);
    dest_map.put(City.ZERIND, 583);
    dest_map.put(City.ARAD, 508);
    dest_map.put(City.TIMISOARA, 626);
    dest_map.put(City.LUGOJ, 594);
    dest_map.put(City.MEHADIA, 524);
    dest_map.put(City.DOBRETA, 449);
    dest_map.put(City.SIBIU, 368);
    dest_map.put(City.RIMNICU_VILCEA, 288);
    dest_map.put(City.CRAIOVA, 329);
    dest_map.put(City.FAGARAS, 301);
    dest_map.put(City.PITESTI, 191);
    dest_map.put(City.GIURGIU, 0);
    dest_map.put(City.BUCHAREST, 90);
    dest_map.put(City.NEAMT, 496);
    dest_map.put(City.URZICENI, 175);
    dest_map.put(City.IASI, 409);
    dest_map.put(City.VASLUI, 317);
    dest_map.put(City.HIRSOVA, 273);
    dest_map.put(City.EFORIE, 359);
    heuristic.put(City.GIURGIU, dest_map);

    dest_map = new HashMap<>();
    dest_map.put(City.ORADEA, 429);
    dest_map.put(City.ZERIND, 493);
    dest_map.put(City.ARAD, 418);
    dest_map.put(City.TIMISOARA, 536);
    dest_map.put(City.LUGOJ, 504);
    dest_map.put(City.MEHADIA, 434);
    dest_map.put(City.DOBRETA, 359);
    dest_map.put(City.SIBIU, 278);
    dest_map.put(City.RIMNICU_VILCEA, 198);
    dest_map.put(City.CRAIOVA, 239);
    dest_map.put(City.FAGARAS, 211);
    dest_map.put(City.PITESTI, 101);
    dest_map.put(City.GIURGIU, 90);
    dest_map.put(City.BUCHAREST, 0);
    dest_map.put(City.NEAMT, 406);
    dest_map.put(City.URZICENI, 85);
    dest_map.put(City.IASI, 319);
    dest_map.put(City.VASLUI, 227);
    dest_map.put(City.HIRSOVA, 183);
    dest_map.put(City.EFORIE, 269);
    heuristic.put(City.BUCHAREST, dest_map);

    dest_map = new HashMap<>();
    dest_map.put(City.ORADEA, 835);
    dest_map.put(City.ZERIND, 899);
    dest_map.put(City.ARAD, 824);
    dest_map.put(City.TIMISOARA, 942);
    dest_map.put(City.LUGOJ, 910);
    dest_map.put(City.MEHADIA, 840);
    dest_map.put(City.DOBRETA, 765);
    dest_map.put(City.SIBIU, 684);
    dest_map.put(City.RIMNICU_VILCEA, 604);
    dest_map.put(City.CRAIOVA, 645);
    dest_map.put(City.FAGARAS, 617);
    dest_map.put(City.PITESTI, 507);
    dest_map.put(City.GIURGIU, 496);
    dest_map.put(City.BUCHAREST, 406);
    dest_map.put(City.NEAMT, 0);
    dest_map.put(City.URZICENI, 321);
    dest_map.put(City.IASI, 87);
    dest_map.put(City.VASLUI, 179);
    dest_map.put(City.HIRSOVA, 419);
    dest_map.put(City.EFORIE, 505);
    heuristic.put(City.NEAMT, dest_map);

    dest_map = new HashMap<>();
    dest_map.put(City.ORADEA, 514);
    dest_map.put(City.ZERIND, 578);
    dest_map.put(City.ARAD, 503);
    dest_map.put(City.TIMISOARA, 621);
    dest_map.put(City.LUGOJ, 589);
    dest_map.put(City.MEHADIA, 519);
    dest_map.put(City.DOBRETA, 444);
    dest_map.put(City.SIBIU, 363);
    dest_map.put(City.RIMNICU_VILCEA, 283);
    dest_map.put(City.CRAIOVA, 324);
    dest_map.put(City.FAGARAS, 296);
    dest_map.put(City.PITESTI, 186);
    dest_map.put(City.GIURGIU, 175);
    dest_map.put(City.BUCHAREST, 85);
    dest_map.put(City.NEAMT, 321);
    dest_map.put(City.URZICENI, 0);
    dest_map.put(City.IASI, 234);
    dest_map.put(City.VASLUI, 142);
    dest_map.put(City.HIRSOVA, 98);
    dest_map.put(City.EFORIE, 184);
    heuristic.put(City.URZICENI, dest_map);

    dest_map = new HashMap<>();
    dest_map.put(City.ORADEA, 748);
    dest_map.put(City.ZERIND, 812);
    dest_map.put(City.ARAD, 737);
    dest_map.put(City.TIMISOARA, 855);
    dest_map.put(City.LUGOJ, 823);
    dest_map.put(City.MEHADIA, 753);
    dest_map.put(City.DOBRETA, 678);
    dest_map.put(City.SIBIU, 597);
    dest_map.put(City.RIMNICU_VILCEA, 517);
    dest_map.put(City.CRAIOVA, 558);
    dest_map.put(City.FAGARAS, 530);
    dest_map.put(City.PITESTI, 420);
    dest_map.put(City.GIURGIU, 409);
    dest_map.put(City.BUCHAREST, 319);
    dest_map.put(City.NEAMT, 87);
    dest_map.put(City.URZICENI, 234);
    dest_map.put(City.IASI, 0);
    dest_map.put(City.VASLUI, 92);
    dest_map.put(City.HIRSOVA, 332);
    dest_map.put(City.EFORIE, 418);
    heuristic.put(City.IASI, dest_map);

    dest_map = new HashMap<>();
    dest_map.put(City.ORADEA, 656);
    dest_map.put(City.ZERIND, 720);
    dest_map.put(City.ARAD, 645);
    dest_map.put(City.TIMISOARA, 763);
    dest_map.put(City.LUGOJ, 731);
    dest_map.put(City.MEHADIA, 661);
    dest_map.put(City.DOBRETA, 586);
    dest_map.put(City.SIBIU, 505);
    dest_map.put(City.RIMNICU_VILCEA, 425);
    dest_map.put(City.CRAIOVA, 466);
    dest_map.put(City.FAGARAS, 438);
    dest_map.put(City.PITESTI, 328);
    dest_map.put(City.GIURGIU, 317);
    dest_map.put(City.BUCHAREST, 227);
    dest_map.put(City.NEAMT, 179);
    dest_map.put(City.URZICENI, 142);
    dest_map.put(City.IASI, 92);
    dest_map.put(City.VASLUI, 0);
    dest_map.put(City.HIRSOVA, 240);
    dest_map.put(City.EFORIE, 326);
    heuristic.put(City.VASLUI, dest_map);

    dest_map = new HashMap<>();
    dest_map.put(City.ORADEA, 612);
    dest_map.put(City.ZERIND, 676);
    dest_map.put(City.ARAD, 601);
    dest_map.put(City.TIMISOARA, 719);
    dest_map.put(City.LUGOJ, 687);
    dest_map.put(City.MEHADIA, 617);
    dest_map.put(City.DOBRETA, 542);
    dest_map.put(City.SIBIU, 461);
    dest_map.put(City.RIMNICU_VILCEA, 381);
    dest_map.put(City.CRAIOVA, 422);
    dest_map.put(City.FAGARAS, 394);
    dest_map.put(City.PITESTI, 284);
    dest_map.put(City.GIURGIU, 273);
    dest_map.put(City.BUCHAREST, 183);
    dest_map.put(City.NEAMT, 419);
    dest_map.put(City.URZICENI, 98);
    dest_map.put(City.IASI, 332);
    dest_map.put(City.VASLUI, 240);
    dest_map.put(City.HIRSOVA, 0);
    dest_map.put(City.EFORIE, 86);
    heuristic.put(City.HIRSOVA, dest_map);

    dest_map = new HashMap<>();
    dest_map.put(City.ORADEA, 698);
    dest_map.put(City.ZERIND, 762);
    dest_map.put(City.ARAD, 687);
    dest_map.put(City.TIMISOARA, 805);
    dest_map.put(City.LUGOJ, 773);
    dest_map.put(City.MEHADIA, 703);
    dest_map.put(City.DOBRETA, 628);
    dest_map.put(City.SIBIU, 547);
    dest_map.put(City.RIMNICU_VILCEA, 467);
    dest_map.put(City.CRAIOVA, 508);
    dest_map.put(City.FAGARAS, 480);
    dest_map.put(City.PITESTI, 370);
    dest_map.put(City.GIURGIU, 359);
    dest_map.put(City.BUCHAREST, 269);
    dest_map.put(City.NEAMT, 505);
    dest_map.put(City.URZICENI, 184);
    dest_map.put(City.IASI, 418);
    dest_map.put(City.VASLUI, 326);
    dest_map.put(City.HIRSOVA, 86);
    dest_map.put(City.EFORIE, 0);
    heuristic.put(City.EFORIE, dest_map);

    // Search
    bfs(City.ARAD, City.EFORIE);

    System.out.println("========");
    dfs(City.ARAD, City.EFORIE);

    System.out.println("========");
    dls(City.ARAD, City.EFORIE, 4);

    System.out.println("========");
    ids(City.ARAD, City.EFORIE);

    System.out.println("========");
    ucs(City.ARAD, City.EFORIE);

    System.out.println("========");
    greedy_best_first_search(City.ARAD, City.EFORIE);

    System.out.println("========");
    a_star_search(City.ARAD, City.EFORIE);
  }

  private static void bfs(City start, City goal) {
    System.out.println("Running BFS...");
    Set<City> explored = new HashSet<>();
    Queue<Node> frontier = new LinkedList<>();
    frontier.add(new Node(start));

    int round = 0;
    while (!frontier.isEmpty()) {
      System.out.print("Round " + (++round) + ": " + frontier);

      // Choose a node
      Node chosen_node = frontier.remove();
      System.out.println(", " + chosen_node + " is chosen");

      // Check for solution
      if (chosen_node.city == goal) {
        Node backtrack_node = chosen_node;
        List<City> solution = new LinkedList<>();
        solution.add(backtrack_node.city);
        while (backtrack_node.parent != null) {
          backtrack_node = backtrack_node.parent;
          solution.add(0, backtrack_node.city);
        }

        System.out.println("Found a solution: " + solution + " with depth = " + chosen_node.depth + ", path cost = "
            + chosen_node.path_cost);
        return;
      }

      // Add the selected node to the explored set
      explored.add(chosen_node.city);

      // Expand the selected node
      for (Map.Entry<City, Integer> entry : map.get(chosen_node.city).entrySet()) {
        // Add the connected node only if it has not been explored
        if (!explored.contains(entry.getKey())) {
          frontier.add(new Node(entry.getKey(), chosen_node, chosen_node.depth + 1,
              chosen_node.path_cost + entry.getValue(), chosen_node.path_cost + entry.getValue()));
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

    int round = 0;
    while (!frontier.isEmpty()) {
      System.out.print("Round " + (++round) + ": " + frontier);

      // Choose a node
      Node chosen_node = frontier.remove();
      System.out.println(", " + chosen_node + " is chosen");

      // Check for solution
      if (chosen_node.city == goal) {
        Node backtrack_node = chosen_node;
        List<City> solution = new LinkedList<>();
        solution.add(backtrack_node.city);
        while (backtrack_node.parent != null) {
          backtrack_node = backtrack_node.parent;
          solution.add(0, backtrack_node.city);
        }

        System.out.println("Found a solution: " + solution + " with depth = " + chosen_node.depth + ", path cost = "
            + chosen_node.path_cost);
        return;
      }

      // Add the selected node to the explored set
      explored.add(chosen_node.city);

      // Expand the selected node
      for (Map.Entry<City, Integer> entry : map.get(chosen_node.city).entrySet()) {
        // Add the connected node only if it has not been explored
        if (!explored.contains(entry.getKey())) {
          frontier.add(new Node(entry.getKey(), chosen_node, chosen_node.depth + 1,
              chosen_node.path_cost + entry.getValue(), chosen_node.path_cost + entry.getValue()));
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

    int round = 0;
    while (!frontier.isEmpty()) {
      System.out.print("Round " + (++round) + ": " + frontier);

      // Choose a node
      Node chosen_node = frontier.remove();
      System.out.println(", " + chosen_node + " is chosen");

      // Check for solution
      if (chosen_node.city == goal) {
        Node backtrack_node = chosen_node;
        List<City> solution = new LinkedList<>();
        solution.add(backtrack_node.city);
        while (backtrack_node.parent != null) {
          backtrack_node = backtrack_node.parent;
          solution.add(0, backtrack_node.city);
        }

        System.out.println("Found a solution: " + solution + " with depth = " + chosen_node.depth + ", path cost = "
            + chosen_node.path_cost);
        return;
      }

      // Add the selected node to the explored set
      explored.add(chosen_node.city);

      // Expand the selected node
      if (chosen_node.depth < depth_limit) {
        for (Map.Entry<City, Integer> entry : map.get(chosen_node.city).entrySet()) {
          // Add the connected node only if it has not been explored
          if (!explored.contains(entry.getKey())) {
            frontier.add(new Node(entry.getKey(), chosen_node, chosen_node.depth + 1,
                chosen_node.path_cost + entry.getValue(), chosen_node.path_cost + entry.getValue()));
          }
        }
      } else if (!frontier.isEmpty()) {
        explored.remove(chosen_node.city);
        while (chosen_node.parent != null) {
          chosen_node = chosen_node.parent;
          if (chosen_node.city == frontier.peek().city) {
            break;
          }
          explored.remove(chosen_node.city);
        }
      }
    }

    System.out.println("Cannot find a solution");
  }

  private static void ids(City start, City goal) {
    System.out.println("Running IDS...");

    int depth_limit = 0;
    while (true) {
      System.out.println("- Depth limit = " + depth_limit);

      Set<City> explored = new HashSet<>();
      Queue<Node> frontier = Collections.asLifoQueue(new LinkedList<>());
      frontier.add(new Node(start));

      int round = 0;
      while (!frontier.isEmpty()) {
        System.out.print("Round " + (++round) + ": " + frontier);

        // Choose a node
        Node chosen_node = frontier.remove();
        System.out.println(", " + chosen_node + " is chosen");

        // Check for solution
        if (chosen_node.city == goal) {
          Node backtrack_node = chosen_node;
          List<City> solution = new LinkedList<>();
          solution.add(backtrack_node.city);
          while (backtrack_node.parent != null) {
            backtrack_node = backtrack_node.parent;
            solution.add(0, backtrack_node.city);
          }

          System.out.println("Found a solution: " + solution + " with depth = " + chosen_node.depth + ", path cost = "
              + chosen_node.path_cost);
          return;
        }

        // Add the selected node to the explored set
        explored.add(chosen_node.city);

        // Expand the selected node
        if (chosen_node.depth < depth_limit) {
          for (Map.Entry<City, Integer> entry : map.get(chosen_node.city).entrySet()) {
            // Add the connected node only if it has not been explored
            if (!explored.contains(entry.getKey())) {
              frontier.add(new Node(entry.getKey(), chosen_node, chosen_node.depth + 1,
                  chosen_node.path_cost + entry.getValue(), chosen_node.path_cost + entry.getValue()));
            }
          }
        } else if (!frontier.isEmpty()) {
          explored.remove(chosen_node.city);
          while (chosen_node.parent != null) {
            chosen_node = chosen_node.parent;
            if (chosen_node.city == frontier.peek().city) {
              break;
            }
            explored.remove(chosen_node.city);
          }
        }
      }

      depth_limit++;
    }
  }

  private static void ucs(City start, City goal) {
    System.out.println("Running UCS...");
    Set<City> explored = new HashSet<>();
    PriorityQueue<Node> frontier = new PriorityQueue<>();
    frontier.add(new Node(start));

    int round = 0;
    while (!frontier.isEmpty()) {
      System.out.print("Round " + (++round) + ": " + frontier);

      // Choose a node
      Node chosen_node = frontier.remove();
      System.out.println(", " + chosen_node + " is chosen");

      // Check for solution
      if (chosen_node.city == goal) {
        Node backtrack_node = chosen_node;
        List<City> solution = new LinkedList<>();
        solution.add(backtrack_node.city);
        while (backtrack_node.parent != null) {
          backtrack_node = backtrack_node.parent;
          solution.add(0, backtrack_node.city);
        }

        System.out.println("Found a solution: " + solution + " with depth = " + chosen_node.depth + ", path cost = "
            + chosen_node.path_cost);
        return;
      }

      // Add the selected node to the explored set
      explored.add(chosen_node.city);

      // Expand the selected node
      for (Map.Entry<City, Integer> entry : map.get(chosen_node.city).entrySet()) {
        // Add the connected node only if it has not been explored
        if (!explored.contains(entry.getKey())) {
          frontier.add(new Node(entry.getKey(), chosen_node, chosen_node.depth + 1,
              chosen_node.path_cost + entry.getValue(), chosen_node.path_cost + entry.getValue()));
        }
      }
    }

    System.out.println("Cannot find a solution");
  }

  private static void greedy_best_first_search(City start, City goal) {
    System.out.println("Running Greedy Best-First Search...");
    Set<City> explored = new HashSet<>();
    PriorityQueue<Node> frontier = new PriorityQueue<>();
    frontier.add(new Node(start));

    int round = 0;
    while (!frontier.isEmpty()) {
      System.out.print("Round " + (++round) + ": " + frontier);

      // Choose a node
      Node chosen_node = frontier.remove();
      System.out.println(", " + chosen_node + " is chosen");

      // Check for solution
      if (chosen_node.city == goal) {
        Node backtrack_node = chosen_node;
        List<City> solution = new LinkedList<>();
        solution.add(backtrack_node.city);
        while (backtrack_node.parent != null) {
          backtrack_node = backtrack_node.parent;
          solution.add(0, backtrack_node.city);
        }

        System.out.println("Found a solution: " + solution + " with depth = " + chosen_node.depth + ", path cost = "
            + chosen_node.path_cost);
        return;
      }

      // Add the selected node to the explored set
      explored.add(chosen_node.city);

      // Expand the selected node
      for (Map.Entry<City, Integer> entry : map.get(chosen_node.city).entrySet()) {
        // Add the connected node only if it has not been explored
        if (!explored.contains(entry.getKey())) {
          frontier.add(new Node(entry.getKey(), chosen_node, chosen_node.depth + 1,
              chosen_node.path_cost + entry.getValue(), heuristic.get(chosen_node.city).get(entry.getKey())));
        }
      }
    }

    System.out.println("Cannot find a solution");
  }

  private static void a_star_search(City start, City goal) {
    System.out.println("Running A* Search...");
    Set<City> explored = new HashSet<>();
    PriorityQueue<Node> frontier = new PriorityQueue<>();
    frontier.add(new Node(start));

    int round = 0;
    while (!frontier.isEmpty()) {
      System.out.print("Round " + (++round) + ": " + frontier);

      // Choose a node
      Node chosen_node = frontier.remove();
      System.out.println(", " + chosen_node + " is chosen");

      // Check for solution
      if (chosen_node.city == goal) {
        Node backtrack_node = chosen_node;
        List<City> solution = new LinkedList<>();
        solution.add(backtrack_node.city);
        while (backtrack_node.parent != null) {
          backtrack_node = backtrack_node.parent;
          solution.add(0, backtrack_node.city);
        }

        System.out.println("Found a solution: " + solution + " with depth = " + chosen_node.depth + ", path cost = "
            + chosen_node.path_cost);
        return;
      }

      // Add the selected node to the explored set
      explored.add(chosen_node.city);

      // Expand the selected node
      for (Map.Entry<City, Integer> entry : map.get(chosen_node.city).entrySet()) {
        // Add the connected node only if it has not been explored
        if (!explored.contains(entry.getKey())) {
          frontier.add(
              new Node(entry.getKey(), chosen_node, chosen_node.depth + 1, chosen_node.path_cost + entry.getValue(),
                  chosen_node.path_cost + entry.getValue() + heuristic.get(chosen_node.city).get(entry.getKey())));
        }
      }
    }

    System.out.println("Cannot find a solution");
  }
}
