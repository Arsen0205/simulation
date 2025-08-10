package org.example.utils;

import org.example.entity.Entity;
import org.example.entity.Tree;
import org.example.entity.creature.Creature;
import org.example.entity.creature.Herbivore;
import org.example.entity.creature.Predator;
import org.example.world.Coordinates;
import org.example.world.World;

import java.util.*;

public final class Pathfinding {

    public static final List<Coordinates> SHIFTS = List.of(
            new Coordinates(0, 1),
            new Coordinates(1, 0),
            new Coordinates(0, -1),
            new Coordinates(-1, 0)
    );

    private Pathfinding() {}

    public static List<Coordinates> find(World world, Coordinates start, Class<? extends Entity> targetClass, int visionRadius) {
        Queue<List<Coordinates>> queue = new LinkedList<>();
        Set<Coordinates> visited = new HashSet<>();

        queue.add(List.of(start));
        visited.add(start);

        while (!queue.isEmpty()) {
            List<Coordinates> path = queue.poll();
            Coordinates current = path.get(path.size() - 1);

            Entity entity = world.getEntity(current);
            if (entity != null && targetClass.isInstance(entity)) {
                return path;
            }

            for (Coordinates shift : SHIFTS) {
                Coordinates next = new Coordinates(current.x() + shift.x(), current.y() + shift.y());

                if (!isInsideWorld(next, world)) continue;
                if (visited.contains(next)) continue;

                Entity atNext = world.getEntity(next);
                if (atNext instanceof Tree || (atNext != null && !targetClass.isInstance(atNext))) continue;

                if (distance(start, next) > visionRadius) continue;

                visited.add(next);
                List<Coordinates> newPath = new ArrayList<>(path);
                newPath.add(next);
                queue.add(newPath);
            }
        }

        return Collections.emptyList();
    }

    public static boolean isInsideWorld(Coordinates coord, World world) {
        return coord.x() >= 0 && coord.x() < world.getWidth()
                && coord.y() >= 0 && coord.y() < world.getHeight();
    }

    private static int distance(Coordinates a, Coordinates b) {
        return Math.abs(a.x() - b.x()) + Math.abs(a.y() - b.y());
    }
}
