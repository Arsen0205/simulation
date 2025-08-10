package org.example.entity.creature;

import org.example.utils.WorldUtils;
import org.example.entity.Entity;
import org.example.entity.Grass;
import org.example.world.Coordinates;
import org.example.world.World;

public class Herbivore extends Creature{
    private int health = 5;

    public Herbivore(Coordinates coordinates) {
        super(2, 5, coordinates);
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    @Override
    public String getSymbol() {
        return "\uD83D\uDC07";
    }

    @Override
    protected Class<? extends Entity> getTargetClass(){
        return Grass.class;
    }

    @Override
    protected void handleInteraction(World world, Coordinates targetCoord, Entity target) {
        if (target instanceof Grass) {
            world.removeEntity(targetCoord);
            health += ((Grass) target).getRESTORING_HEALTH();
            System.out.println("Заяц съел траву, его здоровье: " + health);
            world.moveEntity(this, getCoordinates(), targetCoord);
            WorldUtils.spawnRandomGrass(world);
        } else {
            world.moveEntity(this, getCoordinates(), targetCoord);
            System.out.println("Заяц не нашел траву и переместился на клетку: " + getCoordinates());
        }
    }
}
