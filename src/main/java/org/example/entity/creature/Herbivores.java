package org.example.entity.creature;

import org.example.interfaces.Detection;
import org.example.interfaces.Eating;
import org.example.util.Pathfinding;
import org.example.world.Coordinates;
import org.example.world.World;


import java.util.Random;

public class Herbivores extends Creature implements Detection, Eating {
    private int health;
    private static final int MAX_HEALTH = 5;

    public Herbivores(int vision, int speed, Coordinates coordinates) {
        super(vision, speed, coordinates);
        this.health = MAX_HEALTH;
    }

    public Herbivores(Coordinates coordinates){
        this(5, 2, coordinates);
    }

    public void setHealth(int health) {this.health = health;}

    public int getHealth() {return health;}

    //Метод, отвечающее за действие зайца
    @Override
    public void makeMove(World world) {
        // Если здоровье неполное - ищем траву
        if (this.health < MAX_HEALTH) {
            Coordinates targetGrass = findNearestTarget(world);

            if (targetGrass != null) {
                // Если трава рядом - съедаем
                if (coordinates.isAdjacent(targetGrass)) {
                    eat(world, targetGrass);
                    return;
                }

                // Иначе двигаемся к траве
                Coordinates nextStep = Pathfinding.getNextStep(coordinates, targetGrass, world);
                if (nextStep != null && world.isSquareAvailableForMove(this, nextStep)) {
                    world.moveCreature(this, nextStep);
                    return;
                }
            }
        }

        //Если здоровье полное, то заяц продолжает дальше гулять
        moveRandomly(world);
    }

    //Нахождение доступного перемещения
    @Override
    protected Coordinates findAvailableMove(Coordinates coordinates, int speed, World world) {
        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            int dx = random.nextInt(speed*2 + 1) - speed;
            int dy = random.nextInt(speed*2 + 1) - speed;

            int newX = coordinates.x + dx;
            int newY = coordinates.y + dy;


            if (newX >= 0 && newX < world.getWidth() && newY >= 0 && newY < world.getHeight()) {
                Coordinates newCoords = new Coordinates(newX, newY);
                if (!world.getHerbivores().containsKey(newCoords) &&
                        !world.getPredators().containsKey(newCoords) &&
                        !world.getTree().containsKey(newCoords)) {
                    return newCoords;
                }
            }
        }
        return null;
    }

    //Рандомный ход зайца
    @Override
    protected void moveRandomly(World world) {
        Coordinates oldCoordinates = coordinates;
        Coordinates newCoordinates = findAvailableMove(coordinates, speed, world);

        if (newCoordinates != null){
            System.out.println("Заяц спрыгнул с клетки: " + oldCoordinates);
            System.out.println("Заяц прыгнул на клетку: " + newCoordinates);

            world.getHerbivores().remove(oldCoordinates);
            this.coordinates = newCoordinates;
            world.getHerbivores().put(newCoordinates, this);
        }
    }

    //Поиск травы
    @Override
    public Coordinates findNearestTarget(World world) {
        Coordinates nearest = null;
        int minDistance = Integer.MAX_VALUE;

        for (Coordinates grassCoord : world.getGrass().keySet()) {
            int distance = coordinates.distanceTo(grassCoord);

            if (distance <= this.vision && distance < minDistance) {
                nearest = grassCoord;
                minDistance = distance;
            }
        }

        return nearest;
    }

    //Поедание травы и пополнение здоровья
    @Override
    public void eat(World world, Coordinates herbCoord) {
        world.getGrass().remove(herbCoord);
        this.health = Math.min(this.health + 1, MAX_HEALTH);
        System.out.println("Заяц съел траву на " + herbCoord + ". Здоровье: " + health);
    }
}
