package com.lawsmat.graph;

import java.util.*;

class Graph {
    // HashMap to store the adjacency list
    HashMap<Integer, List<Edge>> adjList;

    // Constructor
    public Graph() {
        adjList = new HashMap<>();
    }

    // Function to add an edge to the graph
    public void addEdge(int src, int dest, int weight) {
        // Add edge from src to dest
        //putIfAbsent is used to check if a list of edges already exists for a given node (key).
        //If not, it initializes a new list for that node:
        adjList.putIfAbsent(src, new ArrayList<>());
        adjList.get(src).add(new Edge(dest, weight));

        // Since it's an undirected graph, add an edge from dest to src as well
        adjList.putIfAbsent(dest, new ArrayList<>());
        adjList.get(dest).add(new Edge(src, weight));
    }

    public void addEdgeh(int src, int dest, int weight, int heu) {
        adjList.putIfAbsent(src, new ArrayList<>());
        adjList.get(src).add(new Edge(dest, weight, heu));
        // even though this is an undirected graph, the heuristic value changes based
        // on the direction so we can't add an edge from dest --> src
    }

    // Function to get the adjacency list
    public Map<Integer, List<Edge>> getAdjList() {
        return adjList;
    }

    // Function to print the adjacency list
    public void printGraph() {
        //FYI - used to obtain a set view of the keys contained in the map.
        //This method is useful when you want to iterate over all the keys in the map
        for (int node : adjList.keySet()) {
            System.out.print("Node " + node + " makes an edge with: ");
            //for each edge in the list display the destination and weight
            for (Edge edge : adjList.get(node)) {
                System.out.print(edge.dest + "(" + edge.weight + ") ");
            }
            System.out.println();
        }
    }

    public void dfs(int start, int destination) {
        var stack = new Stack<Integer>();
        var seen = new ArrayList<Integer>();

        stack.push(start);

        while(!stack.isEmpty()) {
            var cur = stack.pop();
            if(!seen.contains(cur)) {
                seen.add(cur);
                if(cur == destination) {
                    System.out.println(seen);
                    return;
                }
            }
            for(var edge : adjList.get(cur)) {
                if(!seen.contains(edge.dest)) {
                    stack.push(edge.dest);
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    public void bfs(int start, int destination) {
        var paths = new LinkedList<ArrayList<Integer>>();
        var queue = new LinkedList<Integer>();
        var seen = new ArrayList<Integer>();

        queue.add(start);
        var a = new ArrayList<Integer>();
        a.add(start);
        paths.add(a);

        while(!queue.isEmpty()) {
            var cur = queue.poll();
            var path = paths.poll();
            if(!seen.contains(cur)) {
                seen.add(cur);
                if(cur == destination) {
                    System.out.println(path);
                    return;
                }
            }
            for(var edge : adjList.get(cur)) {
                if(!seen.contains(edge.dest)) {
                    queue.add(edge.dest);
                    var p = (ArrayList<Integer>) path.clone();
                    p.add(edge.dest);
                    paths.add(p);
                }
            }
        }
    }

    public void ucf(int start, int end) {
        var frontier = new PriorityQueue<Edge>();
        var costSoFar = new HashMap<Integer, Integer>();
        var cameFrom = new HashMap<Integer, Integer>();

        frontier.add(new Edge(start, 0));
        costSoFar.put(start, 0);
        cameFrom.put(start, null);

        frontier_search:
        while(!frontier.isEmpty()) {
            var edge = frontier.poll();
            System.out.println("Looking at " + edge.dest);
            for(var e : adjList.get(edge.dest)) {
                var totalCost = costSoFar.get(edge.dest) + e.weight;
                if(costSoFar.getOrDefault(e.dest, Integer.MAX_VALUE) > totalCost) {
                    costSoFar.put(e.dest, totalCost);
                    cameFrom.put(e.dest, edge.dest);
                    frontier.add(new Edge(e.dest, totalCost));
                }
                if(edge.dest == end) {
                    System.out.println("ending early");
                    break frontier_search;
                }
            }
        }

        printPath(start, end, cameFrom);
    }

    public void astar(int start, int end) {
        var frontier = new PriorityQueue<>(new HeuristicComparator());
        var costSoFar = new HashMap<Integer, Integer>();
        var cameFrom = new HashMap<Integer, Integer>();

        frontier.add(new Edge(start, 0));
        costSoFar.put(start, 0);
        cameFrom.put(start, null);

        frontier_search:
        while(!frontier.isEmpty()) {
            var edge = frontier.poll();
            System.out.println("Looking at " + edge.dest);
            for(var e : adjList.get(edge.dest)) {
                var totalCost = costSoFar.get(edge.dest) + e.weight;
                if(costSoFar.getOrDefault(e.dest, Integer.MAX_VALUE) > totalCost) {
                    costSoFar.put(e.dest, totalCost);
                    cameFrom.put(e.dest, edge.dest);
                    frontier.add(new Edge(e.dest, totalCost, e.heuristic));
                }
                if(edge.dest == end) {
                    System.out.println("ending early");
                    break frontier_search;
                }
            }
        }

        printPath(start, end, cameFrom);
    }

    private static void printPath(int start, int end, HashMap<Integer, Integer> cameFrom) {
        var path = new Stack<Integer>();
        var l = end;
        while (l != start) {
            path.push(l);
            l = cameFrom.get(l);
        }
        path.push(l);
        var revpath = new ArrayList<Integer>();
        while(!path.isEmpty()) {
            revpath.add(path.pop());
        }
        System.out.println(revpath);
    }
}
