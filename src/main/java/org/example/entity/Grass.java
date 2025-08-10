package org.example.entity;

public class Grass extends Entity {
    private final int RESTORING_HEALTH = 1;

    @Override
    public String getSymbol() {
        return "\uD83C\uDF3F"; // 🌿
    }

    public int getRESTORING_HEALTH() {
        return RESTORING_HEALTH;
    }
}