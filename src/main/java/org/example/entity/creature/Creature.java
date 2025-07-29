package org.example.entity.creature;

import org.example.world.Coordinates;
import org.example.world.World;

abstract public class Creature {
    public final int vision;
    public final int speed;
    public Coordinates coordinates;

    public Creature(int vision, int speed, Coordinates coordinates) {
        this.vision = vision;
        this.speed = speed;
        this.coordinates = coordinates;
    }

    public void makeMove(World world){}

    protected abstract Coordinates findAvailableMove(Coordinates coordinates, int speed, World world);

    protected abstract void moveRandomly(World world);
}
