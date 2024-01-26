package com.lawsmat.tube;

public record Station(Location location, String name) {
    public int computeHeuristic(Station dest) {
        return location.dist(dest.location);
    }
}
