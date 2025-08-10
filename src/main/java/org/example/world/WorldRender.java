package org.example.world;

import org.example.entity.Entity;

public class WorldRender {
    private final World world;

    private static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    private static final String ANSI_RESET = "\u001B[0m";


    public WorldRender(World world) {
        this.world = world;
    }

    public void render(){
        for (int y = world.getHeight() - 1; y >= 0; y--){
            String line = "";
            for (int x = 0; x < world.getWidth(); x++){
                Coordinates coordinates = new Coordinates(x, y);
                line += getSprite(coordinates);
            }
            line += ANSI_RESET;
            System.out.println(line);
        }
    }

    private String getSprite(Coordinates coordinates) {
        Entity entity = world.getAllEntities().get(coordinates);

        if (entity != null) {
            return ANSI_GREEN_BACKGROUND + formatSymbol(entity.getSymbol());
        } else {
            return ANSI_GREEN_BACKGROUND + formatSymbol(" ");
        }
    }

    private String formatSymbol(String emoji){
        return String.format(" %-2s", emoji);
    }
}
