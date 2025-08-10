package org.example.entity.creature;

import org.example.utils.Pathfinding;
import org.example.world.Coordinates;
import org.example.world.World;
import org.example.entity.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

abstract public class Creature extends Entity {
    private Coordinates coordinates;
    private final int speed;
    private final int vision;

    public Creature(int speed, int vision, Coordinates coordinates) {
        this.coordinates = coordinates;
        this.speed = speed;
        this.vision = vision;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public int getSpeed() {
        return speed;
    }

    public int getVision() {
        return vision;
    }

    public void makeMove(World world) {
        if (attackAdjacent(world)) return;

        List<Coordinates> path = Pathfinding.find(world, getCoordinates(), getTargetClass(), getVision());
        if (path.size() > 1) {
            int steps = Math.min(getSpeed(), path.size() - 1);
            for (int i = 1; i <= steps; i++) {
                Coordinates next = path.get(i);
                Entity target = world.getEntity(next);
                if (target == null) {
                    world.moveEntity(this, getCoordinates(), next);
                } else {
                    break;
                }
            }
        } else {
            makeRandomMove(world);
        }
    }


    private boolean attackAdjacent(World world) {
        for (Coordinates shift : Pathfinding.SHIFTS) {
            Coordinates targetCoord = new Coordinates(getCoordinates().x() + shift.x(), getCoordinates().y() + shift.y());
            if (!Pathfinding.isInsideWorld(targetCoord, world)) continue;

            Entity target = world.getEntity(targetCoord);
            if (target != null && getTargetClass().isInstance(target)) {
                handleInteraction(world, targetCoord, target);
                return true;
            }
        }
        return false;
    }



    private void makeRandomMove(World world) {
        for (int step = 0; step < getSpeed(); step++) {
            List<Coordinates> possibleMoves = new ArrayList<>();
            for (Coordinates shift : Pathfinding.SHIFTS) {
                Coordinates next = new Coordinates(getCoordinates().x() + shift.x(), getCoordinates().y() + shift.y());
                if (!Pathfinding.isInsideWorld(next, world)) continue;
                if (world.getEntity(next) == null) {
                    possibleMoves.add(next);
                }
            }

            if (possibleMoves.isEmpty()) break;

            Coordinates nextStep = possibleMoves.get(new Random().nextInt(possibleMoves.size()));
            world.moveEntity(this, getCoordinates(), nextStep);
        }
    }


    protected abstract Class<? extends Entity> getTargetClass();

    protected abstract void handleInteraction(World world, Coordinates targetCoord, Entity target);

}
