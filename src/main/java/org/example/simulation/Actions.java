package org.example.simulation;

import org.example.entity.Entity;
import org.example.entity.creature.Creature;
import org.example.world.World;
import org.example.world.WorldRender;

import java.util.ArrayList;
import java.util.List;

public class Actions {
    private final World world;
    private final WorldRender render;

    public Actions(World world) {
        this.world = world;
        this.render = new WorldRender(world);
    }


    public void initActions(){
        render.render();
    }

    public void turnActions() {
        if (world == null) {
            throw new IllegalArgumentException("World cannot be null");
        }

        List<Entity> entitiesCopy = new ArrayList<>(world.collectionEntities());

        for (Entity entity : entitiesCopy) {
            if (entity instanceof Creature) {
                Creature creature = (Creature) entity;
                try {
                    creature.makeMove(world);
                } catch (Exception e) {
                    System.err.println("Error during creature move: " + e.getMessage());
                }
            }
        }
    }
}
