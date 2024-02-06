package com.lawsmat.animgraph;


import com.lawsmat.graph.Edge;
import com.lawsmat.graph.Graph;

import java.awt.Point;
import java.util.*;

public class CreateGraph {

    Graph graph = new Graph();
    //6 by 8 grid
    int rows = 6;
    int cols = 8;
    Point startPt = new Point(0, 2);
    int startIdx = 0;
    Point endPt = new Point(5, 0);
    int endIdx = 0;
    int[][] grid = new int[rows][cols];

    ArrayList<Point> walls = new ArrayList<>();

    public CreateGraph() {
        /*
         * populate the adjList with all the edges
         * each edge needs to check north/south/east/west to
         * see if there is an edge to connect to.
         * won't connect if off the grid or the cell is -1
         *
         */
        buildGraph();
    }

    public void buildGraph() {
        graph = new Graph();
        int i = 0;
        // make grid
        for(int r = 0; r < rows; r++) {
            for(int c = 0; c < cols; c++) {
                var found = false;
                for(Point pt : walls) {
                    if(pt.equals(new Point(c, r))) {
                        found = true;
                        break;
                    }
                }
                if(!found) {
                    grid[r][c] = i;
                    if(endPt.x == c && endPt.y == r) {
                        endIdx = i;
                    } else if(startPt.x == c && startPt.y == r) {
                        startIdx = i;
                    }
                    i++;
                } else {
                    grid[r][c] = -1;
                }
            }
        }
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {

                if (grid[r][c] != -1) {
                    //check left
                    if (c > 0) {//make sure we can look left
                        //make edge
                        if (grid[r][c - 1] != -1) {
                            graph.addEdge(grid[r][c], new Edge(grid[r][c - 1], 2, 0, new Point(c - 1, r)));
                        }

                    }
                    //check right
                    if (c < cols - 1) {
                        //make edge
                        if (grid[r][c + 1] != -1) {
                            graph.addEdge(grid[r][c], new Edge(grid[r][c + 1], 2, 0, new Point(c + 1, r)));
                        }

                    }

                    //check up
                    if (r > 0) {
                        //make edge
                        if (grid[r - 1][c] != -1) {
                            graph.addEdge(grid[r][c], new Edge(grid[r-1][c], 2, 0, new Point(c, r - 1)));
                        }
                    }

                    //check left
                    if (r < rows - 1) {
                        //make edge
                        if (grid[r + 1][c] != -1) {
                            graph.addEdge(grid[r][c], new Edge(grid[r+1][c], 2, 0, new Point(c, r + 1)));
                        }

                    }
                }

            }
        }

    }
}
