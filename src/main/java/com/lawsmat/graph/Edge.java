package com.lawsmat.graph;

public class Edge implements Comparable<Edge> {
    int dest;
    int weight;

    Edge(int dest, int weight) {
        this.dest = dest;
        this.weight = weight;
    }

    @Override
    public int compareTo(Edge o) {
        return Integer.compare(weight, o.weight);
    }
}