package org.example.util;

import org.example.world.Coordinates;
import org.example.world.World;

import java.util.*;

public class Pathfinding {
    public static Coordinates getNextStep(Coordinates start, Coordinates goal, World world) {
        if (start == null || goal == null || world == null) {
            return null;
        }

        if (start.equals(goal)) {
            return start;
        }

        Queue<Coordinates> queue = new LinkedList<>();
        Set<Coordinates> visited = new HashSet<>();
        Map<Coordinates, Coordinates> cameFrom = new HashMap<>();

        queue.add(start);
        visited.add(start);

        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

        while (!queue.isEmpty()) {
            Coordinates current = queue.poll();

            for (int[] dir : directions) {
                Coordinates neighbor = new Coordinates(
                        current.x + dir[0],
                        current.y + dir[1]
                );


                if (!isValidMove(neighbor, world, visited)) {
                    continue;
                }

                visited.add(neighbor);
                queue.add(neighbor);
                cameFrom.put(neighbor, current);

                if (neighbor.equals(goal)) {
                    return reconstructPath(start, neighbor, cameFrom);
                }
            }
        }

        return null;
    }

    private static boolean isValidMove(Coordinates coord, World world, Set<Coordinates> visited) {
        return world.inBounds(coord) &&
                !visited.contains(coord) &&
                !world.getTree().containsKey(coord) &&
                !world.getPredators().containsKey(coord);
    }

    private static Coordinates reconstructPath(Coordinates start,
                                               Coordinates goal,
                                               Map<Coordinates, Coordinates> cameFrom) {
        Coordinates current = goal;
        while (!cameFrom.get(current).equals(start)) {
            current = cameFrom.get(current);
        }
        return current;
    }
}