package com.lawsmat.graph;

public class Edge implements Comparable<Edge> {
    int dest;
    int weight;
    int heuristic;

    Edge(int dest, int weight) {
        this.dest = dest;
        this.weight = weight;
    }

    public Edge(int d, int w, int heu) {
        this(d, w);
        this.heuristic = heu;
    }

    @Override
    public int compareTo(Edge o) {
        return Integer.compare(weight, o.weight);
    }
}