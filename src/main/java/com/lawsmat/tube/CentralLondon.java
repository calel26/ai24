package com.lawsmat.tube;

import java.awt.*;
import java.util.ArrayList;

public class CentralLondon {
    // unnecessary helper class for peak laziness
    private record NextStop(int t_length, Station st) {}

    private final Network n;

    public CentralLondon(Network n) { this.n = n; }

    // peak laziness
    private static Location l(int x, int y) {
        return new Location(x, y);
    }

    private static NextStop s(int distance, Station st) {
        return new NextStop(distance, st);
    }

    private void route(Line line, Station origin, NextStop ...stations) {
        var st = origin;
        for (NextStop station : stations) {
            var tunnel = new Tunnel(station.t_length, station.st, new ArrayList<>());
            for(var tun : n.getTunnels(st)) {
                if(tun.sameTunnel(tunnel)) {
                    tunnel = tun;
                }
            }
            tunnel.services().add(line);
            n.addTunnel(st, tunnel);
            st = station.st;
        }
    }

    public void populate() {
        var earlsCourt = new Station(l(31, 767), "Earl's Court");
        var gloucesterRd = new Station(l(115, 761), "Gloucester Road");
        var victoria = new Station(l(312, 745), "Victoria");
        var kennington = new Station(l(528, 802), "Kennington");
        var elephantAndCastle = new Station(l(551, 742), "Elephant & Castle");
        var londonBridge = new Station(l(602, 677), "London Bridge");
        var monument = new Station(l(608, 632), "Monument");
        var towerHill = new Station(l(686, 611), "Tower Hill");
        var bank = new Station(l(594, 595), "Bank");
        var moorgate = new Station(l(589, 550), "Moorgate");
        var liverpoolSt = new Station(l(625, 556), "Liverpool Street");
        var kingsXStPancras = new Station(l(403, 445), "King's Cross St. Pancras");
        var euston = new Station(l(361, 468), "Euston");
        var camdenTown = new Station(l(314, 369), "Camden Town");
        var warrenSt = new Station(l(339, 521), "Warren Street");
        var bakerSt = new Station(l(261, 521), "Baker Street");
        var edgwareRd = new Station(l(191, 546), "Edgware Rd");
        var paddington = new Station(l(128, 560), "Paddington");
        var nottingHillGate = new Station(l(28, 628), "Notting Hill Gate");
        var bondStreet = new Station(l(279, 575), "Bond Street");
        var oxfordCircus = new Station(l(313, 570), "Oxford Circus");
        var tottenhamCourtRd = new Station(l(361, 563), "Tottenham Court Road");
        var holborn = new Station(l(428, 558), "Holborn");
        var leicesterSq = new Station(l(388, 597), "Leicester Square");
        var piccadillyCircus = new Station(l(354, 619), "Piccadilly Circus");
        var embankment = new Station(l(426, 645), "Embankment");
        var westminster = new Station(l(406, 681), "Westminster");
        var waterloo = new Station(l(476, 678), "Waterloo");
        var greenPark = new Station(l(295, 660), "Green Park");

        var northern = new Line("Northern", Color.BLACK);
        var jubilee = new Line("Jubilee", Color.LIGHT_GRAY);
        var central = new Line("Central", Color.RED);
        var circle = new Line("Circle", Color.YELLOW);
        var district = new Line("District", Color.GREEN);
        var piccadilly = new Line("Piccadilly", Color.BLUE);
        var victoriaLn = new Line("Victoria", Color.BLUE.brighter());
        var metropolitan = new Line("Metropolitan", Color.MAGENTA.darker());
        var hammersmith = new Line("Hammersmith & City", Color.MAGENTA.brighter());
        var bakerloo = new Line("Bakerloo", Color.ORANGE.darker());

        // -- Northern Line
        // Charing Cross Branch
        route(northern,
                camdenTown,
                s(276, euston),
                s(191, warrenSt),
                s(140, tottenhamCourtRd),
                s(140, leicesterSq),
                s(200, embankment),
                s(150, waterloo),
                s(400, kennington)
        );

        // Bank Branch
        route(northern,
                camdenTown,
                s(409, euston),
                s(835, moorgate),
                s(150, bank),
                s(165, londonBridge),
                s(250, elephantAndCastle),
                s(240, kennington)
        );

        // Jubilee Line
        route(jubilee,
                bakerSt,
                s(170, bondStreet),
                s(268, greenPark),
                s(380, westminster),
                s(210, waterloo),
                s(410, londonBridge)
        );

        // Central Line
        route(central,
                liverpoolSt,
                s(170, bank),
                s(560, holborn),
                s(200, tottenhamCourtRd),
                s(170, oxfordCircus),
                s(90, bondStreet),
                s(800, nottingHillGate)
        );

        // Circle Line
        route(circle,
                paddington,
                s(220, edgwareRd),
                s(220, bakerSt),
                s(514, kingsXStPancras),
                s(714, moorgate),
                s(100, liverpoolSt),
                s(300, towerHill),
                s(250, monument),
                s(650, embankment),
                s(150, westminster),
                s(350, victoria),
                s(650, gloucesterRd),
                s(450, nottingHillGate),
                s(480, paddington)
        );

        // District Line (earl's court -> edgware road)
        route(district,
                earlsCourt,
                s(450, nottingHillGate),
                s(480, paddington),
                s(220, edgwareRd)
        );

        // -> tower hill branch
        route(district,
                earlsCourt,
                s(250, gloucesterRd),
                s(650, victoria),
                s(350, westminster),
                s(150, embankment),
                s(650, monument),
                s(250, towerHill)
        );

        // Piccadilly Line
        route(piccadilly,
                kingsXStPancras,
                s(390, holborn),
                s(200, leicesterSq),
                s(140, piccadillyCircus),
                s(170, greenPark),
                s(850, gloucesterRd),
                s(250, earlsCourt)
        );

        // Victoria Line
        route(victoriaLn,
                kingsXStPancras,
                s(166, euston),
                s(178, warrenSt),
                s(160, oxfordCircus),
                s(250, greenPark),
                s(325, victoria)
        );

        // Metropolitan Line
        route(metropolitan,
                bakerSt,
                s(514, kingsXStPancras),
                s(714, moorgate),
                s(100, liverpoolSt)
        );

        // Hammersmith & City Line
        route(hammersmith,
                paddington,
                s(220, edgwareRd),
                s(220, bakerSt),
                s(514, kingsXStPancras),
                s(714, moorgate),
                s(100, liverpoolSt)
        );

        // Bakerloo Line
        route(bakerloo,
                paddington,
                s(483, bakerSt),
                s(227, oxfordCircus),
                s(220, piccadillyCircus),
                s(250, waterloo),
                s(450, elephantAndCastle)
        );

        n.inverseAllTunnels();

        // done setup
        var nav = new Navigator(n);
        nav.navigate(victoria, camdenTown);
    }
}
