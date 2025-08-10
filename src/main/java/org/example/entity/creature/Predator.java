package org.example.entity.creature;

import org.example.entity.Entity;
import org.example.world.Coordinates;
import org.example.world.World;

public class Predator extends Creature{
    private final int damage = 2;

    public Predator(Coordinates coordinates) {
        super(5, 10, coordinates);
    }

    @Override
    public String getSymbol() {
        return "\uD83D\uDC3A"; // 🐺
    }


    @Override
    protected Class<? extends Entity> getTargetClass(){
        return Herbivore.class;
    }

    @Override
    protected void handleInteraction(World world, Coordinates targetCoord, Entity target) {
        if (target instanceof Herbivore herbivore) {
            herbivore.setHealth(herbivore.getHealth() - damage);
            System.out.println("Волк атакует зайца, здоровье: " + herbivore.getHealth());

            if (herbivore.getHealth() <= 0) {
                world.removeEntity(targetCoord);
                Coordinates oldCoord = getCoordinates();
                world.moveEntity(this, oldCoord, targetCoord);
                setCoordinates(targetCoord);

                System.out.println("Заяц погиб, волк встал на его клетку");
            }
        }
    }

}
