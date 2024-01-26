package com.lawsmat.graph;

import java.util.Comparator;

public class HeuristicComparator implements Comparator<Edge> {
    @Override
    public int compare(Edge e, Edge eb) {
        return Integer.compare((e.weight + e.heuristic), (eb.weight + eb.heuristic));
    }
}
