package org.example;

import org.example.simulation.Simulation;

public class Main {
    public static void main(String[] args) {
        Simulation simulation = new Simulation(20, 20);
        simulation.runInteractive();
    }
}