package org.example.world;

import org.example.entity.Entity;
import org.example.entity.Grass;
import org.example.entity.Tree;
import org.example.entity.creature.Creature;
import org.example.entity.creature.Herbivore;
import org.example.entity.creature.Predator;
import org.example.utils.BoardUtils;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

public class WorldInitializer {
    private final World world;

    public WorldInitializer(World world) {
        this.world = world;
    }

    public void generateWorld(int trees, int grass, int predators, int herbivores) {
        Set<Coordinates> occupied = new HashSet<>();

        placeEntities(c -> new Tree(), trees, occupied);
        placeEntities(c -> new Grass(), grass, occupied);
        placeEntities(Predator::new, predators, occupied);
        placeEntities(Herbivore::new, herbivores, occupied);

    }

    private void placeEntities(Function<Coordinates, Entity> factory, int count, Set<Coordinates> occupied) {
        for (int i = 0; i < count; i++) {
            Coordinates coord = BoardUtils.generateFreeRandomCoordinate(
                    occupied,
                    world.getWidth(),
                    world.getHeight()
            );

            Entity entity = factory.apply(coord);

            if (entity instanceof Creature creature) {
                creature.setCoordinates(coord);
            }

            world.getAllEntities().put(coord, entity);
            occupied.add(coord);
        }
    }
}
