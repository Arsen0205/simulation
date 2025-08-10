package org.example.utils;

import org.example.entity.Grass;
import org.example.world.Coordinates;
import org.example.world.World;

import java.util.Random;

public final class WorldUtils {

    private WorldUtils() {}

    public static void spawnRandomGrass(World world) {
        Random random = new Random();
        for (int attempts = 0; attempts < 100; attempts++) {
            int x = random.nextInt(world.getWidth());
            int y = random.nextInt(world.getHeight());
            Coordinates coord = new Coordinates(x, y);

            if (world.getEntity(coord) == null) {
                world.addEntity(coord, new Grass());
                break;
            }
        }
    }
}

