package com.lawsmat.tube;

public record Location(int x, int y) {
    public int dist(Location other) {
        return (int) Math.round(Math.sqrt(Math.pow(x - other.x, 2) + Math.pow(y - other.y, 2)));
    }
}
