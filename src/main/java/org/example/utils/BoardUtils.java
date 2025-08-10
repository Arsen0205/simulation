package org.example.utils;

import org.example.world.Coordinates;

import java.util.Random;
import java.util.Set;

public final class BoardUtils {
    private BoardUtils(){}

    public static Coordinates generateFreeRandomCoordinate(Set<Coordinates> used, int width, int height) {
        Random rand = new Random();
        Coordinates coord;
        do {
            int x = rand.nextInt(width);
            int y = rand.nextInt(height);
            coord = new Coordinates(x, y);
        } while (used.contains(coord));
        return coord;
    }
}
