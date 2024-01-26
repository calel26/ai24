package com.lawsmat.tube;

import java.util.*;

public class Navigator {
    private final Network n;
    private int steps = 0;
    private int cost = 0;

    public Navigator(Network n) { this.n = n; }

    private void results() {
        String c = cost == 0 ? "N/A" : cost+"";
        System.out.println("(distance -> " + c + " in " + steps + " examinations)");
        cost = 0;
        steps = 0;
    }

    public void navigate(Station from, Station to) {
        System.out.println("A* Path=" + astar(from, to));
        results();
        System.out.println("UCS Path=" + ucs(from, to));
        results();
        System.out.println("BFS Path=" + bfs(from, to));
        results();
        System.out.println("DFS Path=" + dfs(from, to));
        results();
    }

    public ArrayList<Station> astar(Station from, Station to) {
        return ucs_base(from, to, new PriorityQueue<>(
                Comparator.comparingInt(tu -> tu.length() + tu.to().computeHeuristic(to))
        ));
    }

    public ArrayList<Station> ucs(Station from, Station to) {
        return ucs_base(from, to, new PriorityQueue<>());
    }

    private ArrayList<Station> ucs_base(Station from, Station to, PriorityQueue<Tunnel> frontier) {
        var costSoFar = new HashMap<Station, Integer>();
        var cameFrom = new HashMap<Station, Station>();

        frontier.add(new Tunnel(0, from, new ArrayList<>()));
        costSoFar.put(from, 0);
        cameFrom.put(from, null);

        while(!frontier.isEmpty()) {
            var station = frontier.poll().to();
            steps++;
            if(station == to) {
                break;
            }
            for(var tunnel : n.getTunnels(station)) {
                var totalCost = costSoFar.get(station) + tunnel.length();
                if(costSoFar.getOrDefault(tunnel.to(), Integer.MAX_VALUE) > totalCost) {
                    costSoFar.put(tunnel.to(), totalCost);
                    cameFrom.put(tunnel.to(), station);
                    frontier.add(new Tunnel(totalCost, tunnel.to(), tunnel.services()));
                }
            }
        }

        cost = costSoFar.get(to);

        return traverseCameFrom(cameFrom, from, to);
    }

    public ArrayList<Station> dfs(Station start, Station destination) {
        var stack = new Stack<Station>();
        var seen = new ArrayList<Station>();

        stack.push(start);

        while(!stack.isEmpty()) {
            var cur = stack.pop();
            steps++;
            if(!seen.contains(cur)) {
                seen.add(cur);
                if(cur == destination) {
                    break;
                }
            }
            for(var edge : n.getTunnels(cur)) {
                if(!seen.contains(edge.to())) {
                    stack.push(edge.to());
                }
            }
        }

        return seen;
    }

    @SuppressWarnings("unchecked")
    public ArrayList<Station> bfs(Station start, Station destination) {
        var paths = new LinkedList<ArrayList<Station>>();
        var queue = new LinkedList<Station>();
        var seen = new ArrayList<Station>();

        queue.add(start);
        var a = new ArrayList<Station>();
        a.add(start);
        paths.add(a);

        while(!queue.isEmpty()) {
            var cur = queue.poll();
            steps ++;
            var path = paths.poll();
            if(!seen.contains(cur)) {
                seen.add(cur);
                if(cur == destination) {
                    break;
                }
            }
            for(var edge : n.getTunnels(cur)) {
                if(!seen.contains(edge.to())) {
                    queue.add(edge.to());
                    var p = (ArrayList<Station>) path.clone();
                    p.add(edge.to());
                    paths.add(p);
                }
            }
        }

        return seen;
    }

    private ArrayList<Station> traverseCameFrom(HashMap<Station, Station> cameFrom, Station start, Station end) {
        var l = cameFrom.get(end);
        var path = new ArrayList<Station>();
        path.add(end);
        path.add(l);
        while(l != start) {
            l = cameFrom.get(l);
            path.add(l);
        }
        var revpath = new ArrayList<Station>();
        for(int i = path.size() - 1; i >= 0; i--) {
            revpath.add(path.get(i));
        }
        return revpath;
    }
}
