package com.lawsmat.tube;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Network {
    private final HashMap<Station, ArrayList<Tunnel>> adjList = new HashMap<>();

    public void addTunnel(Station s, Tunnel to) {
        adjList.putIfAbsent(s, new ArrayList<>());
        adjList.get(s).add(to);
    }

    public void inverseAllTunnels() {
        // To avoid concurrency modification exception
        var newTunnels = new ArrayList<Tunnel>();
        var inStations = new ArrayList<Station>();
        for(var entry : adjList.entrySet()) {
            var station = entry.getKey();
            var tunnels = entry.getValue();
            for(var tun : tunnels) {
                var inv = tun.inverse(station);
                inStations.add(tun.to());
                newTunnels.add(inv);
            }
        }

        // now, do the processing. classic cs moment
        for(int i = 0; i < newTunnels.size(); i++) {
            var t = newTunnels.get(i);
            var s = inStations.get(i);
            addTunnel(s, t);
        }
    }

    public Set<Station> getStations() {
        return adjList.keySet();
    }

    public ArrayList<Tunnel> getTunnels(Station s) {
        return adjList.getOrDefault(s, new ArrayList<>());
    }
}
