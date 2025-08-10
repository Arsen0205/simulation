package org.example.world;

import org.example.entity.Entity;
import org.example.entity.creature.Creature;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class World {
    private final Map<Coordinates, Entity> entities = new HashMap<>();
    private final int width;
    private final int height;

    public World(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void addEntity(Coordinates coord, Entity entity) {
        entities.put(coord, entity);
    }

    public Entity getEntity(Coordinates coord) {
        return entities.get(coord);
    }

    public void removeEntity(Coordinates coord) {
        entities.remove(coord);
    }

    public Map<Coordinates, Entity> getAllEntities() {
        return entities;
    }

    public void moveEntity(Creature creature, Coordinates from, Coordinates to) {
        removeEntity(from);
        addEntity(to, creature);
        creature.setCoordinates(to);
    }

    public Collection<Entity> collectionEntities(){
        return entities.values();
    }
}
