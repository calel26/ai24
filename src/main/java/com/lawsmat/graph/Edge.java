package com.lawsmat.graph;

import java.awt.*;

public class Edge implements Comparable<Edge> {
    public int dest;
    public int weight;
    public int heuristic;
    public Point point;

    Edge(int dest, int weight) {
        this.dest = dest;
        this.weight = weight;
    }

    public Edge(int d, int w, int heu) {
        this(d, w);
        this.heuristic = heu;
    }

    public Edge(int d, int w, int heu, Point point) {
        this(d, w, heu);
        this.point = point;
    }

    @Override
    public int compareTo(Edge o) {
        return Integer.compare(weight, o.weight);
    }

    public Edge inverse(int newDest) {
        Edge cl = new Edge(this.dest, this.weight, this.heuristic, this.point);
        cl.dest = newDest;
        return cl;
    }
}