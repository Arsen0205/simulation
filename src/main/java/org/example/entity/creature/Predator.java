package org.example.entity.creature;

import org.example.interfaces.Detection;
import org.example.interfaces.Eating;
import org.example.util.Pathfinding;
import org.example.world.Coordinates;
import org.example.world.World;

import java.util.Random;

public class Predator extends Creature implements Detection, Eating {
    private static final int MAX_DAMAGE = 2;

    public Predator(int vision, int speed ,Coordinates coordinates) {
        super(vision, speed, coordinates);
    }

    public Predator(Coordinates coordinates){
        this(3, 1 ,coordinates);
    }

    //Метод, отвечающее за действие волка
    @Override
    public void makeMove(World world){
        Coordinates targetHerbivores = findNearestTarget(world);

        if (targetHerbivores != null){
            if (coordinates.isAdjacent(targetHerbivores)){
                eat(world, targetHerbivores);
                return;
            }

            Coordinates nextStep = Pathfinding.getNextStep(coordinates, targetHerbivores, world);
            if (nextStep != null && world.isSquareAvailableForMove(this, nextStep)){
                world.moveCreature(this, nextStep);
                return;
            }
        }

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

    //Случайный ход волка
    @Override
    protected void moveRandomly(World world){
        Coordinates oldCoordinates = coordinates;
        Coordinates newCoordinates = findAvailableMove(coordinates, speed, world);

        if (newCoordinates != null){
            System.out.println("Волк ушел с клетки: " + oldCoordinates);
            System.out.println("Волк пришел на клетку: " + newCoordinates);

            world.getPredators().remove(oldCoordinates);
            this.coordinates = newCoordinates;
            world.getPredators().put(newCoordinates, this);
        }
    }

    //Поиск травоядных
    @Override
    public Coordinates findNearestTarget(World world) {
        Coordinates nearest = null;
        int minDistance = Integer.MAX_VALUE;

        for (Coordinates predatorCoord : world.getHerbivores().keySet()) {
            int distance = coordinates.distanceTo(predatorCoord);

            if (distance <= this.vision && distance < minDistance) {
                nearest = predatorCoord;
                minDistance = distance;
            }
        }

        return nearest;
    }

    //Нанесение урона или поедание зайца
    @Override
    public void eat(World world, Coordinates herbCoord) {
        if (world.getHerbivores().containsKey(herbCoord)){
            Herbivores herbivores = world.getHerbivores().get(herbCoord);

            herbivores.setHealth(herbivores.getHealth() - MAX_DAMAGE);
            System.out.printf("Хищник на %s атаковал зайца на %s (урон: %d)%n",
                    this.coordinates, herbCoord, MAX_DAMAGE);

            if (herbivores.getHealth() <= 0){
                world.getHerbivores().remove(herbCoord);
                System.out.println("Заяц на " + herbCoord + " умер");
            }else {
                System.out.println("Заяц выжил, осталось здоровья: " + herbivores.getHealth());
            }
        }
    }
}
