package org.example.world;

import org.example.entity.Grass;
import org.example.entity.Tree;
import org.example.entity.creature.Creature;
import org.example.entity.creature.Herbivores;
import org.example.entity.creature.Predator;

import java.util.*;

public class World {
    private final int width;  // Ширина мира
    private final int height; // Высота мира

    HashMap<Coordinates, Herbivores> herbivores = new HashMap<>();
    HashMap<Coordinates, Predator> predators = new HashMap<>();
    HashMap<Coordinates, Grass> grass = new HashMap<>();
    HashMap<Coordinates, Tree> tree = new HashMap<>();

    public World(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void setGrass(Coordinates coordinates, Grass herb){
        herb.coordinates = coordinates;
        grass.put(coordinates, herb);
    }

    public void setHerbivores(Coordinates coordinates, Herbivores herbivore){
        herbivore.coordinates = coordinates;
        herbivores.put(coordinates, herbivore);
    }

    public void setPredators(Coordinates coordinates, Predator predator){
        predator.coordinates = coordinates;
        predators.put(coordinates, predator);
    }

    public void setTree(Coordinates coordinates, Tree wood){
        wood.coordinates = coordinates;
        tree.put(coordinates, wood);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public HashMap<Coordinates, Herbivores> getHerbivores() {
        return herbivores;
    }

    public HashMap<Coordinates, Predator> getPredators() {
        return predators;
    }

    public HashMap<Coordinates, Grass> getGrass() {
        return grass;
    }

    public HashMap<Coordinates, Tree> getTree() {
        return tree;
    }


    public void setupDefaultPiecesPositions(){
        Random random = new Random();
        HashSet<Coordinates> used = new HashSet<>();

        //10 камней
        for (int i = 0; i < 10; i++){
            Coordinates coordinates = getRandomEmptyCoordinates(random, used);
            setTree(coordinates, new Tree(coordinates));
        }

        //15 травы
        for (int i = 0; i < 15; i++){
            Coordinates coordinates = getRandomEmptyCoordinates(random, used);
            setGrass(coordinates, new Grass(coordinates));
        }

        //5 травоядных
        for (int i = 0; i < 5; i++){
            Coordinates coordinates = getRandomEmptyCoordinates(random, used);
            setHerbivores(coordinates, new Herbivores(coordinates));
        }

        //3 хищника
        for (int i = 0; i < 3; i++){
            Coordinates coordinates = getRandomEmptyCoordinates(random, used);
            setPredators(coordinates, new Predator(coordinates));
        }
    }

    public void makeAll() {
        List<Herbivores> herbivoresList = new ArrayList<>(herbivores.values());
        for (Herbivores herb : herbivoresList){
            herb.makeMove(this);
        }

        List<Predator> predatorList = new ArrayList<>(predators.values());
        for (Predator predator : predatorList){
            predator.makeMove(this);
        }
    }

    private Coordinates getRandomEmptyCoordinates(Random random, HashSet<Coordinates> used) {
        int x, y;
        Coordinates coordinates;
        do {
            x = random.nextInt(width);
            y = random.nextInt(height);
            coordinates = new Coordinates(x, y);
        } while (used.contains(coordinates));
            used.add(coordinates);
            return coordinates;
        }

    public boolean isSquareAvailableForMove(Creature creature, Coordinates coord) {
        return inBounds(coord) &&
                !tree.containsKey(coord) &&
                (creature instanceof Herbivores || !herbivores.containsKey(coord)) &&
                (creature instanceof Predator || !predators.containsKey(coord));
    }

    public boolean inBounds(Coordinates coord) {
        return coord.x >= 0 && coord.x < width &&
                coord.y >= 0 && coord.y < height;
    }

    //Движение к целям зайца и волка
    public void moveCreature(Creature creature, Coordinates newCoord) {

        if (!isSquareAvailableForMove(creature, newCoord)) {
            throw new IllegalStateException("Невозможно переместиться на занятую клетку");
        }

        // Удаляем со старой позиции
        if (creature instanceof Herbivores) {
            herbivores.remove(creature.coordinates);
        } else if (creature instanceof Predator) {
            predators.remove(creature.coordinates);
        }

        // Обновляем координаты существа
        creature.coordinates = newCoord;

        // Добавляем на новую позицию
        if (creature instanceof Herbivores) {
            herbivores.put(newCoord, (Herbivores) creature);
        } else if (creature instanceof Predator) {
            predators.put(newCoord, (Predator) creature);
        }
    }
}

