package org.example.entity;

import org.example.world.Coordinates;

abstract public class Entity {
    public Coordinates coordinates;

    public Entity(Coordinates coordinates){
        this.coordinates = coordinates;
    }
}
