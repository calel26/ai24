package com.lawsmat.tube;

public class Runner {
    public static void main(String[] args) {
        var net = new Network();
        var city = new CentralLondon(net);
        city.populate();
    }
}
