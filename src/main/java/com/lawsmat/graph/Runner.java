package com.lawsmat.graph;

public class Runner {
    public static void main(String[] args) {
        Graph graph = new Graph();
        graph.addEdge(0,1,5);
        graph.addEdge(0,4,6);
        graph.addEdge(1,2,7);
        graph.addEdge(1,3,4);
        graph.addEdge(1,4,9);
        graph.addEdge(2,3,12);
        graph.addEdge(3,4,3);

        graph.printGraph();
    }
}