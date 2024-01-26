package com.lawsmat.tube;

import java.util.ArrayList;

public record Tunnel(int length, Station to, ArrayList<Line> services) implements Comparable<Tunnel> {
    public Tunnel inverse(Station other) {
        return new Tunnel(length, other, services);
    }

    public boolean sameTunnel(Tunnel other) {
        return this.length == other.length && this.to == other.to;
    }

    @Override
    public int compareTo(Tunnel o) {
        return Integer.compare(length, o.length);
    }
}
