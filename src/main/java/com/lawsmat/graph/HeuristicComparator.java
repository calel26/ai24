package com.lawsmat.graph;

import java.util.Comparator;
import java.util.function.Function;

public class HeuristicComparator implements Comparator<Edge> {
    private final Function<Edge, Integer> f;

    public HeuristicComparator(Function<Edge, Integer> f) {
        this.f = f;
    }

    @Override
    public int compare(Edge e, Edge eb) {
        var heuA = f.apply(e);
        var heuB = f.apply(eb);
        System.out.println(heuA + " " + heuB);
        return Integer.compare((e.weight + heuA), (eb.weight + heuB));
    }
}
