package com.lawsmat.graph;

public class AStarRunner {
    public static void main(String[] args) {
        Graph g = new Graph();

        g.addEdgeh(0, 1, 75, 374);
        g.addEdgeh(0, 3, 118, 329);
        g.addEdgeh(0, 4, 140, 253);

        g.addEdgeh(1, 0, 75, 366);
        g.addEdgeh(1, 2, 71, 380);

        g.addEdgeh(2, 1, 71, 374);
        g.addEdgeh(2, 4, 151, 253);

        g.addEdgeh(3, 0, 118, 366);
        g.addEdgeh(3, 5, 111, 244);

        g.addEdgeh(4, 2, 151, 380);
        g.addEdgeh(4, 0, 140, 366);
        g.addEdgeh(4, 8, 80, 193);
        g.addEdgeh(4, 9, 99, 176);

        g.addEdgeh(5, 3, 111, 329);
        g.addEdgeh(5, 6, 70, 241);

        g.addEdgeh(6, 5, 70, 244);
        g.addEdgeh(6, 7, 75, 242);

        g.addEdgeh(7, 6, 75, 241);
        g.addEdgeh(7, 11, 120, 160);

        g.addEdgeh(8, 4, 80, 253);
        g.addEdgeh(8, 11, 146, 160);
        g.addEdgeh(8, 10, 97, 100);

        g.addEdgeh(9, 4, 99, 253);
        g.addEdgeh(9, 12, 211, 0);

        g.addEdgeh(10, 8, 97, 193);
        g.addEdgeh(10, 11, 138, 160);
        g.addEdgeh(10, 12, 101, 0);

        g.addEdgeh(11, 10, 138, 100);
        g.addEdgeh(11, 8, 146, 193);
        g.addEdgeh(11, 7, 120, 242);

        g.addEdgeh(12, 10, 101, 100);
        g.addEdgeh(12, 9, 211, 176);
        g.addEdgeh(12, 13, 85, 80);
        g.addEdgeh(12, 14, 90, 90);

        g.addEdgeh(13, 12, 85, 0);

        g.addEdgeh(14, 12, 90, 0);

        System.out.println("Running A*...");
        g.astar(0, 12);

        System.out.println("Running UCF...");
        g.ucf(0, 12);
    }
}