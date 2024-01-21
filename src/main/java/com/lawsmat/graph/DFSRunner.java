package com.lawsmat.graph;

public class DFSRunner {
    public static void main(String[] args) {
        Graph g = new Graph();

        g.addEdge(0, 1, 75);
        g.addEdge(0, 3, 118);
        g.addEdge(0, 4, 140);

        g.addEdge(1, 2, 71);

        g.addEdge(2, 4, 151);

        g.addEdge(3, 5, 111);


        g.addEdge(4, 8, 80);
        g.addEdge(4, 9, 99);

        g.addEdge(5, 6, 70);


        g.addEdge(6, 7, 75);


        g.addEdge(7, 11, 120);

        g.addEdge(8, 11, 146);
        g.addEdge(8, 10, 97);


        g.addEdge(9, 12, 211);


        g.addEdge(10, 11, 138);
        g.addEdge(10, 12, 101);


        g.addEdge(13, 12, 85);

        g.addEdge(14, 12, 90);

        System.out.println("Following is Depth First Traversal " +
                "(starting from vertex 0 and finding 12)");

        g.dfs(0, 12);
        g.bfs(0, 12);
        g.ucf(0, 12);
    }
}

