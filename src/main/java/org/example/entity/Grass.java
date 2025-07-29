package org.example.entity;

import org.example.world.Coordinates;

public class Grass extends Entity{
    public final int recovery = 1;

    public Grass(Coordinates coordinates) {
        super(coordinates);
    }
}
