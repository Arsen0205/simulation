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

        // Очередь для BFS-поиска
        Queue<Coordinates> queue = new LinkedList<>();
        // Отслеживание посещенных клеток
        Set<Coordinates> visited = new HashSet<>();
        // Для восстановления пути
        Map<Coordinates, Coordinates> cameFrom = new HashMap<>();

        queue.add(start);
        visited.add(start);

        // 4 направления движения (без диагоналей)
        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

        while (!queue.isEmpty()) {
            Coordinates current = queue.poll();

            for (int[] dir : directions) {
                Coordinates neighbor = new Coordinates(
                        current.x + dir[0],
                        current.y + dir[1]
                );

                // Пропускаем неподходящие клетки
                if (!isValidMove(neighbor, world, visited)) {
                    continue;
                }

                visited.add(neighbor);
                queue.add(neighbor);
                cameFrom.put(neighbor, current);

                // Если достигли цели
                if (neighbor.equals(goal)) {
                    return reconstructPath(start, neighbor, cameFrom);
                }
            }
        }

        return null; // Путь не найден
    }

    private static boolean isValidMove(Coordinates coord, World world, Set<Coordinates> visited) {
        // Проверяем что клетка:
        // 1. В пределах мира
        // 2. Не посещена
        // 3. Не содержит препятствий (деревья, хищники)
        return world.inBounds(coord) &&
                !visited.contains(coord) &&
                !world.getTree().containsKey(coord) &&
                !world.getPredators().containsKey(coord);
    }

    private static Coordinates reconstructPath(Coordinates start,
                                               Coordinates goal,
                                               Map<Coordinates, Coordinates> cameFrom) {
        Coordinates current = goal;
        // Идем назад от цели к старту
        while (!cameFrom.get(current).equals(start)) {
            current = cameFrom.get(current);
        }
        return current; // Возвращаем первый шаг от старта
    }
}