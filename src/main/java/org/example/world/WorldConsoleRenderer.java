package org.example.world;

public class WorldConsoleRenderer {
    private final World world;

    private static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String HARE_SYMBOL = "\uD83D\uDC07";
    private static final String WOLF_SYMBOL = "\uD83D\uDC3A";
    private static final String SYMBOL_GRASS = "\uD83C\uDF3F";
    private static final String SYMBOL_TREES = "\uD83C\uDF32";

    public WorldConsoleRenderer(World world) {
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
        String symbol = "  ";

        if (world.getTree().containsKey(coordinates)) {
            symbol = formatSymbol(SYMBOL_TREES);
        } else if (world.getPredators().containsKey(coordinates)) {
            symbol = formatSymbol(WOLF_SYMBOL);
        } else if (world.getHerbivores().containsKey(coordinates)) {
            symbol = formatSymbol(HARE_SYMBOL);
        } else if (world.getGrass().containsKey(coordinates)) {
            symbol =  formatSymbol(SYMBOL_GRASS);
        } else {
            symbol = formatSymbol(" ");
        }

        return ANSI_GREEN_BACKGROUND + symbol;
    }

    private String formatSymbol(String emoji){
        return String.format(" %-2s", emoji);
    }


}
